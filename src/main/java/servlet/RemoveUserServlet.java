package servlet;

import manager.UserManager;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/removeUser")
public class RemoveUserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        int userId= Integer.parseInt(id);

        UserManager userManager=new UserManager();
        userManager.deleteUserById(userId);

        List<User> allUsers = userManager.getAllUsers();
        req.setAttribute("allUsers", allUsers);
        req.getRequestDispatcher("home.jsp").forward(req, resp);
    }
}
