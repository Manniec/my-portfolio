package com.google.sps.servlets;

import java.io.IOException;
import java.io.PrintWriter; //to out html
//Webservlet stuff
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//for datastore 
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
//for querying datastore
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
//for user API
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
//formating json
import com.google.gson.Gson;

@WebServlet("/loginstat")
public class LoginServlet extends HttpServlet {

     public final class Login {
        private boolean loggedIn; //is user logged-in
        private String email;
        private String username;
        private String url;

        public Login(){
            this.loggedIn = false;
            this.email = "";
            this.username = "";
            this.url = "";
        }
        public void setLoggedIn(String userEmail, String userName, String logouturl) {
            this.loggedIn = true;
            this.email = userEmail;
            this.username = userName;
            this.url = logouturl;
        }
        public void setLogoutURL(String logouturl){
            this.url = logouturl;
        }
        /*
        public Login(boolean logstat, String userEmail, String userName) {
            this.loggedIn = logstat;
            this.email = userEmail;
            this.username = userName;
        }
        */
       
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //set response to json
        response.setContentType("application/json;");

        UserService userService = UserServiceFactory.getUserService();
        Login userlogstat = new Login();
        if (userService.isUserLoggedIn()) {
            String email = userService.getCurrentUser().getEmail();
            String username = getUsername(userService.getCurrentUser().getUserId());
            String logoutUrl = userService.createLogoutURL("/");
            userlogstat.setLoggedIn(email, username, logoutUrl);
        }else{
            String logoutUrl = userService.createLoginURL("/");
            userlogstat.setLogoutURL(logoutUrl);
        }
        //convert login status to json
        String json = new Gson().toJson(userlogstat);
        response.getWriter().println(json);

        /*
        response.setContentType("text/html");
        UserService userService = UserServiceFactory.getUserService();//user login api
        if (userService.isUserLoggedIn()) {
            String username = getUsername(userService.getCurrentUser().getUserId());
            out.println("<h1>User Logged-in</h1>");
            out.println("<p>Username: " + username + "</p>");
            out.println("<p>Email: " + userService.getCurrentUser().getEmail()+ "</p>");
            //logout url when logged in
            String logoutUrl = userService.createLogoutURL("/loginstat");
            out.println("<p>Logout <a href=\"" + logoutUrl + "\">here</a>.</p>");
  
        }else {
            out.println("<h1>User Not Logged-in</h1>");
            //logout url when logged in
            String loginUrl = userService.createLoginURL("/loginstat");
            out.println("<p>Login <a href=\"" + loginUrl + "\">here</a>.</p>");
        }
        */
        
    }
    /*
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserService userService = UserServiceFactory.getUserService();
        if (!userService.isUserLoggedIn()) {
            response.sendRedirect("/loginstat");
            return;
        }
        String username = request.getParameter("nickname");
        String id = userService.getCurrentUser().getUserId();
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Entity entity = new Entity("UserInfo", id);
        entity.setProperty("id", id);
        entity.setProperty("nickname", nickname);
        // The put() function automatically inserts new data or updates existing data based on ID
        datastore.put(entity);

        response.sendRedirect("/home");
    }*/

    //get username
    private String getUsername(String id) {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        //query by id
        Query query = new Query("UserInfo").setFilter(new Query.FilterPredicate("id", Query.FilterOperator.EQUAL, id));
        Entity entity = datastore.prepare(query).asSingleEntity();
        if (entity == null) {
            return "";
        }
        String nickname = (String) entity.getProperty("nickname");
        return nickname;
    }
}