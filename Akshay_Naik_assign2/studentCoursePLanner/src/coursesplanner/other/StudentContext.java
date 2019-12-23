package coursesplanner.other;

import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.regex.PatternSyntaxException;
import java.util.ArrayList;
import java.util.LinkedList;

import coursesplanner.other.Course;
import coursesplanner.other.CourseGrouping;
import coursesplanner.util.FileProcessor;
import coursesplanner.state.State1;
import coursesplanner.state.State2;
import coursesplanner.state.State3;
import coursesplanner.state.State4;
import coursesplanner.state.State5;
import coursesplanner.state.CoursePlannerStateI;


public class StudentContext{
    private Student student;
    private List<Course> waitList;
    private int stateTransCounter;
    private State1 state1;
    private State2 state2;
    private State3 state3;
    private State4 state4;
    private State5 state5;

    CoursePlannerStateI state;

    //constructor
    public StudentContext(FileProcessor fileInput){ 

        String[] readStudentName;
        String[] readCoursePref;
        List<Course> coursePrefList;
        String line;

        coursePrefList = new ArrayList<Course>();

        try{
            line = fileInput.readLine();
            readStudentName = line.split(":");
            readCoursePref = readStudentName[1].split(" ");
            for(int i = 0; i < readCoursePref.length; i++){
                coursePrefList.add(convertToCourse(readCoursePref[i]));
            }
            student = new Student(readStudentName[0], coursePrefList);
            waitList = new LinkedList<Course>();
        }
        catch(PatternSyntaxException ex){
            System.err.println("Encountered Error: " + ex + " in Student Context constructor..");
        }

        state1 = new State1(this);
        state2 = new State2(this);
        state3 = new State3(this);
        state4 = new State4(this);
        state5 = new State5(this);
        
        state = state1;

        fileInput.closeInFile();
    
    }
    // avoiding help; remove when student data member gets used
    public Student getStudent(){
        return student;
    }

    public int checkState(int groupNo){
        int count = 0;
        if(groupNo == 1){
            count = state1.getStateCount();
        }
        else if(groupNo == 2){
            count = state2.getStateCount();
        }
        else if(groupNo == 3){
             count = state3.getStateCount();
        }
        else if(groupNo == 4){
            count = state4.getStateCount();
        }
        else if(groupNo == 5){
            count = state5.getStateCount();
        }

        return count;
    }

    public void setState(CoursePlannerStateI stateIn){
        state = stateIn;
    }

    public CoursePlannerStateI getState(int courseGroup){

        CoursePlannerStateI state = (CoursePlannerStateI)state1;
        
        if(courseGroup == 2){
            state = (CoursePlannerStateI)state2;
        }
        else if(courseGroup == 3){
            state = (CoursePlannerStateI)state3;
        }
        else if(courseGroup == 4){
            state = (CoursePlannerStateI)state4;
        }
        else if(courseGroup == 5){
            state = (CoursePlannerStateI)state5;
        }

        return state;
    }
    
    public void incrementStateCount(Course courseIn){
        
        int group = courseIn.getCourseGroup();

        if(group == 1){
            state1.incrementStateCount();
        }
        else if(group == 2){
            state2.incrementStateCount();
        }
        else if(group == 3){
            state3.incrementStateCount();
        }
        else if(group == 4){
            state4.incrementStateCount();
        }
        else if(group == 5){
            state5.incrementStateCount();
        }
    }

    public List<Course> getWaitList(){
        return waitList;
    }

    public void addToWaitList(Course courseIn){
        waitList.add(courseIn);
    }

    public Course convertToCourse(String courseNameIn){
        Course courseOut;
        int group = CourseGrouping.getGroup(courseNameIn);
        courseOut = new Course(courseNameIn, group);

        return courseOut;
    }

    public void incrStateTransCount(){
        stateTransCounter = stateTransCounter + 1;
    }

    public int getStateTransCount(){
        return stateTransCounter;
    }

    public List<Course> createPreReqList(Course courseIn){
        List<String> preReqStr;
        List<Course> coursePreReq = null;

        preReqStr = CourseGrouping.getPreReq(courseIn);
        coursePreReq = new ArrayList<Course>();
        for(int i = 0; i < preReqStr.size(); i++){
            coursePreReq.add(convertToCourse(preReqStr.get(i)));
        }
        return coursePreReq;
    }

    public void studentProcessor(){
        int currentSemester = student.getCurrentSem();
        ListIterator<Course> waitListItr = null;
        Course candidateCourse;
        List<Course> coursePreReq;
        ListIterator<Course> coursePrefListItr = student.getStudentCoursePref().listIterator();

        try{
            while(coursePrefListItr.hasNext() || !getWaitList().isEmpty()){
                boolean gradCondition = false;
                // if the course addition moves to a new semester currentSemester will be out of date
                // being in a new semester allows us to reconsider the waitlisted courses 
                if(currentSemester != student.getCurrentSem()){
                    waitListItr = getWaitList().listIterator();
                    while(waitListItr.hasNext()){
                         candidateCourse = waitListItr.next();
                         coursePreReq = createPreReqList(candidateCourse);
                         if(student.checkPreReq(coursePreReq)){
                             waitListItr.remove();
                             if(gradCondition = state.addCourse(candidateCourse)){
                                 break;
                             }
                         }
                    }
                    currentSemester = currentSemester + 1;
                }
                else if(student.getCurrentCourse() < student.getStudentCoursePref().size()){
                    candidateCourse = coursePrefListItr.next();
                    coursePreReq = createPreReqList(candidateCourse);
                    if(student.checkPreReq(coursePreReq)){
                        gradCondition = state.addCourse(candidateCourse);
                    }
                    else{
                        addToWaitList(candidateCourse);
                    }
                    student.incrCurrentCourse();
                }
                else if(student.getCurrentCourse() >= student.getStudentCoursePref().size()){
                    waitListItr = getWaitList().listIterator();
                    while(waitListItr.hasNext()){
                        waitListItr.next();
                        waitListItr.remove();
                    }
                }
                if(gradCondition){
                    break;
                }
            }
        }
        catch(NoSuchElementException ex){  
            System.err.println("Encountered Error: " + ex + " in method studentProcessor()");       
        }
    }

    public boolean isGraduate(){

        int group1Count = 0, group2Count = 0, group3Count = 0, group4Count = 0, group5Count = 0;
        ListIterator<Semester> semItr = student.getSemesterPlan().listIterator();
        ListIterator<Course> courseItr = null;
        Semester currentSem = null;
        Course currentCourse = null;

        while(semItr.hasNext()){
            currentSem = semItr.next();
            courseItr = currentSem.getCourseList().listIterator();
            while(courseItr.hasNext()){
                currentCourse = courseItr.next();
                if(currentCourse.getCourseGroup() == 1){
                    group1Count = group1Count + 1;
                }
                else if(currentCourse.getCourseGroup() == 2){
                    group2Count = group2Count + 1;
                }
                else if(currentCourse.getCourseGroup() == 3){
                    group3Count = group3Count + 1;
                }
                else if(currentCourse.getCourseGroup() == 4){
                    group4Count = group4Count + 1;
                }
                else if(currentCourse.getCourseGroup() == 5){
                    group5Count = group5Count + 1;
                }
            }
        }
        if(group1Count >= 2 && group2Count >= 2 && group3Count >= 2 && group4Count >= 2 && group5Count >= 2){
            return true;
        }
        else{
            return false;
        }

    }
}