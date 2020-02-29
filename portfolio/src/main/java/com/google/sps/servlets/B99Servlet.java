
package com.google.sps.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/b99")
public class B99Servlet extends HttpServlet {

    private List<String> quotes;

    //make array of possible quotes

    @Override
    public void init() {
        quotes = new ArrayList<>();
        quotes.add("If I die, turn my tweets into a book. -Gina Linetti");
        quotes.add("The English language can not fully capture the depth and complexity of my thoughts, so I’m incorporating emojis into my speech to better express myself. Winky face. - Ginna Linetti");
        quotes.add("I have zero interest in food. If it were feasible, my diet would consist entirely of flavorless beige smoothies containing all the nutrients required by the human animal. - Captain Ray Holt");
        quotes.add("I only have one dream a year, always on Tax Day. In it, I must file an extension. So, yes, it is best not to have dreams. - Captian Ray Holt");
    }

    //return random quote from array 
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String quote = quotes.get((int) (Math.random() * quotes.size())); 
        //pick random quote
        response.setContentType("text/html;");
        response.getWriter().println(quote);
    }
}