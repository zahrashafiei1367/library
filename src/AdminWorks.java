import dao.BookDao;
import dao.BorrowingDao;
import dao.UserDao;
import dto.Book;
import dto.Borrowing;
import dto.Category;
import dto.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Objects;

@WebServlet(name = "AdminWorks")
public class AdminWorks extends HttpServlet {
    private final UserDao userDao = new UserDao();
    private final BookDao bookDao = new BookDao();
    private final BorrowingDao borrowingDao = new BorrowingDao();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)  {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        String job = request.getParameter("id");
        switch (job) {
            case "user":
                try {
                    request.setAttribute("users", userDao.showAll());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                RequestDispatcher rd = request.getRequestDispatcher("user.jsp");
                rd.forward(request, response);
                break;
            case "book":
                try {
                    request.setAttribute("books", bookDao.showAll());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                rd = request.getRequestDispatcher("book.jsp");
                rd.forward(request, response);
                break;
            case "borrow":
                rd = request.getRequestDispatcher("borrow.jsp");
                rd.forward(request, response);
                break;
            case "borrowAction":
                borrow(request, response);
                break;
            case "return":
                rd = request.getRequestDispatcher("return.jsp");
                rd.forward(request, response);
                break;
            case "returnAction":
                returnAction(request, response);
                rd = request.getRequestDispatcher("admin.jsp");
                rd.include(request, response);
                break;
            case "reportbooks":
                rd = request.getRequestDispatcher("reportBooks.jsp");
                rd.forward(request, response);
                break;
            case "reportintrestreport":
                request.setAttribute("map", borrowingDao.reportingOfFavoriteBookClassification());
                rd = request.getRequestDispatcher("reportinterest.jsp");
                rd.forward(request, response);
                break;
            case "addUser":
                addUser(request, response);
                try {
                    request.setAttribute("users", userDao.showAll());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                rd = request.getRequestDispatcher("user.jsp");
                rd.forward(request, response);
                break;

            case "backhome":
                rd = request.getRequestDispatcher("admin.jsp");
                rd.forward(request, response);
                break;
            case "addBook":
                addBook(request, response);
                try {
                    request.setAttribute("books", bookDao.showAll());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                rd = request.getRequestDispatcher("book.jsp");
                rd.forward(request, response);
                break;
            default:
                if (job.contains("edituser")) {
                    request.setAttribute("idid", job.substring(8));
                    editUser(request);
                    try {
                        request.setAttribute("users", userDao.showAll());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    rd = request.getRequestDispatcher("user.jsp");
                    rd.forward(request, response);
                } else if (job.contains("deleteuser")) {
                    userDao.delete(Integer.parseInt(job.substring(10)));
                    try {
                        request.setAttribute("users", userDao.showAll());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    rd = request.getRequestDispatcher("user.jsp");
                    rd.forward(request, response);
                } else if (job.contains("editbook")) {
                    request.setAttribute("idid", job.substring(8));
                    editBook(request, response);
                    try {
                        request.setAttribute("books", bookDao.showAll());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    rd = request.getRequestDispatcher("book.jsp");
                    rd.forward(request, response);
                } else if (job.contains("deletebook")) {
                    bookDao.delete(Integer.parseInt(job.substring(10)));
                    try {
                        request.setAttribute("books", bookDao.showAll());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    rd = request.getRequestDispatcher("book.jsp");
                    rd.forward(request, response);
                }
                break;
        }

    }

    private void editBook(HttpServletRequest request, HttpServletResponse response) {
        String id = (String) request.getAttribute("idid");
        String name = request.getParameter("bookName");
        String writer = request.getParameter("bookWriter");
        String publisher = request.getParameter("bookPublisher");
        String category = request.getParameter("bookCategory");
        String isAvailable = request.getParameter("isAvailable");
        Book book;
        try {
            book = bookDao.findById(Integer.parseInt(id));
            if (!writer.equals(""))
                book.setWriter(writer);
            if (!publisher.equals(""))
                book.setPublisher(publisher);
            if (!category.equals(""))
                book.setCategory(Category.valueOf(category));
            if (!name.equals(""))
                book.setName(name);
            book.setAvailable(!Objects.isNull(isAvailable));
            bookDao.update(book);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addBook(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("bookName");
        String writer = request.getParameter("bookWriter");
        String publisher = request.getParameter("bookPublisher");
        String category = request.getParameter("bookCategory");
        String isAvailable = request.getParameter("isAvailable");
        Book book = new Book();
        book.setCategory(Category.valueOf(category));
        book.setName(name);
        book.setWriter(writer);
        book.setPublisher(publisher);
        book.setAvailable(!Objects.isNull(isAvailable));
        response.setContentType("text/html");
        PrintWriter wr = null;
        try {
            wr = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            bookDao.insert(book);
            wr.println("book is successfully added.");

        } catch (Exception e) {
            wr.println("problem with adding new book");
            e.printStackTrace();
        }
    }

    private void returnAction(HttpServletRequest request, HttpServletResponse response) {
        Borrowing borrowing=getBorrowing(request,true);
        response.setContentType("text/html");
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            borrowingDao.bookReturning(borrowing);
            writer.println("RETURNING SUCCESSFULLY DONE");

        } catch (Exception e) {
            writer.println("problem with returning");
            e.printStackTrace();
        }

    }

    private Borrowing getBorrowing(HttpServletRequest request, boolean b) {
        String bookId = request.getParameter("bookId");
        String userId = request.getParameter("userId");
        LocalDate localDate = LocalDate.now();
        Borrowing borrowing = new Borrowing();
        borrowing.setBookId(Integer.parseInt(bookId));
        borrowing.setUserId(Integer.parseInt(userId));
        if (b) {
            borrowing.setDateOfReturning(localDate);
        } else {
            borrowing.setDateOfBorrowing(localDate);
        }
        return borrowing;
    }

    private void borrow(HttpServletRequest request, HttpServletResponse response) {
        Borrowing borrowing=getBorrowing(request,false);
        response.setContentType("text/html");
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            borrowingDao.bookBorrow(borrowing);
            writer.println("BORROWING SUCCESSFULLY DONE");
            RequestDispatcher rd = request.getRequestDispatcher("admin.jsp");
            rd.include(request, response);
        } catch (Exception e) {
            writer.println("book id or user id is not correct.");
            RequestDispatcher rd = request.getRequestDispatcher("borrow.jsp");
            try {
                rd.include(request, response);
            } catch (Exception exception) {
                e.printStackTrace();
            }
        }
    }

    private void editUser(HttpServletRequest request) {
        String id = (String) request.getAttribute("idid");
        String name = request.getParameter("userName");
        String family = request.getParameter("userFamily");
        String username = request.getParameter("userUsername");
        String password = request.getParameter("userPassword");
        User user;
        try {
            user = userDao.findById(Integer.parseInt(id));
            if (!password.equals(""))
                user.setPassword(password);
            if (!username.equals(""))
                user.setUsername(username);
            if (!family.equals(""))
                user.setFamily(family);
            if (!name.equals(""))
                user.setName(name);
            userDao.update(user);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void addUser(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("userName");
        String family = request.getParameter("userFamily");
        String username = request.getParameter("userUsername");
        String password = request.getParameter("userPassword");
        String isAdmin = request.getParameter("isAdmin");
        User user = new User();
        user.setPassword(password);
        user.setUsername(username);
        user.setFamily(family);
        user.setName(name);
        user.setAdmin(!Objects.isNull(isAdmin));
        response.setContentType("text/html");
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            userDao.insert(user);
            writer.println("user is successfully added.");

        } catch (Exception e) {
            writer.println("problem with adding new user");
            e.printStackTrace();
        }
    }


//    private void showAll(HttpServletResponse response) throws IOException {
//        PrintWriter writer = response.getWriter();
//        Session session = sessionFactory.openSession();
//        Transaction transaction = session.beginTransaction();
//        Query query = session.createNamedQuery("showAllBooks");
//        List<Book> bookList = query.getResultList();
//        writer.println(bookList.toString());
//        Query query2 = session.createNamedQuery("showAllUsers");
//        List<User> users = query2.getResultList();
//        writer.println(users.toString());
//        transaction.commit();
//        session.close();
//    }
//
//    private void displayBook(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        response.setContentType("text/html");
//        PrintWriter wr = response.getWriter();
//    }
//
//    private void updateBook(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        String bookId = request.getParameter("bookId");
//        String name = request.getParameter("name");
//        String writer = request.getParameter("writer");
//        response.setContentType("text/html");
//        PrintWriter wr = response.getWriter();
//        if (idIsCorrect(bookId)) {
//            Session session = sessionFactory.openSession();
//            Transaction transaction = session.beginTransaction();
//            Book book = session.get(dto.Book.class, Integer.parseInt(bookId));
//            if (book == null)
//                wr.println("book id is not correct");
//            else {
//                book.setWriter(writer);
//                book.setName(name);
//            }
//            transaction.commit();
//            session.close();
//            wr.println("updating name or writer was successful");
//        }
//    }
//
//
//    private void giveBack(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        String bookId = request.getParameter("bookId");
//        String userId = request.getParameter("userId");
//        response.setContentType("text/html");
//        PrintWriter writer = response.getWriter();
//        if (idIsCorrect(bookId) & idIsCorrect(userId)) {
//            Session session = sessionFactory.openSession();
//            Transaction transaction = session.beginTransaction();
//            Book book = session.get(dto.Book.class, Integer.parseInt(bookId));
//            User user = session.get(dto.User.class, Integer.parseInt(userId));
//            if (book == null)
//                writer.println("book id is not correct");
//            else if (user == null)
//                writer.println("user id is not correct");
//            else {
//                book.getUsers().remove(user);
//                user.getBooks().remove(book);
//            }
//            transaction.commit();
//            session.close();
//            writer.println("giving back book was successful");
//        }
//    }
//
//    private void borrow(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        String bookId = request.getParameter("bookId");
//        String userId = request.getParameter("userId");
//        response.setContentType("text/html");
//        PrintWriter writer = response.getWriter();
//        if (idIsCorrect(bookId) & idIsCorrect(userId)) {
//            Session session = sessionFactory.openSession();
//            Transaction transaction = session.beginTransaction();
//            Book book = session.get(dto.Book.class, Integer.parseInt(bookId));
//            User user = session.get(dto.User.class, Integer.parseInt(userId));
//            if (book == null)
//                writer.println("book id is not correct");
//            else if (user == null)
//                writer.println("user id is not correct");
//            else {
//                book.getUsers().add(user);
//                user.getBooks().add(book);
//            }
//            transaction.commit();
//            session.close();
//            writer.println("borrowing book was successful");
//        }
//    }
//
//    private void deleteBook(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        String bookId = request.getParameter("bookId");
//        String userId = request.getParameter("userId");
//        response.setContentType("text/html");
//        PrintWriter writer = response.getWriter();
//        if (bookId.equals("") & userId.equals("")) {
//            writer.println("you should fill member id or book id to delete .");
//        } else if (!bookId.equals("") & !userId.equals("")) {
//            writer.println("you should fill one of member id or book id to delete .");
//        } else if (userId.equals("")) {
//            if (idIsCorrect(bookId)) {
//                Session session = sessionFactory.openSession();
//                Transaction transaction = session.beginTransaction();
//                Book book = session.get(dto.Book.class, Integer.parseInt(bookId));
//                if (book != null)
//                    session.delete(book);
//                transaction.commit();
//                session.close();
//                if (book != null)
//                    writer.println("deleting book :{ name: " + book.getName() + " writer: " + book.getWriter() + " was successful!");
//                else
//                    writer.println("deleting was unsuccessful.book id was not exist");
//            }
//        } else {
//            if (idIsCorrect(userId)) {
//                Session session = sessionFactory.openSession();
//                Transaction transaction = session.beginTransaction();
//                User user = session.get(dto.User.class, Integer.parseInt(userId));
//                if (user != null)
//                    session.delete(user);
//                transaction.commit();
//                session.close();
//                if (user != null)
//                    writer.println("deleting user :{ name: " + user.getName() + " family: " + user.getFamily() + " username : " + user.getUsername() + " was successful!");
//                else
//                    writer.println("deleting was unsuccessful.member id was not exist");
//            }
//        }
//    }
//
//    private boolean idIsCorrect(String id) {
//        for (int i = 0; i < id.length(); i++) {
//            if (id.charAt(i) < '0' | id.charAt(i) > '9')
//                return false;
//        }
//        return true;
//    }
//
//    private void addBook(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//        String name = request.getParameter("name");
//        String writr = request.getParameter("writer");
//        Session session = sessionFactory.openSession();
//        Transaction transaction = session.beginTransaction();
//        Book book = new Book();
//        book.setName(name);
//        book.setWriter(writr);
//        session.save(book);
//        transaction.commit();
//        session.close();
//        response.setContentType("text/html");
//        PrintWriter writer = response.getWriter();
//        writer.println("adding book :{ name: " + name + " writer: " + writr + " was successful!");
//    }
}
