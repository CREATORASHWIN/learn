package com.web;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import com.model.Student;
import com.util.HibernateSessionUtil;

//import sun.print.resources.serviceui_es;

/**
 * Servlet implementation class InitSession
 */
@WebServlet("/delete-student")
public class DeleteStudent extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DeleteStudent() {
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession(false);
		if(session != null) {
				out.println("<center><h2 style='color:green'> Welcome to Admin Access Page </h2></center>");
		
				request.getRequestDispatcher("delete-student.html").include(request, response);
			} 
			
		else {
			out.println("<h2 style='color:red'>Login failed, Re-enter the logins </h2>");
			request.getRequestDispatcher("login.html").include(request, response);
		}

		response.setContentType("text/html");
		
		request.getRequestDispatcher("index.html").include(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		request.getRequestDispatcher("index.html").include(request, response);
		
		String studentId = request.getParameter("id");
		// build hibernate session factory
		try {
			// 1. load session factory
			SessionFactory factory = HibernateSessionUtil.buildSessionFactory();

			// 2. create a session
			Session session = factory.openSession();
			
			// 3. create transaction
			 Transaction tx = session.beginTransaction();
			 
			 //4. create student object
			 Student student = new Student();
			 student.setId(Integer.parseInt(studentId));
			 
			 //5. delete student
			 session.delete(student);
			 
			 //6. commit transaction.
			 tx.commit();

			 if (session != null) {
					out.print("<center><h2 style='color:green'> Student entry deleted sucessfully!!!</h2></center>");
				}

				// close session
				session.close();
			} catch (Exception e) {
				out.print("<center><h2 style='color:red'> Couldn't delete the Student Entry!!! </h2><center>");
			}
	}

}
