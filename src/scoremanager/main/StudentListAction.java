package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Teacher;
import dao.ClassNumDAO;
import dao.StudentDao;
import tool.Action;

public class StudentListAction  extends Action {

	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception{
		HttpSession session = req.getSession();//セッション
		Teacher teacher = (Teacher)session.getAttribute("user");


		String entYearStr = "";//入力された入学年度
		String classNum = "";//入力されたクラス番号
		String isAttendStr = "";//入力された在学フラグ
		int entYear = 0;//入学年度
		boolean isAttend = false;//在学フラグ
		List<Student> students = null;//学生リスト
		LocalDate todaysDate = LocalDate.now();//LocalDateインスタンスを取得
        int year = todaysDate.getYear();//現在の年を取得
        StudentDao sDao = new StudentDao();//学生DAO
        ClassNumDAO cNumDao = new ClassNumDAO();//クラス番号DAOを初期化
        Map<String, String> errors = new HashMap<>();//エラーメッセージ

       //リクエストパラメーターの取得
        entYearStr = req.getParameter("f1");
        classNum=req.getParameter("f2");
        isAttendStr=req.getParameter("f3");

        //ビジネスロック 4
        if (entYearStr !=null) {
        	//数値に変換
        	entYear = Integer.parseInt(entYearStr);
        }
        //リストを初期化
        List<Integer> entYearSet = new ArrayList<>();
        //10年前から1年後まで年をリストに追加
        for (int i = year - 10; i < year +1; i++) {
        	entYearSet.add(i);
        }
        //DBからデータ取得
        //ログインユーザーの学校コードをもとにクラス番号の一覧を取得
        List<String> list=cNumDao.filter(teacher.getSchool());

        if (entYear !=0 && !classNum.equals("0")) {
        	//入学年度とクラス番号を指定
        	students=sDao.filter(teacher.getSchool(), entYear, classNum, isAttend);
        } else if (entYear !=0 && classNum.equals("0")) {
        	//入学年度のみ指定
        	students=sDao.filter(teacher.getSchool(), entYear, isAttend);
        } else if (entYear == 0 && classNum == null || entYear == 0 && classNum.equals("0")) {
        	//指定なしの場合
        	//全学生情報を取得
        	students= sDao.filter(teacher.getSchool(), isAttend);
        }else {
        	errors.put("f1", "クラスを指定する場合は入学年度も指定してください");
        	req.setAttribute("errors", errors);
        	//全学生情報を取得
        	students= sDao.filter(teacher.getSchool(), isAttend);
        }
        //レスポンス値をセット
        //リクエストに入学年度をセット
        req.setAttribute("f1", entYear);
        //リクエストにクラス番号をセット
        req.setAttribute("f2", classNum);
        //在学フラグが送信されてた場合
        if (isAttendStr != null){
        	//在学フラグを立てる
        	isAttend = true;
        	//リクエストに在学フラグをセット
        	req.setAttribute("f3", isAttendStr);
        }
        //リクエストに学生リストをセット
        req.setAttribute("students", students);
        //リクエストにデータをセット
        req.setAttribute("class_num_set", list);
        req.setAttribute("ent_year_set", entYearSet);

        //JSPへフォワード
        req.getRequestDispatcher("student_list.jsp").forward(req, res);

	}
}
