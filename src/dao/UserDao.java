package dao;

import dto.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao {

    private Connection getConnetion() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try {
                return DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", null);
            } catch (SQLException e) {
                System.out.println("problem with get connection");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found ");
        }
        return null;
    }

    public User findById(int id) throws Exception {
        Connection connection = getConnetion();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from member where member.id='" + id + "'");
            ResultSet resultSet = preparedStatement.executeQuery();
            User user = new User();
            if (resultSet.next()) {
                user.setId(resultSet.getInt(1));
                user.setName(resultSet.getString(2));
                user.setFamily(resultSet.getString(3));
                user.setUsername(resultSet.getString(4));
                user.setPassword(resultSet.getString(5));
                user.setAdmin(resultSet.getBoolean(6));
            } else throw new Exception("user not exists.id is incorrect");
            preparedStatement.close();
            connection.close();
            return user;
        } catch (SQLException e) {
            System.out.println("problem with sql");
        }
        return null;
    }

    public void insert(User user) throws Exception {
        try {
            Connection connection = getConnetion();
            if (connection == null) {
                System.out.println("connection is null");
                return;
            }
            Boolean isExist = usernameIsExist(user.getUsername());
            if (isExist)
                throw new Exception("username is exist,try another username");
            PreparedStatement preparedStatement = connection.prepareStatement("insert into member (name,family,username,password,isAdmin)values(?,?,?,?,?)");

            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getFamily());
            preparedStatement.setString(3, user.getUsername());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setBoolean(5, user.isAdmin());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("problem with inserting");
        }
    }

    private Boolean usernameIsExist(String username) {
        Connection connection = getConnetion();
        Boolean isExist = false;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select member.* from member where member.username like ?");
            preparedStatement.setString(1, "%" + username + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                isExist = true;
            }
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("problem with finding username is exist");
        }
        return isExist;
    }

    public User signIn(String username, String password) throws Exception {
        Connection connection = getConnetion();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from member where member.username='" + username + "' and member.password='" + password + "'");
            ResultSet resultSet = preparedStatement.executeQuery();
            User user = new User();
            if (resultSet.next()) {
                user.setId(resultSet.getInt(1));
                user.setName(resultSet.getString(2));
                user.setFamily(resultSet.getString(3));
                user.setUsername(resultSet.getString(4));
                user.setAdmin(resultSet.getBoolean(6));
            } else throw new Exception("username or password is incorrect.");
            preparedStatement.close();
            connection.close();
            return user;
        } catch (SQLException e) {
            System.out.println("problem with sql");
        }
        return null;
    }


    public List<User> showAll() {
        Connection connection = getConnetion();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select member.* from member where 1");
            ResultSet resultSet = preparedStatement.executeQuery();
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt(1));
                user.setName(resultSet.getString(2));
                user.setFamily(resultSet.getString(3));
                user.setUsername(resultSet.getString(4));
                user.setAdmin(resultSet.getBoolean(6));
                users.add(user);
            }
            preparedStatement.close();
            connection.close();
            return users;
        } catch (SQLException e) {
            System.out.println("problem with sql");
        }
        return null;
    }

    public void delete(int id) {
        try {
            Connection connection = getConnetion();
            PreparedStatement preparedStatement = connection.prepareStatement("delete from member where id='" + id + "'");
            preparedStatement.execute();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("problem with sql");
        }
    }

    public void update(User user) throws Exception {
        try {
            Connection connection = getConnetion();
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE member set name='" + user.getName() + "',family='" + user.getFamily() + "',username='" + user.getUsername() + "',password='" + user.getPassword() + "' where id='" + user.getId() + "'");
            if (preparedStatement.executeUpdate() == 1)
                System.out.println("it is done!");
            else throw new Exception("incorrect user id.");
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("problem with sql");
        }

    }
}
