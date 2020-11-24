import dao.UserDao;
import dto.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

@WebServlet(name = "Registration")
public class Registration extends HttpServlet {
    private final UserDao userDao = new UserDao();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();
        String name = request.getParameter("name");
        String family = request.getParameter("family");
        String username = request.getParameter("username");
        String password = request.getParameter("psw");
        User user = new User();
        user.setName(name);
        user.setFamily(family);
        user.setUsername(username);
        user.setPassword(password);
        try {
            userDao.insert(user);
            writer.println("registration is successfully done!");
            RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
            rd.include(request, response);
        } catch (Exception e) {
            writer.println(Arrays.toString(e.getStackTrace()));
            RequestDispatcher rd = request.getRequestDispatcher("register.jsp");
            rd.include(request, response);
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)  {

    }

}
