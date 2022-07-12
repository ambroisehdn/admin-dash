package com.yieldigit.authapp.repositories.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yieldigit.authapp.models.user.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {
    
}
