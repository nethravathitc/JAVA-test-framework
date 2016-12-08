package com.styletag.scripts;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.styletag.functional_lib.*;

public class Driver {
	Method m;
	public static void main(String[] args) {
		BusinessAction baction= new BusinessAction();
		String execution_flag,actions;

		baction.launchStyletag("http://styletag.com");
						
		ExcelRead xl= new ExcelRead("//home//styletag//java_test//Test Framework//src//com//styletag//test_cases//TestSuit.xlsx");
		int n =xl.rowCountInSheet(0);
		int i=1;
		try{
		while (xl.read(i,0)!=null)
		{
			System.out.println("Test Scenario: "+xl.read(i, 0));
			System.out.println("Test Case ID: "+xl.read(i,1));
			execution_flag=xl.read(i,2);
			System.out.println(execution_flag);
			int j=3;
			if(execution_flag.equals("YES"))
			{	int k=1;	
				do
				{
				actions = xl.read(i,j);
				System.out.println("Action"+k+": "+actions);
								
				 try {
					Method method = baction.getClass().getMethod(actions);
					method.invoke(baction);
				} catch (Exception e) {
					e.printStackTrace();
				} 
				
				j++;
				k++;
				}while(xl.read(i,j)!=null);
			}
			i++;
		}
		}catch(Exception e)
		{
			
		}
		
	}

}
