package authentication;

import java.util.HashMap;

import database.SystemAdmin;
import database.SystemDrivers;
import user.Driver;
import user.User;

public class DriverAuthValidator implements AuthenticationValidator {
    private SystemDrivers systemDrivers = SystemDrivers.createInstance();
    private SystemAdmin systemAdmin = SystemAdmin.createInstance();

    @Override
    public User isUsernameExist(String username) {
        for(Driver driver: systemDrivers.getDrivers()){
            if((driver.getUserInfo().getUsername()).equals(username)){
                return driver;
            }
        }
        
        return null;
    }

    @Override
    public boolean isValidPassword(User user, String password) {
        return (user.getUserInfo().getPassword()).equals(password);
    }

    @Override
    public User signupValidation(HashMap<String, String> userInfo) throws Exception {
        for(Driver driver: systemDrivers.getDrivers()){
            if((driver.getUserInfo().getUsername()).equals(userInfo.get("username"))){
                throw new Exception("There's already a driver with this username!");
            }

            if(
                !userInfo.get("email").equals("") &&
                (driver.getUserInfo().getEmail()).equals(userInfo.get("email"))
            ){
                throw new Exception("There's already a driver with this email!");
            }

            if((driver.getUserInfo().getMobileNumber()).equals(userInfo.get("mobile_number"))){
                throw new Exception("There's already a driver with mobile number!");
            }
        }

        Driver newDriver = new Driver(
            userInfo.get("username"),
            userInfo.get("mobile_number"),
            userInfo.get("email"),
            userInfo.get("password"),
            userInfo.get("driving_licence"),
            userInfo.get("national_id")
        );

        this.systemDrivers.addDriver(newDriver);

        systemAdmin.getAdmin().addVerificationRequest(newDriver);

        return newDriver;
    }

    @Override
    public User loginValidation(String username, String password) throws Exception {
        User user  = isUsernameExist(username);

        if(user == null){
            throw new Exception("There's no a driver with this username!");
        }

        if(!isValidPassword(user, password)){
            throw new Exception("This password is incorrect!");
        }

        return user;
    }
}
