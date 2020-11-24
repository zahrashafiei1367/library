import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "SignOut")
public class SignOut extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session2 = request.getSession(false);
        ServletContext context = getServletContext();
        String username = (String) context.getAttribute("username");
        if (session2 != null) {
            String str = (String) session2.getAttribute("uname");
            String[] temp = str.split(username);
            StringBuilder output = new StringBuilder();
            for (String s : temp) {
                output.append(s);
            }
            session2.setAttribute("uname", output.toString());
        }
        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();
        writer.println("you are successfully signed out!");
        RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
        rd.include(request, response);
    }
}
