
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
 
        // do some processing here...
        Reader reader = new Reader(){};
        
        // get response writer
        PrintWriter out = response.getWriter();
        
        try {
            if(Reader.verify(name, ticketNumber)){
                
                // Get client IP address
                String ipAddress = request.getRemoteAddr();
                
                // Whitelist IP address (hopefully)
                String command = "sudo iptables -A INPUT -s " + ipAddress + " -j ACCEPT && sudo iptables -A OUTPUT -d " + ipAddress + " -j ACCEPT && sudo iptables -P INPUT DROP && sudo iptables OUTPUT DROP";
                Runtime runtime = Runtime.getRuntime();
                Process process = null;

                process = runtime.exec(command);
                BufferedReader in = 
                new BufferedReader(new InputStreamReader(process.getInputStream()));
                
                String contextPath = "http://google.com";
                response.sendRedirect(response.encodeRedirectURL(contextPath));
                
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