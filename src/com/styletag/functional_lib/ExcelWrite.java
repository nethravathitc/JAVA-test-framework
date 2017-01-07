package com.styletag.functional_lib;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelWrite {
	XSSFWorkbook workbook ;
	XSSFSheet spreadsheet1;
	XSSFSheet spreadsheet2;
	XSSFSheet spreadsheet3;
	XSSFRow row ; Cell cell;
	DateFormat df;
	Date dateobj;
	String path;
	int rowsh1=0,rowsh2=0,rowsh3=0;

	public ExcelWrite()
	{
		workbook = new XSSFWorkbook();
		spreadsheet1 = workbook.createSheet("Results");
		spreadsheet2 = workbook.createSheet("Log");
		spreadsheet3= workbook.createSheet("Error");
		setHeader();// called only once when constructor is called	   
	    
	}
	public void setHeader()
	{
		df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
	    dateobj = new Date();
	    String d =df.format(dateobj); String date1=d;
	    String sd[] = d.split("\\s");
	    System.out.println(sd[0]);
	    System.out.println(sd[1]);
	    sd[0]=sd[0].replaceAll("\\/","_");
	    sd[1]=sd[1].replaceAll(":","_");
	    d="_date_"+sd[0]+"_time_"+sd[1];
	    
	   path="//home//styletag//Sanity_report//report"+d+".xlsx";
	   
	   String msg1="TEST EXECUTION RESULTS ",msg2="DATE: "+df.format(dateobj);
	   //rowsh1=0;rowsh2=0;
	   row=spreadsheet1.createRow(0); // writing in to first row  of Result sheet
	   cell=row.createCell(0);
	   cell.setCellValue(msg1);
	   cell=row.createCell(1);
	   cell.setCellValue(msg2);
	   
	   row=spreadsheet2.createRow(0); // writing in to first row page of Console sheet
	   cell=row.createCell(0);
	   cell.setCellValue(msg1);
	   cell=row.createCell(1);
	   cell.setCellValue(msg2);	
	   
	   row=spreadsheet3.createRow(0); // writing in to first row page of Error sheet
	   cell=row.createCell(0);
	   cell.setCellValue(msg1);
	   cell=row.createCell(1);
	   cell.setCellValue(msg2);
	   
	}
	public void writeReports(String sheetName,String msg)
	{	//int col=0;
		//System.out.println(sheetName+"  "+msg);
	
		if (sheetName.equals("Result")) 
		{	
			rowsh1=spreadsheet1.getLastRowNum();
			rowsh1++;
			row= spreadsheet1.createRow(rowsh1);
			//if(column!=-1)
				//col=column;

			cell = row.createCell(0);
			cell.setCellValue(msg);
			
		}
		if(sheetName.equals("Log"))
		{
			rowsh2=spreadsheet2.getLastRowNum();
			rowsh2++;
			row= spreadsheet2.createRow(rowsh2);
			//if(column!=-1)
				//col=column;
			
			cell=row.createCell(0);
			cell.setCellValue(msg);
		}
		
		if(sheetName.equals("Error"))
		{
			rowsh3=spreadsheet3.getLastRowNum();
			rowsh3++;
			row= spreadsheet3.createRow(rowsh3);
			//if(column!=-1)
				//col=column;
			
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
	
	public void writeReports(String sheetName,ArrayList<String> msg)
	{	int col=0;
		//System.out.println(sheetName+"  "+msg);
		//System.out.println("coloumn count "+msg.size());
		if (sheetName.equals("Result")) 
		{	
			rowsh1=spreadsheet1.getLastRowNum();
			rowsh1++;
			row= spreadsheet1.createRow(rowsh1);
			for(int i=0;i<msg.size();i++)
			{
				cell = row.createCell(i);
				cell.setCellValue(msg.get(i));
			}
		}
		if(sheetName.equals("Console"))
		{
			rowsh2=spreadsheet2.getLastRowNum();
			rowsh2++;
			row= spreadsheet2.createRow(rowsh2);
			for(int i=0;i<msg.size();i++)
			{
				cell = row.createCell(i);
				cell.setCellValue(msg.get(i));
			}
		}
		
		if(sheetName.equals("Error"))
		{
			rowsh3=spreadsheet3.getLastRowNum();
			rowsh3++;
			row= spreadsheet1.createRow(rowsh3);
			for(int i=0;i<msg.size();i++)
			{
				cell = row.createCell(i);
				cell.setCellValue(msg.get(i));
			}
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
