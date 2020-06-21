package servlet;

import manager.LessonManager;
import manager.UserManager;
import model.Gender;
import model.User;
import model.UserType;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@WebServlet(urlPatterns = "/register")
public class RegisterServlet extends HttpServlet {

    private LessonManager lessonManager = new LessonManager();
    private UserManager userManager = new UserManager();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("lessons", lessonManager.getAllLessons());
        req.getRequestDispatcher("register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String gender = req.getParameter("gender");
        String userType = req.getParameter("userType");
        String lessonId = req.getParameter("lessonId");

        User user = User.builder()
                .name(name)
                .surname(surname)
                .email(email)
                .password(password)
                .gender(Gender.valueOf(gender))
                .userType(UserType.USER)
                .lesson(lessonManager.getLessonById(Integer.parseInt(lessonId)))
                .creationDate(new Date())
                .build();

        userManager.register(user);

        req.setAttribute("message", "You successfully registered!! Now you can login");
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }
}
