// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.servlets;

import java.io.IOException;
//for formating json
import com.google.gson.Gson;
//for datastore nosql
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
//for querying datastore
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
//for array lists
import java.util.ArrayList;
import java.util.List;
//for webservelts/get/post 
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//for user API
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data")
public class DataServlet extends HttpServlet {

    //Loads Entities from Datastore

    //build java struct/class for comments
    public final class Comment {
        private final long id; //unique key from datastore
        private String text;
        private long timestamp;
        private String email;

        public Comment(long id, String text, long timestamp, String email) {
            this.id = id;
            this.text = text;
            this.timestamp = timestamp;
            this.email = email;
        }
    }
   

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Query query = new Query("Comments").addSort("timestamp", SortDirection.DESCENDING);

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        PreparedQuery results = datastore.prepare(query);

        List<Comment> listComments = new ArrayList<>(); //list of comments
        for (Entity entity : results.asIterable()) {//iterate through results of query
            //for each entinty get id, text, timestamp form comment
            long id = entity.getKey().getId(); 
            String text = (String) entity.getProperty("text");
            long timestamp = (long) entity.getProperty("timestamp");
            String email = (String) entity.getProperty("email");

            //make new comment and add to list of comments
            Comment comment = new Comment(id, text, timestamp, email);
            listComments.add(comment);
        }

        //convert list of comments to json
        String json = new Gson().toJson(listComments);
        
        //set to return json
        response.setContentType("application/json;");
        response.getWriter().println(json);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //get text from form by id
        String newComment = request.getParameter("comment-input");
        //timestamp
        long timestamp = System.currentTimeMillis();
        //instance of login api
        UserService userService = UserServiceFactory.getUserService();
        String email = userService.getCurrentUser().getEmail();

        //create entity type of comments with parts .timestamp and .text
        Entity commentEntity = new Entity("Comments");
        commentEntity.setProperty("timestamp", timestamp);
        commentEntity.setProperty("text", newComment);
        commentEntity.setProperty("email", email);

        //instance of access datastore
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        datastore.put(commentEntity);   //add to datastore


        //return to page
        response.sendRedirect("/index.html");

    }
}
    



