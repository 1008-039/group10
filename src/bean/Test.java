package bean;

public class Test {
	private Student student;
	private String classNum;
	private Subject subject;
	private School school;
	private int no;
	private int point;
	private String name;
	private int entyear;
	private boolean attend;


	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public String getClassNum() {
		return classNum;
	}
	public void setClassNum(String classNum) {
		this.classNum = classNum;
	}
	public School getSchool() {
		return school;
	}
	public void setSchool(School school) {
		this.school = school;
	}
	public Subject getSubject() {
		return subject;
	}
	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}

	public String getString(){
		return name;
	}
	public void setName(String subject_name) {
		// TODO 自動生成されたメソッド・スタブ
		this.name = subject_name;
	}

	public int Int1(){
		return entyear;
	}
	public void setEntYear(int int1) {
		// TODO 自動生成されたメソッド・スタブ
		this.entyear = int1;

	}

	public boolean Boolean1(){
		return attend;
	}
	public void setAttend(boolean boolean1) {
		// TODO 自動生成されたメソッド・スタブ
		this.attend = boolean1;

	}
	public String getName() {
		return name;
	}
	public int getEntYear() {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}
	public boolean isAttend() {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

}
