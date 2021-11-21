package authentication;

import java.util.HashMap;

import database.SystemPassengers;
import user.Passenger;
import user.User;

public class PassengerAuthValidator implements AuthenticationValidator {
    private SystemPassengers systemPassengers = SystemPassengers.createInstance();

    @Override
    public User isUsernameExist(String username) {
        for(Passenger passenger: systemPassengers.getPassengers()){
            if((passenger.getUserInfo().getUsername()).equals(username)){
                return passenger;
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
        for(Passenger passenger: systemPassengers.getPassengers()){
            if((passenger.getUserInfo().getUsername()).equals(userInfo.get("username"))){
                throw new Exception("There's already a passenger with this username!");
            }

            if(
                !userInfo.get("email").equals("") &&
                (passenger.getUserInfo().getEmail()).equals(userInfo.get("email"))
            ){
                throw new Exception("There's already a passenger with this email!");
            }

            if((passenger.getUserInfo().getMobileNumber()).equals(userInfo.get("mobile_number"))){
                throw new Exception("There's already a passenger with mobile number!");
            }
        }

        Passenger newPassenger = new Passenger(
            userInfo.get("username"),
            userInfo.get("mobile_number"),
            userInfo.get("email"),
            userInfo.get("password")
        );

        this.systemPassengers.addPassenger(newPassenger);

        return newPassenger;
    }

    @Override
    public User loginValidation(String username, String password) throws Exception {
        User user  = isUsernameExist(username);

        if(user == null){
            throw new Exception("There's no a passenger with this username!");
        }

        if(!isValidPassword(user, password)){
            throw new Exception("This password is incorrect!");
        }

        return user;
    }
}
