package config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
public class Config_Reader {
	int result ;
	InputStream inputStream;
 
	public int getPropValues() throws IOException {
 
		try {
			Properties prop = new Properties();
			inputStream = Config_Reader.class.getResourceAsStream("DBApp.properties");
			if (inputStream != null) {
				prop.load(inputStream);
			} 
			else {
				throw new FileNotFoundException("property file '" + "DBApp.properties" + "' not found in the classpath");
			}
			String MaximumRows = prop.getProperty("MaximumRowsCountinPage");
			result = Integer.parseInt(MaximumRows);
		}
		catch (Exception e) {
			System.out.println("Exception: " + e);
		} 
		finally {
			inputStream.close();
		}
		return result;
	}
}
