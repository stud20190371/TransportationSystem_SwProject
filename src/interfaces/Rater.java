package interfaces;

import rating.Rate;

public interface Rater {
    public void rate(Rateable rateable, Rate rate);
    public String getRatingsAvg(Rateable rateable);
}
