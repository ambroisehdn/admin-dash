package com.yieldigit.authapp.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.yieldigit.authapp.models.file.File;
import com.yieldigit.authapp.models.user.Role;
import com.yieldigit.authapp.models.user.User;
import com.yieldigit.authapp.repositories.file.FileRepository;
import com.yieldigit.authapp.repositories.user.RoleRepository;
import com.yieldigit.authapp.services.user.UserService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api")
public class RestFrontController {
    

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    FileRepository fileRepository;

    @Autowired
    UserService userService;

    private final String UPLOAD_DIR = "./src/main/resources/storages/";
    @PostMapping(value = "role")
    public Role addRole(@RequestBody Role role) {
        return roleRepository.save(role);
    }

    @GetMapping(value = "role")
    public List<Role> getRole() {
        return roleRepository.findAll();
    }

    @GetMapping(value = "role/{id}")
    public Role getRoleById(@PathVariable int id) {
        return roleRepository.findById(id).get();
    }

    @DeleteMapping(value = "role/{id}")
    public void deleteRole(@PathVariable int id) {
        roleRepository.deleteById(id);

    }

    @PostMapping(value = "role/{id}")
    public Role updateUser(@RequestBody Role role, @PathVariable int id) {
        Role roleToUpdate = roleRepository.findById(id).get();
        roleToUpdate.setRolename(role.getRolename());
        return roleRepository.save(roleToUpdate);
    }

    @PostMapping(value = "user")
    public User addUser(@RequestBody User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        return userService.addUser(user);
    }

    @GetMapping(value = "user")
    public List<User> getUser() {
        return userService.getUser();
    }

    @GetMapping(value = "user/{id}")
    public User getUserById(@PathVariable int id) {
        return userService.getUserById(id);
    }
    
    @DeleteMapping(value = "user/{id}")
    public void deleteUser(@PathVariable int id) {
        userService.deleteUser(id);

    }
    
    @PostMapping(value="user/{id}")
    public User updateUser(@RequestBody User user, @PathVariable int id) {
        return userService.updateUser(user, id);
    }
    
    @GetMapping(value = "file")
    public List<File> getFile() {
        return fileRepository.findAll();
    }

    @GetMapping(value = "file/{id}")
    public File getFileById(@PathVariable int id) {
        return fileRepository.findById(id).get();
    }

    @PostMapping(value = "file", consumes = "multipart/form-data")
    public File addFile(@RequestParam MultipartFile file,@RequestParam String name) throws IOException,Exception {

        Path uploadPath = Paths.get(UPLOAD_DIR);
        // Create the directory if it does not exist
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        //check if the file is empty
        if (file.isEmpty()) {
            throw new Exception("File is empty");
        }


        // normalize the file path
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String newFileName = java.util.UUID.randomUUID().toString();
        String extension = fileName.substring(fileName.lastIndexOf("."));
        String newFileNameWithExtension = newFileName + extension;
        
        // save the file on the local file system
        try {
            // Path path = Paths.get(UPLOAD_DIR + fileName);
            // Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            Path targetLocation = uploadPath.resolve(newFileNameWithExtension);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        File newFile = new File();
        newFile.setName(name);
        newFile.setPath(newFileNameWithExtension);
        return fileRepository.save(newFile);
         
    }

    @DeleteMapping(value = "file/{id}")
    public void deleteFile(@PathVariable int id) {
        File fileToDelete = fileRepository.findById(id).get();
        Path path = Paths.get(UPLOAD_DIR + fileToDelete.getPath());
        try {
            Files.delete(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        fileRepository.deleteById(id);

    }
    
}
