package com.neoblacko.service;

import com.neoblacko.model.UserFile;
import com.neoblacko.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileRepository fileRepository;

    @Override
    public UserFile addFile(UserFile file) {
        return fileRepository.saveAndFlush(file);
    }

    @Override
    public List<UserFile> getAllFiles() {
        return fileRepository.findAll();
    }

    @Override
    public UserFile updateFile(UserFile file) {
        var updatedFile = fileRepository.saveAndFlush(file);
        return updatedFile;
    }

    @Override
    public UserFile getFileById(Integer id) {
        return fileRepository.findById(id).orElse(null);
    }

    @Override
    public UserFile getFileByUUID(UUID id) {
        return fileRepository.findByFileUUID(id);
    }

    @Override
    public void deleteFile(UserFile file) {
        fileRepository.delete(file);
    }
}
