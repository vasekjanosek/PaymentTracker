package cz.janosek.paymentTracker.Service;

/**
 * Created by Václav Janošek on 5.3.15.
 */
public interface ValidatorService{
    boolean validateCurrency(String currency);
    boolean validateAmount(String amount);
}
