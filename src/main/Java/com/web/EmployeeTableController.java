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
 * Created by R500 on 21.7.2014 г..
 */

//EmployeeTableController controls the jTable from the controlPanel.html
public class EmployeeTableController extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        if (request.getParameter("action") != null) {
            String action = (String) request.getParameter("action");
            List<Employee> employees = new ArrayList<Employee>();
            Gson gson = new Gson();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

//            list action takes all employees' data from the database via CrudDao and fills the jTable with the info
            if (action.equals("list")) {
                try {
                    int startPageIndex = Integer.parseInt(request.getParameter("jtStartIndex"));
                    int numRecordsPerPage = Integer.parseInt(request.getParameter("jtPageSize"));
                    String sort = request.getParameter("jtSorting");
                    employees = CrudDao.getAllEmployees(startPageIndex, numRecordsPerPage, sort);
                    int employeeCount = CrudDao.getEmployeeCount();

                    JsonElement element = gson.toJsonTree(employees, new TypeToken<List<Employee>>() {}.getType());
                    JsonArray jsonArray = element.getAsJsonArray();
                    String listData = jsonArray.toString();

                    listData = "{\"Result\":\"OK\",\"Records\":" + listData + ",\"TotalRecordCount\":" + employeeCount + "}";

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

//            create action creates new employee in the database and adds it to the jTable
//            the new user's password is set to "initial", the vacationStatus is set to 0
            else if (action.equals("create")){
                Employee employee = new Employee();
                String convertedPass = Utility.toSHA1(Utility.salt("initial").getBytes());

                employee.setfName(request.getParameter("fName"));
                employee.setSurname(request.getParameter("surname"));
                employee.setlName(request.getParameter("lName"));

                employee.setEmployeeName(request.getParameter("fName") + " " + request.getParameter("surname") + " " + request.getParameter("lName"));
                employee.setEmail(request.getParameter("email"));
                employee.setPassword(convertedPass);
                employee.setAccessLevel(Integer.parseInt(request.getParameter("accessLevel")));
                employee.setAccountStatus(0);
                employee.setMyManager(Integer.parseInt(request.getParameter("myManager")));
                employee.setVacationDaysLeft(Integer.parseInt(request.getParameter("vacationDaysLeft")));

                try {
                    employee.setEmployee_id(CrudDao.addEmployee(employee));
                    employees.add(employee);
                    String json = gson.toJson(employee);
                    String listData = "{\"Result\":\"OK\",\"Record\":" + json + "}";
                    try {
                        response.getWriter().print(listData);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }catch (Exception ex){
                    String error = "{\"Result\":\"ERROR\",\"Message\":"+ex.getStackTrace()+"}";
                    try {
                        response.getWriter().print(error);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

//            update action updates the selected employee from the jTable and update the data in the database
            else if (action.equals("update")){
                Employee employee = new Employee();
//                String convertedPass = Utility.toSHA1(Utility.salt(request.getParameter("password")).getBytes());
                employee.setEmployee_id(Integer.parseInt(request.getParameter("employee_id")));
                employee.setfName(request.getParameter("fName"));
                employee.setSurname(request.getParameter("surname"));
                employee.setlName(request.getParameter("lName"));

                employee.setEmployeeName(request.getParameter("fName") + " " + request.getParameter("surname") + " " + request.getParameter("lName"));
                employee.setEmail(request.getParameter("email"));
//                employee.setPassword(convertedPass);
                employee.setAccessLevel(Integer.parseInt(request.getParameter("accessLevel")));
                employee.setMyManager(Integer.parseInt(request.getParameter("myManager")));
                employee.setVacationDaysLeft(Integer.parseInt(request.getParameter("vacationDaysLeft")));
                CrudDao.updateEmployee(employee);
                CrudDao.updateVacationManager(employee.getEmployee_id(), employee.getMyManager());

                String listData="{\"Result\":\"OK\"}";
                try {
                    response.getWriter().print(listData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

//           !!!CascadeDelete!!! delete action deletes the selected record BUT first it deletes all the vacation requests of the employee that we want to delete
            else if (action.equals("delete")){
                try{
                    if(request.getParameter("employee_id") != null){
                        String employeeID = (String)request.getParameter("employee_id");
                        CrudDao.cascadeDeleteEmployee(Integer.parseInt(employeeID));
                        String listData="{\"Result\":\"OK\"}";
                        response.getWriter().print(listData);
                    }
                }catch(Exception ex){
                    String error="{\"Result\":\"ERROR\",\"Message\":"+ex.getStackTrace().toString()+"}";
                    try {
                        response.getWriter().print(error);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}

