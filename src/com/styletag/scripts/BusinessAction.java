package com.styletag.scripts;


import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.styletag.functional_lib.*;
import com.styletag.ui_object_info.UIobjects;

public class BusinessAction {
	WebDriverWait wait;
	public WebDriver webdriver;
	Actions act;
	String pd_product_name ,ct_product_name,orderNo;
	int sort_flag;
	ExcelRead xl;
	String msg;
	ExcelWrite write;
	DateFormat df;
	Date dateobj;
	String date;
	public BusinessAction(ExcelWrite write1){
		xl=new ExcelRead("//home//styletag//java_test//Test Framework//src//com//styletag//test_cases//InputData.xlsx");
		write= write1;
		df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
	    dateobj = new Date();
	    date =df.format(dateobj);
	    String sd[] = date.split("\\s");
	    System.out.println(sd[0]);
	    System.out.println(sd[1]);
	    sd[0]=sd[0].replaceAll("\\/","_");
	    sd[1]=sd[1].replaceAll(":","_");
	    date="_date_"+sd[0]+"_time_"+sd[1];
		
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
		
		//sort_value=sort_value.replaceAll("[^0-9]", "");
		//sort_value_int=Integer.parseInt(sort_value);
		//System.out.println("sort value: "+sort_value_int);
		
		msg="Clicking on search tab";
		System.out.println(msg);
		write.writeReports("Log", msg);
		
		webdriver.findElement(By.cssSelector(UIobjects.search_field_css)).click();
		System.out.println("Clearing the field");
		webdriver.findElement(By.cssSelector(UIobjects.search_field_css)).clear();
		
		msg="Entering "+search_keyword+" in search tab";
		write.writeReports("Log",msg);
		
		webdriver.findElement(By.cssSelector(UIobjects.search_field_css)).sendKeys(search_keyword);
		webdriver.findElement(By.cssSelector(UIobjects.search_button_css)).click();
		String title = webdriver.findElement(By.cssSelector(UIobjects.page_title_css)).getText();
		msg=" Search page title: "+title;
		System.out.println(msg);
		write.writeReports("Log",msg);
		
		wait=new WebDriverWait(webdriver,20);
		WebElement product;
		try {
			product = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(UIobjects.product_css)));
			msg="Products are found in the search page";
			System.out.println(msg);
			write.writeReports("Log", msg);
			Driver.FLAG=1;
		} catch (Exception e1) {
			e1.printStackTrace();
			msg="Products are not found in the search page";
			System.out.println(msg);
			write.writeReports("Log", msg);
			Driver.FLAG=0;
		}
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
		
		//sort(sort_value_int);
		
			
	}
	public void sort(int option){
		wait = new WebDriverWait(webdriver,5 );
		if (option==1){
		msg="clicking on Low-High";
		System.out.println("\nclicking on Low-High");
		write.writeReports("Log", msg);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(UIobjects.low_high_css)));
		webdriver.findElement(By.cssSelector(UIobjects.low_high_css)).click();
		
		Driver.FLAG++;
		compare(option);
		}
		if (option == 2){
			msg="\nclicking on High-Low";
			System.out.println("\nclicking on High-Low");
			write.writeReports("Log", msg);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(UIobjects.high_low_css)));
			webdriver.findElement(By.cssSelector(UIobjects.high_low_css)).click();
			
			Driver.FLAG++;
			compare(option);
		}
		
		
		
	}
	public void compare(int option)
	{   
		//try
		int count1=0;
		System.out.println("inside compare");
		
		try {
			Thread.sleep(9);
			String value=webdriver.findElement(By.cssSelector(UIobjects.slider_value_css)).getText();
			String numberOnly= value.replaceAll("[^0-9]", "");
			count1 = Integer.parseInt(numberOnly);
			System.out.println("slider value is: "+count1);
			write.writeReports("Log","slider value is: "+count1 );
		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			Driver.FLAG=0;
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			Driver.FLAG=0;
		}
		
		{
			String Sprice1,Sprice2;
			int Iprice1,Iprice2,j=1;
			sort_flag=0;
			wait = new WebDriverWait(webdriver,2);
          for (int i=1;i<count1;i++){
				
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
				
				//System.out.println("price1 and price2 before replace all"+Sprice1+Sprice2);
				Sprice1=Sprice1.replaceAll("[^0-9]" ,"");
				Sprice2=Sprice2.replaceAll("[^0-9]", "");
				Iprice1=Integer.parseInt(Sprice1);
			
				Iprice2=Integer.parseInt(Sprice2);
				
				//System.out.println(Iprice2);
				if(option==1){
					if(Iprice1>Iprice2)
					{	System.out.println("inside first if"); // in low_high, first product price is greater than second product
						msg="product"+(i-1)+" price: "+Iprice1+" product"+i+" price: "+Sprice2;
						System.out.println(msg);
						write.writeReports("Error", msg);
						
						sort_flag=1;
						break;
					}
				}
				if (option==2 ){
					if(Iprice1<Iprice2)
					{	System.out.println("inside second if");// in high_low, first product price is lesser than second product
					    msg="product"+(i-1)+" price: "+Iprice1+" product"+i+" price: "+Sprice2;
						System.out.println(msg);
						write.writeReports("Error", msg);
					    sort_flag=1;
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
							Driver.FLAG=0;
					}
				}
			
			}
			if(sort_flag==0 && option==1)
			{	
				msg="products are acended_by_master_price";
				System.out.println(msg);
				write.writeReports("Log", msg);
				Driver.FLAG++;
				
			}
			else if(sort_flag==0 && option==2)
			{
				msg="products are decended_by_master_price";
				System.out.println(msg);
				write.writeReports("Log", msg);
				Driver.FLAG++;
				
			}
			else if(sort_flag==1)
			{	
				msg="products are not in order";
				System.out.println(msg);
				write.writeReports("Error", msg);
				Driver.FLAG=0;
				File scrFile = ((TakesScreenshot)webdriver).getScreenshotAs(OutputType.FILE);
				try {
					FileUtils.copyFile(scrFile, new File("//home//styletag//java_exp_pgm//product_price_mismatch.png"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					//Driver.FLAG=0;
				}
				
			
			}
		}/* catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		Driver.FLAG++;
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
	
	public void productDetailPage()
	{
		int size_flag=0,size_presence_falg=0;
		String parentBrowser = webdriver.getWindowHandle();// capturing parent tab browser.
		
		msg="clicking on product";
		System.out.println(msg);
		write.writeReports("Log",msg);
		
		wait= new WebDriverWait(webdriver,10);
		try {
			wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(UIobjects.product_css)));
			webdriver.findElement(By.cssSelector(UIobjects.product_css)).click();
			
			//get list of all tab browser
			Set<String> allBrowser = webdriver.getWindowHandles();
			for (String eachBrower:allBrowser){
				//System.out.println(eachBrower);
					if(!(eachBrower.equals(parentBrowser)))
					{
					//switching to child browser
						webdriver.switchTo().window(eachBrower);
						msg="moving to product detail page";
						System.out.println(msg);
						write.writeReports("Log",msg);
						break;
					}
				}
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(UIobjects.product_title_css)));
			pd_product_name = webdriver.findElement(By.cssSelector(UIobjects.product_title_css)).getText().toLowerCase();//product Name
			msg="Product Name: "+pd_product_name;
			System.out.println(msg);
			write.writeReports("log",msg);
			
			msg="checking for size chart";
			System.out.println(msg);
			write.writeReports("Log",msg);
			// webdriver.findElement(By.cssSelector("#cartform > div > p.view-sizechart > a:nth-child(2)")).click();// clicking on size chart
			 //webdriver.findElement(By.cssSelector("#ngdialog4 > div.ngdialog-content > div")).click();// closing size chart
			
			try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(UIobjects.option_css)));
				WebElement options= webdriver.findElement(By.cssSelector(UIobjects.option_css));
				
				msg="selecting size";
				System.out.println(msg);
				write.writeReports("log",msg);
				
				size_presence_falg=1;
				for(int i=0;i<=7;i++)
				{    
					try{
						//Thread.sleep(1000);
						WebElement size =webdriver.findElement(By.cssSelector(".in-stock:nth-child("+i +") div"));
						if (size.isEnabled())
						{
							size.click();
							msg="selected size: "+size.getText();
							System.out.println(msg);
							write.writeReports("Log",msg);
							size_flag=1;
						}
						break;
				
						}
					catch (Exception e){
						
						msg="size " +i+" is not available";
						System.out.println(msg);
						write.writeReports("Log",msg);
						
						}
				}				
				
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				msg="Product with no Variants type";
				System.out.println(msg);
				write.writeReports("Log",msg);
			}
			
			msg="ckecking on ADD_TO_CART button enability";
			System.out.println(msg);
			write.writeReports("Log",msg);
			WebElement add_to_cart_button=webdriver.findElement(By.cssSelector("#add-to-cart-button"));
			if(add_to_cart_button.isEnabled())
			{
				msg="ADD_TO_CART button is enabled";
				System.out.println(msg);
				write.writeReports("Log",msg);
				
				add_to_cart_button.click();
				Thread.sleep(2000);
				WebElement flash_msg= webdriver.findElement(By.cssSelector(UIobjects.flash_msg_css));
				if (flash_msg.isDisplayed())
				{	msg="Product added to cart ,'Added to Cart' msg displayed";
					System.out.println(msg);
					write.writeReports("Log", msg);
				}
			}
			else
			{
				msg="ADD_TO_CART button is disabled";
				System.out.println(msg);
				write.writeReports("Log",msg);
				 
				if((size_presence_falg==1)&&(size_flag==0)) // size chart present and not selected
				{
					msg="size has not selected";
					System.out.println(msg);
					write.writeReports("Log",msg);
					
				}
				else
				{
					msg="Product is already added to cart";
					System.out.println(msg);
					write.writeReports("Log",msg);
				}
				
			}
			Driver.FLAG++;

		} catch (Exception e) {
			e.printStackTrace();
			Driver.FLAG=0;
		}finally{ 
			//screenshot is taken in case of pass or fail of the testcase
			File scrFile = ((TakesScreenshot)webdriver).getScreenshotAs(OutputType.FILE);
			try {
				
				String path="//home//styletag//Sanity_report//screen_shots//ProductDetailPage"+date+".png";
				FileUtils.copyFile(scrFile, new File(path));
				msg="Screenchot taken";
				System.out.println(msg);
				write.writeReports("Log", msg);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
	}
	
	public void productCatalogPage(){
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
		try {
			msg = "clicking on c1";
			System.out.println(msg);
			write.writeReports("Log", msg);
					
			WebElement ethnicwear=	webdriver.findElement(By.id(UIobjects.ethnicwear_id));
			ethnicwear.click();
			//Thread.sleep(1000);
			act=new Actions(webdriver);
			act.moveToElement(ethnicwear).build().perform();
			
			msg="clicking on c2 or c3";
			System.out.println(msg);
			write.writeReports("Log", msg);
			
			wait =new  WebDriverWait(webdriver,60);
			//kurta_kurtis
			msg="clicking on Kuta_kutis";
			System.out.println(msg);
			write.writeReports("Log", msg);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(UIobjects.kurta_kurti_css)));
			webdriver.findElement(By.cssSelector(UIobjects.kurta_kurti_css)).click();
			//waitForSpinner();
			
			//anarkalis
			/*msg="clicking on Anarkalis";
			System.out.println(msg);
			write.writeReports("Log", msg);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(UIobjects.anarkalis)));
			webdriver.findElement(By.cssSelector(UIobjects.anarkalis)).click();
			*/
			//this is to overcome the menu bars drop down
			System.out.println("scrolling down");
			JavascriptExecutor js = (JavascriptExecutor)webdriver;
			js.executeScript("window.scrollBy(0,100)","");
			
			Driver.FLAG++;
		} catch (Exception e) {
			Driver.FLAG=0;
			e.printStackTrace();
		}
		
	}
	
	public void applyFilters()
	{  
		int length=1;
		String s1[]=null;
		// moving cursor to some filter type, to come out of drop down main menu- filter type - color
		new Actions(webdriver).moveToElement(webdriver.findElement(By.cssSelector(UIobjects.color_css))).build().perform();
			
		wait= new WebDriverWait(webdriver, 20);
		for(int j=2;j<=7;j++)// j=2 :starting from discount filters . 7 different filter types.
		{
			if(j==2||j==7)// j=2 - Discount and j=7 - Delivery type filters which are in collapsed mode
			{
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#shared-filter > div > div.sidebar > form > div:nth-child("+j+") > div.filter-box-heading.text-uppercase.text-bold > h2 > a")));
				webdriver.findElement(By.cssSelector("#shared-filter > div > div.sidebar > form > div:nth-child("+j+") > div.filter-box-heading.text-uppercase.text-bold > h2 > a")).click();
			}
			WebElement filtertype = webdriver.findElement(By.cssSelector("#shared-filter > div > div.sidebar > form > div:nth-child("+j+") > div.filter-box-heading.text-uppercase.text-bold > h2 > a"));
			System.out.println("\nFILTER TYPE: "+filtertype.getText().toLowerCase());
			String lowercase=filtertype.getText().toLowerCase().replaceAll("[^a-z]","_");//converting the filter attribute text to lower
			
			List<WebElement> filterattribute = webdriver.findElements(By.id(lowercase));	
			for(WebElement we :filterattribute )
			{
				String s=we.getText();
				System.out.println("FILTER ATTRIBUTES are: \n"+s);
				write.writeReports("Log", s);
				s1 = s.split("\\n");
				msg = "No of attributes in the selected Filter type is "+length;
				write.writeReports("Log", msg);
				length=s1.length; System.out.println(msg);
				
			}
			for(int i=1;i<=length;i++ ) // iterating filter attribute 
			{  
				msg="\nclicking on: "+s1[i-1];
				System.out.println(msg);
				write.writeReports("Log", msg);
				System.out.println("Filter attribute count value "+i);
				
				
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#"+lowercase+" > span:nth-child("+i+") > label")));
				webdriver.findElement(By.cssSelector("#"+lowercase+" > span:nth-child("+i+") > label")).click();
				//waitForSpinner();
				try {
					//System.out.println("thread.sleep AFTER filter click");
					Thread.sleep(3000);
					sort(1);//low_high
					try {
						webdriver.findElement(By.cssSelector(".scrollup")).click();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					sort(2);//high_low
					try {
						webdriver.findElement(By.cssSelector(".scrollup")).click();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
				
					Thread.sleep(3000);
					wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#"+lowercase+" > span:nth-child("+i+") > label")));
					webdriver.findElement(By.cssSelector("#"+lowercase+" > span:nth-child("+i+") > label")).click();
					Thread.sleep(3000);
					} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				 //System.out.println("i value after increment"+i);
				
			}
			//to minimize the previously selected filter
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#shared-filter > div > div.sidebar > form > div:nth-child("+j+") > div.filter-box-heading.text-uppercase.text-bold > h2 > a")));
			webdriver.findElement(By.cssSelector("#shared-filter > div > div.sidebar > form > div:nth-child("+j+") > div.filter-box-heading.text-uppercase.text-bold > h2 > a")).click();
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
