package interfaces;

import rating.Rate;

public interface Rateable {
    public void addRate(Rate rate);
    public void removeRate(Rate rate);
    public void updateRate(Rate rate, float newRate);
    public float ratingsAvg();
}
