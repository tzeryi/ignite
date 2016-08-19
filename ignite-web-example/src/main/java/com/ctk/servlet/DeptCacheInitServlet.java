package com.ctk.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;

import com.ctk.vo.Departments;
import com.ctk.vo.DepartmentsKey;

/**
 * Servlet implementation class DeptCacheInitServlet
 */
@WebServlet("/deptCacheInit")
public class DeptCacheInitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void init() {
		IgniteCache<DepartmentsKey, Departments> cache = Ignition.ignite().cache("deptCache");
		cache.loadCache(null);
	}
}
