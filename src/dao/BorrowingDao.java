package dao;

import dto.Borrowing;
import dto.Category;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class BorrowingDao {
    private static final Logger logger = LogManager.getLogger(BorrowingDao.class);

    private Connection getConnetion() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try {
                return DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", null);

            } catch (SQLException e) {
                System.out.println("problem with sql");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found ");
        }
        return null;
    }

    public void bookBorrow(Borrowing borrowing) throws Exception {
        Connection connection = getConnetion();
        if (connection == null) {
            System.out.println("connection is null");
        }
        PreparedStatement preparedStatement = connection.prepareStatement("insert into borrowing (dateOfBorrowing,bookId,userId)values(?,?,?)");
        preparedStatement.setDate(1, Date.valueOf(borrowing.getDateOfBorrowing()));
        preparedStatement.setInt(2, borrowing.getBookId());
        preparedStatement.setInt(3, borrowing.getUserId());
        if (preparedStatement.executeUpdate() == 1) {
            logger.debug("book with id=" + borrowing.getBookId() + " get borrowed with user id:" + borrowing.getUserId() + " at:");
            preparedStatement = connection.prepareStatement("update book SET book.isAvailable='0' where id='" + borrowing.getBookId() + "'");
            preparedStatement.executeUpdate();
        }
        preparedStatement.close();
        connection.close();
    }

    public Map<Category, Integer> reportingOfFavoriteBookClassification() {
        Connection connection = getConnetion();
        Map<Category, Integer> favariteBookCategory = new HashMap<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select COUNT(br.id),b.category from borrowing br join book b on b.id=br.bookId GROUP BY b.category");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Category category = Category.valueOf(resultSet.getString(2));
                    favariteBookCategory.put(category, resultSet.getInt(1));
            }
            preparedStatement.close();
            connection.close();
            return favariteBookCategory;
        } catch (SQLException e) {
            System.out.println("problem with sql");
        }
        return favariteBookCategory;
    }


    public void bookReturning(Borrowing borrowing) throws Exception {
        Connection connection = getConnetion();
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE borrowing SET dateOfReturning='" + borrowing.getDateOfReturning() + "' where bookId='" + borrowing.getBookId() + "'" + " and userId='" + borrowing.getUserId() + "' and dateOfReturning IS NULL");
        if (preparedStatement.executeUpdate() == 1) {
            System.out.println("it is done!");
            preparedStatement = connection.prepareStatement("update book SET isAvailable='1' where id='" + borrowing.getBookId() + "'");
            preparedStatement.executeUpdate();
        } else throw new Exception("incorrect user id or book id");
        preparedStatement.close();
        connection.close();
    }
}
