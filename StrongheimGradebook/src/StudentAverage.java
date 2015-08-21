

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
 * Servlet implementation class StudentAverage
 */
@WebServlet("/StudentAverage")
public class StudentAverage extends HttpServlet {
	private static final long serialVersionUID = 1L;
     private String message; 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StudentAverage() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
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
	        message+="<div align=\"center\"><table style=\"border:2px solid black\">";
            message+="<th style=\" background-color:gray;border:2px solid black\">Class</th><th style=\" background-color:gray;border:2px solid black\">Avg_Grade</th>";
	        ResultSet res2=s.executeQuery("select C,sum(Weighted_grade) as Avg_Grade from (select  C,T,(Avg*assign_weights.weight) as Weighted_Grade from assign_weights,(select class as C,type as T, avg(grade) as Avg from sgradebook  where student_name='"+Name+"' group by class,type) where assign_weights.ASSIGN_TYPE=T) group by C");
	        while (res2.next()){
		    	   message+="<tr ><td style=\" background-color:white;border:2px solid black\">"+ res2.getString("C")+ 
		    			   "</td><td style=\"background-color:white;border:2px solid black\">" +res2.getInt("avg_grade")+
		             		  "</td></tr>" ;  
		       }
	        //System.out.println(message);
	        ResultSet res=s.executeQuery("select sum( Avg*assign_weights.weight)as Sum from assign_weights,(select class,type as T, avg(grade) as Avg from sgradebook  where student_name='"+Name+"' group by class,type)where assign_weights.ASSIGN_TYPE=T");
	      
	        while(res.next()){

                message+="Average grade  for all classes for "+Name+" = "+res.getInt("Sum");
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
