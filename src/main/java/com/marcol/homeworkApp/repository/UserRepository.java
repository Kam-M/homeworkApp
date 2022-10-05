package com.marcol.homeworkApp.repository;

import com.marcol.homeworkApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
