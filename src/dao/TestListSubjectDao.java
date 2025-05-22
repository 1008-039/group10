package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.TestListStudent;
import bean.TestListSubject;

public class TestListSubjectDao extends Dao{

	private String basesql="select * from student where school_cd=?";

	public List<TestListSubject> postFilter(ResultSet rSet)throws Exception {
		//リストを初期化
		List<TestListSubject> list =new ArrayList<>();
		try {
			//リザルトセットを全権走査
			while (rSet.next()) {
				//学生インスタンスを初期化
				TestListSubject subject =new TestListSubject();
				//学生インスタンスに検索結果をセット
				subject.setEntYear(rSet.getInt("entyear"));
				subject.setStudentNo(rSet.getString("studentno"));
				subject.setStudentName(rSet.getString("studentname"));
				subject.setClassNum(rSet.getString("classnum"));
				subject.putPoint(rSet.getInt("points"), (Integer) null);
				//リストに追加
				list.add(subject);
			}
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		}
		return list;
	}
// 学校、入学年度、クラス番号、在学フラグ


	//学校、入学年度、在学フラグ

//学校、在学フラグ
	public List<TestListSubject> filter(int entyear, String classNum, boolean subject, School school) throws Exception {
		//リストを初期化
		List<TestListSubject> list = new ArrayList<>();
		//コネクションを確立
		Connection connection = getConnection();
		//プリペアードステートメント
		PreparedStatement statement = null;
		//リザルトセット
		ResultSet rSet = null;
		//SQL文の条件
		String order = "order by no asc";
		//SQL文の在学フラグ
		String conditionIsAttend ="";
		//在学フラグがtrueの場合
		if (subject) {
			basesql = "and student=true";
		}

		try {
			//プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement(basesql +  order);
			//プリペアードステートメントに学校コードをバインド
			statement.setString(1, TestListStudent.getSubjectName());
			//プリペアードステートメントを実行
			rSet = statement.executeQuery();
			//リストへの格納処理を実行
			list= postFilter(rSet);
		}catch (Exception e) {
			throw e;
		} finally {
			//プリペアードステートメントを閉じる
			if (statement !=null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
			//コネクションを閉じる
			if (connection !=null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}
		return list;
	}
}
