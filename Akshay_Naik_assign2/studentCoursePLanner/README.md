# CSX42: Assignment 2
## Name: Student Course Planner

-----------------------------------------------------------------------
-----------------------------------------------------------------------


Following are the commands and the instructions to run ANT on your project.
#### Note: build.xml is present in coursesRegistration/src folder.

-----------------------------------------------------------------------
## Instruction to clean:

####Command: ant -buildfile studentCoursePlanner/src/build.xml clean

Description: It cleans up all the .class files that were generated when you
compiled your code.

-----------------------------------------------------------------------
## Instruction to compile:

####Command: ant -buildfile studentCoursePLanner/src/build.xml all

Description: Compiles your code and generates .class files inside the BUILD folder.

-----------------------------------------------------------------------
## Instruction to run:

####Command: ant -buildfile studentCoursePLanner/src/build.xml run -Darg0="input.txt" -Darg1="output.txt"

Note: Arguments accept the absolute path of the files.


-----------------------------------------------------------------------
## Description: 
The waitList for courses which do not satisfy the preRequisites is implemented as a Linked List. This is because it allows for subsequent iterative check and deletion of courses which can be added to the students Semester plan. A variable called current Semester is used to keep atrack of the current semester to which courses are being added. The comparison of this variable with the getCurrentSem method (which tracks the very current semester added to the students semester plan) allows us to know if the latest course addition caused a new Semester to be added. At the addition of any semester the waitList is iterated through for courseswhich can now be added.

A polling mechanism embedded in every states addCourse method lets us know when the graduation condition has been met at whic point the Student processor method stops execution. A scheme for maintaining a list sorted on the basis of preRequisites was considered.

PS: I would like to use 2 of my slack days for this assignment

-----------------------------------------------------------------------
### Academic Honesty statement:
-----------------------------------------------------------------------

"I have done this assignment completely on my own. I have not copied
it, nor have I given my solution to anyone else. I understand that if
I am involved in plagiarism or cheating an official form will be
submitted to the Academic Honesty Committee of the Watson School to
determine the action that needs to be taken. "

Date: [10/10/2019]


