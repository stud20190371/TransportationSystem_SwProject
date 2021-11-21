package database;

import user.Admin;

public class SystemAdmin {
    private Admin admin;
    private static SystemAdmin sysAdminInstance = null;

    private SystemAdmin(){}

    public static SystemAdmin createInstance(){
        if(sysAdminInstance == null){
            sysAdminInstance = new SystemAdmin();
        }

        return sysAdminInstance;
    }

    public Admin getAdmin(){
        return this.admin;
    }
    
    public void setAdmin(Admin admin){
        this.admin = admin;
    }
}
