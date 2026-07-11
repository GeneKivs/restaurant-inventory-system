package com.restaurant.inventory.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.restaurant.inventory.model.Roles;
import com.restaurant.inventory.model.User;



public interface UserRepository extends JpaRepository<User,Integer>{
    Optional<User>  findByUserName(String userName);
    Optional<User> findByEmail(String email);
    List<User> findByRole(Roles role);

    @Query("SELECT u FROM User u JOIN u.role r WHERE r.roleName = :roleName AND u.active = true")
    List<User> findByRoleName(@Param("roleName") String roleName);

    @Query("SELECT u FROM User u WHERE u.active = true")
    List<User> findAllActive();
}
