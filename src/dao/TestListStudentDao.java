package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.TestListStudent;

public class TestListStudentDao extends Dao{

	private String basesql="select * from student where school_cd=?";

	public List<TestListStudent> postFilter(ResultSet rSet)throws Exception {
		//リストを初期化
		List<TestListStudent> list =new ArrayList<>();
		try {
			//リザルトセットを全権走査
			while (rSet.next()) {
				//学生インスタンスを初期化
				TestListStudent student =new TestListStudent();
				//学生インスタンスに検索結果をセット
				student.setSubjectName(rSet.getString("subjectname"));
				student.setSubjectCd(rSet.getString("subjectcd"));
				student.setNum(rSet.getInt("num"));
				student.setpoint(rSet.getInt("point"));
				//リストに追加
				list.add(student);
			}
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		}
		return list;
	}
// 学校、入学年度、クラス番号、在学フラグ


	//学校、入学年度、在学フラグ

//学校、在学フラグ
	public List<TestListStudent> filter(boolean student) throws Exception {
		//リストを初期化
		List<TestListStudent> list = new ArrayList<>();
		//コネクションを確立
		Connection connection = getConnection();
		//プリペアードステートメント
		PreparedStatement statement = null;
		//リザルトセット
		ResultSet rSet = null;
		//SQL文の条件
		String order = "order by no asc";
		//在学フラグがtrueの場合
		if (student) {
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
