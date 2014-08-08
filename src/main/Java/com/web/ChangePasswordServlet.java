package com.web;

import org.json.JSONObject;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by R500 on 22.7.2014 г..
 */
public class ChangePasswordServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response){
        String oldPass = request.getParameter("oldPassword");
        HttpSession session = request.getSession();
        String oldPassFromDB = CrudDao.getPassword((Integer) session.getAttribute("employeeID"));
        String oldConvertedPass = Utility.toSHA1(Utility.salt(oldPass).getBytes());
        String newPass = request.getParameter("newPassword");
        String confirmNewPass = request.getParameter("confirmNewPassword");
        JSONObject obj = new JSONObject();

        if (oldPass.trim().equals("") || newPass.trim().equals("") || confirmNewPass.trim().equals("")){
            obj.put("message", "You must fill all fields.");
        } else if (!(newPass.equals(confirmNewPass))) {
            obj.put("message", "The New password must be the same as the Confirm new password field.");
        } else if (!(oldPassFromDB.equals(oldConvertedPass))) {
            obj.put("message", "Wrong Old password.");
        } else {
            String newConvertedPass = Utility.toSHA1(Utility.salt(newPass).getBytes());
            CrudDao.updateEmployeePassword(newConvertedPass, (Integer) session.getAttribute("employeeID"));

            if (session.getAttribute("accountStatus").equals(0)){
                CrudDao.updateEmployeeAccountStatus((Integer) session.getAttribute("employeeID"));
                session.setAttribute("accountStatus", 1);
            }

            int access = (Integer)session.getAttribute("access");
            obj.put("message", "");
            obj.put("access", access);
        }
        try {
            PrintWriter out = response.getWriter();
            out.println(obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
