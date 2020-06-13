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

@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        UserManager userManager=new UserManager();
        User user = userManager.getByEmailAndPassword(email, password);
        List<User> allUsers = userManager.getAllUsers();

        if (user!=null){
            req.setAttribute("user",user);
            req.setAttribute("allUsers", allUsers);
            req.getRequestDispatcher("home.jsp").forward(req,resp);
        } else {
            req.setAttribute("message","Email or password invalid");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
    }
}
