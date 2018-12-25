package com.snow.ims.common.repository;
import com.snow.ims.common.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long>, JpaSpecificationExecutor {

    Optional<User> findByUsername(String username);

}
