package com.styletag.functional_lib;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelWrite {
	XSSFWorkbook workbook ;
	XSSFSheet spreadsheet1;
	XSSFSheet spreadsheet2;
	XSSFRow row ; Cell cell;
	DateFormat df;
	Date dateobj;
	String path;

	public ExcelWrite()
	{
		workbook = new XSSFWorkbook();
		spreadsheet1 = workbook.createSheet("Results");
		spreadsheet2 = workbook.createSheet("Error");	
	    df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
	    dateobj = new Date();
	    String d =df.format(dateobj);
	    String sd[] = d.split("\\s");
	    System.out.println(sd[0]);
	    System.out.println(sd[1]);
	    sd[0]=sd[0].replaceAll("\\/","_");
	    sd[1]=sd[1].replaceAll(":","_");
	    d="_date_"+sd[0]+"_time_"+sd[1];
	    
	    path="//home//styletag//Sanity_report//report"+d+".xlsx";
	    
	}
	public void writeReports(String sheetName,String msg)
	{	int rowsh1,rowsh2;
		System.out.println(sheetName+"  "+msg);
	
		if (sheetName.equals("Result")) 
		{	
			rowsh1=spreadsheet1.getLastRowNum();
			rowsh1++;
			row= spreadsheet1.createRow(rowsh1);
			cell = row.createCell(0);
			cell.setCellValue(msg);
			
		}
		if(sheetName.equals("Error"))
		{
			rowsh2=spreadsheet1.getLastRowNum();
			rowsh2++;
			row= spreadsheet2.createRow(rowsh2);
			cell=row.createCell(0);
			cell.setCellValue(msg);
		}
		
		
		
		try {
			File file = new File(path);
			FileOutputStream out= new FileOutputStream(file);
			workbook.write(out);
		} catch (FileNotFoundException e) {
			System.out.println("Error in fileoutput stream");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error in closing fileoutputstream");
			e.printStackTrace();
		}
		
		
		
	}	
	

}
