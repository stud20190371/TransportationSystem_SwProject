package authentication;

import java.util.HashMap;

import enums.AuthFieldName;
import user.User;

public interface AuthenticationValidator {
    
    public User isUsernameExist(String username);

    public boolean isValidPassword(User user, String password);

    public User signupValidation(HashMap<AuthFieldName, String> userInfo) throws Exception;

    public User loginValidation(String username, String password) throws Exception;
}
