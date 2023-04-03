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


import com.model.Class;
import com.util.HibernateSessionUtil;

@WebServlet("/list-class")
public class ListClass extends HttpServlet {
private static final long serialVersionUID = 1L;
       

    public ListClass() { }
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

response.setContentType("text/html");
PrintWriter out = response.getWriter();
request.getRequestDispatcher("index.html").include(request, response);

try {
//load session factory
SessionFactory factory = HibernateSessionUtil.buildSessionFactory();
// create a session object
Session session = factory.openSession();
// read products
List<Class> classes = session.createQuery("from Class").list();

//show data as table.
out.print("<center><h1> Class List: </h1></center>");

out.print("<center><style> table,td,th {"
+ "border:1px solid black;"
+ "padding: 10px; "
+ "}</style></center>");
out.print("<center><table></center>");
out.print("<center><tr><center>");

out.print("<center><th> Class ID</th></center>");
out.print("<center><th> Class </th></center>");
out.print("<center><th> SCHOOL NAME</th></center>");

out.print("</tr>");

for(Class p : classes) {
out.print("<tr>");

out.print("<td>"+p.getId()+"</td>");
out.print("<td>"+p.getClassPos()+"</td>");
out.print("<td>"+p.getSchoolName()+"</td>");
   
out.print("</tr>");
}
out.print("</table>");

//close session
session.close();
} catch (Exception e) {
out.print("<center><h2 style='color:red'> Couldn't load the list of Classes!!! </h2></center>");
}

}

protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
doGet(request, response);
}

}
