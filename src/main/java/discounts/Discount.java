package discounts;

import exceptions.InvalidDiscountException;

public class Discount {
    private String id;
    private String reason;
    private float discount;

    private static int discountsCount = 0;

    public Discount(String reason, float discount) throws Exception{
        this.id = ("discount"+(++discountsCount));
        this.reason = reason;
        
        if(discount < 0){
            throw new InvalidDiscountException("Discount can't be negative value");
        }

        this.discount = discount;
    }

    public String getId(){
        return this.id;
    }

    public float applyDiscount(float price){
        return price - (price * (discount/100));
    }

    public String getReason(){
        return this.reason;
    }

    public float getDiscount(){
        return this.discount;
    }
}
