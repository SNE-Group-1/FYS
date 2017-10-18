package net.codejava.servlet;
 
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
 
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
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/Servlet", "root", "");
            
            if (con != null){
                System.out.println("Connected to the database");
            }
 
            PreparedStatement ps = con
                    .prepareStatement("SELECT * FROM flights where flightNumber = " + flightNumber + " AND name = " + name );
 
            int i = ps.executeUpdate();
            if (i > 0){
                System.out.println("Checked the database");
            }else{
                System.out.println("Didnt work");
            }
                
            
 
        } catch (Exception e2) {
            System.out.println(e2);
        }
         
    }
 
}