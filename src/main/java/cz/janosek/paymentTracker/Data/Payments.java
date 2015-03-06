package cz.janosek.paymentTracker.Data;

import cz.janosek.paymentTracker.Service.Impl.ValidatorServiceImpl;
import cz.janosek.paymentTracker.Service.ValidatorService;

import java.util.*;

/**
 * Created by Václav Janošek on 5.3.15.
 */
public class Payments{

    private Map<String, List<Payment>> payments;
    private ValidatorService validator;

    /**
     * Constructor
     */
    public Payments(){
        this(new HashMap<String, List<Payment>>());
    }

    /**
     * Constructor
     * @param payments  data used to fill payments
     */
    public Payments(Map<String, List<Payment>> payments){
        this.payments = payments;
        validator = new ValidatorServiceImpl();
    }


    /**
     * Add new payment to existing ones
     *
     * @param payment   payment, which should be inserted
     * @return
     */
    public boolean insertPayment(Payment payment){

        if(payment == null){
            return false;
        }

        if(!validator.validateCurrency(payment.getCurrency())){
            return false;
        }

        if(payments.containsKey(payment.getCurrency())){
            payments.get(payment.getCurrency()).add(payment);
        }else{
            List<Payment> amounts = new ArrayList<Payment>();
            amounts.add(payment);
            payments.put(payment.getCurrency(), amounts);
        }

        return true;
    }


    /**
     * Returns sum of given currency, only if payments of currency exists
     * @param currency  for this currency will be sum counted
     * @return  sum of payments specified by currency
     */
    private Double getCurrencySum(String currency){
        if(!validator.validateCurrency(currency)){
            return null;
        }

        Double sum = 0d;
        if(payments.containsKey(currency)){

            for(Payment payment : payments.get(currency)){
                sum += payment.getAmount();
            }
        }

        return sum;
    }

    /**
     * Counts sum of payments for all currencies
     * @return  map of currencies and sums for them
     */
    public Map<String, Double> getCurrenciesAndSums(){

        Map<String, Double> sums = new HashMap<String, Double>();
        Iterator iterator = payments.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry pairs = (Map.Entry)iterator.next();
            sums.put((String)pairs.getKey(), getCurrencySum((String)pairs.getKey()));
        }
        return sums;
    }
}
