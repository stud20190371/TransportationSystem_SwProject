package system;

import user.Admin;

public class TransportationSystem {

    public static void main(String[] args) {
        Admin systemAdmin = new Admin(
            "fciAdmin",
            "12345678910",
            null,
            "12345678"
        );

        CommonSection.sysDatabase.setSystemAdmin(systemAdmin);

        AuthSection.displayAuthMenu(true);
    }
}
