package com.neoblacko.repository;

import com.neoblacko.model.UserFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FileRepository extends JpaRepository<UserFile,Integer> {
    UserFile findByFileUUID(UUID id);
}
