package coursesplanner.driver;

import coursesplanner.other.StudentContext;
import coursesplanner.util.FileProcessor;
import coursesplanner.util.Results;


/**
 * @author John Doe
 *
 */
public class Driver {
	
	public static void main(String[] args) {
		/*
		 * As the build.xml specifies the arguments as argX, in case the
		 * argument value is not given java takes the default value specified in
		 * build.xml. To avoid that, below condition is used
		 */
		if (args.length != 2 || args[0].equals("${arg0}") || args[1].equals("${arg1}")) {

			System.err.println("Error: Incorrect number of arguments. Program accepts 2 arguments.");
			System.exit(0);
		}
		
		FileProcessor studentData = new FileProcessor(args[0], args[1]);

		StudentContext studentContext = new StudentContext(studentData);

		studentContext.studentProcessor();
		
		Results result = new Results();
		result.writeToFile(studentContext, studentData);
		result.writeToStdout(studentContext);
	}
}
