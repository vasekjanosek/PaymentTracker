package cz.janosek.paymentTracker.tests;

import cz.janosek.paymentTracker.Service.Impl.ParserServiceImpl;
import cz.janosek.paymentTracker.Service.ParserService;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by Václav Janošek on 5.3.15.
 */
public class ParserServiceTest {

    @Test
    public void wrongCurrencies(){
        ParserService parser = new ParserServiceImpl();
        assertEquals(parser.parsePayment("AaA"), false);
        assertEquals(parser.parsePayment("AaA123"), false);
        assertEquals(parser.parsePayment("AAA 132"), true);
        assertEquals(parser.parsePayment("AAA 123 2"), false);
        assertEquals(parser.parsePayment("AAAA 235"), false);
    }
}
