package ru.oshkin;

public class SqlHelper {

    static final String CONNECTION_URL = "jdbc:postgresql://localhost:5432/otus_db";
    static final String USER = "postgres1";
    static final String PASSWORD = "postgres1";
    static final String CREATE_GROUP_UNIVERSITY_SQL =
            "create table if not exists group_university" +
                    "(" +
                    "    id         bigserial," +
                    "    name       varchar(30) not null," +
                    "    id_curator bigserial," +
                    "    primary key (id)," +
                    "    foreign key (id_curator) references curator (id)" +
                    ");";

    static final String CREATE_CURATOR_SQL =
            "create table  if not exists curator" +
                    "(" +
                    "    id  bigserial," +
                    "    fio varchar(30)," +
                    "    primary key (id)" +
                    ");";
    static final String CREATE_STUDENT_SQL =
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
    static final String INSERT_STUDENTS_SQL =
            "insert into student(fio, sex, id_group)" +
                    "values (?,?,?);";

    static final String INSERT_CURATORS = "insert into curator(fio) " +
            "VALUES (?)";
    static final String INSERT_GROUP_UNIVERSITY_SQL = "insert into " +
            "group_university(name, id_curator)" +
            "values (?, ?);";
    static final String SELECT_STUDENT_CURATORS_SQL = "select s.fio as student_fio, " +
            "c.fio as curator_fio, gu.name as group_name" +
            " from student s" +
            "         join group_university gu on gu.id = s.id_group" +
            "         join curator c on c.id = gu.id_curator;";
    static final String SELECT_COUNT_STUDENT_SQL = "select count(id) as cnt " +
            "from student;";
    static final String SELECT_COUNT_STUDENT_GIRLS_SQL = "select distinct fio " +
            "from student " +
            "where sex = 'Ж';";
    static final String UPDATE_GROUP_UNIVERSITY_SQL = "update group_university " +
            "set id_curator = 2 " +
            "where id = 8;";
    static final String SELECT_GROUP_WITH_CURATORS_SQL = "select gu.name as gu_name, c.fio as c_fio " +
            "from group_university gu " +
            "         join curator c on gu.id_curator = c.id;";
    static final String SElECT_STUDENTS_FROM_GROUP =
            "select st.fio as student_fio from student st " +
                    "where id_group in " +
                    "(select id from group_university where name = 'SsDPnlHGIdJFekMiGjTy');";

}
