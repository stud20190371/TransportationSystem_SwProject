package interfaces;

import java.util.ArrayList;

public interface Notifiable {
    void addNotification(String notification);
    ArrayList<String> getNotifications();
}
