package com.web.filter;

import com.web.DateCompare;
import com.web.EmployeeAttributes;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

/**
 * Created by R500 on 16.7.2014 г..
 */

// the filter checks if the requests to the server are accessible at different cases
// and if they are NOT accessible the filter redirects the user to an available page
@WebFilter("/AuthenticationFilter")
public class AuthenticationFilter implements Filter {
    private ServletContext context;

    public void init(FilterConfig filterConfig){
        this.context = filterConfig.getServletContext();
        this.context.log("AuthenticationFilter initialized");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain){
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse res = (HttpServletResponse)response;

        String uri = req.getRequestURI();
        this.context.log("Requested Resource::" + uri);

        HttpSession session = req.getSession(false);
        int access = session!=null&&session.getAttribute("access")!=null?(Integer)session.getAttribute("access"):0;

//      check if the link for changing a forgotten password is accurate, checks the email, code and expiry date
//      if the link is accurate it proceeds to the forgotten password page, if NOT it redirects the user to the login page
        if (req.getParameter("email") != null && req.getParameter("code") != null) {
            String email = req.getParameter("email");
            Map data = EmployeeAttributes.getForgottenPasswordCodeAndLinkExpiryDate(email);
            String code = String.valueOf(data.get("code"));
            String expiryDateString = String.valueOf(data.get("expiryDate"));
            DateFormat df = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH);
            Date expiryDate = null;
            if(!expiryDateString.equals("")) {
                try {
                    expiryDate = df.parse(expiryDateString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            if (code.equals(req.getParameter("code")) && !(code.equals("")) && DateCompare.CompareJavaDates(new Date(), expiryDate)) {
                session = req.getSession();
                session.setAttribute("email", email);
                session.setAttribute("access", EmployeeAttributes.getEmployeeAccessLevel(email));
                session.setAttribute("employeeID", EmployeeAttributes.getEmployeeID(email));
                session.setAttribute("accountStatus", EmployeeAttributes.getAccountStatus(email));
                try {
                    res.sendRedirect("#/forgottenPassword");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    res.sendRedirect("#/login");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
//      this "else if" gives the accessible resources if the session is null(when no user is logged in)
//      if an inaccessible resource is required the user is redirected to the login page
        else if((session == null || session.getAttribute("email") == null) &&
                (!(uri.endsWith("VacationRequestManager/") || uri.endsWith("index.html") || uri.endsWith("signUp.html") || uri.endsWith(".js") || uri.endsWith(".png")
                        || uri.endsWith("login.html") || uri.endsWith(".css") || uri.endsWith("/LoginServlet") || uri.endsWith("/SignUpServlet") || uri.endsWith("/ForgottenPassword")))){
            this.context.log("Unauthorized access request");
            try {
                res.sendRedirect("login.html");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//      this "else if" checks for the access level of the user and forbid normal users to access requestManager.html
//      if a normal user try to access requestManager.html he is redirected to myRequests.html
        else if (session != null && (access != 1 && access != 2) && (uri.endsWith("requestManager.html"))){
            this.context.log("Unauthorized access request");
            try {
                res.sendRedirect("myRequests.html");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//      this "else if" checks for the access level of the user and forbid normal users and managers to access controlPanel.html and holidaysManager.html
//      if a normal user or a manager try to access controlPanel.html and holidaysManager.html they are redirected to myRequests.html
        else if (session != null && access != 1 && (uri.endsWith("controlPanel.html") || uri.endsWith("holidaysManager.html"))){
            this.context.log("Unauthorized access request");
            try {
                res.sendRedirect("myRequests.html");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                chain.doFilter(request, response);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ServletException e) {
                e.printStackTrace();
            }
        }
    }

    public void destroy(){}
}
