

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
 * Servlet implementation class WeightsEntry
 */
@WebServlet("/WeightsEntry")
public class WeightsEntry extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private String message;   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WeightsEntry() {
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
		message="";
		String Weight = request.getParameter("Weight");
		String Type=request.getParameter("Type");
		double wt=Double.parseDouble(Weight);
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
	        ResultSet res=s.executeQuery("select assign_type from assign_weights where assign_type='"+Type+"'");
	        if(res.next()){
	        	
	             PreparedStatement ps=conn.prepareStatement("update assign_weights set weight="+wt+" where assign_type='"+Type+"'");            
	             ps.executeQuery(); 
	        }
	        else{

	        	Statement st=conn.createStatement();
	        	st.executeQuery("insert into assign_weights values('"+Type+"',"+wt+")");
		         
	        }
           message+="The Information you enetered is as follows:<br>";
           message+="Assign_Type:"+Type+"<br>New Weight:"+Weight;
		// set User object in request object and set URL
		request.setAttribute("message", message);
		url2="/output.jsp";
		getServletContext().getRequestDispatcher(url2).forward(request, response);
		}catch(Exception e){
			System.out.println(e.getMessage());

		}
	

	}

}
