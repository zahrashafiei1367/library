import dao.UserDao;
import dto.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "Auotentication")
public class Auotentication extends HttpServlet {
    private final UserDao userDao = new UserDao();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("uname");
        String password = request.getParameter("psw");
        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();
        writer.println("<!DOCTYPE html>");
        writer.println("<html>");
        try {
            User user = userDao.signIn(username, password);
            ServletContext context = getServletContext();
            context.setAttribute("username", username);
            if (user.isAdmin()) {
                context.setAttribute("admin", "admin");
                RequestDispatcher rd = request.getRequestDispatcher("admin.jsp");
                rd.forward(request, response);
            } else {
                writer.println("<head>welcome " + user.getName() + "</head>");
                writer.println("<a href=\"signOut\">sign out</a>");
                writer.println("</html>");
            }
        } catch (Exception e) {
            writer.println("username or password is wrong");
            RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
            rd.include(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)  {

    }
}
