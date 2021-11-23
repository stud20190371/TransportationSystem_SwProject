package rating;

import java.util.Date;

import interfaces.Rateable;
import interfaces.Rater;

public class Rate {
    private Rater rater;
    private Rateable rated;
    private float rate;
    private Date date;

    public Rate(Rater rater, Rateable rated, float rate){
        this.rater = rater;
        this.rated = rated;
        this.rate = rate;
        this.date = new Date();
    }

    public String getRaterName(){
        return rater.getRaterName();
    }

    public String getRateableName(){
        return rated.getRateableName();
    }

    public void setRate(float rate){
        if(rate >=1 && rate <= 5){
            this.rate = rate;
        }
    }

    public float getRate(){
        return this.rate;
    }

    public Date getDate(){
        return this.date;
    }
}
