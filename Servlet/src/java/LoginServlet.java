
import java.io.*;
import java.io.IOException;
import java.io.PrintWriter;
 
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

 
@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
 
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
         
        // read form fields
        String flightNumber = request.getParameter("flightNumber");
        String name = request.getParameter("name");
         
        System.out.println("flightNumber: " + flightNumber);
        System.out.println("name: " + name);
 
        // do some processing here...
         
        // get response writer
        PrintWriter out = response.getWriter();
         
        // build HTML code
        String htmlResponse = "<html>";
        htmlResponse += "<h2>Your flight number is: " + flightNumber + "<br/>";      
        htmlResponse += "Your name is: " + name + "</h2>";    
        htmlResponse += "</html>";
        
        // return response
        out.println(htmlResponse);
               
    }
 
}