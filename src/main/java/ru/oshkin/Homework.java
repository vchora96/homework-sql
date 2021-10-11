package ru.oshkin;

import org.apache.commons.lang3.RandomStringUtils;

import java.sql.*;

public class Homework {

    private static final String CONNECTION_URL = "jdbc:postgresql://localhost:5432/otus_db";
    private static final String USER = "postgres1";
    private static final String PASSWORD = "postgres1";
    private static final String CREATE_GROUP_UNIVERSITY_SQL =
            "create table if not exists group_university" +
                    "(" +
                    "    id         bigserial," +
                    "    name       varchar(30) not null," +
                    "    id_curator bigserial," +
                    "    primary key (id)," +
                    "    foreign key (id_curator) references curator (id)" +
                    ");";

    private static final String CREATE_CURATOR_SQL =
            "create table  if not exists curator" +
                    "(" +
                    "    id  bigserial," +
                    "    fio varchar(30)," +
                    "    primary key (id)" +
                    ");";
    private static final String CREATE_STUDENT_SQL =
            "create table if not exists student" +
                    "(" +
                    "    id       bigserial," +
                    "    fio      varchar(30) not null," +
                    "    sex      varchar(1)  not null," +
                    "    id_group bigserial," +
                    "    primary key (id)," +
                    "    foreign key (id_group) references group_university (id)" +
                    "" +
                    ");";
    private static final String INSERT_STUDENTS_SQL =
            "insert into student(fio, sex, id_group)" +
                    "values (?,?,?);";

    private static final String INSERT_CURATORS = "insert into curator(fio) " +
            "VALUES (?)";
    private static final String INSERT_GROUP_UNIVERSITY_SQL = "insert into " +
            "group_university(name, id_curator)" +
            "values (?, ?);";
    private static final String SELECT_STUDENT_CURATORS_SQL = "select s.fio as student_fio, " +
            "c.fio as curator_fio, gu.name as group_name" +
            " from student s" +
            "         join group_university gu on gu.id = s.id_group" +
            "         join curator c on c.id = gu.id_curator;";


    public void createCuratorTable(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute(CREATE_CURATOR_SQL);
        statement.close();
    }

    public void createGroupTable(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute(CREATE_GROUP_UNIVERSITY_SQL);
        statement.close();
    }

    public void createStudentTable(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute(CREATE_STUDENT_SQL);
        statement.close();
    }

    public void insertCurators(Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CURATORS)) {
            for (int i = 0; i < 4; i++) {
                preparedStatement.setString(1, RandomStringUtils.randomAlphabetic(20));
                int insertN = preparedStatement.executeUpdate();
                System.out.println("количество вставленных строк: " + insertN);
            }
        } catch (SQLException exception) {
            System.out.println("вызывайте срочно разработчиков: " + exception);
        }
    }

    public void insertGroupUniversitySql(Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_GROUP_UNIVERSITY_SQL)) {
            for (int i = 0; i < 3; i++) {
                preparedStatement.setString(1, RandomStringUtils.randomAlphabetic(20));
                preparedStatement.setInt(2, i + 1);
                int insertN = preparedStatement.executeUpdate();
                System.out.println("количество вставленных строк: " + insertN);
            }
        } catch (SQLException exception) {
            System.out.println("вызывайте срочно разработчиков: " + exception);
        }
    }

    public void insertStudentsSql(Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_STUDENTS_SQL)) {
            for (int i = 0; i < 15; i++) {
                preparedStatement.setString(1, RandomStringUtils.randomAlphabetic(20));
                preparedStatement.setString(2, i % 2 == 0 ? "М" : "Ж");
                preparedStatement.setInt(3, i % 3 + 1);
                int insertN = preparedStatement.executeUpdate();
                System.out.println("количество вставленных строк: " + insertN);
            }
        } catch (SQLException exception) {
            System.out.println("вызывайте срочно разработчиков: " + exception);
        }
    }

    public void selectStudentsCuratorsData(Connection connection) {
        try (final Statement statement = connection.createStatement();
             final ResultSet resultSet = statement.executeQuery(SELECT_STUDENT_CURATORS_SQL)) {
            while (resultSet.next()) {
                final String studentFio = resultSet.getString("student_fio");
                final String curatorFio = resultSet.getString("curator_fio");
                final String groupName = resultSet.getString("group_name");
                System.out.println(studentFio + " " + curatorFio + " " + groupName);
            }
        } catch (SQLException exception) {
            System.out.println("вызывайте срочно разработчиков select!!!: " + exception);
        }
    }

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(CONNECTION_URL, USER, PASSWORD)) {
            Homework homework = new Homework();
            homework.createCuratorTable(connection);
            homework.createGroupTable(connection);
            homework.insertGroupUniversitySql(connection);
            homework.createStudentTable(connection);
            homework.insertCurators(connection);
            homework.insertStudentsSql(connection);
            homework.selectStudentsCuratorsData(connection);
        } catch (Exception exception) {
            System.out.println("вызывайте срочно разработчиков " + exception);


        }
    }
}

