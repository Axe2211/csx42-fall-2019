package coursesplanner.other;

import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.ArrayList;
import java.util.Arrays;
import coursesplanner.other.Course;


public class CourseGrouping{
    private static List<List<String>> coursegroups;

    static {
        coursegroups = new ArrayList<List<String>>();
        coursegroups.add(new ArrayList<String>(Arrays.asList("A", "B", "C", "D")));
        coursegroups.add(new ArrayList<String>(Arrays.asList("E", "F", "G", "H")));
        coursegroups.add(new ArrayList<String>(Arrays.asList("I", "J", "K", "L")));
        coursegroups.add(new ArrayList<String>(Arrays.asList("M", "N", "O", "P")));
        coursegroups.add(new ArrayList<String>(Arrays.asList("Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z")));
        coursegroups.add(new ArrayList<String>(Arrays.asList("")));
    }

    // get methods
    public static List<String> getPreReq(Course courseIn){
        int group, index, coursePosition;
        List<String> preReq = null;
        try{
            group = courseIn.getCourseGroup();
            index = group - 1;
            coursePosition = coursegroups.get(index).indexOf(courseIn.getCourseName());
            //to return an empty list as any of Group 5 courses can be added without any concerns about preReq
            if(group == 5){
                coursePosition = 0;
            }
            preReq = coursegroups.get(index).subList(0, coursePosition);
        }
        catch(IndexOutOfBoundsException | IllegalArgumentException ex){
            System.err.println("Encountered Exception: " + ex + "in CourseGrouping.getPreReq()");
        }
        return preReq;      
    }

    public static int getGroup(String courseIn){
        ListIterator<List<String>> itr = coursegroups.listIterator();
        List<String> currentList = null;
        int retGroup = -1;
        try{
            while(itr.hasNext()){
                currentList = (ArrayList<String>)itr.next();
                if(currentList.contains(courseIn)){
                    retGroup = itr.nextIndex();
                }
                else{
                    continue;
                }
            }
        }
        catch(NoSuchElementException | UnsupportedOperationException | IllegalStateException ex){
            System.err.println("Encountered Exception: " + ex + "in CourseGrouping.getGroup()");
        }
        return retGroup;
    }

}