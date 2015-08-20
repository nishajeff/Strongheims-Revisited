

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class GradebookEntry
 */
@WebServlet("/GradebookEntry")
public class GradebookEntry extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private String message;   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GradebookEntry() {
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
		String Assignment = request.getParameter("Assignment");
		String Type=request.getParameter("Assignment_Type");
		String grade= request.getParameter("Grade");
		int gr=Integer.parseInt(grade);
		System.out.println(Assignment+"\n"+gr);
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
	        ResultSet res=s.executeQuery("select student_id from sgradebook where student_name='"+Name+"'");
	        if(res.next()){
	        	int student_id=res.getInt("student_id");
	        	System.out.println(student_id);
	             PreparedStatement ps=conn.prepareStatement("insert into sgradebook values(sgrade_id_seq.nextval,"+student_id+",'"+Name+"','"+Assignment+"','"+Type+"',"+gr+")");            
	             ps.executeQuery(); 
	        }
	        else{

	        	Statement st=conn.createStatement();
	        	st.executeQuery("insert into sgradebook values(sgrade_id_seq.nextval,student_id_seq.nextval,'"+Name+"','"+Assignment+"','"+Type+"',"+gr+")");
		         
	        }
             message+="The Information you enetered is as follows:<br>";
             message+="Name:"+Name+"<br>Assignment:"+Assignment+"<br>Assignment Type:"+Type+"<br>Grade:"+grade;
		// set User object in request object and set URL
		request.setAttribute("message", message);
		url2="/output.jsp";
		getServletContext().getRequestDispatcher(url2).forward(request, response);
		}catch(Exception e){
			System.out.println(e.getMessage());

		}
	
	}
}


