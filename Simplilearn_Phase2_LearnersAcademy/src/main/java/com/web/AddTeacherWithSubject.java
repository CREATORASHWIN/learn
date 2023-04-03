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
import com.model.Subject;
import com.model.Teacher;
import com.util.HibernateSessionUtil;


@WebServlet("/add-teacher-with-subject")
public class AddTeacherWithSubject extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AddTeacherWithSubject() {}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession(false);
		if(session != null) {
		
			
				out.println("<center><h2 style='color:green'> Welcome to Admin Access Page </h2></center>");
		
				request.getRequestDispatcher("add-teacher-with-subject.html").include(request, response);
			} 
			
		else {
			out.println("<h2 style='color:red'>Login failed, Re-enter the logins </h2>");
			request.getRequestDispatcher("login.html").include(request, response);
		}

		response.setContentType("text/html");
		
		request.getRequestDispatcher("index.html").include(request, response);
	

	}
    

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		request.getRequestDispatcher("index.html").include(request, response);
		//student
		String teacherName = request.getParameter("tname");
		String teacherempcode = request.getParameter("emp_c");
		//student-details
		String name = request.getParameter("subname");
		String subcode = request.getParameter("subcode");
		
		// build hibernate session factory
		try {
			// 1. load session factory
			SessionFactory factory = HibernateSessionUtil.buildSessionFactory();

			// 2. create a session
			Session session = factory.openSession();
			
			// 3. create transaction
			 Transaction tx = session.beginTransaction();
			 
			 //4. create student object & student details
			 Teacher teacher = new Teacher(teacherName,teacherempcode );
			 Subject sub = new Subject(name,subcode);
			 teacher.setSubject(sub);
			 
			 //5. save student
			 session.save(teacher);
			 
			 //6. commit transaction.
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
