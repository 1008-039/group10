package dao; // データベースへのアクセスを管理するDAOクラス群

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Test;

public class TestDao extends Dao {
    // 科目を取得するための基本SQL文
    private String baseSql = "SELECT * FROM Test WHERE school_cd=?";

    // 科目コードを指定して、1つの科目情報を取得する
    public Test get(String cd, Student student, Subject subject, School school, int no) throws Exception {
        Test test = new Test();
        Connection connection = getConnection();
        PreparedStatement statement = null;
        try {
            // 科目コードを基に、科目情報を取得するSQLクエリを準備
            statement = connection.prepareStatement("SELECT * FROM test WHERE no=?");
            statement.setString(1, cd);
            ResultSet rSet = statement.executeQuery();
            SchoolDao schoolDao = new SchoolDao();

            // 取得結果を Subject オブジェクトにセット
            if (rSet.next()) {
                test.setNo(rSet.getInt("no"));
                test.setName(rSet.getString("name"));
                test.setEntYear(rSet.getInt("ent_year"));
                test.setClassNum(rSet.getString("class_num"));
                test.setAttend(rSet.getBoolean("is_attend"));
                test.setSchool(schoolDao.get(rSet.getString("school_cd")));
            } else {
                test = null; // 科目が見つからなかった場合は null を返す
            }
        } catch (Exception e) {
            throw e;
        } finally {
            // リソースの解放
            closeResources(statement, connection);
        }
        return test;
    }

    // 学校情報を基に、科目リストをフィルタリングして取得する
    public List<Test> filter(School school) throws Exception {
        List<Test> list = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet rSet = null;
        try {
            // 条件を設定（入学年度とクラス番号）
            String condition = "AND ent_year=? AND class_num=?";
            String order = " ORDER BY no ASC";

            // フィルタリング用のSQL文を準備
            statement = connection.prepareStatement(baseSql + " " + condition + " " + order);
            statement.setInt(1, school.getEntYear());
            statement.setString(2, school.getClassNum());

            // 実行して結果を取得
            rSet = statement.executeQuery();
            while (rSet.next()) {
            	Test test = new Test();
            	test.setNo(rSet.getInt("no"));
            	test.setName(rSet.getString("name"));
            	test.setEntYear(rSet.getInt("ent_year"));
            	test.setClassNum(rSet.getString("class_num"));
            	test.setAttend(rSet.getBoolean("is_attend"));
            	test.setSchool(school);
                list.add(test); // 科目リストに追加
            }
        } catch (Exception e) {
            throw e;
        } finally {
            // リソースの解放
            closeResources(statement, connection);
        }
        return list;
    }

    // 科目情報を保存（新規登録または更新）
    public boolean save(Test test) throws Exception {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        int count = 0;
        try {
            Subject old = get(test.getNo()); // 既存の科目を取得
            if (old == null) {
                // 新規科目の登録処理
                statement = connection.prepareStatement(
                        "INSERT INTO test (no, name, subject, class_num, student, school) VALUES (?, ?, ?, ?, ?, ?)");
                statement.setInt(1, test.getNo());
                statement.setString(2, test.getName());
                statement.setString(4, test.getClassNum());
                statement.setString(6, test.getSchool().getCd());
            } else {
                // 既存科目の更新処理
                statement = connection.prepareStatement(
                        "UPDATE subject SET name=?, ent_year=?, class_num=?, is_attend=? WHERE no=?");
                statement.setString(1, test.getName());
                statement.setInt(2, test.getEntYear());
                statement.setString(3, test.getClassNum());
                statement.setBoolean(4, test.isAttend());
                statement.setInt(5, test.getNo());
            }
            count = statement.executeUpdate(); // SQL実行
        } catch (Exception e) {
            throw e;
        } finally {
            // リソースの解放
            closeResources(statement, connection);
        }
        return count > 0; // 保存が成功したかどうかを返す
    }

    private Subject get(int no) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public String getBaseSql() {
        return baseSql;
    }

    public void setBaseSql(String baseSql) {
        this.baseSql = baseSql;
    }

    // クエリ実行後、使用したリソースを解放する
    private void closeResources(PreparedStatement statement, Connection connection) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        }
    }

	public List<Test> filter(int entYear, String classNum, Subject subject_name) {
		// TODO 自動生成されたメソッド・スタブ
		return null;

	}
}