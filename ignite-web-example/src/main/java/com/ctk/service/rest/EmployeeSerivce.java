package com.ctk.service.rest;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.SqlFieldsQuery;

import com.ctk.vo.Employees;
import com.ctk.vo.EmployeesKey;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Path("/employee")
public class EmployeeSerivce {
	@GET
	@Path("/queryEmpByNo")
	@Produces("application/json")
	public Response queryEmpByNo (
			@QueryParam("empNo") int empNo) {

		IgniteCache<EmployeesKey, Employees> cache = Ignition.ignite().cache("employeeCache");
		
		EmployeesKey key = new EmployeesKey(empNo);

		Employees emp = cache.get(key);

		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		String output = gson.toJson(emp);

		return Response.status(200).entity(output).build();
	}

	@GET
	@Path("/queryEmpByNoList")
	@Produces("application/json")
	public Response queryEmpByNoList (
			@QueryParam("empNoList") String empNoList) {

		IgniteCache<EmployeesKey, Employees> cache = Ignition.ignite().cache("employeeCache");

		Set<EmployeesKey> keySet = new HashSet<>();
		for (String empNo : empNoList.split(",")) {
			keySet.add(new EmployeesKey(Integer.parseInt(empNo)));
		}

		Map<EmployeesKey, Employees> emp = cache.getAll(keySet);

		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		String output = gson.toJson(emp);

		return Response.status(200).entity(output).build();
	}

	@GET
	@Path("/sqlQuery")
	@Produces("application/json")
	public Response sqlQuery() {

		IgniteCache<EmployeesKey, Employees> cache = Ignition.ignite().cache("employeeCache");

		// The result of sql function count() is incorrect
		SqlFieldsQuery sql = new SqlFieldsQuery("select distinct birth_date from employees");

		int cnt = 0;
		HashMap<String, String> result = new HashMap<String, String>();
		try (QueryCursor<List<?>> cursor = cache.query(sql)) {
			cnt = cursor.getAll().size();
//			  for (List<?> row : cursor) {
//				  cnt++;
//				  //result.put("birthDateCount", row.get(0).toString());
//			  }
		}
		result.put("birthDateCount", Integer.toString(cnt));
		Gson gson = new Gson();
		String output = gson.toJson(result);
		return Response.status(200).entity(output).build();
	}
}
