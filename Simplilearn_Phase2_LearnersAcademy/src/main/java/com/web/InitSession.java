package com.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.util.HibernateSessionUtil;

/**
 * Servlet implementation class InitSession
 */
@WebServlet("/login")
public class InitSession extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public InitSession() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		request.getRequestDispatcher("login.html").include(request, response);
		request.getRequestDispatcher("index.html").include(request, response);
	
		// build hibernate session factory
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String user = request.getParameter("username");
		String  pass= request.getParameter("password");
		
		response.setContentType("text/html");
		
		PrintWriter out = response.getWriter();
		
		System.out.println(user);
		if(user.equals("User")&&pass.equals("pass"))
		{
			HttpSession session = request.getSession(true);
			//set session attribute
			session.setAttribute("username", user);
			session.setAttribute("token", UUID.randomUUID());
			// 1. load session factory
			SessionFactory factory = HibernateSessionUtil.buildSessionFactory();

			// 2. create a session
			Session session1 = factory.openSession();

			if (session1 != null) {
				out.print("<h2 style='color:green'>Login Successful!!! Proceed With the Administrative Portal </h2>");
			}

			// close session
			session1.close();
		
			request.getRequestDispatcher("index.html").include(request, response);
		
		
		}
		else {
			out.print("<h2 style='color:red'>Login failed, Re-enter the logins </h2>");
			request.getRequestDispatcher("index.html").include(request, response);
		}
		}
	}
	
		

