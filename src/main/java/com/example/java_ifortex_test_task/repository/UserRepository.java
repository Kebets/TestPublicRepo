package com.example.java_ifortex_test_task.repository;

import com.example.java_ifortex_test_task.entity.DeviceType;
import com.example.java_ifortex_test_task.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT u.* FROM users u " +
            "JOIN sessions s ON s.user_id = u.id " +
            "WHERE s.device_type = :#{T(com.example.java_ifortex_test_task.entity.DeviceType).MOBILE.code} " +
            "GROUP BY u.id " +
            "ORDER BY MAX(s.started_at_utc) DESC", nativeQuery = true)
    List<User> getUsersWithAtLeastOneMobileSession();


    @Query(value = "SELECT u.* FROM users u " +
            "JOIN sessions s ON s.user_id = u.id " +
            "GROUP BY u.id " +
            "ORDER BY COUNT(s.id) DESC " +
            "LIMIT 1", nativeQuery = true)
    User getUserWithMostSessions();
}
