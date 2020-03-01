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
/*
//for array lists
import java.util.ArrayList;
import java.util.List;
*/
//for webservelts/get/post 
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data")
public class DataServlet extends HttpServlet {
    /*
    private List<String> strings;

    //make array of hardcoded strings
    @Override
    public void init() {
        strings = new ArrayList<>();
        strings.add("Hello World!");
        strings.add("My Name is Mannie");
        strings.add("Testing 1, 2, 3");
    }
    

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //convert to json
        String json = new Gson().toJson(strings);
        
        //set to return json
        response.setContentType("application/json;");
        response.getWriter().println(json);
    }
    */

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //get text from form by id
        String newComment = request.getParameter("comment-input");
        //timestamp
        long timestamp = System.currentTimeMillis();

        /* //add text to ArrayList
        strings.add(newComment);
        */

        Entity commentEntity = new Entity("Comments");
        commentEntity.setProperty("timestamp", timestamp);
        commentEntity.setProperty("text", newComment);

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        datastore.put(commentEntity);


        //return to page
        response.sendRedirect("/index.html");

    }
    
}


