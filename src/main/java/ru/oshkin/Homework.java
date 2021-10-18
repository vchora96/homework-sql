package ru.oshkin;

import org.apache.commons.lang3.RandomStringUtils;

import java.sql.*;

import static ru.oshkin.SqlHelper.*;

public class Homework {

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
            System.out.println("вызывайте срочно разработчиков insertCurators: " + exception);
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
            System.out.println("вызывайте срочно разработчиков, insertGroupUniversitySql: " + exception);
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
            System.out.println("вызывайте срочно разработчиков, insertStudentsSql: " + exception);
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
            System.out.println("вызывайте срочно разработчиков, selectStudentsCuratorsData: " + exception);
        }
    }

    /*
     * Метод печатает на экране количество студентов
     * @param connection соединение с БД
     */
    public void selectCountStudentSql(Connection connection) {
        try (final Statement statement = connection.createStatement();
             final ResultSet resultSet = statement.executeQuery(SELECT_COUNT_STUDENT_SQL)) {
            while (resultSet.next()) {
                int cnt = resultSet.getInt("cnt");
                System.out.println("количество студентов = " + cnt);
            }
        } catch (SQLException exception) {
            System.out.println("вызывайте срочно разработчиков, selectCountStudentSql: " + exception);
        }
    }

    /*
     * Метод печатает на экране фамилии студенток
     * @param connection соединение с БД
     */
    public void setSelectCountStudentGirlsSql(Connection connection) {
        try (final Statement statement = connection.createStatement();
             final ResultSet resultSet = statement.executeQuery(SELECT_COUNT_STUDENT_GIRLS_SQL)) {
            while (resultSet.next()) {
                String fio = resultSet.getString("fio");
                System.out.println("студентка = " + fio);
            }
        } catch (SQLException exception) {
            System.out.println("вызывайте срочно разработчиков, имя метода = setSelectCountStudentGirlsSql: " + exception);
        }
    }

    /*
     * Метод обновляет в таблице group_university кураторов
     * @param connection соединение с БД
     */
    public void updateGroupUniversitySql(Connection connection) {
        try (final Statement statement = connection.createStatement()) {
            final int number = statement.executeUpdate(UPDATE_GROUP_UNIVERSITY_SQL);
            System.out.println("обновлено строк = " + number);
        } catch (SQLException exception) {
            System.out.println("вызывайте срочно разработчиков, имя метода = setSelectCountStudentGirlsSql: " + exception);
        }
    }

    /*
     * Метод печатает на экране группу и её куратора
     * @param connection соединение с БД
     */
    public void selectGroupWithCuratorsSql(Connection connection) {
        try (final Statement statement = connection.createStatement();
             final ResultSet resultSet = statement.executeQuery(SELECT_GROUP_WITH_CURATORS_SQL)) {
            while (resultSet.next()) {
                String groupName = resultSet.getString("gu_name");
                String curatorFio = resultSet.getString("c_fio");
                System.out.println("группа = " + groupName);
                System.out.println("фамилия куратора = " + curatorFio);
            }
        } catch (SQLException exception) {
            System.out.println("вызывайте срочно разработчиков, имя метода = selectGroupWithCuratorsSql: " + exception);
        }
    }

    /*
     * Метод печатает на экране группу и её куратора
     * @param connection соединение с БД
     */
    public void selectStudentsFromGroup(Connection connection) {
        try (final Statement statement = connection.createStatement();
             final ResultSet resultSet = statement.executeQuery(SElECT_STUDENTS_FROM_GROUP)) {
            while (resultSet.next()) {
                String studentFio = resultSet.getString("student_fio");
                System.out.println("ФИО студента = " + studentFio);
            }
        } catch (SQLException exception) {
            System.out.println("вызывайте срочно разработчиков, имя метода = selectStudentsFromGroup : " + exception);
        }
    }

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(CONNECTION_URL, USER, PASSWORD)) {
            Homework homework = new Homework();
            homework.createCuratorTable(connection);
            homework.createGroupTable(connection);
            homework.insertCurators(connection);
            homework.insertGroupUniversitySql(connection);
            homework.createStudentTable(connection);
            homework.insertStudentsSql(connection);
            homework.selectStudentsCuratorsData(connection);
            homework.selectCountStudentSql(connection);
            homework.setSelectCountStudentGirlsSql(connection);
            homework.updateGroupUniversitySql(connection);
            homework.selectGroupWithCuratorsSql(connection);
            homework.selectStudentsFromGroup(connection);
        } catch (Exception exception) {
            System.out.println("вызывайте срочно разработчиков " + exception);
        }
    }
}

