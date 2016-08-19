package com.ctk.service.rest;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.transactions.Transaction;

import com.ctk.vo.Departments;
import com.ctk.vo.DepartmentsKey;
import com.ctk.vo.Employees;
import com.ctk.vo.EmployeesKey;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Path("/dept")
public class DepartmentService {

	@GET
	@Path("/queryDeptByNo")
	@Produces("application/json")
	public Response queryDeptByNo (
			@QueryParam("deptNo") String deptNo) {

		IgniteCache<DepartmentsKey, Departments> cache = Ignition.ignite().cache("deptCache");
		
		DepartmentsKey key = new DepartmentsKey(deptNo);

		Departments dept = cache.get(key);

		Gson gson = new Gson();
		String output = gson.toJson(dept);

		return Response.status(200).entity(output).build();
	}

	@POST
	@Path("/addDept")
	public Response addDept(
			@QueryParam("deptNo") String deptNo,
			@QueryParam("deptName") String deptName) {
		IgniteCache<DepartmentsKey, Departments> cache = Ignition.ignite().cache("deptCache");

		String msg = "Add Department OK";

		try (Transaction tx = Ignition.ignite().transactions().txStart()) {
			DepartmentsKey key = new DepartmentsKey(deptNo);
			Departments value = new Departments(deptName);
			cache.put(key, value);
			
			tx.commit();
		} catch (Exception ex) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
		}

		return Response.status(Response.Status.CREATED).entity(msg).build();
	}
}
