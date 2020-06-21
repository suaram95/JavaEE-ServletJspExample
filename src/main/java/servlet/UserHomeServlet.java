package servlet;

import manager.LessonManager;
import manager.UserManager;
import model.Lesson;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = "/userHome")
public class UserHomeServlet extends HttpServlet {

    private UserManager userManager=new UserManager();
    private LessonManager lessonManager=new LessonManager();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user==null){
            resp.sendRedirect("/login.jsp");
        } else{
            req.setAttribute("users", userManager.getAllUsers());
            req.setAttribute("lessons", lessonManager.getAllLessons());
            req.getRequestDispatcher("/userHome.jsp");
        }
    }
}
