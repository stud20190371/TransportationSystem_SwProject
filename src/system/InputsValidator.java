package system;

import java.util.regex.Pattern;

public class InputsValidator {
    private void checkUsernameValidation(String username) throws Exception{
    		
		if(username.length() < 6) {
			throw new Exception("invalid username");
		}
		
		int underscore = 0 , letters = 0 ; 
		for(int i = 0 ; i < username.length() ; i++)
		{
			if(username.charAt(i) == '_')	underscore++;
			else if(username.charAt(i) >= 'a' && username.charAt(i) <= 'z')	letters++;
			else if(username.charAt(i) >= 'A' && username.charAt(i) <= 'Z') letters++;
		}
		
		if((letters != username.length() && letters != username.length()-1) || underscore > 1) {
			throw new Exception("invalid username");
		}
			
    }

    private void checkEmailValidation(String email) throws Exception{
    	
    	if(email == null)
    		return ;
    	
    	String regexEmail = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
                  
		Pattern pattern = Pattern.compile(regexEmail);
		if(!pattern.matcher(email).matches()) {
			throw new Exception("invalid email");
		}
    }

    private void checkPhoneNumValidation(String phoneNumber) throws Exception{

		if(phoneNumber.length() != 11) {
			throw new Exception("invalid phone number");
		}
		
		String str = phoneNumber.substring(0,3) ;
		if((!str.equals("010") && !str.equals("011") && !str.equals("012"))) {
			throw new Exception("invalid phone number");
		}
		
		boolean foundChar = false ;
		for(int i = 0 ; i < phoneNumber.length() ; i++)
		{
			if(phoneNumber.charAt(i) < '0' || phoneNumber.charAt(i) > '9') {
				foundChar = true ;
			}
		}
		
		if(foundChar) {
			throw new Exception("invalid phone number");
		}
		
    }

    private void checkPasswordValidation(String password) throws Exception{
    	
		if(password.length() < 6) {
			throw new Exception("invalid password");
		}
		
    }

    private void checkNationalIdValidation(String id) throws Exception{
    	
		if(id.length() != 14) {
			throw new Exception("invalid National id");
		}
		
		boolean foundChar = false ; 
		for(int i = 0 ; i < id.length() ; i++) 
		{
			if(id.charAt(i) < '0' || id.charAt(i) > '9') {
				foundChar = true ;
			}
		}
		
		if(foundChar) {
			throw new Exception("invalid National id");
		}
			
    }

    private void checkLicenseValidation(String license) throws Exception{
    	
		if(license.length() != 14) {
			throw new Exception("invalid license");
		}
		
		boolean foundchar = false ; 
		for(int i = 0 ; i < license.length() ; i++)
		{
			if(license.charAt(i) < '0' || license.charAt(i) > '9') {
				foundchar = true ;
			}
		}
		
		if(foundchar) {
			throw new Exception("invalid license");
		}
			
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
