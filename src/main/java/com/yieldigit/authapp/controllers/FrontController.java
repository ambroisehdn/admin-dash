package com.yieldigit.authapp.controllers;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import java.net.MalformedURLException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.yieldigit.authapp.models.user.Role;
import com.yieldigit.authapp.repositories.user.RoleRepository;
import com.yieldigit.authapp.repositories.user.UserRepository;

@Controller
public class FrontController {
    
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;
    
    private final String UPLOAD_DIR = "./storages/";

    public String logedUserName(HttpServletRequest request) {
        return  request.getUserPrincipal().getName();
    }

    @GetMapping( "/")
    public String adminHome(Model model, HttpServletRequest request) {
        String user = this.logedUserName(request);
        String userCount = Long.toString(userRepository.count());
        model.addAttribute("userCount", userCount);
        model.addAttribute("username", user);
       
        return "pages/admin/home";
    }

    @GetMapping( "/user")
    public String userManager(Model model, HttpServletRequest request) {
        String user = this.logedUserName(request);
        List<Role> roles = roleRepository.findAll();
        model.addAttribute("username", user);
        model.addAttribute("roles", roles);
        return "pages/admin/user/index";
    }

    @GetMapping("/role")
    public String roleManager(Model model, HttpServletRequest request) {
        String user = this.logedUserName(request);
        model.addAttribute("username", user);
        return "pages/admin/role/index";
    }

    @GetMapping("/file")
    public String fileManager(Model model, HttpServletRequest request) {
        String user = this.logedUserName(request);
        model.addAttribute("username", user);
        return "pages/admin/file/index";
    }

    @GetMapping("/storages/{filename:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename, HttpServletRequest request)
            throws IOException {
        String contentType = null;
        Resource resource;
        try {

            Path fileInStorage = Paths.get(UPLOAD_DIR).resolve(filename);
            resource = new UrlResource(fileInStorage.toUri());

        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
        contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        // Try to determine file's content type

        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

}
