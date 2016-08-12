package com.ctk.ignite;

import java.util.List;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.SqlFieldsQuery;

import com.ctk.ignite.vo.Person;
import com.ctk.ignite.vo.PersonKey;

public class CacheJdbcPojoStoreDemo {

	public static void main(String[] args) {
		load();
		//query();
	}

	public static void load() {
		try (Ignite ignite = Ignition.start("ignite-cache-store.xml")) {
			IgniteCache<PersonKey, Person> cache = ignite.getOrCreateCache("personCache");
			
			cache.loadCache(null);

            QueryCursor<List<?>> cursor = cache.query(new SqlFieldsQuery(
                    "select * from Person where salary > 1000 and salary <= 300"));
            System.out.println(cursor.getAll());
		}
	}

	public static void query() {
		try (Ignite ignite = Ignition.start("ignite-cache-store.xml")) {
			IgniteCache<PersonKey, Person> cache = ignite.getOrCreateCache("personCache");
			
            QueryCursor<List<?>> cursor = cache.query(new SqlFieldsQuery(
                    "select * from Person where salary > 1000 and salary <= 300"));
            System.out.println(cursor.getAll());
		}
	}
}
