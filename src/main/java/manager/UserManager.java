package manager;


import db.DBConnectionProvider;
import model.Gender;
import model.User;
import model.UserType;
import util.DateUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserManager {

    private Connection connection;

    private LessonManager lessonManager;

    public UserManager(){
        connection=DBConnectionProvider.getInstance().getConnection();
        lessonManager=new LessonManager();
    }

    public boolean register(User user) {
        String sql = "INSERT INTO user(name,surname,email,password,gender,user_type,lesson_id,creation_date) " +
                "VALUES(?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getName());
            statement.setString(2, user.getSurname());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getGender().name());
            statement.setString(6, user.getUserType().name().toUpperCase());
            statement.setLong(7,user.getLesson().getId());
            statement.setString(8, DateUtil.convertDateToString(user.getCreationDate()));
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                user.setId(rs.getLong(1));
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public User getById(long id) {
        String sql = "SELECT * FROM user WHERE id = " + id;
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return getUserFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteUserById(long id) {
        String sql = "DELETE FROM user WHERE id = " + id;
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public User getByEmailAndPassword(String email, String password) {
        String sql = "SELECT * FROM user WHERE email = ? AND password = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return getUserFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User getByEmail(String email) {
        String sql = "SELECT * FROM user WHERE email = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return getUserFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<User>();
        String sql = "SELECT * FROM user";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                users.add(getUserFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    private User getUserFromResultSet(ResultSet resultSet) {
        try {
            return User.builder()
                    .id(resultSet.getLong(1))
                    .name(resultSet.getString(2))
                    .surname(resultSet.getString(3))
                    .email(resultSet.getString(4))
                    .password(resultSet.getString(5))
                    .gender(Gender.valueOf(resultSet.getString(6)))
                    .userType(UserType.valueOf(resultSet.getString(7)))
                    .lesson(lessonManager.getLessonById(resultSet.getInt(8)))
                    .creationDate(DateUtil.convertStringToDate(resultSet.getString(9)))
                    .build();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
