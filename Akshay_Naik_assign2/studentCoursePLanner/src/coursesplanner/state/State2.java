package coursesplanner.state;

import coursesplanner.state.CoursePlannerStateI;
import coursesplanner.other.Course;
import coursesplanner.other.CourseGrouping;
import coursesplanner.other.StudentContext;

public class State2 implements CoursePlannerStateI{
    
    private int stateCount;
    private StudentContext studentContext;

    public State2(StudentContext studentContextIn){
        studentContext = studentContextIn;
        stateCount = 0;
    }

    public boolean addCourse(Course courseIn){
        int courseGroup = CourseGrouping.getGroup(courseIn.getCourseName());
        
        studentContext.getStudent().addCourse(courseIn);
        studentContext.incrementStateCount(courseIn);
        
        if(studentContext.checkState(courseGroup) > getStateCount()){
            studentContext.setState(studentContext.getState(courseGroup));
            studentContext.incrStateTransCount();
        }
        if(studentContext.isGraduate()){
            return true;
        }
        return false;
    }

    public int getStateCount(){
        return stateCount;
    }

    public void incrementStateCount(){ 
        stateCount = stateCount + 1;
    }

}