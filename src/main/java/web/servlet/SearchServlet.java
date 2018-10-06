package web.servlet;

import service.impl.SearchServiceImpl;
import utils.HeaderUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "SearchServlet", urlPatterns = "/s")
public class SearchServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws  IOException {
        request.setCharacterEncoding("utf-8");
        String keyword = request.getParameter("keyword");
        int page = Integer.parseInt(request.getParameter("page"));
        String result = null;
        try {
            result = new SearchServiceImpl().orderByPop(keyword, page);
        } catch (Exception e) {
            e.printStackTrace();
        }
        HeaderUtil.decorateResponse(response);
        PrintWriter out =  response.getWriter();
        try {
            out.write(result);
        }
        finally {
            out.close();
        }

    }

}
