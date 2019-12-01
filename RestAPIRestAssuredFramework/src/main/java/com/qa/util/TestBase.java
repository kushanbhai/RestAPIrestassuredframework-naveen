package com.qa.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class TestBase {
	public static Properties prop;

	public static void init() {
prop = new Properties();
try {
	FileInputStream fis = new FileInputStream("D:\\Users\\choudhku\\Automation\\RestAPIRestAssuredFramework\\src\\main\\java\\com\\qa\\config\\config.properties");
prop.load(fis);
} catch (FileNotFoundException e) {
	e.printStackTrace();
} catch (IOException e) {
	e.printStackTrace();
}
	}
}
