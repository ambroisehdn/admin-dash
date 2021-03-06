package com.yieldigit.authapp.services.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.yieldigit.authapp.models.user.User;
import com.yieldigit.authapp.repositories.user.UserRepository;

@Service
public class UserService implements UserDetailsService{
    
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        UserDetailsPrincipal userDetailsPrincipal = new UserDetailsPrincipal(user);
        return userDetailsPrincipal;
    }


    public User addUser(User user) {
        return userRepository.save(user);
    }


    public List<User> getUser() {
        return userRepository.findAll();
    }

    
    public User getUserById(int id) {
        return userRepository.findById(id).get();
    }


    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

    public User updateUser(User user, int id) {
        User userToUpdate = userRepository.findById(id).get();

        userToUpdate.setUsername(user.getUsername());
        userToUpdate.setPassword(user.getPassword());
        userToUpdate.setRole(user.getRole());
        return userRepository.save(userToUpdate);
    }

}
