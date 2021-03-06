package com.web;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by R500 on 18.7.2014 г..
 */

//LogoutServlet redirects the user to the login page and empties the session by calling session.invalidate() method
public class LogoutServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response){
        response.setContentType("text/html");
        HttpSession session = request.getSession(false);

        if(session != null){
            session.invalidate();
        }

        try {
            response.sendRedirect("index.html");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
