package com.neoblacko.controller;


import com.neoblacko.model.UserFile;
import com.neoblacko.service.FileService;
import com.neoblacko.service.UserService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Controller
@RequestMapping("/file")
@Secured({ "ROLE_admin", "ROLE_user" })
public class UploadController {

    @Autowired
    @Qualifier("fileServiceImpl")
    private FileService fileService;

    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;

    @Value("${cloud.folder.root}")
    private String cloudFolder;

    @GetMapping("/upload")
    public String uploadPage(Model model) {
        return "upload";
    }

    @PostMapping("/upload")
    public String sendFile(@RequestParam(name = "uploadFile") MultipartFile file,
                           @ModelAttribute UserFile userFile, Authentication authentication, RedirectAttributes redirectAttributes) throws IOException {
        var extension = FilenameUtils.getExtension(file.getOriginalFilename());
        var currentUser = userService.getUser(authentication.getName());

        File userDirectory = new File(cloudFolder + File.separator + currentUser.getUserId());
        userDirectory.mkdir();

        var y = FileUtils.sizeOfDirectory(userDirectory);
        var x = currentUser.getTariff().getTariffLimit();

        if ( y > x*1048576) {
            redirectAttributes.addAttribute("error", "Нет свободного места. Лимит исчерпан.");
            return "redirect:/home";
        }


        var transferFile = File.createTempFile("neoblacko_", "_temp." + extension, userDirectory);
        var zipFileName = FilenameUtils.removeExtension(transferFile.getName()) + ".zip";

        file.transferTo(transferFile);
        try (var fileOut = new FileOutputStream(new File(userDirectory, zipFileName));
             var zipFileOut = new ZipOutputStream(fileOut)) {

            var transferZipFile = new File(userDirectory, zipFileName);
            zipFileOut.putNextEntry(new ZipEntry(file.getOriginalFilename()));
            zipFileOut.write(Files.readAllBytes(Paths.get(transferFile.getPath())));
            zipFileOut.closeEntry();

            File tempFileInDir = new File(transferFile.getPath());
            tempFileInDir.delete();

            userFile.setUser(currentUser);
            userFile.setFileName(transferZipFile.getName());
            userFile.setPath(transferZipFile.getCanonicalPath());
            userFile.setSize((int) transferZipFile.length() / 1024);
            userFile.setFileUUID(UUID.randomUUID());

            fileService.addFile(userFile);
        }

        return "redirect:/home";
    }

    @PostMapping("/update/{id}")
    public String changeFileUUID(@PathVariable(name = "id") UUID id) {

        var file = fileService.getFileByUUID(id);
        file.setFileUUID(UUID.randomUUID());

        fileService.updateFile(file);

        return "redirect:/home";
    }

    @PostMapping("/delete/{id}")
    public String deleteFileById(@PathVariable(name = "id") UUID id, Authentication authentication) throws IOException {
        File file = new File(fileService.getFileByUUID(id).getPath());
        file.delete();

        fileService.deleteFile(fileService.getFileByUUID(id));

        return "redirect:/home";
    }

}
