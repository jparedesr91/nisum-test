package com.nisum.interviewtest.user.service;

import com.nisum.interviewtest.user.model.UserTokenSession;

public interface UserTokenSessionService {
    boolean isValidUserTokenSessionMapping(UserTokenSession userTokenSession);
    UserTokenSession saveUserTokenSessionMapping(UserTokenSession userTokenSession);
    class ValidMappingResponse {

        private boolean valid;
        private UserTokenSession userTokenSession;

        public ValidMappingResponse() {
        }

        public ValidMappingResponse(boolean valid, UserTokenSession userTokenSession ) {
            this.valid = valid;
            this.userTokenSession = userTokenSession;
        }

        public boolean isValid() {
            return valid;
        }

        public void setValid(boolean valid) {
            this.valid = valid;
        }

        public UserTokenSession getUserTokenSession() {
            return userTokenSession;
        }

        public void setUserTokenSession(UserTokenSession userTokenSession) {
            this.userTokenSession = userTokenSession;
        }
    }

}
