package interfaces;

import rating.Rate;

public interface Rateable {
    public void addRate(Rate rate);
    public String ratingsAvg();
    public String getRateableName();
}
