/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


/**
 *
 * @author lesley
 */
public class Reader {
    
        public static boolean verify(String name, String ticketNumber) throws ParseException {
        JSONParser parser = new JSONParser();

        try {         
            URL ticketUrl = new URL("http://fys.securidoc.nl:11111/Ticket"); // URL to Parse
            HttpURLConnection conn = (HttpURLConnection) ticketUrl.openConnection();
            
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            
            String payload = "{\"function\":\"List\", \"teamId\":\"IN105-1\", \"teamKey\":\"9f5549e597bcca8f5c4a190f1b54344a\", \"requestId\":\"1\"}";
            conn.setRequestProperty("Content-Length", "" + payload.getBytes("UTF-8").length);
            conn.getOutputStream().write(payload.getBytes("UTF-8"));
            conn.getOutputStream().flush();
            
            InputStreamReader ticketReader = new InputStreamReader(conn.getInputStream());
            JSONObject rootObject = (JSONObject) parser.parse(ticketReader);
            
            if (!rootObject.containsKey("tickets")) {
                return false;
            }
            
            JSONObject ticketList = (JSONObject) rootObject.get("tickets");
            
            // passengerId will not be blank once the right ticket has been found
            String passengerId = "";
            for (String ticketId : (Iterable<String>) ticketList.keySet()) {
                JSONObject ticket = (JSONObject) ticketList.get(ticketId);
                
                if (((String)ticket.get("ticketNumber")).equalsIgnoreCase(ticketNumber)) {
                    passengerId = (String) ticket.get("pid");
                    break;
                }
            }
            
            if (passengerId.equals("")) {
                return false;
            }
            
            URL passengerUrl = new URL("http://fys.securidoc.nl:11111/Passenger");
            conn = (HttpURLConnection) passengerUrl.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Content-Length", "" + payload.getBytes("UTF-8").length);
            conn.getOutputStream().write(payload.getBytes("UTF-8"));
            conn.getOutputStream().flush();
            
            InputStreamReader passengerReader = new InputStreamReader(conn.getInputStream());
            rootObject = (JSONObject) parser.parse(passengerReader);
            
            if (!rootObject.containsKey("passengers")) {
                return false;
            }
            
            JSONObject passengerList = (JSONObject) rootObject.get("passengers");
            
            if (!passengerList.containsKey(passengerId)) {
                return false;
            }
            
            JSONObject passenger = (JSONObject) passengerList.get(passengerId);
            if (((String) passenger.get("lastName")).equalsIgnoreCase(name)){ 
                return true;
            } else {
                return false;
            }
        } catch (FileNotFoundException e) {  
        } catch (IOException e) {
        }  
        
        return false;
    }   
    
}
