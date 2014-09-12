package com.web;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by R500 on 12.9.2014 г..
 */
public class HolidaysManagerController extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        if (request.getParameter("action") != null) {
            String action = (String) request.getParameter("action");
            List<Holiday> holidays = new ArrayList<Holiday>();
            Gson gson = new Gson();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            if (action.equals("list")) {
                try {
//                    int startPageIndex = Integer.parseInt(request.getParameter("jtStartIndex"));
//                    int numRecordsPerPage = Integer.parseInt(request.getParameter("jtPageSize"));
//                    String sort = request.getParameter("jtSorting");
                    holidays = CrudDao.getAllHolidays();

                    JsonElement element = gson.toJsonTree(holidays, new TypeToken<List<Employee>>() {}.getType());
                    JsonArray jsonArray = element.getAsJsonArray();
                    String listData = jsonArray.toString();

                    listData = "{\"Result\":\"OK\",\"Records\":" + listData + ":}";

                    response.getWriter().print(listData);
                } catch (Exception ex) {
                    String error = "{\"Result\":\"ERROR\",\"Message\":" + ex.getStackTrace() + "}";
                    try {
                        response.getWriter().print(error);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            else if (action.equals("create")){

            }

            else if (action.equals("update")){

            }

            else if (action.equals("delete")){

            }
        }
    }
}
