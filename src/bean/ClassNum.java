package bean;

public class ClassNum implements java.io.Serializable{

	private String class_num;
	private School school;

	public String getclass_num() {
		 return class_num;
	 }
	 public void setclass_num(String class_num) {
		 this.class_num =class_num;
	 }
	 public School getschool() {
		 return school;
	 }
	 public void setschool(School school) {
		 this.school =school;
	 }
}