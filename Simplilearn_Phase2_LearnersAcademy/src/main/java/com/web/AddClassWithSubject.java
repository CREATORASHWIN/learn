package com.web;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import com.model.Class;
import com.model.Subject;
import com.util.HibernateSessionUtil;

@WebServlet("/add-class-with-subject")
public class AddClassWithSubject extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AddClassWithSubject() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession(false);
		if(session != null) {
		
			
				out.println("<center><h2 style='color:green'> Welcome to Admin Access Page </h2></center>");
		
				request.getRequestDispatcher("add-class-with-subject.html").include(request, response);
			} 
			
		else {
			out.println("<h2 style='color:red'>Login failed, Re-enter the logins </h2>");
			request.getRequestDispatcher("login.html").include(request, response);
		}

		response.setContentType("text/html");
		
		request.getRequestDispatcher("index.html").include(request, response);
	

	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		request.getRequestDispatcher("index.html").include(request, response);

		// get order params
		String classPos = request.getParameter("clas");
		String schoolName =request.getParameter("schname");
		

		// get product details
		String subjectOneName = request.getParameter("name1");
		String subjectOneCode =request.getParameter("code1");
		
		
		String subjectTwoName = request.getParameter("name2");
		String subjectTwoCode =request.getParameter("code2");
		
		
		String subjectThreeName = request.getParameter("name3");
		String subjectThreeCode =request.getParameter("code3");
	
		
	
		
		// build hibernate session factory
		try {
			// 1. load session factory
			SessionFactory factory = HibernateSessionUtil.buildSessionFactory();

			// 2. create a session
			Session session = factory.openSession();

			// 3. create transaction
			Transaction tx = session.beginTransaction();

			// 4. create order object
			Class csub = new Class (classPos,schoolName);
			
			Set<Subject> subjects= new HashSet<>();
			Subject subject1 = new Subject(subjectOneName,subjectOneCode);
			Subject subject2 = new Subject(subjectTwoName,subjectTwoCode);
			Subject subject3 = new Subject(subjectThreeName,subjectThreeCode);
		
			subjects.add(subject1);
			subjects.add(subject2);
			subjects.add(subject3);
			
			// add products list to order
			csub.setSubjects(subjects);
			
			// 5. save product
			session.save(csub);

			// 6. commit transaction.
			tx.commit();

			if (session != null) {
				out.print("<center><h2 style='color:green'> Stored Details sucessfully!!! </h2></center>");
			}

			// close session
			session.close();
		} catch (Exception e) {
			out.print("<center><h2 style='color:red'> Storing details failed!!! </h2></center>"+e);
		}

	}

}