package web.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.Illustration;
import service.impl.DailyRankServiceImpl;
import utils.HeaderUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "DailyRankServlet", urlPatterns = "/d")
public class DailyRankServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String date = request.getParameter("date");
        int page = Integer.parseInt(request.getParameter("page"));
        HeaderUtil.decorateResponse(response);
        List<Illustration> illustrations;
        PrintWriter out = null;
        String result;
        try {
            illustrations = new DailyRankServiceImpl().getDailyRank(date, page);
            result = new ObjectMapper().writeValueAsString(illustrations);
            out = response.getWriter();
            out.write(result);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            out.close();
        }

    }
}
