package coursesplanner.util;

import coursesplanner.other.StudentContext;
import coursesplanner.util.FileDisplayInterface;
import coursesplanner.util.StdoutDisplayInterface;
import coursesplanner.other.Semester;
import coursesplanner.other.Course;
import java.util.ListIterator;


public class Results implements FileDisplayInterface, StdoutDisplayInterface {

    public void writeToStdout(StudentContext studentContextIn){

        try{

            System.out.print(studentContextIn.getStudent().getStudentName() + ": ");
            ListIterator<Semester> itr = studentContextIn.getStudent().getSemesterPlan().listIterator();

            while(itr.hasNext()){
                Semester sem = itr.next();
                ListIterator<Course> courseitr = sem.getCourseList().listIterator();
                while(courseitr.hasNext()){
                    System.out.print(courseitr.next().getCourseName() + " ");
                }
            }
            if(studentContextIn.isGraduate()){
                System.out.println("--" + studentContextIn.getStudent().getCurrentSem() + " " + studentContextIn.getStateTransCount());
        
            }
            else{
                System.out.println("-- 0" + " " + studentContextIn.getStateTransCount());
                System.out.println("Student does not graduate..");
            }
        }
        catch(Exception ex){
            System.out.println(" Error in writing to standard display.. ");
        }
    } 

    public void writeToFile(StudentContext studentContextIn, FileProcessor fileIn){
        try{

            fileIn.writeFile(studentContextIn.getStudent().getStudentName() + ": ");
            ListIterator<Semester> itr = studentContextIn.getStudent().getSemesterPlan().listIterator();

            while(itr.hasNext()){
                Semester sem = itr.next();
                ListIterator<Course> courseitr = sem.getCourseList().listIterator();
                while(courseitr.hasNext()){
                    fileIn.writeFile(courseitr.next().getCourseName() + " ");
                }
            }
            if(studentContextIn.isGraduate()){
                fileIn.writeFile("--" + studentContextIn.getStudent().getCurrentSem() + " " + studentContextIn.getStateTransCount());
        
            }
            else{
                fileIn.writeFile("-- 0" + " " + studentContextIn.getStateTransCount() + "\n");
                fileIn.writeFile("Student does not graduate..");
            }

            fileIn.closeOutFile();
        }
        catch(Exception ex){
            System.out.println(" Error in writing to standard display.. ");
        }

    }
	
}
