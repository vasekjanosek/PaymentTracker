package cz.janosek.paymentTracker;

import cz.janosek.paymentTracker.Data.Payment;
import cz.janosek.paymentTracker.Data.Payments;
import cz.janosek.paymentTracker.Input.TrackerFileReader;
import cz.janosek.paymentTracker.Output.TrackerTerminalOutput;
import cz.janosek.paymentTracker.Service.Impl.ParserServiceImpl;
import cz.janosek.paymentTracker.Service.ParserService;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Václav Janošek on 5.3.15.
 */
public class App {

    public static void main(String[] args) {

        Payments payments = new Payments();

        if(args.length == 2){
            if(!args[0].equals("-f")){
                System.out.println("Wrong parameter type, for input file you should type -f");
                System.exit(1);
            }

            File file = new File(args[1]);
            if(!file.exists()){
                System.out.println("Given path to file is wrong, file doesn't exist.");
                System.exit(1);
            }

            TrackerFileReader reader = new TrackerFileReader(payments);
            reader.readFile(file);
        }

        TrackerTerminalOutput trackerThread = new TrackerTerminalOutput(payments);
        Thread t = new Thread(trackerThread);
        t.start();

        boolean running = true;
        while(running){
            String line = null;
            BufferedReader bReader = new BufferedReader(new InputStreamReader(System.in));

            try{
                line = bReader.readLine();
                ParserService parser = new ParserServiceImpl();
                Payment payment = parser.parsePayment(line);
                if(payment != null){
                    payments.insertPayment(payment);
                }
            }catch(IOException ioex){
                System.err.println(ioex.getMessage());
            }
            if(line != null && !line.isEmpty()) {
                if (line.toLowerCase().equals("quit")) {
                    trackerThread.quitThread();
                    t.interrupt();
                    running = false;
                }
            }
        }

    }
}
