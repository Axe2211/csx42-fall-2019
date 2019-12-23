package coursesplanner.other;

public class Course{
    private String courseName;
    private int courseGroup;

    public Course(String courseNameIn, int courseGroupIn){
        courseName = courseNameIn;
        courseGroup = courseGroupIn;
    }

    //get methods
    public int getCourseGroup(){
        return courseGroup;
    }

    public String getCourseName(){
        return courseName;
    }
}