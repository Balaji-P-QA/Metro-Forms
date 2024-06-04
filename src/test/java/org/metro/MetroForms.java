package org.metro;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class MetroForms {

	public static WebDriver driver;
	// public static WebElement mobileNumber;
	JavascriptExecutor js;

	@BeforeClass
	private void start() {

		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();

		driver.get("http://13.71.30.13:8080/metrocard/licenserenewal");

		driver.manage().window().maximize();

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	}

	public void scrollDown(WebElement elementRef) {

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true)", elementRef);

	}

	public void scrollUp(WebElement elementRef) {

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(false)", elementRef);

	}

	public void minimize() {

		driver.manage().window().minimize();

	}

	public void maximize() {

		driver.manage().window().maximize();

	}

	@Test
	public void actions() throws InterruptedException, ParseException {

		driver.manage().window().minimize();

		Thread.sleep(2000);

		// 1 Scenario

		Scanner s = new Scanner(System.in);
		System.out.println("How would you like to enter in this Application Mobile number or Customer card number");
		String userliketoEnterIntheApplication = s.nextLine();

		Thread.sleep(10000);

		driver.manage().window().maximize();

		Thread.sleep(3000);

		if ((userliketoEnterIntheApplication.equalsIgnoreCase("Mobile"))
				|| (userliketoEnterIntheApplication.equalsIgnoreCase("Mobile Number"))) {

			WebElement mobileNumber = driver.findElement(By.xpath("//input[@name='applicantMobile']"));
			mobileNumber.sendKeys("9361310727");

			WebElement searchBtn = driver.findElement(By.xpath("(//span[@class='searchIcon'])[1]"));
			searchBtn.click();

			System.out.println("Thanks for entering in to the application by using Mobile Number");

		} else {

			WebElement cardNumber = driver.findElement(By.xpath("//input[@name='cardNumber']"));
			cardNumber.sendKeys("3500023687");

			WebElement searchBtn = driver.findElement(By.xpath("(//span[@class='searchIcon'])[2]"));
			searchBtn.click();

			System.out.println("Thanks for entering in to the application by using Customer card Number");
		}

		Thread.sleep(3000);

		WebElement companyName = driver.findElement(By.xpath("//input[@name='companyName']"));
		System.out.println("Your Company Name is " + companyName.getAttribute("value"));

		Thread.sleep(1000);

		WebElement licenseExpiryDate = driver.findElement(By.xpath("//input[@name='licenseExpiry']"));
		String ExpiryDate = licenseExpiryDate.getAttribute("value");

		Thread.sleep(1000);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date ExpDate = sdf.parse(ExpiryDate);
		System.out.println(ExpDate);
		Date d = new Date();
		System.out.println(d);

		WebElement expiredTooltip = driver.findElement(By.xpath("//label[text()=' License is expired ']"));

		if (expiredTooltip.isDisplayed()) {

			if (ExpDate.before(d)) {

				System.out.println("Your license is already expired");
			} else {
				Assert.assertTrue(false);
			}

		} else {

			if (ExpDate.after(d)) {
				System.out.println("Your license will expire on " + ExpiryDate);
			} else {
				Assert.assertTrue(false);
			}

		}

		// 2 Scenario

		WebElement whatsappNumber = driver.findElement(By.xpath("//input[@name='applicantWhatsApp']"));
		driver.manage().window().minimize();

		Thread.sleep(1000);

		System.out.println(
				"Is same mobile number used as WhatsApp number ? If same number please enter 'yes' not using please enter 'No'");
		Thread.sleep(2000);
		String sameMobNumasWhatsappNum = s.nextLine();

		Thread.sleep(5000);

		driver.manage().window().maximize();

		Thread.sleep(1500);

		if (sameMobNumasWhatsappNum.equalsIgnoreCase("yes")) {

			WebElement whatsNumCheckbox = driver.findElement(By.xpath("//input[@name='chk_id']"));
			whatsNumCheckbox.click();

			Thread.sleep(2000);

			String whatsappNumbernotEmpty = whatsappNumber.getAttribute("value");

			Thread.sleep(2000);

			if (!whatsappNumbernotEmpty.isEmpty()) {

				Thread.sleep(2000);

				String mobileNumgetting = driver.findElement(By.xpath("//input[@name='applicantMobile']"))
						.getAttribute("value");

				String whatsappNumgetting = whatsappNumber.getAttribute("value");

				if (mobileNumgetting.equals(whatsappNumgetting)) {
					Assert.assertTrue(true);
				} else {
					Assert.assertTrue(false);
				}

			} else {
				Assert.assertTrue(false);
			}

		} else {

			// WebElement notUsingWhatsAppCheckkbox =
			// driver.findElement(By.xpath("//input[@name='notUsingWhatsApp']"));
			// notUsingWhatsAppCheckkbox.click();

			Thread.sleep(2000);

			String whatsappNumbernotEmpty = whatsappNumber.getAttribute("value");

			if (whatsappNumbernotEmpty.isEmpty()) {
				Assert.assertTrue(true);

				driver.manage().window().minimize();
				Thread.sleep(1500);
				System.out.println("Please update your 10 Digit WhatsApp number");
				long whatsappNum = s.nextLong();

				Thread.sleep(2000);
				driver.manage().window().maximize();

				Thread.sleep(1500);

				String whatsappNumNew = String.valueOf(whatsappNum);
				whatsappNumber.sendKeys(whatsappNumNew);

			} else {
				Assert.assertTrue(false);
			}

		}

		// 3 Scenario

		WebElement scr1 = driver.findElement(By.xpath("//label[text()=' Company Name ']"));
		scrollDown(scr1);

		Thread.sleep(3000);

		WebElement licenseType = driver.findElement(By.xpath("//select[@name='licenseTypeId']"));
		Select s1 = new Select(licenseType);

		driver.manage().window().minimize();

		Thread.sleep(2000);

		System.out.println("Please enter the index num which type license you want");
		Thread.sleep(2000);
		short licenseTypeIndNum = s.nextShort();

		Thread.sleep(1000);

		driver.manage().window().maximize();

		Thread.sleep(2000);

		s1.selectByIndex(licenseTypeIndNum);

		Thread.sleep(2000);

		// Scenario :4 = Please upload the same license copy selected from above list *

		WebElement selectFileLicensecopyBtn = driver.findElement(By.xpath("(//button[text()='Select a File'])[1]"));
		selectFileLicensecopyBtn.click();

		Thread.sleep(7000);

		// Scenario :5 = Please upload the KYC document *

		WebElement submitKYCdocumentBtn = driver.findElement(By.xpath("(//button[text()='Select a File'])[2]"));
		submitKYCdocumentBtn.click();

		Thread.sleep(10000);

		// Scenario: 6 = Update your Email Address

		WebElement email = driver.findElement(By.xpath("//input[@placeholder='Your Email']"));

		minimize();

		Thread.sleep(2000);

		System.out.println("Enter your Email");
		Thread.sleep(8000);
		String emailValue = s.next();
		Thread.sleep(5000);

		driver.manage().window().maximize();

		Thread.sleep(2000);

		email.sendKeys(emailValue);

		Thread.sleep(1500);

		WebElement otpCheckbox = driver.findElement(By.xpath("//input[@name='email_otp_chk_id']"));
		otpCheckbox.click();

		Thread.sleep(1500);

		try {

			WebElement emailErrMsg = driver.findElement(By.xpath("//p[text()='Please enter new email.']"));

			if (emailErrMsg.isDisplayed()) {

				System.out.println("Please enter Your Email");

			}

		} catch (Exception e) {

			WebElement otpTxtbox = driver.findElement(By.xpath("//input[@name='emailOTP']"));

			driver.manage().window().minimize();

			Thread.sleep(2000);

			System.out.println("Please enter OTP");
			driver.manage().timeouts().implicitlyWait(Duration.ofMinutes(1));

			int getingOtp = s.nextInt();

			String otpValue = String.valueOf(getingOtp);

			Thread.sleep(9000);

			driver.manage().window().maximize();

			Thread.sleep(3000);

			otpTxtbox.sendKeys(otpValue);

			Thread.sleep(1000);

			WebElement otpSubmitBtn = driver.findElement(By.xpath("//button[@id='emailOTPButton']"));
			otpSubmitBtn.click();

			try {

				WebElement otpInvalidMsg = driver.findElement(By.xpath("//p[text()='Please enter valid OTP.']"));

				if (otpInvalidMsg.isDisplayed()) {

					System.out.println(otpInvalidMsg.getText());

				}

			} catch (Exception e2) {

				WebElement emailOtpSuccTick = driver.findElement(By.xpath("(//span[@class='successful-tick'])[1]"));
				
				if (emailOtpSuccTick.isDisplayed()) {
					
					System.out.println("Thanks for Entering the Proper OTP");
					
				} else {

					Assert.fail();
					
				}
				
				
			}

		}

		// Scenario : 7 = Update your new mobile number *

		WebElement scrUp = driver.findElement(By.xpath("//h1[text()='Please Update Your Data']"));

		scrollUp(scrUp);

		Thread.sleep(2000);

		driver.manage().window().minimize();

		System.out.println("Whether the customer mobile number is the same as in the METRO?");
		String mobileSameMetroNum = s.next();

		driver.manage().window().maximize();

		Thread.sleep(2000);

		if (!mobileSameMetroNum.equalsIgnoreCase("yes")) {

			WebElement unCheckSameasMetroMobNum = driver.findElement(By.xpath("//input[@name='mob_same_chk_id']"));
			unCheckSameasMetroMobNum.click();

			Thread.sleep(1000);

			scrollDown(scr1);

			Thread.sleep(2000);

			WebElement newMobile = driver.findElement(By.xpath("//input[@name='applicantNewMobile']"));

			if (newMobile.isDisplayed()) {

				driver.manage().window().minimize();

				Thread.sleep(1000);

				System.out.println("Update your new mobile number");

				long newMobileNum = s.nextLong();

				driver.manage().window().maximize();

				Thread.sleep(2000);

				String newMobileNumber = String.valueOf(newMobileNum);

				newMobile.sendKeys(newMobileNumber);

			} else {

				Assert.assertTrue(false);

			}

		} else {

			WebElement newMobile = driver.findElement(By.xpath("//input[@name='applicantNewMobile']"));

			if (newMobile.isDisplayed()) {

				Assert.assertTrue(false);

			}

		}

		Thread.sleep(2000);

		// Scenario : 8 = Select your business clarification

		minimize();

		Thread.sleep(1000);

		System.out.println("Enter the Business Clarification name");
		
		String businessClarificationName = s.next();

		Thread.sleep(5000);

		maximize();
		
		Thread.sleep(5000);

		WebElement businessClarification = driver
				.findElement(By.xpath("(//div[text()='--Select Business Classification--'])[1]"));

		businessClarification.click();

		Thread.sleep(2000);

		WebElement businessClarificationTxtbox = driver.findElement(By.xpath("//input[@class='fstsearchinput']"));

		Thread.sleep(1000);

		businessClarificationTxtbox.sendKeys(businessClarificationName);

		Thread.sleep(1000);

		List<WebElement> businessClarificationOptions = driver.findElements(By.xpath("//div[@class='hideFst']"));
		
	    js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].click()", businessClarificationOptions.get(0));

		//businessClarificationOptions.get(0).click();

		Thread.sleep(2000);

		/*
		 * Select s2 = new Select(businessClarification);
		 * 
		 * Thread.sleep(2000);
		 * 
		 * driver.manage().window().minimize();
		 * 
		 * Thread.sleep(3000);
		 * 
		 * System.out.println("Please enter the Business clarification index Number");
		 * 
		 * short businessClarificationIndNum = s.nextShort();
		 * 
		 * Thread.sleep(3000);
		 * 
		 * driver.manage().window().maximize();
		 * 
		 * Thread.sleep(3000);
		 * 
		 * s2.selectByIndex(businessClarificationIndNum);
		 */

		// Thread.sleep(3000);

		// Scenario :8 = Select Your Business Segment

		WebElement businessSegment = driver.findElement(By.xpath("//select[@name='businessSegment']"));
		Select s3 = new Select(businessSegment);

		Thread.sleep(2000);

		driver.manage().window().minimize();

		Thread.sleep(3000);

		System.out.println("Please enter the Business Segment index Number");

		short businessSegmentIndNum = s.nextShort();

		Thread.sleep(3000);

		driver.manage().window().maximize();

		Thread.sleep(3000);

		s3.selectByIndex(businessSegmentIndNum);

		// Scenario :9 = In-case the business address is changed, then update your
		// current business address.

		WebElement currentBusinsAddress = driver
				.findElement(By.xpath("//input[@placeholder='Current Business Address']"));

		Thread.sleep(5000);

		driver.manage().window().minimize();

		Thread.sleep(3000);

		System.out.println("Please Update your Current Business Address");
		
		Thread.sleep(5000);

		String curBusnsAdd = s.nextLine();

		Thread.sleep(5000);

		maximize();

		Thread.sleep(3000);

		Actions a = new Actions(driver);
		
		
		currentBusinsAddress.sendKeys(curBusnsAdd);

		Thread.sleep(2000);

		// Scenario :10 = Please upload the address document proof as per business
		// license

		WebElement SubmitaddressdocumentBtn = driver.findElement(By.xpath("(//button[text()='Select a File'])[3]"));
		SubmitaddressdocumentBtn.click();

		Thread.sleep(7000);

		// Scenario :11 = Select your communication medium preference: *

		// 1.SocialMedia

		WebElement socialMedia = driver.findElement(By.xpath("//select[@name='socialMediaId']"));

		Select s4 = new Select(socialMedia);

		Thread.sleep(5000);

		minimize();

		Thread.sleep(3000);

		System.out.println("Please enter the Social Media index Number");

		Thread.sleep(5000);
		
		int socialMediaIndNum = s.nextInt();

		Thread.sleep(3000);

		driver.manage().window().maximize();

		Thread.sleep(3000);

		s4.selectByIndex(socialMediaIndNum);

		Thread.sleep(2000);

		// 2.chatbox

		WebElement chatMedia = driver.findElement(By.xpath("//select[@name='chatMediaId']"));

		Select s5 = new Select(chatMedia);

		Thread.sleep(2000);

		minimize();

		Thread.sleep(3000);

		System.out.println("Please enter the chatbox  index Number");

		short chatMediaIndNum = s.nextShort();

		Thread.sleep(3000);

		driver.manage().window().maximize();

		Thread.sleep(3000);

		s5.selectByIndex(chatMediaIndNum);

		Thread.sleep(2000);

		// 3.Advertisement

		WebElement addMedia = driver.findElement(By.xpath("//select[@name='adMediaId']"));

		Select s6 = new Select(addMedia);

		Thread.sleep(2000);

		minimize();

		Thread.sleep(3000);

		System.out.println("Please enter the Advertisement index Number");

		short addMediaIndNum = s.nextShort();

		Thread.sleep(3000);

		driver.manage().window().maximize();

		Thread.sleep(3000);

		s6.selectByIndex(addMediaIndNum);

		Thread.sleep(2000);

		// Scenario :12 = Please upload an Image of the Business Unit(Along with Name
		// board) *

		WebElement submitNameBoardImgBtn = driver.findElement(By.xpath("(//button[text()='Select a File'])[4]"));
		SubmitaddressdocumentBtn.click();

		driver.manage().timeouts().implicitlyWait(Duration.ofMinutes(1));

		Thread.sleep(1000);

		// Scenario :13 = Select your Mode of data correction: *

		WebElement dataCorrectionMode = driver.findElement(By.xpath("//select[@name='dataCorrectionMode']"));

		Select s7 = new Select(dataCorrectionMode);

		Thread.sleep(2000);

		driver.manage().window().minimize();

		Thread.sleep(3000);

		System.out.println("Please enter the Advertisement index Number");

		short dataCorrectionModeIndNum = s.nextShort();

		Thread.sleep(3000);

		driver.manage().window().maximize();

		Thread.sleep(3000);

		s7.selectByIndex(dataCorrectionModeIndNum);

		Thread.sleep(2000);

		// Scenario :14 = Generate OTP and Submit the Form

		WebElement submitOTPchkbox = driver.findElement(By.xpath("//input[@id='otp_chk_id']"));
		submitOTPchkbox.click();

		Thread.sleep(1000);

		WebElement OtpTxtbox = driver.findElement(By.xpath("//input[@name='mainOTP']"));

		driver.manage().window().minimize();

		Thread.sleep(1500);

		System.out.println("Please enter the OTP sent to your current registered email id/mobile number");

		int submitOtp = s.nextInt();

		driver.manage().window().maximize();

		Thread.sleep(2000);

		String FormSubmitOTP = String.valueOf(submitOtp);

		OtpTxtbox.sendKeys(FormSubmitOTP);

		WebElement submitOtpErrMsg = driver.findElement(By.xpath("//p[text()='Please enter valid OTP.']"));

		if (submitOtpErrMsg.isDisplayed()) {

			System.out.println(submitOtpErrMsg.getText());

		} else {

			System.out.println("Thankyou For entering the Proper OTP");
		}

		// Main

	}

}
