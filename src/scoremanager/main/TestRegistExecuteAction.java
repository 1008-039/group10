package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tool.Action;

public class TestRegistExecuteAction extends Action{
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception{
		req.getRequestDispatcher("test_regist.jsp").forward(req, res);
	}
}