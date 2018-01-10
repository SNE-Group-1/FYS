
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
 
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.parser.ParseException;

 
@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
 
    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
         
        // read form fields
        String ticketNumber = request.getParameter("ticketNumber");
        String name = request.getParameter("name");
         
        System.out.println("ticketNumber: " + ticketNumber);
        System.out.println("name: " + name);
 
        // do some processing here...
        Reader reader = new Reader(){};
        
        // get response writer
        PrintWriter out = response.getWriter();
        
        try {
            if(Reader.verify(name, ticketNumber)){
                
                // Get client IP address
                String ipAddress = request.getRemoteAddr();
                
                // Whitelist IP address (hopefully)
                String[] command = {"sudo","mv","/etc/iptables/rules.v4"};  // Only change!

                Runtime runtime = Runtime.getRuntime();
                Process process = null;

                process = runtime.exec(command);
                BufferedReader in = 
                new BufferedReader(new InputStreamReader(process.getInputStream()));
                
                // build HTML code
                String htmlResponse = "<html>";
                htmlResponse += "<h1>You are logged in</h1>";
                htmlResponse += "<p>IP address: " + ipAddress +"</p>";
                htmlResponse += "<h2>Your flight number is: " + ticketNumber + "<br/>";      
                htmlResponse += "Your name is: " + name + "</h2>";    
                htmlResponse += "</html>";
                // return response
                out.println(htmlResponse);
            }else{
                System.out.println("Wrong input");
                String htmlResponse = "<html>";
                htmlResponse += "<h1>Wrong input</h1>";  
                htmlResponse += "</html>";
                // return response
                out.println(htmlResponse);
            }
        } catch (ParseException ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
                 
    }
 
}