package scoremanager.main;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.Subject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import dao.ClassNumDAO;
import tool.Action;

public class SubjectListAction extends Action {

	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception{
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");

        String entYearStr="";
        String classNum = "";
        String isAttendStr="";
        int entYear = 0;
        List<Subject> subjects = null;
        LocalDate todaysDate = LocalDate.now();
        int year = todaysDate.getYear();
        ClassNumDAO cNumDao = new ClassNumDAO();




	entYearStr= req.getParameter("f1");
	classNum = req.getParameter("f2");
	isAttendStr = req.getParameter("f3");

	if (entYearStr != null){
		entYear = Integer.parseInt(entYearStr);
	}




	List<Integer> entYearSet = new ArrayList<>();
	for (int i = year - 10; i < year + 1; i++) {
		entYearSet.add(i);
	}


	List<String> list = cNumDao.filter(teacher.getSchool());


	    req.setAttribute("f1", entYear);
		req.setAttribute("f2", classNum);

		req.setAttribute("f3",isAttendStr);

		req.setAttribute("subjects", subjects);
		req.setAttribute("class_num_set",list);
		req.setAttribute("ent_year_set", entYearSet);
		req.getRequestDispatcher("subject_list.jsp").forward(req, res);

	}

}



