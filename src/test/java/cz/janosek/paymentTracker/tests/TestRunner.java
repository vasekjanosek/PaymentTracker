package cz.janosek.paymentTracker.tests;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * Created by vasek on 6.3.15.
 */
public class TestRunner {

    public static void main(String[] args){
        Result result = JUnitCore.runClasses(ValidatorServiceTest.class);
        for(Failure failure: result.getFailures()){
            System.out.println(failure.toString());
        }
    }
}
