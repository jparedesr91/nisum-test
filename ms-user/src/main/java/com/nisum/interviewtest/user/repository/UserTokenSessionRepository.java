package com.nisum.interviewtest.user.repository;
import com.nisum.interviewtest.user.model.UserTokenSession;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTokenSessionRepository extends CrudRepository<UserTokenSession, Long> {
    UserTokenSession findOneByUsername(String username);
}