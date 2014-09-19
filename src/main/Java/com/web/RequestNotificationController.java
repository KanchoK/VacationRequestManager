package com.web;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by R500 on 20.8.2014 г..
 */

//RequestNotificationController returns JSON with array of notifications which is used in the pop-up message after the login of a manager or admin
public class RequestNotificationController extends HttpServlet{
    public void doPost(HttpServletRequest request, HttpServletResponse response){
        JSONObject obj = new JSONObject();

        List<String> notifications = new ArrayList<String>();
        HttpSession session = request.getSession();
        notifications = CrudDao.getRequestNotifications((Integer)session.getAttribute("employeeID"));
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        obj.put("endIndex", notifications.size());
        JSONArray array = new JSONArray();
        for(int i = 0; i < notifications.size(); i++){
            array.put(notifications.get(i));
        }
        obj.put("notifications", array);

        try {
            PrintWriter out = response.getWriter();
            out.println(obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
