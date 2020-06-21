package manager;


import db.DBConnectionProvider;
import model.Lesson;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class LessonManager {

    private Connection connection = DBConnectionProvider.getInstance().getConnection();


    public void addLesson(Lesson lesson){
        try {
            String sql="INSERT INTO lesson(name, duration, price) VALUES(?,?,?)";
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, lesson.getName());
            statement.setDouble(2, lesson.getDuration());
            statement.setDouble(3,lesson.getPrice());
            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                lesson.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Lesson getLessonById(long id) {
        String sql = "SELECT * FROM lesson WHERE id = " + id;
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return getLessonFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteLessonById(long id) {
        String sql = "DELETE FROM lesson WHERE id = " + id;
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }



    public List<Lesson> getAllLessons() {
        List<Lesson> lessons = new ArrayList<Lesson>();
        String sql = "SELECT * FROM lesson";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                lessons.add(getLessonFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lessons;
    }

    private Lesson getLessonFromResultSet(ResultSet resultSet) {
        try {
            return Lesson.builder()
                    .id(resultSet.getLong(1))
                    .name(resultSet.getString(2))
                    .duration(resultSet.getDouble(3))
                    .price(resultSet.getDouble(4))
                    .build();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
