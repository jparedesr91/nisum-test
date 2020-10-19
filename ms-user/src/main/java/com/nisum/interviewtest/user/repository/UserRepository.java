package com.nisum.interviewtest.user.repository;
import com.nisum.interviewtest.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import javax.transaction.Transactional;

public interface UserRepository extends JpaRepository<User, Integer> {
  boolean existsByUsername(String username);
  User findByUsername(String username);
}
