package pl.coderslab.user;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/edit")
public class UserEdit extends HttpServlet {
    private static userDAO userDAO = new userDAO();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String id = req.getParameter("id");
        User user = userDAO.read(Integer.parseInt(id));
        req.setAttribute("user", user);
        getServletContext().getRequestDispatcher("/editform.jsp")
                .forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String id = req.getParameter("id");
        User user = userDAO.read(Integer.parseInt(id));
        String username = req.getParameter("userName");
        String pass = req.getParameter("userPassword");
        String email = req.getParameter("userEmail");

        user.setPassword(pass);
        user.setUsername(username);
        user.setEmail(email);
        userDAO.update(user);

        resp.sendRedirect("/user/list");


    }
}
