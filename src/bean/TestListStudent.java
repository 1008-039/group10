package bean;

public class TestListStudent implements java.io.Serializable{
	private static String subjectName;
	private String subjectCd;
	private int num;
	private int point;

	public static String getSubjectName(){
		return subjectName;
	}
	public void setSubjectName(String subjectname){
		this.subjectName=subjectname;
	}
	public String getSubjectCd() {
		return subjectCd;
	}
	public void setSubjectCd(String subjectCd) {
		this.subjectCd=subjectCd;
	}
	public  int getNum(){
		return num;
	}
	public void setNum(int num){
		this.num=num;
	}
	public int getpoint(){
		return point;
	}
	public void setpoint(int point){
		this.point=point;
	}
}