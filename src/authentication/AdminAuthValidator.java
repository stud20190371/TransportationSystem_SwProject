package authentication;

import java.util.HashMap;

import database.SystemAdmin;
import user.User;

public class AdminAuthValidator implements AuthenticationValidator {
    private SystemAdmin systemAdmin = SystemAdmin.createInstance();

    @Override
    public User isUsernameExist(String username) {
        if(
            systemAdmin.getAdmin() != null &&
            (systemAdmin.getAdmin().getUserInfo().getUsername()).equals(username)
        ){
            return systemAdmin.getAdmin();
        }

        return null;
    }

    @Override
    public boolean isValidPassword(User user, String password) {
        return (user.getUserInfo().getPassword()).equals(password);
    }

    @Override
    public User signupValidation(HashMap<String, String> userInfo) throws Exception {
        return null;
    }

    @Override
    public User loginValidation(String username, String password) throws Exception {
        User user  = isUsernameExist(username);

        if(user == null){
            throw new Exception("There's no an admin with this username!");
        }

        if(!isValidPassword(user, password)){
            throw new Exception("This password is incorrect!");
        }

        return user;
    }
}
