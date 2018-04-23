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
import com.expertloop.versioning.actionflow.ActionFlowException;
import com.expertloop.versioning.actionflow.ActionFlowVersion;
import com.expertloop.web.versioning.readers.text.TextActionFlowStepReader;

/**
 * Servlet for getting an action flow step
 */
@WebServlet("/GetStep")
public class GetStep extends HttpServlet {
    
	private static final long serialVersionUID = 1L;
    private static VersioningDao dao;

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
    	// http://localhost:9080/com.expertloop.web.versioning/GetStep?actionFlowId=4000&versionId=1&stepId=1
    	PrintWriter out = response.getWriter();
    	int actionFlowId = Integer.parseInt(request.getParameter("actionFlowId"));
    	int versionId = Integer.parseInt(request.getParameter("versionId"));
    	int stepId = Integer.parseInt(request.getParameter("stepId"));
    	
    	ActionFlowVersion actionFlowVersion;
    	TextActionFlowStepReader stepReader;
    	try {	
    		actionFlowVersion = dao.getActionFlowVersion(actionFlowId, versionId);
    		stepReader = new TextActionFlowStepReader(actionFlowVersion, stepId);
		} catch (DaoException | ActionFlowException e) {
			throw new ServletException(e.getMessage());
		}
    	out.println(stepReader.getStepInfo());
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	PrintWriter out = response.getWriter();
    	out.println("GetStep - POST detected!");
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