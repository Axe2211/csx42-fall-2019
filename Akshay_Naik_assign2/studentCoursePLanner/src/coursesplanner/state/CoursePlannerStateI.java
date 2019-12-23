package coursesplanner.state;

import coursesplanner.other.Course;

public interface CoursePlannerStateI{

    public boolean addCourse(Course courseIn);
    public int getStateCount();
    public void incrementStateCount();
    
}