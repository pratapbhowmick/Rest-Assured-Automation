package Utility;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertyReader {

	public static String getProperty(String filePath,String propertyKey) throws FileNotFoundException, IOException {
		Properties prop=new Properties();
		prop.load(new FileInputStream(filePath));
		return prop.getProperty(propertyKey);
		
	}
	
}
