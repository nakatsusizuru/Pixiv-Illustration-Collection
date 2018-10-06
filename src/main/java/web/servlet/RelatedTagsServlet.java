package web.servlet;

import service.impl.RelatedTagsServiceImpl;
import utils.HeaderUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "RelatedTagsServlet" ,urlPatterns = "/t")
public class RelatedTagsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doGet(request, response);
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String keyword = request.getParameter("keyword");
        String tags = null;
        tags = new RelatedTagsServiceImpl().getRelatedTagsString(keyword);
        HeaderUtil.decorateResponse(response);
        PrintWriter out = response.getWriter();
        try {
            out.write(tags);
        } finally {
            out.close();
        }
    }
}
