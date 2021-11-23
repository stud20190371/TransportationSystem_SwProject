package user;

import database.SystemDatabase;

public abstract class User {
    private UserInfo userInfo;
    protected SystemDatabase sysDatabase;


    User(String id, String username, String mobileNum, String email, String password){
        this.userInfo = new UserInfo(id, username, mobileNum, email, password);

        this.sysDatabase = SystemDatabase.createInstance();
    }

    public UserInfo getUserInfo(){
        return this.userInfo;
    }

    @Override
    public String toString() {
        return 
            "\nusername: " + userInfo.getUsername() + "\n" + 
            (userInfo.getEmail() != null ? ("email: " + userInfo.getEmail() + "\n") : "") +
            "mobile number: " + userInfo.getMobileNumber() + "\n";
    }
}
