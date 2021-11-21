package system;

public class AdminSection {
    static void displayAdminBoard(){
        CommonSection.clearConsole();
        System.out.println("1- Verify new drivers");
        System.out.println("2- Suspend users");
        System.out.println("3- Logout");
        System.out.println("4- Exit");
    }
}
