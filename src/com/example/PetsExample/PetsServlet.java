package com.example.PetsExample;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.PetsExample.model.*;



/**
 * Servlet implementation class PetsServlet
 */

public class PetsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PetsServlet() {
        super();
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        
        try {
        	PrintWriter out = response.getWriter();
            out.println("<html><body>");
            
            String id = request.getParameter("id");
          
                 
            InputStream in = getServletContext().getResourceAsStream("/WEB-INF/config.properties");
            Properties props = new Properties();
            props.load(in);
             
            
            DBConnection conn = new DBConnection(props.getProperty("url"), props.getProperty("userid"), props.getProperty("password"));
        	Statement stmt = conn.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rst = stmt.executeQuery("select * from products where id = " + id);
            
            out.println("<table><tr><th>Name</th><th>Color</th><th>Price</th></tr>");
            
            
            if (rst.next()) {	
            	out.println ("The product ID" + rst.getInt(id) + " you searched for has been located with the name: " + rst.getString("name") + " and the price: "   + rst.getBigDecimal("price") + "and the color " + rst.getString("color")); 
            	
            } else {
            	
            	out.println ("Product not found or invalid ID");
            	
            }
            
//            while (rst.next()) {
//                out.println("<tr><td>" + rst.getString("name") + "</td>" + "<td>" +
//                		rst.getString("color") + "</td><td>" + rst.getBigDecimal("price") + "</td></tr>");
//            }
            
            out.println("</table>");
        
            stmt.close();        
   
        
            out.println("</body></html>");
            conn.closeConnection();
        
        } catch (ClassNotFoundException e) {
        	e.printStackTrace();
        } catch (SQLException e) {
        	e.printStackTrace();
        }
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}