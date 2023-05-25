package com.shevelyanchik.fitnessclub.userservice.persistence;

import com.shevelyanchik.fitnessclub.userservice.constant.Role;
import com.shevelyanchik.fitnessclub.userservice.constant.Status;
import com.shevelyanchik.fitnessclub.userservice.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByEmail(String email);

    boolean existsUserById(Long id);

    boolean existsUserByEmail(String email);

    @Modifying
    @Query("UPDATE User u SET u.status = :status WHERE u.id = :id")
    void updateUserStatusById(Long id, Status status);

    @Modifying
    @Query("UPDATE User u SET u.role = :role WHERE u.id = :id")
    void updateUserRoleById(Long id, Role role);

    @Modifying
    @Query("UPDATE User u SET u.email = :updateEmail WHERE u.email = :actualEmail")
    void updateUserEmailByActualEmail(String actualEmail, String updateEmail);

}
