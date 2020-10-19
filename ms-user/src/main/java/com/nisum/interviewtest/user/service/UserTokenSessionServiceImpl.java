package com.nisum.interviewtest.user.service;

import com.nisum.interviewtest.user.model.UserTokenSession;
import com.nisum.interviewtest.user.repository.UserTokenSessionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Objects;

@Slf4j
@Service
public class UserTokenSessionServiceImpl implements UserTokenSessionService {

    private static final String USER = "User";

    private final UserTokenSessionRepository userTokenSessionRepository;

    public UserTokenSessionServiceImpl(UserTokenSessionRepository userTokenSessionRepository) {
        this.userTokenSessionRepository = userTokenSessionRepository;
    }

    @Override
    public boolean isValidUserTokenSessionMapping(UserTokenSession userTokenSession){

        String username = userTokenSession.getUsername();
        UserTokenSession userTokenSessionFromDB = userTokenSessionRepository.findOneByUsername(username);

        if (Objects.isNull(userTokenSessionFromDB)) {
            log.error(USER + username + " mapping with token is not found in the database.");
            throw new UsernameNotFoundException(USER + username + "  mapping with token is not found in the database.");
        }

        LocalDateTime currentSystemTime = LocalDateTime.now();
        ZonedDateTime currentZonedDateTime = currentSystemTime.atZone(ZoneId.systemDefault());
        long currentTimeInMillis = currentZonedDateTime.toInstant().toEpochMilli();

        ZonedDateTime dataBaseZonedDateTime = userTokenSessionFromDB.getCreatedTime().atZone(ZoneId.systemDefault());

        long  tokenTimeInMillis = dataBaseZonedDateTime.toInstant().toEpochMilli() + (userTokenSessionFromDB.getExpiryTime() * 1000);

        if ( currentTimeInMillis >= tokenTimeInMillis) {

            log.info(USER + username + " token has expired. Please generate new token. Deleting the expired token mapping.");
            userTokenSessionRepository.delete(userTokenSessionFromDB);
            throw new UsernameNotFoundException(USER + username + " token has expired. Please generate new token.");

        }/*else if(!userTokenSession.equals(userTokenSessionFromDB)) {

            if (!userTokenSessionFromDB.getToken().equals(userTokenSession.getToken())){
                log.info(USER + userTokenSession.getUsername()+ " has invalid user and token mapping. Please generate new token.");

            } else {
                log.info(USER+userTokenSession.getUsername()+ " has invalid user and session-id mapping. Please generate new token.");
            }

            log.info("So, Deleting the invalid mapping.");
            userTokenSessionRepository.delete(userTokenSessionFromDB);
            throw new UsernameNotFoundException(USER + username + " has invalid user, session-id and token mapping. Please generate new token.");

        }*/else {

            log.info(USER + username + " has valid token.");
            return true;
        }
    }

    @Override
    public UserTokenSession saveUserTokenSessionMapping(UserTokenSession userTokenSession) {

        UserTokenSession userTokenSessionFromDB = userTokenSessionRepository.findOneByUsername(userTokenSession.getUsername());

        if (Objects.nonNull(userTokenSessionFromDB)) {

            if (userTokenSessionFromDB.equals(userTokenSession)) {
                log.info(USER+userTokenSession.getUsername()+ " making login call again with same token and session-id.");

            } else if (!userTokenSessionFromDB.getToken().equals(userTokenSession.getToken())){
                log.info(USER+userTokenSession.getUsername()+ " making login call with new token");

            } else {
                log.info(USER+userTokenSession.getUsername()+ " making login call with different session-id");

            }
            log.info("So, Deleting older mapping from tbl_user_token_session."+userTokenSessionFromDB);
            userTokenSessionRepository.delete(userTokenSessionFromDB);

        }

        return userTokenSessionRepository.save(userTokenSession);
    }
}
