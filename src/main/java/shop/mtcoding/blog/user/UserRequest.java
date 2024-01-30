package shop.mtcoding.blog.user;

import lombok.Data;

//요청 DTO = Data Transfer Object
//데이터를 전송하는 오브젝트
public class UserRequest {

    @Data
    public static class JoinDTO {
        private String username;
        private String password;
        private String email;
    }

    @Data
    public static class LoginDTO {
        private String username;
        private String password;
    }

}
