package bean;

import java.io.Serializable;

public class School implements Serializable{

    private String cd;
    private String name;
	public String getCd() {
		return cd;
	}
	public void setCd(String cd) {
		this.cd = cd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getEntYear() {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}
	public String getClassNum() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

}