package com.yieldigit.authapp.repositories.file;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yieldigit.authapp.models.file.File;

@Repository
public interface FileRepository extends JpaRepository<File, Integer> {

}
