package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Subject;
import bean.Teacher;
import dao.ClassNumDAO;
import dao.SubjectDao;
import tool.Action;

public class TestListAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

	HttpSession session = req.getSession();
	Teacher teacher= (Teacher)session.getAttribute("user");

	ClassNumDAO cNumDao = (ClassNumDAO) req.getSession();
	SubjectDao subjectDao = new SubjectDao();
	School teacherSchool =teacher.getSchool();
	LocalDate todaysDate = LocalDate.now();
	int year = todaysDate.getYear();

	List<String>cNumlist=cNumDao.filter(teacherSchool);
	List<Subject>list=subjectDao.filter(teacherSchool);

	List<Integer> entYearSet = new ArrayList<>();
	for (int i =year - 10; i < year + 11; i++) {
		entYearSet.add(i);
	}

	req.setAttribute("cNumlist", cNumlist);
	req.setAttribute("list", list);
	req.setAttribute("entYearSet", entYearSet);

	req.getRequestDispatcher("test_list_jsp").forward(req, res);
 }
}