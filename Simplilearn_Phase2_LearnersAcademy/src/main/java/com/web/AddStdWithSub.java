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
import com.model.Student;
import com.util.HibernateSessionUtil;

@WebServlet("/add-std-with-sub")
public class AddStdWithSub extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	public AddStdWithSub() {}
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession(false);
		if(session != null) {
		
			
				out.println("<center><h2 style='color:green'> Welcome to Admin Access Page </h2></center>");
		
				request.getRequestDispatcher("add-std-with-sub.html").include(request, response);
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

		// get student details
		String studentName = request.getParameter("name");
		String age = request.getParameter("age");
		String rollno = request.getParameter("rollno");
		
		// get payment details
		String name1 = request.getParameter("subName1");
		String subcode1 =  request.getParameter("subcode1");
		
		String name2 = request.getParameter("subName2");
		String subcode2 =  request.getParameter("subcode2");


		// build hibernate session factory
		try {
			// 1. load session factory
			SessionFactory factory = HibernateSessionUtil.buildSessionFactory();

			// 2. create a session
			Session session = factory.openSession();

			// 3. create transaction
			Transaction tx = session.beginTransaction();

			// create student object
			Student student = new Student(studentName, age ,rollno);
			
			//create payments set
			Subject sub1 = new Subject(name1 , subcode1);
			Subject sub2 = new Subject(name2, subcode2);
			Set<Subject> subjects = new HashSet<>();
			
			subjects.add(sub1);
			subjects.add(sub2);
			
			//set payments to student
			student.setSubject(subjects);
			
			// 5. save student
			session.save(student);

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
