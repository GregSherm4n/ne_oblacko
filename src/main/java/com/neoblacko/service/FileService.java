package com.neoblacko.service;

import com.neoblacko.model.UserFile;

import java.util.List;
import java.util.UUID;

public interface FileService {

    UserFile addFile(UserFile file);
    List<UserFile> getAllFiles();
    UserFile updateFile(UserFile file);
    UserFile getFileById (Integer id);
    UserFile getFileByUUID (UUID id);
    void deleteFile(UserFile file);


}
