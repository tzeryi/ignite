package com.ctk.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;

import com.ctk.vo.Employees;
import com.ctk.vo.EmployeesKey;



/**
 * Servlet implementation class EmployeeCacheInitServlet
 */
@WebServlet("/employeeCacheInit")
public class EmployeeCacheInitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void init() {
		IgniteCache<EmployeesKey, Employees> cache = Ignition.ignite().cache("employeeCache");
		cache.loadCache(null);
	}
}
