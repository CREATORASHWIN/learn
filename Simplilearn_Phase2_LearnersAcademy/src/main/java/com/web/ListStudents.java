package com.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.model.Student;
import com.util.HibernateSessionUtil;

@WebServlet("/list-students")
public class ListStudents extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public ListStudents() { }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		request.getRequestDispatcher("index.html").include(request, response);
		
		try {
			//load session factory 
			SessionFactory factory = HibernateSessionUtil.buildSessionFactory();
			// create a session object
			Session session = factory.openSession();
			// read students
			List<Student> students = session.createQuery("from Student").list();
			
			//show data as table.
			out.print("<h1> <center>List of Students: </center> </h1>");
			
			out.print("<center><style> table,td,th {"
					+ "border:1px solid black;"
					+ "padding: 6px; "
					+ "}</style></center>");
			out.print("<center><table ></center>");
			out.print("<tr>");
				out.print("<th> Student Id</th>");
				out.print("<th> Student Name</th>");
				out.print("<th> Student Age</th>");
				out.print("<th> Created </th>");
				out.print("<th> Modified </th>");
			out.print("</tr>");
			
			for(Student p : students) {
				out.print("<tr>");
				out.print("<td>"+p.getId()+"</td>");
				out.print("<td>"+p.getName()+"</td>");
				out.print("<td>"+p.getAge()+"</td>");
				out.print("<td>"+p.getCreatedAt()+"</td>");
				out.print("<td>"+p.getModifiedAt()+"</td>");
				out.print("</tr>");
			}
			out.print("</table>");
		
			//close session
			session.close();
		} catch (Exception e) {
			out.print("<center><h2 style='color:red'> Couldn't load the list of Students!!! </h2></center>");
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
