package com.khj.mtvsfinalbe.user.repository;


import com.khj.mtvsfinalbe.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByLoginId(String loginId);
}
