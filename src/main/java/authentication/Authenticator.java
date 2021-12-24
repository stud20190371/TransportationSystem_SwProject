package authentication;

import java.util.HashMap;

import user.User;

public class Authenticator {
    private boolean isAuthenticated = false;
    private AuthenticationValidator authValidator = new AdminAuthValidator();

    public boolean isUserAuthenticated(){
        return this.isAuthenticated;
    }

    public void setAuthValidator(AuthenticationValidator authValidator){
        this.authValidator = authValidator;
    }

    public User signup(HashMap<String, String> userInfo) throws Exception{
        User user = authValidator.signupValidation(userInfo);
        isAuthenticated = true;
        return user;
    }

    public User login(String username, String password) throws Exception{
        User user = authValidator.loginValidation(username, password);
        isAuthenticated = true;
        return user;
    }

    public void logout(){
        isAuthenticated = false;
    }
}
