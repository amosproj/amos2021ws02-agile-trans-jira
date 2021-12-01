package de.tuberlin.amos.gr2.backend_mockup;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import de.tuberlin.amos.gr2.entity.Users;
import de.tuberlin.amos.gr2.service.UserService;
import net.java.ao.Query;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class UserServlet extends HttpServlet {

    @ComponentImport
    private final ActiveObjects ao;
    private final UserService userService;

    @Inject
    public UserServlet(ActiveObjects ao, UserService userService)
    {
        this.ao = ao;
        this.userService = userService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {
        final PrintWriter w = res.getWriter();
        w.write("<h1>User Mockup Service</h1>");

        // the form to post more TODOs
        w.write("<form method=\"post\">");
        w.write("<input type=\"text\" name=\"name\" size=\"25\"/>");
        w.write("  ");
        w.write("<input type=\"submit\" name=\"submit\" value=\"Add\"/>");
        w.write("</form>");

        w.write("<form method=\"post\">");
        w.write("<input type=\"text\" name=\"delete_id\" size=\"25\"/>");
        w.write("  ");
        w.write("<input type=\"submit\" name=\"delete\" value=\"Delete\"/>");
        w.write("</form>");

        w.write("<ol>");

        for(Users user: userService.getAllUsers()){

            w.write("ID: "+String.valueOf(user.getID()));
            w.write("    ");
            w.write("name: "+ user.getName());
            w.write("<br/>");

        }

        w.write("</ol>");
        w.write("<script language='javascript'>document.forms[0].elements[0].focus();</script>");

        w.close();

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {
        final String name = req.getParameter("name");


        if(name!=null){
            userService.add(name);
        }
        if(req.getParameter("delete_id")!=null){
            final int id = Integer.parseInt(req.getParameter("delete_id"));
            userService.delete(id);
        }


        res.sendRedirect(req.getContextPath() + "/plugins/servlet/user/list");



    }



}
