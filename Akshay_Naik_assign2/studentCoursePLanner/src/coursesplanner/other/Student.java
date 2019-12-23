package coursesplanner.other;

import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.ArrayList;
import coursesplanner.other.Course;
import coursesplanner.other.Semester;


public class Student{
    private String studentName;
    private List<Course> studentCoursePreference;
    private int numberOfPrefs;
    private List<Semester> semesterPlan;
    private int currentSem;
    private int currentCourse;

    public Student(String studentNameIn, List<Course> studentCoursePreferenceIn){
        studentName = studentNameIn;
        studentCoursePreference = studentCoursePreferenceIn;
        semesterPlan = new ArrayList<Semester>();
        currentSem = 1;
        semesterPlan.add(new Semester(currentSem));
        currentCourse = 0;
        numberOfPrefs = studentCoursePreferenceIn.size();
    } 
    
    // get methods
    public String getStudentName(){
        return studentName;
    }

    public List<Course> getStudentCoursePref(){
        return studentCoursePreference;
    }

    public List<Semester> getSemesterPlan(){
        return semesterPlan;
    }

    public int getCurrentSem(){
        return currentSem;
    }

    public int getCurrentCourse(){
        return currentCourse;
    }

    // add methods
    public void addCourse(Course courseIn){
        int index = getCurrentSem() - 1;
        Semester currentSem = semesterPlan.get(index);
        if(!currentSem.addCourse(courseIn)){
            addSemester();
        }
    }

    public void addSemester(){
        int currentSem = getCurrentSem();
        semesterPlan.add(new Semester(currentSem + 1));
        incrCurrentSem();
    }

    public boolean prefExhausted(){
        if(currentCourse == numberOfPrefs){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean incrCurrentCourse(){
        if(!prefExhausted()){
            currentCourse = currentCourse + 1;
            return true;
        }
        System.err.println("All the course preferences have been assessed. No more courses in the list..");
        return false;
    }

    public void incrCurrentSem(){
        currentSem = currentSem + 1;
    }

    public boolean checkPreReq(List<Course> preReqIn){
        
        int preReqSize = preReqIn.size();
        int counter = 0;
        int semCounter = getCurrentSem();
        ListIterator<Semester> semItr = semesterPlan.listIterator();
        ListIterator<Course> reqItr = preReqIn.listIterator();
        if(preReqIn.isEmpty()){
            return true;
        }
        try{
            while(reqItr.hasNext()){
                Course course = reqItr.next();
                Semester semester = null;
    
                while(semItr.hasNext() && semCounter > 1){
                    semester = semItr.next();
                    if(semester.courseIsPresent(course)){
                        counter = counter + 1;
                        semCounter = semCounter - 1; // added to prevent checking for pre requiste course in current semester
                        break;
                    }
                    else{
                        semCounter = semCounter - 1;
                        continue;
                    }
                }
            }
            if(counter == preReqSize){
                return true;
            }
        }
        catch(NoSuchElementException | ClassCastException | NullPointerException ex){
            System.err.println("Encountered Error: " + ex + " in checkPreReq function");
        }
        return false;       
    }
}