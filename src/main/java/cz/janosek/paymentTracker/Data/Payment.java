package cz.janosek.paymentTracker.Data;

/**
 * Created by Václav Janošek on 5.3.15.
 */
public class Payment {
    private String currency;
    private Double amount;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
