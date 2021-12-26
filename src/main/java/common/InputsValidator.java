package common;

import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

import enums.AuthFieldName;

public class InputsValidator {
    private void checkUsernameValidation(String username) throws Exception{
    		
		if(username == null || username.length() < 6) {
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

		if(phoneNumber == null || phoneNumber.length() != 11) {
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
    	
		if(password == null || password.length() < 6) {
			throw new Exception("invalid password");
		}
		
    }

    private void checkNationalIdValidation(String id) throws Exception{
    	
		if(id == null || id.length() != 14) {
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
    	
		if(license == null || license.length() != 14) {
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

	private void checkBirthdateValidation(String birthdate) throws Exception{
    	
		if(birthdate == null || birthdate.length() == 0) {
			throw new Exception("Birthdate can't be empty");
		}
		
		new SimpleDateFormat("dd/MM/yyyy").parse(birthdate);
		
    }

    public void checkValidation(AuthFieldName fieldName, String userInput) throws Exception{
        if(fieldName.equals(AuthFieldName.USERNAME)){
            checkUsernameValidation(userInput);
        }else if(fieldName.equals(AuthFieldName.MOBILE_NUMBER)){
            checkPhoneNumValidation(userInput);
        }else if(fieldName.equals(AuthFieldName.EMAIL)){
            checkEmailValidation(userInput);
        }else if(fieldName.equals(AuthFieldName.PASSWORD)){
            checkPasswordValidation(userInput);
        }else if(fieldName.equals(AuthFieldName.BIRTHDATE)){
			checkBirthdateValidation(userInput);
		}else if(fieldName.equals(AuthFieldName.NATIONAL_ID)){
            checkNationalIdValidation(userInput);
        }else if(fieldName.equals(AuthFieldName.DRIVING_LICENCE)){
            checkLicenseValidation(userInput);
        }
    }
}
