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

/**
 * @author pazb
 */
@WebServlet("/GetActionFlows")
public class GetActionFlowNames extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static VersioningDao dao;
	
	@Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
		// http://localhost:9080/com.expertloop.web.versioning/GetActionFlows
		PrintWriter out = response.getWriter();
		out.println(dao.getIdToActionFlowNameMap());
	}

	@Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	PrintWriter out = response.getWriter();
    	out.println("GetActionFlows - POST detected!");
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
