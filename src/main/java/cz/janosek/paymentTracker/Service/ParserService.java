package cz.janosek.paymentTracker.Service;

import cz.janosek.paymentTracker.Data.Payment;

/**
 * Created by Václav Janošek on 5.3.15.
 */
public interface ParserService {
    Payment parsePayment(String line);
}
