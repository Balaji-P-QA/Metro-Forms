package org.addon;

import java.util.List;
import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class AddonForms {

	public WebDriver driver;
	public WebElement EligibleAddonValue;
	public List<WebElement> plusBtn;
	public Select sel;
	public JavascriptExecutor js;
	public String[] set;

	Scanner s = new Scanner(System.in);

	@BeforeClass
	public void setUp() {

		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();

		driver.get("http://13.71.30.13:8080/metrocard/");

		driver.manage().window().maximize();

	}

	public void maximize() {

		driver.manage().window().maximize();

	}

	public void minimize() {

		driver.manage().window().minimize();

	}

	public void holding(int num) throws InterruptedException {
		Thread.sleep(num);
	}

	@AfterClass
	public void tearDown() {

		// driver.quit();

	}

	public void scrollDown(WebElement ref) {

		js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true)", ref);

	}

	public void scrollUp(WebElement ref) {

		js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(false)", ref);

	}

	@Test(priority = 1)
	public void validation() throws InterruptedException {

		WebElement mobileNumber = driver.findElement(By.cssSelector("input[name='mobile']"));

		WebElement cardNumber = driver.findElement(By.cssSelector("input[name='cardNo']"));

		String HowToLikeEnteringInTheApplication = "card"; 

		if (HowToLikeEnteringInTheApplication.equalsIgnoreCase("mobile")) {

			mobileNumber.sendKeys("7010871009");  // 9361310727
		} else {
			cardNumber.sendKeys("1200260115");  //    4500308916      //1600322912ref
		}

		holding(1500);

		WebElement justClick = driver.findElement(By.xpath("//span[text()='OR']"));
		justClick.click();

		WebElement cardHolderNo = driver.findElement(By.name("cardHolderNo"));
		cardHolderNo.sendKeys("1");

		holding(1500);

		WebElement goNextBtn = driver.findElement(By.cssSelector("input[value='Go Next']"));
		goNextBtn.click();

		try {

			WebElement justTitle = driver.findElement(By.xpath("//h1[text()='Please Select Your Option']"));

			if (justTitle.isDisplayed()) {
				System.out.println("Thanks For Entering in to Application ");
			}

		} catch (Exception e) {

			System.out.println(driver.findElement(By.xpath("//div[@role='alert']")).getText());

		}

		holding(5000);

		/*
		 * String UpdateEmail = "yes";
		 * 
		 * if (UpdateEmail.equalsIgnoreCase("yes")) {
		 * 
		 * WebElement emailCheckbox =
		 * driver.findElement(By.xpath("//input[@name='email_update_chk_id']"));
		 * emailCheckbox.click();
		 * 
		 * holding(1000);
		 * 
		 * WebElement EmailTxtbox =
		 * driver.findElement(By.xpath("//input[@name='applicantEmailId']"));
		 * EmailTxtbox.sendKeys("test001@mailinator.com");
		 * 
		 * WebElement otpCheckbox =
		 * driver.findElement(By.xpath("//input[@name='email_otp_chk_id']"));
		 * otpCheckbox.click();
		 * 
		 * WebElement otpTxtbox =
		 * driver.findElement(By.xpath("//input[@id='emailOTP']"));
		 * 
		 * minimize();
		 * 
		 * holding(1000);
		 * 
		 * System.out.println("Please enter the OTP");
		 * 
		 * holding(10000);
		 * 
		 * int otpValue = s.nextInt();
		 * 
		 * maximize();
		 * 
		 * holding(2000);
		 * 
		 * String otpNum = String.valueOf(otpValue); otpTxtbox.sendKeys(otpNum);
		 * 
		 * holding(1000);
		 * 
		 * WebElement otpSubmitBtn =
		 * driver.findElement(By.xpath("//button[@id='emailOTPButton']"));
		 * otpSubmitBtn.click();
		 * 
		 * holding(3000);
		 * 
		 * try {
		 * 
		 * WebElement otpErrMsg =
		 * driver.findElement(By.xpath("//p[text()='Please enter valid OTP.']"));
		 * 
		 * if (otpErrMsg.isDisplayed()) {
		 * 
		 * System.out.println(otpErrMsg.getText());
		 * 
		 * }
		 * 
		 * } catch (Exception e) {
		 * 
		 * WebElement otpSucMsg = driver.findElement(By.className("successful-tick"));
		 * System.out.println("Thanks For Entered Proper OTP");
		 * 
		 * }
		 * 
		 * }
		 */

	}

	@Test(priority = 2,enabled=true)
	private void addOnCustomer() throws InterruptedException {

		try {

			WebElement addOnBtn = driver.findElement(By.xpath("//input[@class='add-on-btn']"));
			addOnBtn.click();

			EligibleAddonValue = driver.findElement(By.xpath("//p[contains(text(),'Eligible Add-on cards ')]"));
			String EligibleAddonText = EligibleAddonValue.getText();
			String[] split = EligibleAddonText.split(" ");
			String EliglbleAddonString = split[4];

			Integer EliglbleAddon = Integer.valueOf(EliglbleAddonString);
			System.out.println(EliglbleAddon);

			plusBtn = driver.findElements(By.xpath("//div[contains(@class,'add-more ml-auto addBtn')]"));

			for (int i = 0; i < EliglbleAddon - 1; i++) {

				plusBtn.get(i).click();

				holding(1000);

				WebElement scroll1 = driver
						.findElement(By.xpath("//span[text()='Accept the T & C and Submit the Form']"));
				scrollDown(scroll1);

				holding(1500);

			}

			scrollUp(EligibleAddonValue);

			holding(2000);

			// validating

			List<WebElement> FormsCount = driver.findElements(By.xpath("//h3[@class='box-tittle']"));

			int ActualEligibleAddOnForm = FormsCount.size(); //-value manually give
			System.out.println(ActualEligibleAddOnForm);

			// here Validating the given Eligble Add on Forms Count and Actual Add on Forms
			// Same or not

			if (ActualEligibleAddOnForm == EliglbleAddon - 1) {

				Assert.assertTrue(true);

			} else {

				Assert.assertTrue(false, "Actual geting AddOn Forms and Given Eligble AddOn Forms Count is different");

			}

			holding(1000);

			/**/ String DeleteApplicant = "yes";

			if (DeleteApplicant.equalsIgnoreCase("yes")) {

				List<WebElement> deleteBtn = driver
						.findElements(By.xpath("//div[@class='add-more ml-auto deleteBtn']"));

				for (int i = 0; i < deleteBtn.size() - 2; i++) { /*-value manually give*/

					deleteBtn.get(i).click();

					holding(1000);
				}

			}
			

			/* Please enter your limit of eligible */
			int HowMuchEligbleLikeToYouGive = 2;

			for (int i = 0; i < HowMuchEligbleLikeToYouGive - 1; i++) {

				js.executeScript("arguments[0].click()", plusBtn.get(i));
				// plusBtn.get(i).click();

				holding(1000);

				WebElement scroll1 = driver
						.findElement(By.xpath("//span[text()='Accept the T & C and Submit the Form']"));
				scrollDown(scroll1);

				holding(2000);
			}

			scrollUp(EligibleAddonValue);

			holding(2000);

			List<WebElement> applicantName = driver.findElements(By.xpath("//input[@placeholder='Applicant Name']"));

			List<WebElement> applicantMobileNumber = driver
					.findElements(By.xpath("//input[contains(@id,'addonApplicantMobile')]"));

			List<WebElement> applicantEmail = driver.findElements(By.xpath("//input[@type='email']"));

			List<WebElement> applicantGender = driver
					.findElements(By.xpath("//select[contains(@id,'addonApplicantGender')]"));

			List<WebElement> applicantPosition = driver
					.findElements(By.xpath("//select[contains(@id,'addonPositionId')]"));

			List<WebElement> selectFileBtn = driver.findElements(By.xpath("//button[text()='Select a File']"));

			/*
			 * String applicantName1 =" "; String applicantMobileNumber1=" "; String
			 * applicantEmail1=" ";
			 * 
			 * String applicantName2 =" "; String applicantMobileNumber2 =" "; String
			 * applicantEmail2 =" ";
			 */

			for (int i = 0; i < HowMuchEligbleLikeToYouGive; i++) {

				if (i == 0) {

					set = new String[3];
					set[0] = "Test One";
					set[1] = "9876543211";
					set[2] = "test1@mailinator.com";

				} else if (i == 1) {

					set = new String[3];
					set[0] = "Test two";
					set[1] = "9876543212";
					set[2] = "test2@mailinator.com";

				} else if (i == 2) {

					set = new String[3];
					set[0] = "Test three";
					set[1] = "9876543213";
					set[2] = "test2@mailinator.com";

				} else if (i == 3) {

					set = new String[3];
					set[0] = "Test four";
					set[1] = "9876543214";
					set[2] = "test4@mailinator.com";

				}

				applicantName.get(i).sendKeys(set[0]);
				holding(1500);
				applicantMobileNumber.get(i).sendKeys(set[1]);
				holding(1500);
				applicantEmail.get(i).sendKeys(set[2]);
				holding(1500);
				Select sel1 = new Select(applicantGender.get(i));
				sel1.selectByIndex(1);
				holding(1500);
				Select sel2 = new Select(applicantPosition.get(i));
				sel2.selectByIndex(3);
				holding(1500);
				selectFileBtn.get(i).click();
				holding(5000);

			}

			holding(1500);

			WebElement scroll1 = driver.findElement(By.cssSelector("div[class='action-body']"));
			scrollDown(scroll1);

			holding(2000);

			WebElement TermsAndConditioncheckbox = driver.findElement(By.cssSelector("input[name='save_chk_id']"));
			TermsAndConditioncheckbox.click();

			holding(1500);

			// WebElement FinalSubmitBtn =
			// driver.findElement(By.xpath("//button[text()='Save']"));
			// FinalSubmitBtn.click();

			holding(3000);

			WebElement nameErrMSg = driver.findElement(By.xpath("//p[text()='Applicant Name is mandatory']"));
			WebElement mobileErrMsg = driver.findElement(By.xpath("//p[text()='Applicant Mobile is mandatory']"));
			WebElement emailErrMSg = driver.findElement(By.xpath("//p[text()='Applicant Email is mandatory']"));

			try {

				WebElement SuccessPage = driver.findElement(By.xpath("//div[@class='thank-you']"));
				if (SuccessPage.isDisplayed()) {

					System.out.println("ThankYou for added the Eligbles of Your Metro Cards");

				}

			} catch (Exception e) {

				try {

					if ((nameErrMSg.isDisplayed()) || (mobileErrMsg.isDisplayed()) || (emailErrMSg.isDisplayed())) {

						System.out.println("Please fill All Mandatory Field");

					}

				} catch (Exception e2) {

					Assert.assertTrue(false, "Something Went Wrong");

				}

			}

		} catch (NoSuchElementException e3) {

			System.out.println("You have Already Added Your Card Nominiees");

		}

	}
	
	@Test(priority=2,enabled=false)
	public void referAFriend() throws InterruptedException {
		
		WebElement referAFriendBtn = driver.findElement(By.xpath("//input[@class='referral-btn']"));
		referAFriendBtn.click();
		
		List<WebElement> referPlusBtn = driver.findElements(By.xpath("//div[contains(@class,'add-more ml-auto addBtn')]"));
		
		//give below 4
		int HowMuchFriendYouRefer = 4;
		
		for (int i = 1; i <= HowMuchFriendYouRefer-1; i++) {
			
			js = (JavascriptExecutor)driver;
			js.executeScript("arguments[0].click()", referPlusBtn.get(i));
			
			holding(1000);
			
		}
		
		WebElement cardHolderName = driver.findElement(By.xpath("//input[@placeholder='Card Holder Name']"));
		cardHolderName.sendKeys("Exam");
		WebElement cardHolderMobNum = driver.findElement(By.xpath("//input[@placeholder='Card Holder Mobile Number']"));
		cardHolderMobNum.sendKeys("9876543210");
		WebElement cardHolderEmail = driver.findElement(By.xpath("//input[@placeholder='Card Holder E-mail ID']"));
		cardHolderEmail.sendKeys("exam@mailinator.com");
		
		
		
		for (int i = 0; i < HowMuchFriendYouRefer ; i++) {
			
			if (i == 0) {

				set = new String[3];
				set[0] = "Test One";
				set[1] = "9876543211";
				set[2] = "test1@mailinator.com";

			} else if (i == 1) {

				set = new String[3];
				set[0] = "Test two";
				set[1] = "9876543212";
				set[2] = "test2@mailinator.com";

			} else if (i == 2) {

				set = new String[3];
				set[0] = "Test three";
				set[1] = "9876543213";
				set[2] = "test3@mailinator.com";

			} else if (i == 3) {

				set = new String[3];
				set[0] = "Test four";
				set[1] = "9876543214";
				set[2] = "test4@mailinator.com";

			}else if (i == 4) {

				set = new String[3];
				set[0] = "Test five";
				set[1] = "9876543215";
				set[2] = "test5@mailinator.com";

			}
			
			
			List<WebElement> referedCustomerName = driver.findElements(By.xpath("//input[contains(@id,'referredCustomerName')]"));
			referedCustomerName.get(i).sendKeys(set[0]);
			
			holding(1500);
			
			List<WebElement> referedCustomerMobileNum = driver.findElements(By.xpath("//input[@placeholder='Mobile Number']"));
			referedCustomerMobileNum.get(i).sendKeys(set[1]);
			
			holding(1500);
			
			List<WebElement> referedCustomerEmaiID = driver.findElements(By.xpath("//input[contains(@name,'referredCustomerEmail')]"));
			referedCustomerEmaiID.get(i).sendKeys(set[2]);
				
			holding(2000);
		}
		
		WebElement saveBtn = driver.findElement(By.xpath("//button[text()='Save']"));
		saveBtn.click();
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
