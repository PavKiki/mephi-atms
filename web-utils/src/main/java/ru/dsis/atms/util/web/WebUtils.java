package ru.dsis.atms.util.web;

public class WebUtils {

    public interface Response {}

    public static class TokenResponse implements Response {
        private String token;
        private String role;

        public TokenResponse(String token, String role) {
            this.token = token;
            this.role = role;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }
    }

    public static class SuccessResponse implements Response {
        private String success;

        public SuccessResponse() {}

        public SuccessResponse(String success) {
            this.success = success;
        }

        public String getSuccess() {
            return success;
        }

        public void setSuccess(String success) {
            this.success = success;
        }
    }

    public static class ErrorResponse implements Response {
        private String error;

        public ErrorResponse(String error) {
            this.error = error;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }
    }
}