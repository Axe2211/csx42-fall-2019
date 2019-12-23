package coursesplanner.other;

import coursesplanner.other.Course;
import java.util.List;
import java.util.ArrayList;


public class Semester{
    private int semNumber;
    private ArrayList<Course> takenCourses;
    private int emptyFrom;

    public Semester(int semNumberIn){
        semNumber = semNumberIn;
        emptyFrom = 0;
        takenCourses = new ArrayList<Course>(3);
    }

    public int getSemNumber(){
        return semNumber;
    }

    public boolean addCourse(Course courseIn){
        try{
            if(emptyFrom < 3){
                takenCourses.add(emptyFrom, courseIn);
                emptyFrom = emptyFrom + 1;
            }
            else{
                System.out.println(" Semester is already full..");
            }
        }
        catch(ArrayIndexOutOfBoundsException ex){
            System.err.println(" Encountered Index out of bound error: " + ex);
        }
        if(emptyFrom == 3){
            return false;
        }
        return true;
    }

    public List<Course> getCourseList(){
        return takenCourses;
    }

    public boolean isFull(){
        if(emptyFrom == 3){
            return true;
        }
        return false;
    }

    public boolean isEmpty(){
        if(emptyFrom == 0){
            return true;
        }
        return false;
    }

    public boolean courseIsPresent(Course courseIn){
        for(int i = 0; i < takenCourses.size(); i++){
            if(courseIn.getCourseName().equals(takenCourses.get(i).getCourseName())){
                return true;
            }
        }
        return false;
    }
}