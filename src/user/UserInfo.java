package user;

public class UserInfo {
    private String id;
    private String username;
    private String mobileNumber;
    private String email;
    private String password;

    UserInfo(String id, String username, String mobileNum, String email, String password){
        this.id = id;
        this.username = username;
        this.mobileNumber = mobileNum;
        this.email = email;
        this.password = password;
    }

    public String getId(){
        return this.id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUserName(String username){
        this.username = username;
    }

    public String getMobileNumber(){
        return this.mobileNumber;
    }

    public void setMobileNumber(String number){
        this.mobileNumber = number;
    }

    public String getEmail(){
        return this.email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getPassword(){
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
