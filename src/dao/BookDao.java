package dao;

import dto.Book;
import dto.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDao {

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

    public void insert(Book book) {
        try {
            Connection connection = getConnetion();
            if (connection == null) {
                System.out.println("connection is null");
                return;
            }
            PreparedStatement preparedStatement = connection.prepareStatement("insert into book (category,name,writer,publisher,isAvailable)values(?,?,?,?,?)");
            preparedStatement.setString(1, String.valueOf(book.getCategory()));
            preparedStatement.setString(2, book.getName());
            preparedStatement.setString(3, book.getWriter());
            preparedStatement.setString(4, book.getPublisher());
            preparedStatement.setBoolean(5, book.getAvailable());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("problem with inserting book");
        }
    }

    public List<Book> search(Book book) {
        Connection connection = getConnetion();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select book.* from book where book.name like ? and book.writer like ? and book.publisher like ? and book.category like ? ");
            preparedStatement.setString(1, "%" + book.getName() + "%");
            preparedStatement.setString(2, "%" + book.getWriter() + "%");
            preparedStatement.setString(3, "%" + book.getPublisher() + "%");
            preparedStatement.setString(4, "%" + book.getCategory() + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Book> result = new ArrayList<>();
            while (resultSet.next()) {
                Book book1 = new Book();
                book1.setId(resultSet.getInt(1));
                book1.setCategory(Category.valueOf(resultSet.getString(2)));
                book1.setName(resultSet.getString(3));
                book1.setWriter(resultSet.getString(4));
                book1.setPublisher(resultSet.getString(5));
                result.add(book1);
            }
            preparedStatement.close();
            connection.close();
            return result;
        } catch (SQLException e) {
            System.out.println("problem with sql");
        }
        return null;
    }

    public void delete(int id) {
        try {
            Connection connection = getConnetion();
            PreparedStatement preparedStatement = connection.prepareStatement("delete from book where id=" + id);
            preparedStatement.execute();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("problem with sql");
        }
    }

    public void update(Book book) throws Exception {
        try {
            Connection connection = getConnetion();
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE book SET name='" + book.getName() + "',category='" + book.getCategory() + "',writer ='" + book.getWriter() + "',publisher='" + book.getPublisher() + "',isAvailable=?" + " where id='" + book.getId() + "'");
            preparedStatement.setBoolean(1, book.getAvailable());
            if (preparedStatement.executeUpdate() == 1)
                System.out.println("it is done!");
            else throw new Exception("incorrect book id.");
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("problem with sql");
        }

    }

    public List<Book> showAll() {
        Connection connection = getConnetion();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from book where 1");
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Book> books = new ArrayList<>();
            while (resultSet.next()) {
                Book book = new Book();
                extractDataFromResultSet(resultSet, book);
                books.add(book);
            }
            preparedStatement.close();
            connection.close();
            return books;
        } catch (SQLException e) {
            System.out.println("problem with sql");
        }
        return null;
    }

    public Book findById(int id) throws Exception {
        Connection connection = getConnetion();

        PreparedStatement preparedStatement = connection.prepareStatement("select * from book where book.id='" + id + "'");
        ResultSet resultSet = preparedStatement.executeQuery();
        Book book = new Book();
        if (resultSet.next()) {
            extractDataFromResultSet(resultSet, book);
        } else throw new Exception("book not exists.id is incorrect");
        preparedStatement.close();
        connection.close();
        return book;
    }

    private void extractDataFromResultSet(ResultSet resultSet, Book book) throws SQLException {
        book.setId(resultSet.getInt(1));
        book.setName(resultSet.getString(2));
        book.setWriter(resultSet.getString(3));
        book.setPublisher(resultSet.getString(4));
        book.setCategory(Category.valueOf(resultSet.getString(5)));
        book.setAvailable(resultSet.getBoolean(6));
    }
}
