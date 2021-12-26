package rideEvents;

import java.util.Date;

public abstract class RideEvent {
    protected String id;
    private String eventName;
    private  Date eventDate;

    private static int eventsCount = 0;

    RideEvent(String eventName){
        this.id = ("event"+(++eventsCount));
        this.eventName = eventName;
        this.eventDate = new Date();
    }

    public String getId(){
        return this.id;
    }

    public String getEventName(){
        return eventName;
    }

    public Date getEventDate(){
        return eventDate;
    }
}
