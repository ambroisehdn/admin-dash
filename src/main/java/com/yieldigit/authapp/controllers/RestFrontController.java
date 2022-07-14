package com.yieldigit.authapp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping(value = "file")
    public File addFile(@RequestBody File file) {
        return fileRepository.save(file);
    }
    
}
