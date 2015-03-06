package cz.janosek.paymentTracker.tests;

import cz.janosek.paymentTracker.Service.Impl.ValidatorServiceImpl;
import cz.janosek.paymentTracker.Service.ValidatorService;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by Václav Janošek on 5.3.15.
 */
public class ValidatorServiceTest {

    @Test
    public void wrongCurrencies(){
        ValidatorService validator = new ValidatorServiceImpl();
        assertEquals(validator.validateCurrency("AaA"), false);
        assertEquals(validator.validateCurrency("1"), false);
        assertEquals(validator.validateCurrency(""), false);
        assertEquals(validator.validateCurrency("AAAA"), false);
        assertEquals(validator.validateCurrency("*as"), false);
        assertEquals(validator.validateCurrency("AAA"), true);
    }

    @Test
    public void wrongAmounts(){
        ValidatorService validator = new ValidatorServiceImpl();
        assertEquals(validator.validateCurrency("AaA"), false);
        assertEquals(validator.validateCurrency("1"), true);
        assertEquals(validator.validateCurrency(""), false);
        assertEquals(validator.validateCurrency("AAAA"), false);
        assertEquals(validator.validateCurrency("*as"), false);
        assertEquals(validator.validateCurrency("AAA"), false);
        assertEquals(validator.validateCurrency("-1"), true);
        assertEquals(validator.validateCurrency("-1.0"), true);
    }
}
