package mvc.servlet;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import mvc.factory.*;
import mvc.vo.*;

public class ViewReportServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//set variable
		HttpSession session = req.getSession();
		String userid = session.getAttribute("userid").toString();		
		int reportid = Integer.parseInt(req.getParameter("reportid"));	
		ArrayList<Attach> attachlist = new ArrayList<Attach>();
		String path = "viewreport.jsp";	
		//get report
		Report report = new Report();
		report.setReportid(reportid);
		report.setUserid(userid);
		try {
			DAOFactory.getIReportDAOInstance().readReportById(report);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//get attachment of report
		Attach attach = new Attach();
		attach.setReportid(reportid);
		attach.setUserId(userid);
		try {
			attachlist = DAOFactory.getIAttachDAOInstance().readAttachByReportId(attach);
			//System.out.println("ViewReportServlet-attachlist-reportid 0:"+attachlist.get(0).getReportid());
		} catch (Exception e) {
			e.printStackTrace();
		}
		//jump to page
		req.setAttribute("report", report);
		req.setAttribute("attachlist", attachlist);
		req.getRequestDispatcher(path).forward(req, resp);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doGet(req, resp);
	}
}
