

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class StudentAssignments
 */
@WebServlet("/StudentAssignments")
public class StudentAssignments extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String message;      
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StudentAssignments() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String url2="";
		
		// get parameters from the request
		String Name=request.getParameter("Name");
		System.out.println(Name);
		// store data in User object and save User object in db
		 try{
			  message="";  
			  String url= "jdbc:oracle:thin:testuser/password@localhost"; 
			  Class.forName("oracle.jdbc.driver.OracleDriver");
	        //properties for creating connection to Oracle database
	        Properties props = new Properties();
	        props.setProperty("user", "testdb");
	        props.setProperty("password", "password");
	        Connection conn = DriverManager.getConnection(url,props);
	        //creating connection to Oracle database using JDBC              
	        Statement s=conn.createStatement();
	        ResultSet res=s.executeQuery("select * from sgradebook where student_name='"+Name+"'");
	        message+="<div align=\"center\"><table style=\"border:2px solid black\">";
            message+="<th style=\" background-color:gray;border:2px solid black\">Assignment</th><th style=\" background-color:gray;border:2px solid black\">Type</th><th style=\" background-color:gray;border:2px solid black\">Grade</th>";
	        while(res.next()){

                message+="<tr ><td style=\" background-color:white;border:2px solid black\">"+ res.getString("assignment")+              
             		   "</td><td style=\" background-color:white;border:2px solid black\">"+res.getString("type")+
             		   "</td><td style=\"background-color:white;border:2px solid black\">" +res.getInt("grade")+
             		  "</td></tr>" ;  
	        }
	        
            
		// set User object in request object and set URL
		request.setAttribute("message", message);
		url2="/output.jsp";
		getServletContext().getRequestDispatcher(url2).forward(request, response);
		}catch(Exception e){
			System.out.println(e.getMessage());

		}
	
	}

}
