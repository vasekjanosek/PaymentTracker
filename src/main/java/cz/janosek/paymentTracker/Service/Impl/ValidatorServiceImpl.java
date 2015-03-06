package cz.janosek.paymentTracker.Service.Impl;

import cz.janosek.paymentTracker.Service.ValidatorService;

/**
 * Created by Václav Janošek on 5.3.15.
 */
public class ValidatorServiceImpl implements ValidatorService{

    /**
     * Validates format of currency, currency must have format: AAA
     * @param currency string to validate
     * @return  true if currency has right fomrat, false otherwise
     */
    public boolean validateCurrency(String currency) {
        if (currency == null || currency.isEmpty()) {
            return false;
        }
        return currency.matches(new String("^[A-Z]{3}$"));
    }

    /**
     * Validates amount, string must by double number
     * @param amount    string to validate
     * @return  true if string can be converted to double, false otherwise
     */
    public boolean validateAmount(String amount){
        if(amount == null || amount.isEmpty()){
            return false;
        }
        try {
            Double.valueOf(amount);
        }catch(NumberFormatException nfex){
            return false;
        }
        return true;
    }
}
