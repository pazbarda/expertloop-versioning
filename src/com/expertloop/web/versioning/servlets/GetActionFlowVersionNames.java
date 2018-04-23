package com.expertloop.web.versioning.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.expertloop.dao.DaoException;
import com.expertloop.dao.versioning.VersioningDao;
import com.expertloop.dao.versioning.VersioningDaoMock;

@WebServlet("/GetActionFlowVersions")
public class GetActionFlowVersionNames extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static VersioningDao dao;
	
	@Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
		// http://localhost:9080/com.expertloop.web.versioning/GetActionFlowVersions?actionFlowId=1000
		int actionFlowId = Integer.parseInt(request.getParameter("actionFlowId"));
		PrintWriter out = response.getWriter();
		try {
			out.println(dao.getIdToActionFlowVersionNameMap(actionFlowId));
		} catch (DaoException e) {
			throw new ServletException(e.getMessage());
		}
	}

	@Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	PrintWriter out = response.getWriter();
    	out.println("GetActionFlowVersions - POST detected!");
    }
	
	@Override
    public void init() throws ServletException {
		try {
			dao = VersioningDaoMock.getInstance();
		} catch (DaoException e) {
			throw new ServletException(e.getMessage());
		}
    }

    public void destroy() {
        super.destroy();
    }

}
