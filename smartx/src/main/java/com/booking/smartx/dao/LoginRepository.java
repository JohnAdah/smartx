package com.booking.smartx.dao;

import com.booking.smartx.entities.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepository extends CrudRepository<User,Long> {

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE users SET password = :password WHERE email = :email",nativeQuery = true)
    void updatePassword(@Param("email")String email, @Param("password") String password);
}
