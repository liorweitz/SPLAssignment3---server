package bgu.spl.net.impl.BGRSServer;



import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Passive object representing the Database where all courses and users are stored.
 * <p>
 * This class must be implemented safely as a thread-safe singleton.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You can add private fields and methods to this class as you see fit.
 */
public class Database {
	private ArrayList<Integer> coursesAsListed;
	private ConcurrentHashMap<Integer, Course> courseNumMap;
	private ConcurrentHashMap<String, User> adminToUserMap;
	private ConcurrentHashMap<String, Student> studentToUserMap;
	private ConcurrentHashMap<ConnectionHandler,User> handlerToLoggedUserleanMap;

	//to prevent user from creating new Database
	private Database() {
		coursesAsListed=new ArrayList<>();
		courseNumMap = new ConcurrentHashMap<>();
		adminToUserMap = new ConcurrentHashMap<>();
		studentToUserMap = new ConcurrentHashMap<>();
		handlerToLoggedUserleanMap = new ConcurrentHashMap<>();
	}

	/**
	 * Retrieves the single instance of this class.
	 */
	public static Database getInstance() {
		return SingletonHolder.instance;
	}

	//checks if a course exists
	public boolean checkCourseExistance(int courseNum) {
		return coursesAsListed.contains(courseNum);
	}

	public boolean checkPlace(int courseNum) {
		Course course=courseNumMap.get(courseNum);
		return course.occupiedPlaces<course.numOfMaxStudents;
	}

	public ArrayList<Integer> getKdam(int courseNum) {
		return (courseNumMap.get(courseNum)).kdamCourseList;
	}

	public Course getCourse(int courseNum) {
		return courseNumMap.get(courseNum);
	}

	public ArrayList<Integer> getEnrolledCourses(String studentName) {
		return studentToUserMap.get(studentName).enrolledCourses;
	}
	public ArrayList<Integer> getEnrolledCourses(ConnectionHandler handler){
		return getEnrolledCourses(handlerToLoggedUserleanMap.get(handler).userName);
	}

	public boolean isEnrolled(ConnectionHandler handler, int courseNum) {
		Student student=(Student) handlerToLoggedUserleanMap.get(handler);
		ArrayList<Integer> enrolledCourses=student.enrolledCourses;
		return enrolledCourses.contains(courseNum);
	}

	public void unregister(ConnectionHandler handler, int courseNum) {
		Student student=(Student) handlerToLoggedUserleanMap.get(handler);
		student.enrolledCourses.remove(courseNum);
		courseNumMap.get(courseNum).enrolledStudents.remove(student.getUserName());
	}

	public ArrayList<String> getEnrolledStudents(int courseNum) {
		return (courseNumMap.get(courseNum)).enrolledStudents;
	}

	public String getCourseName(int courseNum) {
		return (courseNumMap.get(courseNum)).courseName;
	}

	public int getOccupiedPlaces(int courseNum) {
		return ((courseNumMap.get(courseNum))).occupiedPlaces;
	}

	public int getNumOfMaxStudents(int courseNum) {
		return (courseNumMap.get(courseNum)).numOfMaxStudents;
	}

	public String getAdminPwd(String userName) {
		return adminToUserMap.get(userName).getPwd();
	}

	public String getStudentPwd(String userName) {
		return studentToUserMap.get(userName).getPwd();
	}


	private static class SingletonHolder {
		private static Database instance = new Database();
	}

	/**
	 * loades the courses from the file path specified
	 * into the Database, returns true if successful.
	 */
	boolean initialize(String coursesFilePath) throws IOException {
		List<String> lines = Collections.emptyList();
		try {
			lines = Files.readAllLines(Paths.get(coursesFilePath), StandardCharsets.UTF_8);
			Course course;
			for(int i=0;i<lines.size();i++){
				Scanner sc=new Scanner(lines.get(i));
				sc.useDelimiter("\\|");
				coursesAsListed.add(Integer.parseInt(sc.next()));
			}
			for(int i=0;i<lines.size();i++){
				course=parseCourseLine(lines.get(i));
				courseNumMap.put(course.courseNum,course);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	public void adminRegister(String userName,String pwd, ConnectionHandler handler) {
//		handlerToLoggedUserleanMap.put(handler, false);
		adminToUserMap.put(userName,new User (userName,pwd,0));}
	public void studentRegister(String userName,String pwd, ConnectionHandler handler) {
//		handlerToLoggedUserleanMap.put(handler, false);
		studentToUserMap.put(userName,new Student (userName,pwd,1));
	}



	public void logIn(ConnectionHandler handler, String userName){
		handlerToLoggedUserleanMap.put(handler,getUser(userName));
	}
	public void logOut(ConnectionHandler handler){
		handlerToLoggedUserleanMap.remove(handler);
	}

	private User getUser(String userName) {
		if (isAdminExist(userName)){
			return adminToUserMap.get(userName);
		}
		else if(isStudentExist(userName)){
			return studentToUserMap.get(userName);
		}
		throw new NoSuchElementException("there is no such registered user!");
	}

	public void enrollToCourse(ConnectionHandler handler,int courseNum){
		Student student=(Student)handlerToLoggedUserleanMap.get(handler);
		student.enrolledCourses.add(courseNum);
		Course course=courseNumMap.get(courseNum);
		course.occupiedPlaces++;
		course.enrolledStudents.add(student.getUserName());
	}

	public boolean isAdminExist(String user) {return adminToUserMap.containsKey(user);}
	public boolean isStudentExist(String user) {return studentToUserMap.containsKey(user);}

	//return -1 if handler is not logged in. 0 if admin is logged in. 1 if student is logged in.
	public int isLoggedIn(ConnectionHandler handler){
		User user= handlerToLoggedUserleanMap.get(handler);
		if (user==null)
			return -1;
		else
			return user.role;
	}

	//only handler registered on Students will call this method
	public boolean checkKdam(ConnectionHandler handler, int courseNum){
		boolean check=true;
		ArrayList<Integer> kdamCourses=courseNumMap.get(courseNum).kdamCourseList;
		ArrayList<Integer> enrolledCourses=((Student) handlerToLoggedUserleanMap.get(handler)).enrolledCourses;
		for (int i=0; i<kdamCourses.size() & check;i++){
			if (!enrolledCourses.contains(kdamCourses.get(i)))
				check=false;
		}
		return check;
	}

	public User getAdmin(String userName){return adminToUserMap.get(userName);}
	public Student getStudent(String userName) {return studentToUserMap.get(userName);
	}


	private Course parseCourseLine(String courseInfo) {
		int courseNum;
		String courseName;
		ArrayList<Integer> kdamCourseList=new ArrayList<>();
		int maxNumOfStudents;
		Scanner sc=new Scanner(courseInfo);
		sc.useDelimiter("\\|");
		courseNum=Integer.parseInt(sc.next());
		courseName=sc.next();
		kdamCourseList=parseKdamList(sc.next());
		maxNumOfStudents=Integer.parseInt(sc.next());
		Course course=new Course(courseNum,courseName,kdamCourseList,maxNumOfStudents);
		return course;
	}

	//here be a stupid and uneffective way to create kdam course list as specified.
	private ArrayList<Integer> parseKdamList(String kdamList) {
		ArrayList<Integer> unsortedKdamCourseList=new ArrayList<>();
		StringBuilder sb=new StringBuilder();
		for (int i=1;i<kdamList.length();i++){
			if (kdamList.charAt(i)==',' | kdamList.charAt(i)==']'){
				unsortedKdamCourseList.add(Integer.parseInt(sb.toString()));
				sb=new StringBuilder();
				continue;
			}
			sb.append(kdamList.charAt(i));
		}
		return sortCourseNumList(unsortedKdamCourseList);
	}

	public ArrayList<Integer> sortCourseNumList(ArrayList<Integer> unsortedList){
		ArrayList<Integer> sortedList=new ArrayList<>();
		for (int i=0;i<coursesAsListed.size();i++){
			if (unsortedList.contains(coursesAsListed.get(i)))
				sortedList.add(coursesAsListed.get(i));
		}
		return sortedList;
	}



	private void printFile(List<String> lines) {
		Iterator<String> itr = lines.iterator();
		while (itr.hasNext())
			System.out.println(itr.next());
	}


	public class Course {
		private int courseNum;
		private String courseName;
		private ArrayList<Integer> kdamCourseList;
		private int occupiedPlaces;
		final private int numOfMaxStudents;
		private ArrayList<String> enrolledStudents;

		public Course(int courseNum, String courseName, ArrayList<Integer> kdamCourseList, int numOfMaxStudents){
			this.courseNum=courseNum;
			this.courseName=courseName;
			this.kdamCourseList=kdamCourseList;
			this.numOfMaxStudents=numOfMaxStudents;
			this.occupiedPlaces=0;
			enrolledStudents=new ArrayList<>();
		}

//		public int getCourseNum(){
//			return courseNum;
//		}
//
//		public String getCourseName(){
//			return courseName;
//		}
//
//		public ArrayList<Integer> getKdamCourseList(){
//			return kdamCourseList;
//		}
//
//		public int getNumOfMaxStudents(){
//			return numOfMaxStudents;
//		}
//
//		public void increaseOccupiedPlaces(){
//			occupiedPlaces++;
//		}
//
//		public void enrollStudent(Student student){
//			enrolledStudents.add(student.getUserName());
//		}
//
//		public int getOccupiedPlaces() {
//			return occupiedPlaces;
//		}
//
//		public ArrayList<String> getEnrolledStudents() {
//			return enrolledStudents;
//		}
	}

	public class User{
		private String userName;
		private String pwd;
		private int role; //0=admin, 1=student.

		public User(String userName, String pwd, int role){
			this.userName = userName;
			this.pwd=pwd;
			this.role=role;
		}

		public String getUserName(){return userName;}
		public  String getPwd(){return pwd;}
//		public int getRole(){return role;}
	}

	public class Student extends User{
		private ArrayList<Integer> enrolledCourses;
		Student(String userName, String pwd,int role){
			super(userName,pwd,role);
			enrolledCourses=new ArrayList<>();
		}

//		public void enrollToCourse(int courseNum) {
//			enrolledCourses.add(courseNum);
//		}
//		public ArrayList<Integer> getEnrolledCourses(){
//			return enrolledCourses;
//		}
	}


}
