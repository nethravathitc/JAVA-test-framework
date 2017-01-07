package com.styletag.scripts;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import com.styletag.functional_lib.*;

public class Driver {
	Method m;
	public static int FLAG=0;
		
	public static void main(String[] args) {
		
		String execution_flag,actions,msg,url;
		ArrayList<String> array = new ArrayList<String>();
		
		url="http://styletag.com";
		ExcelWrite write= new ExcelWrite();
		BusinessAction baction= new BusinessAction(write);
		
		baction.launchStyletag(url);
		msg ="Test Report of "+url;
		write.writeReports("Result", msg);
						
		ExcelRead xl= new ExcelRead("//home//styletag//java_test//Test Framework//src//com//styletag//test_cases//TestSuit.xlsx");
		int n =xl.rowCountInSheet(0);
		int i=1;
		try{
		while (xl.read(i,0)!=null)
		{	
			String scenario =msg="Test Scenario: "+xl.read(i, 0);
			array.add(msg);
			System.out.println(msg);
			
			msg="Test Case ID: "+xl.read(i,1);
			array.add(msg);
			//write.writeReports("Result", msg);
			System.out.println(msg);
			
			execution_flag=xl.read(i,2);
			//array.add("Execution Flag "+execution_flag);
			System.out.println(execution_flag);
			//write.writeReports("Result", execution_flag);
			int j=3;
			if(execution_flag.equals("YES"))
			{	write.writeReports("Error", scenario);
				write.writeReports("Log", scenario);
				int k=1;	
				do
				{
				actions = xl.read(i,j);
				msg="Action"+k+": "+actions;
				write.writeReports("Log", msg);
				write.writeReports("Error", msg);
				System.out.println(msg);
								
				 try {
					 
					Method method = baction.getClass().getMethod(actions);
					method.invoke(baction);
				} catch (Exception e) {
					//e.printStackTrace();
				} 
				
				j++;
				k++;
				}while(xl.read(i,j)!=null);
				
				if(FLAG==0)
					array.add("FAIL");
				else
					array.add("PASS");
				write.writeReports("Result", array);
				
			}
			i++;
			array.removeAll(array);// to remove the previous entry in the list, otherwise this list will store all the strings
		}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}

}
