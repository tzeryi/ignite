package com.ctk.ignite;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;

public class CacheJdbcPojoStoreDemo {

	public static void main(String[] args) {
		try (Ignite ignite = Ignition.start("ignite-cache-store.xml")) {
			IgniteCache<Integer, String> cache = ignite.getOrCreateCache("personCache");
			
			cache.loadCache(null);
		}
	}

}
