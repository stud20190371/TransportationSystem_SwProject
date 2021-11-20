package system;

public class InputsValidator {
    private void checkUsernameValidation(String username){

    }

    private void checkEmailValidation(String email){

    }

    private void checkPhoneNumValidation(String phoneNumber){

    }

    private void checkPasswordValidation(String password){

    }

    private void checkNationalIdValidation(String id){

    }

    private void checkLicenseValidation(String license){

    }

    public void checkValidation(String fieldName, String userInput){
        if(fieldName.equals("username")){
            checkUsernameValidation(userInput);
        }else if(fieldName.equals("mobile_number")){
            checkPhoneNumValidation(userInput);
        }else if(fieldName.equals("email")){
            checkEmailValidation(userInput);
        }else if(fieldName.equals("password")){
            checkPasswordValidation(userInput);
        }else if(fieldName.equals("national_id")){
            checkNationalIdValidation(userInput);
        }else if(fieldName.equals("driving_licence")){
            checkLicenseValidation(userInput);
        }
    }
}
