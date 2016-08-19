package com.ctk.ignite;

import java.util.List;

import javax.cache.Cache.Entry;
import javax.cache.configuration.FactoryBuilder;
import javax.sql.DataSource;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteCluster;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.SqlFieldsQuery;
import org.apache.ignite.cache.query.SqlQuery;
import org.apache.ignite.cache.store.jdbc.CacheJdbcPojoStore;
import org.apache.ignite.cache.store.jdbc.CacheJdbcPojoStoreFactory;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.resources.SpringResource;

import com.ctk.ignite.config.PersonCacheConfig;
import com.ctk.ignite.vo.Person;
import com.ctk.ignite.vo.PersonKey;
import com.ctk.ignite.vo.Salaries;
import com.ctk.ignite.vo.SalariesKey;

public class CacheJdbcPojoStoreDemo {

    @SpringResource(resourceName = "dataSource")
    private DataSource dataSource;

	public static void main(String[] args) {
		//load();
		query();
		
//		CacheJdbcPojoStoreDemo demo = new CacheJdbcPojoStoreDemo();
//		demo.load1();
//		querySalary();
	}

	public static void load() {
		try (Ignite ignite = Ignition.start("ignite-cache-store.xml")) {
			IgniteCache<PersonKey, Person> cache = ignite.getOrCreateCache("personCache");
			
			cache.loadCache(null);
		}
	}

	public void load1() {
		try (Ignite ignite = Ignition.start("ignite-cache-store2.xml")) {
			CacheJdbcPojoStoreFactory<PersonKey, Person> storeFactory = new CacheJdbcPojoStoreFactory<>();
			storeFactory.setDataSourceBean("dataSource");

			CacheConfiguration<PersonKey, Person> ccfg = PersonCacheConfig.cache("personCache", storeFactory);

			try (IgniteCache<PersonKey, Person> cache = ignite.getOrCreateCache(ccfg)) {
				cache.loadCache(null);
			}
		}
	}

	public static void query() {
		try (Ignite ignite = Ignition.start("ignite-cache-store.xml")) {
			IgniteCache<PersonKey, Person> cache = ignite.getOrCreateCache("personCache");
			
            QueryCursor<List<?>> cursor = cache.query(new SqlFieldsQuery(
                    "select count(distinct id) from Person"));
            System.out.println(cursor.getAll());
		}
	}

	public static void query1() {
		try (Ignite ignite = Ignition.start("ignite-cache-store.xml")) {
			IgniteCache<PersonKey, Person> cache = ignite.getOrCreateCache("personCache");

			SqlQuery<PersonKey, Person> sql = new SqlQuery<>(Person.class, "salary > ?");
			try (QueryCursor<Entry<PersonKey, Person>> cursor = cache.query(sql.setArgs(1000))) {
				for (Entry<PersonKey, Person> e : cursor) {
					System.out.println(e.getValue().toString());
				}
			}
		}
	}

	public static void querySalary() {
		try (Ignite ignite = Ignition.start("ignite-cache-store.xml")) {
			try (IgniteCache<SalariesKey, Salaries> cache = ignite.getOrCreateCache("salaryCache")) {
				long startLoad = System.currentTimeMillis();
				cache.loadCache(null);
				System.out.println("Load Salaries : " + (System.currentTimeMillis() - startLoad));

				long startQuery = System.currentTimeMillis();
	            QueryCursor<List<?>> cursor = cache.query(new SqlFieldsQuery(
	                    "select count(distinct emp_no) from salaries"));
	            System.out.println(cursor.getAll());
	            System.out.println("Query : " + (System.currentTimeMillis() - startQuery));
			}
		}
	}
}
