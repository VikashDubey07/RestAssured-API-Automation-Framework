package com.qa.api.configmanager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReaderManager {
	
		
		private static Properties properties = new Properties();
		
		static
		{
			
			// mvn clean install -Denv=qa
			// mvn clean install -Denv=dev

			// mvn clean install -- if env is not given, then run test cases on QA env by default.
			// env = environment variable (system)

			String envName = System.getProperty("env", "prod");// it sets the env by default to qa if dont pass any env
			
			System.out.println("Running tests on env: " + envName);
			String fileName = "config_"+envName+".properties"; // config_qa.properties
			InputStream input= ConfigReaderManager.class.getClassLoader().getResourceAsStream(fileName);
			
			//InputStream input= ConfigReaderManager.class.getClassLoader().getResourceAsStream("config/config.properties");
			if(input!=null)
			{
				try {
					
					properties.load(input);
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}
			}
		}
		
		public static String get(String key)
		{
			return properties.getProperty(key).trim();
			
		}

		public static void set(String key, String value)
		{
			properties.setProperty(key,value);
			
		}
		


}
