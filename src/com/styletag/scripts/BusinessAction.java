package com.styletag.scripts;


import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.styletag.functional_lib.ExcelRead;
import com.styletag.ui_object_info.UIobjects;

public class BusinessAction {
	public WebDriver webdriver ;
	String url;
	Actions act;
	WebDriverWait wait;
	String product1 ,product2,orderNo;
	ExcelRead xl;
	public void BusinessAction(){
		xl=new ExcelRead("//home//styletag//java_test//Test Framework//src//com//styletag//test_cases//InputData.xlsx");
	}
	
	public void launchStyletag(String url){
		System.setProperty("webdriver.chrome.driver","//home//styletag//Documents//chromedriver");
		webdriver = new ChromeDriver();
		webdriver.manage().timeouts().implicitlyWait(100,TimeUnit.SECONDS);
		webdriver.get(url);
		webdriver.manage().window().maximize();
		}
	
	public void login() {
		
		try {
			System.out.println("maximing windows");
			//spinner();
			Thread.sleep(5);
			WebElement login_name= webdriver.findElement(By.cssSelector(UIobjects.login_name_css));
			act = new Actions(webdriver);
			System.out.println("calling perform function");
			//Thread.sleep(5);
			act.moveToElement(login_name).build().perform();
			webdriver.findElement(By.cssSelector(UIobjects.login_link_css)).click();
			System.out.println("entering login details");
			
			//ExcelRead xl= new ExcelRead("//home//styletag//java_test//Test Framework//src//com//styletag//test_cases//InputData.xlsx");
			int no =xl.rowCountInSheet(1);
			//System.out.println("total count "+no);
			//System.out.println(xl.read(2, 0));
			//System.out.println(xl.read(2, 1));
			
			webdriver.findElement(By.cssSelector(UIobjects.login_email_css)).sendKeys(xl.read(2, 0));
			webdriver.findElement(By.cssSelector(UIobjects.login_pass_css)).sendKeys(xl.read(2, 1));
			webdriver.findElement(By.cssSelector(UIobjects.login_btn_css)).click();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	
	public void logout() throws InterruptedException{
		//Thread.sleep(10);
		System.out.println("User logging out ");
		wait = new WebDriverWait(webdriver,50);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(UIobjects.acc_mem_name_id)));
		WebElement menu = webdriver.findElement(By.id(UIobjects.acc_mem_name_id));
		new Actions(webdriver).moveToElement(menu).build().perform();
		Thread.sleep(2000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(UIobjects.inner_logout_css)));
		webdriver.findElement(By.cssSelector(UIobjects.inner_logout_css)).click();
		//Thread.sleep(5000);
		webdriver.quit();
	}
	
	public void search()
	{	String search_keyword,sort_value;
	    int count=1,sort_value_int;
		ExcelRead xl=new ExcelRead("//home//styletag//java_test//Test Framework//src//com//styletag//test_cases//InputData.xlsx");
		xl.rowCountInSheet(2);
		search_keyword=xl.read(1,0);
		sort_value=xl.read(1,1);
		sort_value=sort_value.replaceAll("[^0-9]", "");
		System.out.println("stirng sort value "+sort_value);
		sort_value_int=Integer.parseInt(sort_value);
		System.out.println("sort value: "+sort_value_int);
		
		System.out.println("Clicking on search tab");
		webdriver.findElement(By.cssSelector(UIobjects.search_field_css)).click();
		System.out.println("Clearing the field");
		webdriver.findElement(By.cssSelector(UIobjects.search_field_css)).clear();
		webdriver.findElement(By.cssSelector(UIobjects.search_field_css)).sendKeys(search_keyword);
		webdriver.findElement(By.cssSelector(UIobjects.search_button_css)).click();
		String title = webdriver.findElement(By.cssSelector(UIobjects.page_title_css)).getText();
		System.out.println("Search page title : "+title);
		
		
		String value=webdriver.findElement(By.cssSelector(UIobjects.slider_value_css)).getText();
		String numberOnly= value.replaceAll("[^0-9]", "");
		//System.out.println(numberOnly);
		count = Integer.parseInt(numberOnly);
		System.out.println("slider value is: "+count);
		sort(sort_value_int,count);
		//compare(count);
		
		
				
	}
	public void sort(int option, int count){
		if (option==1){
		System.out.println("clicking on Low-High");
		webdriver.findElement(By.cssSelector(UIobjects.low_high_css)).click();
		compare(option, count);
		}
		if (option == 2){
			System.out.println("clicking on High-Low");
			webdriver.findElement(By.cssSelector(UIobjects.high_low_css)).click();
			compare(option, count);
		}
		
	}
	public void compare(int option,int count)
	{
		//try
		{
			String Sprice1,Sprice2;
			int Iprice1,Iprice2,flag=0,j=1;
			wait = new WebDriverWait(webdriver,5);
			for (int i=1;i<count;i++){
				
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				//wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#product-container > div.ng-isolate-scope > ul > li:nth-child("+i+"1) > div > div.product-Info > span.product-price > span.product-dmrp")));
				Sprice1= webdriver.findElement(By.cssSelector("#product-container > div.ng-isolate-scope > ul > li:nth-child("+i+") > div > div.product-Info > span.product-price > span.product-dmrp.col-orange.text-capitalize.ng-binding")).getText();
				//System.out.println("i value first "+i);
				i++;
				
				//wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("#product-container > div > ul > li:nth-child(" +i +") > div > div.product-Info > span.product-price.pull-right > span.col-orange.text-right.text-capitalize.ng-binding")));
				Sprice2=webdriver.findElement(By.cssSelector("#product-container > div.ng-isolate-scope > ul > li:nth-child("+i+") > div > div.product-Info > span.product-price > span.product-dmrp.col-orange.text-capitalize.ng-binding")).getText(); 
				//System.out.println("second i value " +i);
				
				System.out.println("price1 and price2 before replace all"+Sprice1+Sprice2);
				Sprice1=Sprice1.replaceAll("[^0-9]" ,"");
				Sprice2=Sprice2.replaceAll("[^0-9]", "");
				Iprice1=Integer.parseInt(Sprice1);
			
				Iprice2=Integer.parseInt(Sprice2);
				
				System.out.println(Iprice2);
				if(option==1){
					if(Iprice1>Iprice2)
					{	System.out.println("inside first if"); // in low_high, first product price is greater than second product
						System.out.println("First product price: "+Iprice1+"second product price: "+Sprice2);
						flag=1;
						break;
					}
				}
				if (option==2 ){
					if(Iprice1<Iprice2)
					{	System.out.println("inside second if");// in high_low, first product price is lesser than second product
					    System.out.println("First product price: "+Iprice1+"second product price: "+Sprice2);
						flag=1;
						break;
					}
				}
				JavascriptExecutor jse= (JavascriptExecutor)webdriver;
				jse.executeScript("window.scrollBy(0,150)", "");
				
				if(i>=15) // DOM will load first 25 product in the begining, to make other products visible requires more scroll
				{
					j++;
						try {
							//System.out.println("j value is "+j);
							JavascriptExecutor jse1= (JavascriptExecutor)webdriver;
							jse1.executeScript("window.scrollBy(0,200)", "");
							Thread.sleep(5);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
					}
				}
			
			}
			if(flag==0 && option==1)
				System.out.println("products are acended_by_master_price");
			else if(flag==0 && option==2)
				System.out.println("products are decended_by_master_price");
			else if(flag==1)
				System.out.println("product are not in order");
		}/* catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	
	public void clearCart() {
		System.out.println("clearing cart");
		//Thread.sleep(500);
		
		try {
			wait = new WebDriverWait(webdriver,20);
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(UIobjects.minicart_css)));
			WebElement minicart = webdriver.findElement(By.cssSelector(UIobjects.minicart_css));
			
			new Actions(webdriver).moveToElement(minicart).build().perform();
			
			try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(UIobjects.minicart_remove_css)));
				webdriver.findElement(By.cssSelector("#minicart-bottom > p.pull-left > a")).click();
				System.out.println("cart cleared \n");
			} catch (Exception e) {
				System.out.println("cart is already empty follwing is the stack trace\n");
				//e.printStackTrace();
			}
		} catch (Exception e) {
			System.out.println("clear cart catch block");
			
			e.printStackTrace();
		}
				
	}
	
	

}
