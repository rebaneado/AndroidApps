package edu.uncc.inclass08;

public class Grade {

    String courseGrade, courseName, courseNumber, creditHours, studentID, Post_id;

    public Grade() {

    }
//    public Grade(String courseGrade, String courseName, String courseNumber, String creditHours, String studentID) {
//
//        this.courseGrade = courseGrade;
//        this.courseName = courseName;
//        this.courseNumber = courseNumber;
//        this.creditHours = creditHours;
//        this.studentID = studentID;
//        this.Post_id = Post_id;
//
//    }

    public String getCourseGrade() {
        return courseGrade;
    }

    public void setCourseGrade(String courseGrade) {
        this.courseGrade = courseGrade;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
    }

    public String getCreditHours() {
        return creditHours;
    }

    public void setCreditHours(String creditHours) {
        this.creditHours = creditHours;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    @Override
    public String toString() {
        return "Grade{" +
                "courseGrade='" + courseGrade + '\'' +
                ", courseName='" + courseName + '\'' +
                ", courseNumber='" + courseNumber + '\'' +
                ", creditHours='" + creditHours + '\'' +
                ", studentID='" + studentID + '\'' +
                '}';
    }


    public String getPost_id() {
        return Post_id;
    }

    public void setPost_id(String post_id) {
        Post_id = post_id;
    }


}
