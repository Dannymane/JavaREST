package rest.aw.connection;

import rest.aw.model.Course;
import rest.aw.model.GroupRecord;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Base {
    public Connection connection = null;
    public void establishConnection(){
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.out.println("Class.forName() Doesn't work");
        }
        try {
//            String serverName = "oracle2.icis.pcz.pl";
//            String portNumber = "1521";
//            String sid = "ICIS";
            String url = "jdbc:sqlite:SQLiteDatabase.db";
//            String username = "******";
//            String password = "******";
            connection = DriverManager.getConnection(url);
            //below method is needed  to comply with foreign key constraints
            pragmaForeignKeysON();

        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void closeConnection(){
        try {
            connection.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void pragmaForeignKeysON(){
        try {
            Statement st5 = connection.createStatement();
            st5.executeUpdate("PRAGMA foreign_keys = ON;");
            st5.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropTable(String tableName_){
        try {
            Statement st6 = connection.createStatement();
            int res6 = st6.executeUpdate("DROP table "+tableName_+";");
            st6.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void createCoursesTable(){
        try {
            Statement st0 = connection.createStatement();
            int res0 = st0.executeUpdate("CREATE TABLE IF NOT EXISTS courses (\n" +
//                    "id INTEGER PRIMARY KEY," +
                    "courseName TEXT PRIMARY KEY," +
                    "lessonName TEXT UNIQUE," +
                    "day TEXT," +
                    "time TEXT," +
                    "classroom TEXT" +
                    ");");
            st0.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void createScheduleTable(){
        try {
            Statement st1 = connection.createStatement();
            int res1 = st1.executeUpdate("CREATE TABLE IF NOT EXISTS schedule (\n" +
//                    "id INTEGER PRIMARY KEY," + //rowid will be created automatically
                    "faculty TEXT," +
                    "degree TEXT," +
                    "session INTEGER," +
                    "groupId TEXT," +
                    "courseName TEXT," +
                    "FOREIGN KEY(courseName) REFERENCES courses(courseName)"+
                    ");");
            st1.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void descTable(String tableName_){
        try {
            Statement st5 = connection.createStatement();
            ResultSet res5 = st5.executeQuery("pragma table_info('"+tableName_+"');");
            while (res5.next()) {
                System.out.println(res5.getInt(1)+" "+res5.getString(2)+
                        " "+res5.getString(3)+" "+res5.getString(4));
            }
            st5.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Course addCourse(Course course_){
        try {
            Statement st2 = connection.createStatement();
            int res2 = st2.executeUpdate("INSERT INTO courses (courseName,lessonName,day,time,classroom) "+
                    "VALUES (\'"+course_.getCourseName()+"\',\'"+
                    course_.getLessonName()+"\',\'"+
                    course_.getDay()+"\',\'"+
                    course_.getTime()+"\',\'"+
                    course_.getClassroom()+"\');");
            st2.close();
            return course_;
        }catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }
    public GroupRecord addCourseToGroup(GroupRecord group_){
        try {
            Statement st3 = connection.createStatement();

            int res3 = st3.executeUpdate("INSERT INTO schedule (faculty,degree,session,groupId,courseName) "+
                    "VALUES (\'"+group_.getFaculty()+"\',\'"+
                    group_.getDegree()+"\',\'"+
                    group_.getSession()+"\',\'"+
                    group_.getGroupId()+"\',\'"+
                    group_.getCourseName()+"\');");
            st3.close();
            return group_;
        }catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Course> getAllCourses(){
        try {
            Statement st4 = connection.createStatement();
            ResultSet res4 = st4.executeQuery("Select * from courses;");
            List<Course> courses = new ArrayList<>();
            while (res4.next()) {
                Course c = new Course(res4.getString(1),
                        res4.getString(2),
                        res4.getString(3),
                        res4.getString(4),
                        res4.getString(5));
                courses.add(c);
            }
            st4.close();
            return courses;
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public List<Course> getCourse(String courseName_){
        try {
            Statement st5 = connection.createStatement();
            ResultSet res5 = st5.executeQuery("SELECT * FROM courses" +
                    " WHERE courseName = \'"+courseName_+"\';");
            List<Course> courses = new ArrayList<>();
            while (res5.next()) {
                Course c = new Course(res5.getString(1),
                        res5.getString(2),
                        res5.getString(3),
                        res5.getString(4),
                        res5.getString(5));
                courses.add(c);
            }
            st5.close();
            return courses;
        }catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public String deleteCourse(String courseName_){
        try {
            Statement st5 = connection.createStatement();
            int res5 = st5.executeUpdate("DELETE FROM courses\n" +
                    "WHERE courseName = \'"+courseName_+"\';");
            st5.close();

            return "Course "+courseName_+" has deleted";
        }catch (SQLException e) {
            e.printStackTrace();
            return "Error. Course "+courseName_+" has not deleted";
        }
    }
    public String removeCourseFromGroup(String groupId_, String courseName_){
        try {
            Statement st5 = connection.createStatement();
            int res5 = st5.executeUpdate("DELETE FROM schedule\n" +
                    "WHERE groupId = \'" + groupId_ + "\' and " +
                    "courseName = \'"+courseName_+"\';");
            st5.close();
            return "Course "+courseName_+" has removed from group " + groupId_;
        }catch (SQLException e) {
            e.printStackTrace();
            return "Error. Course "+courseName_+" has not removed from group "
                    + groupId_;
        }
    }

    public List<GroupRecord> getAllGroupRecords(){
        try {
            Statement st4 = connection.createStatement();
            ResultSet res4 = st4.executeQuery("Select * from schedule;");
            List<GroupRecord> groupRecords = new ArrayList<>();
            while (res4.next()) {
                GroupRecord gr = new GroupRecord(res4.getString(1),
                        res4.getString(2),
                        res4.getInt(3),
                        res4.getString(4),
                        res4.getString(5));
                groupRecords.add(gr);
            }
            st4.close();
            return groupRecords;
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public List<GroupRecord> getRecordsOneWhereClause(String column, String value){
        try {
            Statement st4 = connection.createStatement();
            ResultSet res4 = st4.executeQuery("SELECT * FROM schedule " +
                            "WHERE "+column+" = \'" + value + "\';");
            List<GroupRecord> groupRecords = new ArrayList<>();
            while (res4.next()) {
                GroupRecord gr = new GroupRecord(res4.getString(1),
                        res4.getString(2),
                        res4.getInt(3),
                        res4.getString(4),
                        res4.getString(5));
                groupRecords.add(gr);
            }
            st4.close();
            return groupRecords;
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public List<GroupRecord> getRecordsTwoWhereClause(String column1, String value1,
                                                      String column2, String value2){
        try {
            Statement st4 = connection.createStatement();
            ResultSet res4 = st4.executeQuery("SELECT * FROM schedule " +
                    "WHERE "+column1+" = \'" + value1 + "\' and " +
                    column2 +" = \'" + value2+ "\';");
            List<GroupRecord> groupRecords = new ArrayList<>();
            while (res4.next()) {
                GroupRecord gr = new GroupRecord(res4.getString(1),
                        res4.getString(2),
                        res4.getInt(3),
                        res4.getString(4),
                        res4.getString(5));
                groupRecords.add(gr);
            }
            st4.close();
            return groupRecords;
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Course editCourse(String courseName_, Course course_) {
        try {
            Statement st2 = connection.createStatement();
            int res2 = st2.executeUpdate("UPDATE courses SET " +
                    "courseName = \'" + course_.getCourseName() + "\'," +
                    "lessonName = \'" + course_.getLessonName() + "\'," +
                    "day = \'" + course_.getDay() + "\'," +
                    "time = \'" + course_.getTime() + "\'," +
                    "classroom = \'" + course_.getClassroom() + "\' WHERE " +
                    "courseName = \'" + courseName_ + "\';");
            st2.close();
            return course_;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


}

