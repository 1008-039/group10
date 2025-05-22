package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Teacher;
import bean.Test;
import dao.ClassNumDAO;
import dao.SubjectDao;
import dao.TestDao;
import tool.Action;

public class TestRegistAction extends Action{

	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception{

		HttpSession session = req.getSession();

		Teacher teacher = (Teacher)session.getAttribute("user");

		String entYearStr = null;

		int entYear = 0;

		String classNum = "";

		String subject = "";

		String countStr = null;

		int count = 0;

		ClassNumDAO cNumDao = new ClassNumDAO();

		SubjectDao subjectDao = new SubjectDao();

		TestDao testDao = new TestDao();

		School teacherSchool = teacher.getSchool();

		LocalDate todaysDate = LocalDate.now();

		int year = todaysDate.getYear();

		Map<String, String> errors = new HashMap<>();

		entYearStr = req.getParameter("f1");

		classNum = req.getParameter("f2");

		subject = req.getParameter("f3");

		countStr = req.getParameter("f4");

		List<String>cNumlist = cNumDao.filter(teacherSchool);

		List<bean.Subject>list = subjectDao.filter(teacherSchool);

		if (entYearStr != null){

			entYear = Integer.parseInt(entYearStr);

		}

		if (countStr != null){

			count = Integer.parseInt(countStr);

		}

		List<Integer> entYearSet = new ArrayList<>();

		for (int i = year - 10; i < year + 11; i++){

			entYearSet.add(i);

		}

		List<Integer> countSet = new ArrayList<>();

		for(int i = 1; i < 3 ; i++){

			countSet.add(i);

		}

		if (entYear == 0 && classNum == null && subject == null && count == 0){

		}else if (entYear != 0 && !(classNum.equals("0")) && !(subject.equals("0")) && count != 0){

				List<Test>testlist = testDao.filter(entYear, classNum, subjectDao.get(subject, teacherSchool)).

			    Subject = subjectDao.get(subject, teacherSchool).getName();

				req.setAttribute("testlist", testlist);

				req.setAttribute("subject_name", subject);

	    }else {

	    	errors.put("a", "入学年度とクラスと科目の回数を選択してください");

	    	req.setAttribute("errors", errors);

	    }

		req.setAttribute("f1", entYear);

		req.setAttribute("f2", classNum);

		req.setAttribute("f3", subject);

		req.setAttribute("f4", count);

		req.setAttribute("entYearList", entYearSet);

		req.setAttribute("cNumList", cNumlist);

		req.setAttribute("list", list);

		req.setAttribute("countList", countSet);

		req.getRequestDispatcher("test_regist.jsp").forward(req, res);

	}


}
