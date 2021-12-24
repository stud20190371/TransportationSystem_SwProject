package authentication;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import database.SystemAdmin;
import database.SystemDrivers;
import exceptions.*;
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
                throw new UsernameExistException("There's already a driver with this username!");
            }

            if(
                !userInfo.get("email").equals("") &&
                userInfo.get("email") != null &&
                driver.getUserInfo().getEmail() != null &&
                (driver.getUserInfo().getEmail()).equals(userInfo.get("email"))
            ){
                throw new EmailExistException("There's already a driver with this email!");
            }

            if((driver.getUserInfo().getMobileNumber()).equals(userInfo.get("mobile_number"))){
                throw new MobileNumberExistException("There's already a driver with mobile number!");
            }
        }


        Date userBirthdate = new SimpleDateFormat().parse(userInfo.get("birthdate"));

        Driver newDriver = new Driver(
            userInfo.get("username"),
            userInfo.get("mobile_number"),
            userInfo.get("email").equals("") ? null : userInfo.get("email"),
            userInfo.get("password"),
            userBirthdate,
            userInfo.get("driving_licence"),
            userInfo.get("national_id")
        );

        this.systemDrivers.addDriver(newDriver);

        systemAdmin.getAdmin().addVerificationRequest(newDriver);

        if(!((Driver)newDriver).isVerified()){
            throw new NotVerifiedUserException("You should wait to be verified!");
        }

        return newDriver;
    }

    @Override
    public User loginValidation(String username, String password) throws Exception {
        User driver  = isUsernameExist(username);

        if(driver == null){
            throw new UsernameNotExistException("There's no a driver with this username!");
        }

        if(!isValidPassword(driver, password)){
            throw new IncorrectPasswordException("This password is incorrect!");
        }

        if(!((Driver)driver).isVerified()){
            throw new NotVerifiedUserException("You are not verified yet!");
        }

        if(((Driver)driver).isSuspended()){
            throw new SuspendedUserException("You can not login, you are suspended!");
        }

        return driver;
    }
}
