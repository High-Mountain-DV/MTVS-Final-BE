package com.khj.mtvsfinalbe.profile.repository;

import com.khj.mtvsfinalbe.profile.domain.ProfileGraph;
import com.khj.mtvsfinalbe.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProfileGraphRepository extends JpaRepository<ProfileGraph, Long> {
    List<ProfileGraph> findByUserOrderByDateAsc(User user);
    ProfileGraph findByUserAndDate(User user, LocalDate date);
}
