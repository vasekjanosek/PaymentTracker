package cz.janosek.paymentTracker.Input;

import cz.janosek.paymentTracker.Data.Payment;
import cz.janosek.paymentTracker.Data.Payments;
import cz.janosek.paymentTracker.Service.Impl.ParserServiceImpl;
import cz.janosek.paymentTracker.Service.ParserService;

import java.io.*;

/**
 * Created by Václav Janošek on 5.3.15.
 */
public class TrackerFileReader {

    private Payments payments;

    /**
     * Constructor
     *
     * @param payments  to these payments will be payments readed from file added
     */
    public TrackerFileReader(Payments payments){
        this.payments = payments;
    }

    /**
     * Reads payment from file and adds them to payments
     *
     * @param file  payments will be read from this file
     */
    public void readFile(File file){

        if(file == null || !file.exists()) {
            throw new IllegalArgumentException("Wrong input file");
        }
        BufferedReader bReader;
        try{
            String line;
            bReader = new BufferedReader(new FileReader(file));

            while((line = bReader.readLine()) != null){
                processLine(line);
            }

            bReader.close();
        }
        catch(FileNotFoundException fnfex){
            System.err.println("File not found: " + file.getAbsolutePath());
        }

        catch(IOException ioex){
            System.err.println("Reading lines from file exception");
        }
    }

    /**
     *  Process given line. Searches for payment and if it founds, inserts it into payments.
     *
     * @param line  line to be processed
     */
    private void processLine(String line){
        ParserService parser = new ParserServiceImpl();
        Payment payment = parser.parsePayment(line);
        if(payment != null){
            payments.insertPayment(payment);
        }
    }
}
