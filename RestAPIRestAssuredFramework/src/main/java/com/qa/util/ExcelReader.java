package com.qa.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.testng.annotations.DataProvider;

public class ExcelReader {
	
	
	

	//public static  String filePath = System.getProperty("user.home") +File.separator+"TestData"+File.separator+"testdata.xlsx";
	public static  String filePath = System.getProperty("user.dir")+
			File.separator+"src"+File.separator+"main"+File.separator+"resources"+File.separator+"testdata.xlsx";
	
	public int getNomOfYMarkedTestCases(String testcasename)
	{
		int count=0;
	    try {	
	    		//System.out.println(testcasename);
	    		
	    		FileInputStream fis = new FileInputStream(filePath);
	    		Workbook wb = WorkbookFactory.create(fis);
	    		Sheet sh = wb.getSheet("Sheet1");
	    		for(int i=1;i<sh.getPhysicalNumberOfRows();i++)
	    		{
	    			try {
	    			Row r = sh.getRow(i);
	    			Cell c1 = r.getCell(1);
	    			Cell c2 = r.getCell(2);
	    			//System.out.println(c1.getStringCellValue());
	    			if(c1.getStringCellValue().equalsIgnoreCase(testcasename) && c2.getStringCellValue().equalsIgnoreCase("Y"))
	    			{
	    				count++;
	    			}
	    			}
	    			catch (NullPointerException e)
	    			{
	    			System.out.println("caught null pointer");
	    			}
	    			
	    		}
	    	} catch (Exception e) 
	    	{
	    		System.out.println("Please re-check the required Excel Sheet");
	    		e.printStackTrace();
	    	}
	    	System.out.println( count);
	    	return count;
	
	}
	
	public int getNumOfCellInGivenTestcase(String testcasename)
	{

		int count=0;
		try{
		
			FileInputStream fis = new FileInputStream(filePath);
			Workbook wb = WorkbookFactory.create(fis);
			Sheet sh = wb.getSheet("Sheet1");
			
			for(int i=1;i<sh.getPhysicalNumberOfRows();i++)
			{
				try {
				Row r = sh.getRow(i);
				Cell c1 = r.getCell(1);
				Cell c2 = r.getCell(2);
				if(c1.getStringCellValue().equalsIgnoreCase(testcasename) && c2.getStringCellValue().equalsIgnoreCase("Y"))
				{
					count = r.getPhysicalNumberOfCells()-3;
				}
				}
				catch(NullPointerException e1)
				{
					System.out.println("caught Null pointer");
				}
			}
		}catch(Exception e)
		{
			System.out.println("Please re-check the required Excel Sheet");
			e.printStackTrace();
			
		}
		System.out.println( count);
		return count;
	}
	
	@DataProvider(name="ExcelTestData")
	public String[][] getTestData(Method m)
	{
		String[][] testdata=null;
		try{
			FileInputStream fis = new FileInputStream(filePath);
			Workbook wb = WorkbookFactory.create(fis);
			Sheet sh = wb.getSheet("Sheet1");
			
			String testcasename = m.getName();
			Class dec = m.getDeclaringClass();
			testdata = new String[getNomOfYMarkedTestCases(testcasename)][getNumOfCellInGivenTestcase(testcasename)+1];
			int rowIndex = 0;		
			for(int i=1;i<sh.getPhysicalNumberOfRows();i++)
			{
				try {
				Row r = sh.getRow(i);
				Cell c1 = r.getCell(1);
				Cell c2 = r.getCell(2);
				if(c1.getStringCellValue().equalsIgnoreCase(testcasename) && c2.getStringCellValue().equalsIgnoreCase("Y")){
					int cellIndex = 0;
					 System.out.println("Method = " + m.toString());
			         System.out.println(" Declaring class: " + dec.toString());
					System.out.println();
					for(int j=3;j<r.getPhysicalNumberOfCells();j++)
					{
					try{
						Cell c = r.getCell(j);
						String val = c.getStringCellValue();
						testdata[rowIndex][cellIndex++] = val;
						}
						catch (NullPointerException e) {
							cellIndex++;
						}
					}
					//We are saving the row number by using "" string.
					testdata[rowIndex][cellIndex] = i+"";
					rowIndex++;
					System.out.println(rowIndex);
				}
			}
				catch(NullPointerException e2)
				{
					System.out.println("caught null pointer");
				}
			}
		}catch(Exception e)
		{
			System.out.println("Please re-check the required Excel Sheet");
			e.printStackTrace();
		}
		return testdata;
	}
	
	public static void WriteToExcel(String result, int rowNum, int colNum) throws IOException
	{
		FileOutputStream fos = null;
		try{
			FileInputStream fis = new FileInputStream(filePath);
			Workbook wb = WorkbookFactory.create(fis);
			Sheet sh = wb.getSheet("Sheet1");
			
			Row r = sh.getRow(rowNum);
			Cell c = r.createCell(colNum);
			if(c.getStringCellValue()!=null)
			{
			   //System.out.println(c.getStringCellValue());
			   c.setCellValue(result);
			}
			else
			{
				c.setCellValue("");
			}
			 fos = new FileOutputStream(filePath);
			wb.write(fos);
			fos.close();
			
		}catch(Exception e)
		{
			fos.close();
		}
	}

}
