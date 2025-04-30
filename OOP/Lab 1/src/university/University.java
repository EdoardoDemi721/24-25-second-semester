package university;
import java.util.logging.Logger;

/**
 * This class represents a university education system.
 * 
 * It manages students and courses.
 *
 */
public class University {

// R1
	private String name;
	private String rectorName;
	private String rectorSurname;
	private final int MAX_STUDENTS=1000;
	private int countID=0;
	private Student[] students = new Student[MAX_STUDENTS];
	private final int MAX_COURSES=50;
	private int countCourses=0;
	private Course[] courses = new Course[MAX_COURSES];
	private int[][] attendances = new int [MAX_COURSES][MAX_STUDENTS];


	/**
	 * Constructor
	 * @param name name of the university
	 */
	public University(String name){
		this.name=name;
	}
	
	/**
	 * Getter for the name of the university
	 * 
	 * @return name of university
	 */
	public String getName(){
		return this.name; 
	}
	
	/**
	 * Defines the rector for the university
	 * 
	 * @param first first name of the rector
	 * @param last	last name of the rector
	 */
	public void setRector(String first, String last){
		this.rectorName=first;
		this.rectorSurname=last;
	}
	
	/**
	 * Retrieves the rector of the university with the format "First Last"
	 * 
	 * @return name of the rector
	 */
	public String getRector(){
		if (this.rectorName!=null){
			return this.rectorName+" "+this.rectorSurname;
		}
		else{
			return "Rector not yet assigned";
		}
	}
	
// R2
	/**
	 * Enrol a student in the university
	 * The university assigns ID numbers 
	 * progressively from number 10000.
	 * 
	 * @param first first name of the student
	 * @param last last name of the student
	 * 
	 * @return unique ID of the newly enrolled student
	 */
	public int enroll(String first, String last){
		if (countID>MAX_STUDENTS)
		{
			System.err.println("Error. Students limit reached.");
			return -1;
		}
		students[countID]=new Student(first, last, countID+10000);
		logger.info("New student enrolled: "+students[countID].info());
		countID++;
		return countID+10000-1;
	}
	
	/**
	 * Retrieves the information for a given student.
	 * The university assigns IDs progressively starting from 10000
	 * 
	 * @param id the ID of the student
	 * 
	 * @return information about the student
	 */
	public String student(int id){
		return students[id-10000].info();
	}
	
// R3
	/**
	 * Activates a new course with the given teacher
	 * Course codes are assigned progressively starting from 10.
	 * 
	 * @param title title of the course
	 * @param teacher name of the teacher
	 * 
	 * @return the unique code assigned to the course
	 */
	public int activate(String title, String teacher){
		courses[countCourses]= new Course(title, teacher, countCourses+10);
		logger.info("New course activated: "+courses[countCourses].info());
		countCourses++;
		return countCourses+10-1;
	}
	
	/**
	 * Retrieve the information for a given course.
	 * 
	 * The course information is formatted as a string containing 
	 * code, title, and teacher separated by commas, 
	 * e.g., {@code "10,Object Oriented Programming,James Gosling"}.
	 * 
	 * @param code unique code of the course
	 * 
	 * @return information about the course
	 */
	public String course(int code){
		return courses[code-10].info();
	}
	
// R4
	/**
	 * Register a student to attend a course
	 * @param studentID id of the student
	 * @param courseCode id of the course
	 */

	public void register(int studentID, int courseCode){
		if (Util.count(this.attendances[courseCode-10])>=100)
		{
			throw new IllegalStateException("Error. Can't register more students for this course");
		}
		if (Util.count(this.attendances, studentID-10000)>=25)
		{
			throw new IllegalStateException("Error. Student can't attend more courses");
		}
		this.attendances[courseCode-10][studentID-10000]=1;
		logger.info("Student "+studentID+" signed up for course "+courseCode);
	}
	
	/**
	 * Retrieve a list of attendees.
	 * 
	 * The students appear one per row (rows end with `'\n'`) 
	 * and each row is formatted as describe in in method {@link #student}
	 * 
	 * @param courseCode unique id of the course
	 * @return list of attendees separated by "\n"
	 */
	public String listAttendees(int courseCode){
		String toPrint="";
		for (int i=0; i<countID; i++)
		{
			if(attendances[courseCode-10][i]>=1){
				toPrint+=students[i].info()+"\n";
			}
		}

		return toPrint;
	}

	/**
	 * Retrieves the study plan for a student.
	 * 
	 * The study plan is reported as a string having
	 * one course per line (i.e. separated by '\n').
	 * The courses are formatted as describe in method {@link #course}
	 * 
	 * @param studentID id of the student
	 * 
	 * @return the list of courses the student is registered for
	 */
	public String studyPlan(int studentID){
		String toPrint="";
		for (int i=0; i<countCourses; i++)
		{
			if(attendances[i][studentID-10000]>=1){
				toPrint+=courses[i].info()+"\n";
			}
		}

		return toPrint;
	}

// R5
	/**
	 * records the grade (integer 0-30) for an exam can 
	 * 
	 * @param studentId the ID of the student
	 * @param courseID	course code 
	 * @param grade		grade ( 0-30)
	 */
	public void exam(int studentId, int courseID, int grade) {
		if (grade<18 || grade>30){
			throw new IllegalArgumentException("Grade has to be between 18 and 30");
		}
		if (attendances[courseID-10][studentId-10000]>1){
			throw new IllegalArgumentException("Student "+studentId+" already took exam "+courseID);
		}
		attendances[courseID-10][studentId-10000]=grade;
		logger.info("Student "+studentId+" took an exam in course "+courseID+" with grade "+grade);
	}

	/**
	 * Computes the average grade for a student and formats it as a string
	 * using the following format 
	 * 
	 * {@code "Student STUDENT_ID : AVG_GRADE"}. 
	 * 
	 * If the student has no exam recorded the method
	 * returns {@code "Student STUDENT_ID hasn't taken any exams"}.
	 * 
	 * @param studentId the ID of the student
	 * @return the average grade formatted as a string.
	 */

	 public float studentAvgCompute(int studentId) {
		int c=0;
		int sum=0;
		for (int i=0; i<countCourses; i++)
		{
			if(attendances[i][studentId-10000]>1){
				c++;
				sum+=attendances[i][studentId-10000];
			}
		}
		if (c==0)
		{
			return 0;
		}
		float avg = sum/c;
		return avg;
	}
	
	public String studentAvg(int studentId) {
		for (int i=0; i<countCourses; i++)
		if (studentAvgCompute(studentId)==0)
		{
			return "Student "+studentId+" hasn't taken any exams;";
		}
		return "Student "+studentId+" : "+studentAvgCompute(studentId);
	}
	
	/**
	 * Computes the average grades of all students that took the exam for a given course.
	 * 
	 * The format is the following: 
	 * {@code "The average for the course COURSE_TITLE is: COURSE_AVG"}.
	 * 
	 * If no student took the exam for that course it returns {@code "No student has taken the exam in COURSE_TITLE"}.
	 * 
	 * @param courseId	course code 
	 * @return the course average formatted as a string
	 */
	public String courseAvg(int courseId) {
		int c=0;
		int sum=0;
		for (int x : attendances[courseId-10])
		{
			if(x>1){
				c++;
				sum+=x;
			}
		}
		if (c==0)
		{
			return "No student has taken the exam in: "+courses[courseId-10].getName();
		}
		float avg = sum/c;
		return "The average for the course "+courses[courseId-10].getName()+" is: "+avg;
	}
	

// R6
	/**
	 * Retrieve information for the best students to award a price.
	 * 
	 * The students' score is evaluated as the average grade of the exams they've taken. 
	 * To take into account the number of exams taken and not only the grades, 
	 * a special bonus is assigned on top of the average grade: 
	 * the number of taken exams divided by the number of courses the student is enrolled to, multiplied by 10.
	 * The bonus is added to the exam average to compute the student score.
	 * 
	 * The method returns a string with the information about the three students with the highest score. 
	 * The students appear one per row (rows are terminated by a new-line character {@code '\n'}) 
	 * and each one of them is formatted as: {@code "STUDENT_FIRSTNAME STUDENT_LASTNAME : SCORE"}.
	 * 
	 * @return info on the best three students.
	 */
	public String topThreeStudents() {
		String toPrint="";
		float[] scores = new float[countID];
		float[][] topThree = new float[3][2];
	for (int i = 0; i < topThree.length; i++) {
    	for (int j = 0; j < topThree[i].length; j++) {
        	topThree[i][j] = -1;
    	}
	}
		for (int i=0; i<countID; i++){
			if (Util.count(attendances, i, 1)!=0){
				scores[i]+=studentAvgCompute(i+10000);
				scores[i]+=(Util.count(attendances, i, 1)/Util.count(attendances, i))*10;
				int a=Util.count(attendances, 1, 1);
				if (scores[i] >= topThree[0][1]) { // Confronta con il primo posto
					topThree[2][0] = topThree[1][0];
					topThree[2][1] = topThree[1][1];
				
					topThree[1][0] = topThree[0][0];
					topThree[1][1] = topThree[0][1];
				
					topThree[0][0] = i;
					topThree[0][1] = scores[i];
				} else if (scores[i] >= topThree[1][1]) { // Confronta con il secondo posto
					topThree[2][0] = topThree[1][0];
					topThree[2][1] = topThree[1][1];
				
					topThree[1][0] = i;
					topThree[1][1] = scores[i];
				} else if (scores[i] >= topThree[2][1]) { // Confronta con il terzo posto
					topThree[2][0] = i;
					topThree[2][1] = scores[i];
				}
				
			}		
		}
		for (float[] x : topThree){
			if(x[0]!=-1){
				toPrint+=students[(int) x[0]].getFullName()+" : "+x[1]+"\n";}
		}
		return toPrint;
	}

// R7
    /**
     * This field points to the logger for the class that can be used
     * throughout the methods to log the activities.
     */
    public static final Logger logger = Logger.getLogger("University");

}
