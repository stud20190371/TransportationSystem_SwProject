package authentication;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import database.SystemPassengers;
import enums.AuthFieldName;
import exceptions.*;
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
    public User signupValidation(HashMap<AuthFieldName, String> userInfo) throws Exception {
        for(Passenger passenger: systemPassengers.getPassengers()){
            if((passenger.getUserInfo().getUsername()).equals(userInfo.get(AuthFieldName.USERNAME))){
                throw new UsernameExistException("There's already a passenger with this username!");
            }

            if(
                userInfo.get(AuthFieldName.EMAIL) != null &&
                !userInfo.get(AuthFieldName.EMAIL).equals("") &&
                passenger.getUserInfo().getEmail() != null &&
                (passenger.getUserInfo().getEmail()).equals(userInfo.get(AuthFieldName.EMAIL))
            ){
                throw new EmailExistException("There's already a passenger with this email!");
            }

            if((passenger.getUserInfo().getMobileNumber()).equals(userInfo.get(AuthFieldName.MOBILE_NUMBER))){
                throw new MobileNumberExistException("There's already a passenger with mobile number!");
            }
        }

        Date userBirthdate = new SimpleDateFormat("dd/MM/yyyy").parse(userInfo.get(AuthFieldName.BIRTHDATE));

        Passenger newPassenger = new Passenger(
            userInfo.get(AuthFieldName.USERNAME),
            userInfo.get(AuthFieldName.MOBILE_NUMBER),
            userInfo.get(AuthFieldName.EMAIL), 
            userInfo.get(AuthFieldName.PASSWORD),
            userBirthdate
        );

        this.systemPassengers.addPassenger(newPassenger);

        return newPassenger;
    }

    @Override
    public User loginValidation(String username, String password) throws Exception {
        User user  = isUsernameExist(username);

        if(user == null){
            throw new UsernameNotExistException("There's no a passenger with this username!");
        }

        if(!isValidPassword(user, password)){
            throw new IncorrectPasswordException("This password is incorrect!");
        }

        if(((Passenger)user).isSuspended()){
            throw new SuspendedUserException("You can not login, you are suspended!");
        }

        return user;
    }
}
