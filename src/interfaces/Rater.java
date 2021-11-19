package interfaces;

import rating.Rate;

public interface Rater {
    public void rate(Rateable rateable, Rate rate);
    public void deleteRate(Rateable rateable, Rate rate);
    public void updateRate(Rateable rateable, Rate rate, float newRate);
    public float getRatingsAvg(Rateable rateable);
}
