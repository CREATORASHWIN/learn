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

import com.model.Teacher;
import com.util.HibernateSessionUtil;

@WebServlet("/list-teachers")
public class ListTeacher extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public ListTeacher() { }
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
			List<Teacher> teacher = session.createQuery("from Teacher").list();
			
			//show data as table.
			out.print("<center><h1> Teacher List: </h1></center>");
			
			out.print("<style> table,td,th {"
					+ "border:1px solid black;"
					+ "padding: 10px; "
					+ "}</style>");
			out.print("<center><table></center>");
			out.print("<center><tr>");
				out.print("<center><th> Teacher Id</th></center>");
				out.print("<center><th> Teacher Name</th></center>");
				out.print("<center><th> Teacher emp ID </th></center>");
			out.print("</tr>");
			
			for(Teacher p : teacher) {
				out.print("<tr>");
				out.print("<td>"+p.getId()+"</td>");
				out.print("<td>"+p.getTeachname()+"</td>");
				out.print("<td>"+p.getEcode()+"</td>");
				out.print("</tr>");
			}
			out.print("</table>");
			//close session
			session.close();
		} catch (Exception e) {
			out.print("<center><h2 style='color:red'> Couldn't load the list of Teachers!!! </h2></center>");
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
