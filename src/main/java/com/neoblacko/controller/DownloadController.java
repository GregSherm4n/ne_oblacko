package com.neoblacko.controller;

import com.neoblacko.service.FileService;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
public class DownloadController {

    @Autowired
    @Qualifier("fileServiceImpl")
    private FileService fileService;

    @GetMapping("/download/{id}")
    private ResponseEntity<byte[]> download(@PathVariable(name = "id") UUID id) throws IOException {
        var path = Paths.get(fileService.getFileByUUID(id).getPath().trim());
        var content = Files.readAllBytes(path);

        var tika = new Tika();
        var mimeType = tika.detect(path);

        var httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; file=" + "qwe.png");
        httpHeaders.add(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate");
        httpHeaders.add(HttpHeaders.PRAGMA, "no-cache");
        httpHeaders.add(HttpHeaders.EXPIRES, "0");

        return ResponseEntity.ok().headers(httpHeaders).contentLength(content.length)
                .contentType(MediaType.parseMediaType(mimeType)).body(content);

    }

}
