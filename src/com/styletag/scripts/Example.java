package com.styletag.scripts;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
//import java.util.regex.matrix;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.styletag.functional_lib.ExcelRead;
import com.styletag.ui_object_info.UIobjects;

public class Example {
	WebDriverWait wait;
	public WebDriver webdriver;
	Actions act;
	String product1 ,product2,orderNo;
	int sort_flag;
	
	public static void main(String[] args){
		
		Example ex = new Example();
		
		ex.launchStyletag("http://styletag.com");
		//ex.login();
		//ex.clearCart();
		//ex.addToCart();
		//ex.search();
		ex.productCatalogPage();
	
		
		
	}
	public void waitForSpinner(){
		WebElement spinner=webdriver.findElement(By.cssSelector(".loading-spiner-holder"));
		while(spinner.isDisplayed()){
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				System.out.println("Inside cath of wait for spinner");
				e.printStackTrace();
			}
		}
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
			
			ExcelRead xl= new ExcelRead("//home//styletag//java_test//Test Framework//src//com//styletag//test_cases//InputData.xlsx");
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
	public void addToCart() {
		//Thread.sleep(2000);
		System.out.println("clicking on c1");
		WebElement ethnicwear=	webdriver.findElement(By.id(UIobjects.ethnicwear_id));
		ethnicwear.click();
		//Thread.sleep(1000);
		act=new Actions(webdriver);
		act.moveToElement(ethnicwear).build().perform();
		
		System.out.println("clicking on c2 or c3");
		wait =new  WebDriverWait(webdriver,60);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(UIobjects.kurta_kurti_css)));
		webdriver.findElement(By.cssSelector(UIobjects.kurta_kurti_css)).click();
		
		//System.out.println("scrolling down");
		//JavascriptExecutor js = (JavascriptExecutor)browser;
		//js.executeScript("window.scrollBy(0,100)","");
		
		String parentBrowser = webdriver.getWindowHandle();// capturing parent tab browser. 
		//System.out.println(parentBrowser);
		
		System.out.println("clicking on product");
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
				break;
			}
		}
		
		
		product1 = webdriver.findElement(By.cssSelector("#sale-main-desc > div.cart-form.pull-right > h1")).getText().toLowerCase();//product Name
		System.out.println(product1);
		
		WebElement options= webdriver.findElement(By.cssSelector("#product-size > p > strong"));
		if (options.isDisplayed())
		{	
			System.out.println("selecting size");
			for(int i=0;i<=7;i++)
			{
				try{
					Thread.sleep(1000);
					webdriver.findElement(By.cssSelector(".in-stock:nth-child("+i +") div")).click();
					System.out.println("selected size " +i);
					break;
			
					}
				catch (Exception e){
					System.out.println("size " +i +" is not available");
					}
			}
		}
		else
		{
			System.out.print("no size found");
						
		}
		webdriver.findElement(By.cssSelector("#add-to-cart-button")).click();
		
	}
	
	public void cartCheck() throws InterruptedException{
		Thread.sleep(500);
		System.out.println("Checking cart");
		webdriver.findElement(By.cssSelector("#mini-cart-wrap > li > div.minicart-inner-div > a > span")).click();
		Thread.sleep(3000);
		product2= webdriver.findElement(By.cssSelector("#cart_product_1 > div:nth-child(2) > p:nth-child(1) > a")).getText().toLowerCase();
		if(product1.equals(product2))
			System.out.println("product is added to cart");
		else
			System.out.println("product is not added to cart");
	}
	
	public void checkout() throws InterruptedException{
		System.out.println("proceed to check out");
		webdriver.findElement(By.cssSelector("#mini-cart-wrap > li > div.minicart-inner-div > a > span")).click();
		//Thread.sleep(2000);
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#cart-checkout-btn")));
		webdriver.findElement(By.cssSelector("#cart-checkout-btn")).click();
		//Thread.sleep(4000);
		
		System.out.println("proceed as logged in user");
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#open-two")));
		webdriver.findElement(By.cssSelector("#open-two")).click();
		//Thread.sleep(2000);
		
		System.out.println("selecting address");
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#address-body a.overflow-address :nth-child(1)")));
		webdriver.findElement(By.cssSelector("#address-body a.overflow-address :nth-child(1)")).click();
		//Thread.sleep(1000);
	
		webdriver.findElement(By.cssSelector("#open-three")).click();
		//Thread.sleep(2000);
		
	
		System.out.println("clicking pay button");
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".proceed-pay-btn")));
		webdriver.findElement(By.cssSelector(".proceed-pay-btn")).click();
		//Thread.sleep(10000);
		
		System.out.println("COD payment");
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#cod-payment")));
		webdriver.findElement(By.cssSelector("#cod-payment")).click();
		Thread.sleep(1000);
		
		System.out.println("COD payment");
		int cod_flag=0;
		if (webdriver.findElement(By.cssSelector("#codButton")).isDisplayed())
			{
				System.out.println("clicking on place order button");
				webdriver.findElement(By.cssSelector("#codButton")).click();
				cod_flag=1;
			}
		else
			System.out.println("COD not available");
		
		Thread.sleep(250000);
		
		if(cod_flag==1)
		{
			orderNo= webdriver.findElement(By.cssSelector("#order-cancel > div > section > p:nth-child(2) > span")).getText();
			System.out.println(orderNo);
		}
		
		
		
	}
	
	public void search()
	{	String search_keyword,sort_value;
	    int count=1,sort_value_int;
		ExcelRead xl=new ExcelRead("//home//styletag//java_test//Test Framework//src//com//styletag//test_cases//InputData.xlsx");
		xl.rowCountInSheet(2);
		search_keyword=xl.read(1,0);
		sort_value=xl.read(1,1);
		sort_value=sort_value.replaceAll("[^0-9]", "");
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
		sort(sort_value_int);
			
	}
	public void sort(int option){
		wait = new WebDriverWait(webdriver,5 );
		if (option==1){
		System.out.println("clicking on Low-High");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(UIobjects.low_high_css)));
		webdriver.findElement(By.cssSelector(UIobjects.low_high_css)).click();
		compare(option);
		}
		if (option == 2){
			System.out.println("clicking on High-Low");
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(UIobjects.high_low_css)));
			webdriver.findElement(By.cssSelector(UIobjects.high_low_css)).click();
			compare(option);
		}
		
	}
	public void compare(int option)
	{   
		//try
		int count1=0;
		System.out.println("inside compare\n");
		try {
			Thread.sleep(9);
			String value=webdriver.findElement(By.cssSelector(UIobjects.slider_value_css)).getText();
			String numberOnly= value.replaceAll("[^0-9]", "");
			count1 = Integer.parseInt(numberOnly);
			System.out.println("slider value is: "+count1);
		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
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
						System.out.println("First product price: "+Iprice1+"second product price: "+Sprice2);
						sort_flag=1;
						break;
					}
				}
				if (option==2 ){
					if(Iprice1<Iprice2)
					{	System.out.println("inside second if");// in high_low, first product price is lesser than second product
					    System.out.println("First product price: "+Iprice1+"second product price: "+Sprice2);
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
					}
				}
			
			}
			if(sort_flag==0 && option==1)
				System.out.println("products are acended_by_master_price");
			else if(sort_flag==0 && option==2)
				System.out.println("products are decended_by_master_price");
			else if(sort_flag==1)
				System.out.println("product are not in order");
		}/* catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	public void productCatalogPage(){
		//Thread.sleep(2000);
				System.out.println("clicking on c1");
				WebElement ethnicwear=	webdriver.findElement(By.id(UIobjects.ethnicwear_id));
				ethnicwear.click();
				//Thread.sleep(1000);
				act=new Actions(webdriver);
				act.moveToElement(ethnicwear).build().perform();
				
				System.out.println("clicking on c2 or c3");
				wait =new  WebDriverWait(webdriver,60);
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(UIobjects.kurta_kurti_css)));
				webdriver.findElement(By.cssSelector(UIobjects.kurta_kurti_css)).click();
				waitForSpinner();
				
				//System.out.println("scrolling down");
				//JavascriptExecutor js = (JavascriptExecutor)browser;
				//js.executeScript("window.scrollBy(0,100)","");
				
				String parentBrowser = webdriver.getWindowHandle();// capturing parent tab browser. 
				
				//System.out.println(parentBrowser);
               applyFilters();	
               //System.out.println("scrolling down");
               JavascriptExecutor js = (JavascriptExecutor)webdriver;
               js.executeScript("window.scrollBy(0,50)","");
               
	}
	public void applyFilters()
	{  
		// moving cursor to some filter type, to come out of drop down main menu- filter type - color
		new Actions(webdriver).moveToElement(webdriver.findElement(By.cssSelector(UIobjects.color_css))).build().perform();
			
		wait= new WebDriverWait(webdriver, 20);

		String discount=webdriver.findElement(By.cssSelector(UIobjects.discount_css)).getText();
		System.out.println("Filter type :"+discount);
		try {
			Thread.sleep(2);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		webdriver.findElement(By.cssSelector(UIobjects.discount_css)).click();
		//webdriver.findElement(By.cssSelector("#shared-filter > div > div.sidebar > form > div:nth-child(2) > div.filter-box-heading.text-uppercase.text-bold > h2 > a > span")).click();
		List<WebElement> discountLable = webdriver.findElements(By.id("discount"));
		
		for(WebElement we :discountLable )
		{
			System.out.println(we.getText());
		}
		
		for(int i=1;i<=1;i++)
		{  
			webdriver.findElement(By.cssSelector("#discount > span:nth-child(2) > label")).click();
			//waitForSpinner();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
			//
			sort(1);
			//high_low
			webdriver.findElement(By.cssSelector(".scrollup"));
			sort(2);
			
			
		}
		
		
		
		
	}
	

}
