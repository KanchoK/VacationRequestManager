package com.web;

import org.json.JSONObject;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by R500 on 12.9.2014 г..
 */
public class SessionProvider extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        JSONObject obj = new JSONObject();

        if (request.getParameter("action").equals("getEmployeeID")){
            obj.put("employeeID", session.getAttribute("employeeID"));
        } else if (request.getParameter("action").equals("getAccess")){
            obj.put("accessLevel", session.getAttribute("access"));
        } else if (request.getParameter("action").equals("getAccountStatus")){
            obj.put("accountStatus", session.getAttribute("accountStatus"));
        } else if (request.getParameter("action").equals("getEmail")){
            obj.put("email", session.getAttribute("email"));
        } else if (request.getParameter("action").equals("getAccountStatusAndAccess")) {
            obj.put("accountStatus", session.getAttribute("accountStatus"));
            obj.put("accessLevel", session.getAttribute("access"));
        }

        try {
            PrintWriter out = response.getWriter();
            out.println(obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
