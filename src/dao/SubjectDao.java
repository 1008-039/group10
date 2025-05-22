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

public class SubjectDao extends Dao {
    // 科目を取得するための基本SQL文
    private String baseSql = "SELECT * FROM subject WHERE school_cd=?";

    // 科目コードを指定して、1つの科目情報を取得する
    public Subject get(String subject_name, School teacherSchool) throws Exception {
        Subject subject = new Subject();
        Connection connection = getConnection();
        PreparedStatement statement = null;
        try {
            // 科目コードを基に、科目情報を取得するSQLクエリを準備
            statement = connection.prepareStatement("SELECT * FROM subject WHERE no=?");
            statement.setString(1, subject_name);
            ResultSet rSet = statement.executeQuery();
            SchoolDao schoolDao = new SchoolDao();

            // 取得結果を Subject オブジェクトにセット
            if (rSet.next()) {
                subject.setNo(rSet.getString("no"));
                subject.setName(rSet.getString("name"));
                subject.setEntYear(rSet.getInt("ent_year"));
                subject.setClassNum(rSet.getString("class_num"));
                subject.setAttend(rSet.getBoolean("is_attend"));
                subject.setSchool(schoolDao.get(rSet.getString("school_cd")));
            } else {
                subject = null; // 科目が見つからなかった場合は null を返す
            }
        } catch (Exception e) {
            throw e;
        } finally {
            // リソースの解放
            closeResources(statement, connection);
        }
        return subject;
    }

    // 学校情報を基に、科目リストをフィルタリングして取得する
    public List<Subject> filter(School school) throws Exception {
        List<Subject> list = new ArrayList<>();
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
                Subject subject = new Subject();
                subject.setNo(rSet.getString("no"));
                subject.setName(rSet.getString("name"));
                subject.setEntYear(rSet.getInt("ent_year"));
                subject.setClassNum(rSet.getString("class_num"));
                subject.setAttend(rSet.getBoolean("is_attend"));
                subject.setSchool(school);
                list.add(subject); // 科目リストに追加
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
    public boolean save(Subject subject) throws Exception {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        int count = 0;
        try {
            Subject old = get(subject.getNo(), null); // 既存の科目を取得
            if (old == null) {
                // 新規科目の登録処理
                statement = connection.prepareStatement(
                        "INSERT INTO subject (no, name, ent_year, class_num, is_attend, school_cd) VALUES (?, ?, ?, ?, ?, ?)");
                statement.setString(1, subject.getNo());
                statement.setString(2, subject.getName());
                statement.setInt(3, subject.getEntYear());
                statement.setString(4, subject.getClassNum());
                statement.setBoolean(5, subject.isAttend());
                statement.setString(6, subject.getSchool().getCd());
            } else {
                // 既存科目の更新処理
                statement = connection.prepareStatement(
                        "UPDATE subject SET name=?, ent_year=?, class_num=?, is_attend=? WHERE no=?");
                statement.setString(1, subject.getName());
                statement.setInt(2, subject.getEntYear());
                statement.setString(3, subject.getClassNum());
                statement.setBoolean(4, subject.isAttend());
                statement.setString(5, subject.getNo());
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

    public String getBaseSql() {
        return baseSql;
    }

    public void setBaseSql(String baseSql) {
        this.baseSql = baseSql;
    }


	public List<Student> filter(School school, int entYear, String classNum, boolean isAttend) throws Exception {
		List<Student> list = new ArrayList<>();
		Connection connection = getConnection();
		PreparedStatement statement = null;
		ResultSet rSet = null;
		String condition = "and ent_year=? and class_num=?";
		String order =" order by no asc";
		String conditionIsAttend = "";

		if (isAttend){
			conditionIsAttend = "and is_attend=ture";
		}
		try {
			statement = connection.prepareStatement(baseSql + condition + conditionIsAttend + order);
			statement.setString(1, school.getCd());
			statement.setInt(2, entYear);
			statement.setString(3,classNum);
			rSet = statement.executeQuery();

			list = postFilter(rSet, school);
	  }catch (Exception e){
		  throw e;
	  }finally{
		  if (statement != null){
			  try{
				  statement.close();

			  }catch (SQLException sqle){
				  throw sqle;
			  }
		  }
	  }
	  return list;
	  }



    private List<Student> postFilter(ResultSet rSet, School school) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
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
}