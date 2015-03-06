package cz.janosek.paymentTracker.Service.Impl;

import cz.janosek.paymentTracker.Data.Payment;
import cz.janosek.paymentTracker.Service.ParserService;
import cz.janosek.paymentTracker.Service.ValidatorService;

/**
 * Created by Václav Janošek on 5.3.15.
 */
public class ParserServiceImpl implements ParserService{

    /**
     * Parses payment from given line
     *
     * @param line line to be parsed
     * @return  Payment if line is valid, null otherwise
     */
    public Payment parsePayment(String line){
        if(line == null | line.isEmpty()){
            throw new IllegalArgumentException("");
        }

        Payment payment = new Payment();

        String[] words = line.split("\u0020");
        if(words.length != 2){
            System.out.println("Entered payment is not valid, write it in format: 'AAA 1234'");
            return null;
        }

        ValidatorService validator = new ValidatorServiceImpl();

        if(!validator.validateCurrency(words[0])){
            System.out.println("Entered currency is not valid: " + words[0]);
            System.out.println("Currency must have format AAA");
            return null;
        }

        payment.setCurrency(words[0]);

        if(!validator.validateAmount(words[1])){
            System.out.println("Entered amount is not valid: " + words[1]);
            System.out.println("Amount must be specified only by numbers");
            return null;
        }

        payment.setAmount(Double.valueOf(words[1]));

        return payment;
    }
}
