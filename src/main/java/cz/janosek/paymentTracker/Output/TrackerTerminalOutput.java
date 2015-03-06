package cz.janosek.paymentTracker.Output;

import cz.janosek.paymentTracker.Data.Payments;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import org.json.simple.parser.ParseException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Václav Janošek on 5.3.15.
 */
public class TrackerTerminalOutput implements Runnable {

    private Payments payments;
    private Map<String, Double> exchangeRatesToUSD;
    private volatile boolean run = true;

    public TrackerTerminalOutput(Payments paymentsCount){
        this.payments = paymentsCount;
        exchangeRatesToUSD = new HashMap<String, Double>();
    }

    @Override
    public void run(){
        while(run) {
            synchronized (payments) {
                writeToConsole();
            }
        }
    }

    public void quitThread(){
        run = false;
    }

    /**
     * Writes sum of currencies to console and sleeps for 60 seconds
     */
    public void writeToConsole(){

        Map<String, Double> currAndSum = payments.getCurrenciesAndSums();

        Iterator iterator = currAndSum.entrySet().iterator();

        System.out.println("CUR AMOUNT");
        while(iterator.hasNext()){
            Map.Entry pairs = (Map.Entry)iterator.next();

            if(!exchangeRatesToUSD.containsKey((String)pairs.getKey())){
                try {
                    URL url = new URL("http://rate-exchange.appspot.com/currency?from=" + (String)pairs.getKey() + "&to=USD");
                    URLConnection connection = url.openConnection();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String received = reader.readLine();
                    reader.close();

                    JSONParser parser = new JSONParser();

                    JSONObject object = (JSONObject)parser.parse(received);
                    Double rate = (Double)object.get("rate");
                    if(rate != null) {
                        exchangeRatesToUSD.put((String) pairs.getKey(), rate);
                    }

                }catch(MalformedURLException ex){
                    System.err.println(ex.getMessage());
                }
                catch(IOException ioex){
                    System.err.println(ioex.getMessage());
                }
                catch(ParseException pex){
                    System.err.println(pex.getMessage());
                }
            }

            if((Double)pairs.getValue() != 0f) {
                if(!((String) pairs.getKey()).equals("USD")) {
                    Double usdExRate = exchangeRatesToUSD.get((String) pairs.getKey());
                    if(usdExRate != null) {
                        Double exchanged = usdExRate * (Double) pairs.getValue();
                        System.out.println((String) pairs.getKey() + " " + ((Double) pairs.getValue()).toString() + "(USD " + exchanged.toString() + ")");
                    }else{
                        System.out.println((String) pairs.getKey() + " " + ((Double) pairs.getValue()).toString());
                    }

                }else{
                    System.out.println((String) pairs.getKey() + " " + ((Double) pairs.getValue()).toString());
                }
            }
        }

        System.out.println("--------------");

        try{
            Thread.sleep(60000);
        }catch (InterruptedException iex){
            System.err.println(iex.getMessage());
        }
    }
}
