package com.yieldigit.authapp.repositories.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.yieldigit.authapp.models.user.User;

@Repository
public interface UserRepository extends JpaRepository<User,Integer>{
    
    User findByUsername(String username);

}
