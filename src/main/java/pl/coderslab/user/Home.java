package pl.coderslab.user;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.print.Book;
import java.io.IOException;
import java.util.Arrays;


@WebServlet("/home")
public class Home extends HttpServlet {
    private static userDAO userDAO = new userDAO();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        getServletContext().getRequestDispatcher("/home.jsp")
                .forward(req, resp);
    }

}


