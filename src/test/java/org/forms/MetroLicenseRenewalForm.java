package org.forms;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.github.bonigarcia.wdm.WebDriverManager;

public class MetroLicenseRenewalForm {

	public static WebDriver driver;
	// public static WebElement mobileNumber;
	JavascriptExecutor js;

	DataFormatter d = new DataFormatter();

	public WebElement whatsappNumber;
	public WebElement licenseType;
	public WebElement selectFileLicensecopyBtn;
	public WebElement submitKYCdocumentBtn;
	public WebElement email;
	public WebElement otpTxtboxEmail;
	public WebElement newMobile;
	public WebElement businessClarificationTxtbox;
	public WebElement businessSegment;
	public WebElement currentBusinsAddress;
	public WebElement businesslicense;
	public WebElement socialMedia;
	public WebElement chatMedia;
	public WebElement addMedia;
	public WebElement submitNameBoardImgBtn;
	public static WebElement NameBoardField1;
	public WebElement dataCorrectionMode;
	public static WebElement OtpTxtbox;
	public static String OtpTxtboxValue;

	public ExtentReports extent;
	public ExtentTest test;
	
	SoftAssert soft = new SoftAssert();
	
	@BeforeClass
	public void setUp() {

		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();

		driver.get("http://13.71.30.13:8080/metrocard/licenserenewal");

		driver.manage().window().maximize();

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	}

	@AfterClass
	public void tearDown() {

		 driver.quit();

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
	
	public static String getScreenshot(WebDriver driver,String ScreenshotName) throws IOException {

		String dateName = new SimpleDateFormat("yyyymmddhhmmss").format(new Date());
	    
		TakesScreenshot tk = (TakesScreenshot)driver;
		
		File src = tk.getScreenshotAs(OutputType.FILE);
		
		String des = System.getProperty("user.dir")+"//FailedScreenshot//"+ScreenshotName+dateName +".png";
		
		File FinalDestination = new File(des);
		
		FileUtils.copyFile(src, FinalDestination);
		
		return des;	
	}

	@BeforeTest
	private void reportsStart() {

		extent = new ExtentReports(System.getProperty("user.dir")+"//ReportSChhart//report.index.html",true);
		extent.addSystemInfo("Tester Name", "Balaji.P");
		extent.addSystemInfo("Project Name", "Metro License Renewal Form");	
		
		
	}
	
	@AfterTest
	private void reportEnd() {

		extent.flush();			
		
	}
	
	@AfterMethod
	private void listening(ITestResult result) throws IOException {

		if (result.getStatus()==ITestResult.FAILURE) {
			
			test.log(LogStatus.FAIL,result.getName());
			test.log(LogStatus.FAIL,result.getThrowable());

			
			String ScreenshotPath = null;
			try {
				ScreenshotPath = MetroLicenseRenewalForm.getScreenshot(driver, result.getName());
			} catch (IOException e) {
				e.printStackTrace();
			}
			test.log(LogStatus.FAIL, test.addScreenCapture(ScreenshotPath));		
		}else if (result.getStatus()==ITestResult.SKIP) {
			
			test.log(LogStatus.SKIP,result.getName());
			test.log(LogStatus.SKIP,result.getThrowable());

		}else if(result.getStatus()==ITestResult.SUCCESS) {
			
			test.log(LogStatus.PASS,result.getName());
			
		}		
	}
	
	
	
	@Test(dataProvider = "datas")
	public void actions(String UserLikesToEnterApplicationViaMobile, String MobileNumber, String customerCardNumber,
			String sameMobNumWhapNum, String wtsappNum, String licenseTypeindex, String EmailEnteringYes, String Email,
			String newMobYesorNo, String newMobNumber, String BusClariName, String BusiSegNum, String CurBusiAddress,
			String socIndNum, String chatIndNum, String addIndNum, String ModDatCorrect)
			throws InterruptedException, ParseException {

		test = extent.startTest("loginValidations");
		
		Scanner s = new Scanner(System.in);
		
		Thread.sleep(2000);

		// 1 Scenario

		if (UserLikesToEnterApplicationViaMobile.equalsIgnoreCase("yes")) {

			WebElement mobileNumber = driver.findElement(By.xpath("//input[@name='applicantMobile']"));
			mobileNumber.sendKeys(MobileNumber);

			WebElement searchBtn = driver.findElement(By.xpath("(//span[@class='searchIcon'])[1]"));
			searchBtn.click();

			System.out.println("Thanks for entering in to the application by using Mobile Number");

		} else {

			WebElement cardNumber = driver.findElement(By.xpath("//input[@name='cardNumber']"));
			cardNumber.sendKeys(customerCardNumber);

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

		whatsappNumber = driver.findElement(By.xpath("//input[@name='applicantWhatsApp']"));

		Thread.sleep(1000);

		String sameMobNumasWhatsappNum = sameMobNumWhapNum;

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

			Thread.sleep(2000);

			String whatsappNumbernotEmpty = whatsappNumber.getAttribute("value");

			if (whatsappNumbernotEmpty.isEmpty()) {
				Assert.assertTrue(true);

				String whatsappNumNew = wtsappNum;
				whatsappNumber.sendKeys(whatsappNumNew);

			} else {
				Assert.assertTrue(false);
			}

		}

		// 3 Scenario

		WebElement scr1 = driver.findElement(By.xpath("//label[text()=' Company Name ']"));
		scrollDown(scr1);

		Thread.sleep(3000);

		licenseType = driver.findElement(By.xpath("//select[@name='licenseTypeId']"));
		Select s1 = new Select(licenseType);

		String licenseTypeIndNum = licenseTypeindex;

		Thread.sleep(2000);

		Integer licTypIndNum = Integer.valueOf(licenseTypeIndNum);

		s1.selectByIndex(licTypIndNum);

		Thread.sleep(2000);

		// Scenario :4 = Please upload the same license copy selected from above list *

		selectFileLicensecopyBtn = driver.findElement(By.xpath("(//button[text()='Select a File'])[1]"));
		selectFileLicensecopyBtn.click();

		Thread.sleep(7000);

		// Scenario :5 = Please upload the KYC document *

		submitKYCdocumentBtn = driver.findElement(By.xpath("(//button[text()='Select a File'])[2]"));
		submitKYCdocumentBtn.click();

		Thread.sleep(10000);

		// Scenario: 6 = Update your Email Address

		if (EmailEnteringYes.equalsIgnoreCase("yes")) {

			email = driver.findElement(By.xpath("//input[@placeholder='Your Email']"));

			String emailValue = Email;

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

				otpTxtboxEmail = driver.findElement(By.xpath("//input[@name='emailOTP']"));

				Thread.sleep(1000);

				driver.manage().window().minimize();

				Thread.sleep(2000);

				System.out.println("Please enter OTP");
				driver.manage().timeouts().implicitlyWait(Duration.ofMinutes(1));

				int getingOtp = s.nextInt();

				String otpValue = String.valueOf(getingOtp);

				Thread.sleep(9000);

				driver.manage().window().maximize();

				Thread.sleep(3000);

				otpTxtboxEmail.sendKeys(otpValue);

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

		}

		// Scenario : 7 = Update your new mobile number *

		WebElement scrUp = driver.findElement(By.xpath("//h1[text()='Please Update Your Data']"));

		scrollUp(scrUp);

		Thread.sleep(2000);

		String mobileSameMetroNum = newMobYesorNo;

		Thread.sleep(2000);

		if (!mobileSameMetroNum.equalsIgnoreCase("yes")) {

			WebElement unCheckSameasMetroMobNum = driver.findElement(By.xpath("//input[@name='mob_same_chk_id']"));
			unCheckSameasMetroMobNum.click();

			Thread.sleep(1000);

			scrollDown(scr1);

			Thread.sleep(2000);

			newMobile = driver.findElement(By.xpath("//input[@name='applicantNewMobile']"));

			Thread.sleep(1000);

			if (newMobile.isDisplayed()) {

				String newMobileNum = newMobNumber;

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

		Thread.sleep(1000);

		String businessClarificationName = BusClariName;

		Thread.sleep(1000);

		WebElement businessClarification = driver
				.findElement(By.xpath("(//div[text()='--Select Business Classification--'])[1]"));

		businessClarification.click();

		Thread.sleep(2000);

		businessClarificationTxtbox = driver.findElement(By.xpath("//input[@class='fstsearchinput']"));

		Thread.sleep(1000);

		businessClarificationTxtbox.sendKeys(businessClarificationName);

		Thread.sleep(1000);

		List<WebElement> businessClarificationOptions = driver.findElements(By.xpath("//div[@class='hideFst']"));

		js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click()", businessClarificationOptions.get(0));

		Thread.sleep(2000);

		// Scenario :8 = Select Your Business Segment

		businessSegment = driver.findElement(By.xpath("//select[@name='businessSegment']"));
		Select s3 = new Select(businessSegment);

		Thread.sleep(2000);

		String businessSegmentIndNum = BusiSegNum;

		Integer busiIndNum = Integer.valueOf(businessSegmentIndNum);

		Thread.sleep(15000);

		s3.selectByIndex(busiIndNum);

		// Scenario :9 = In-case the business address is changed, then update your
		// current business address.

		currentBusinsAddress = driver.findElement(By.xpath("//input[@placeholder='Current Business Address']"));

		Thread.sleep(5000);

		String curBusnsAdd = CurBusiAddress;

		Thread.sleep(3000);

		Actions a = new Actions(driver);

		currentBusinsAddress.sendKeys(curBusnsAdd);

		Thread.sleep(2000);

		// Scenario :10 = Please upload the address document proof as per business
		// license

		businesslicense = driver.findElement(By.xpath("(//button[text()='Select a File'])[3]"));
		businesslicense.click();

		Thread.sleep(7000);

		// Scenario :11 = Select your communication medium preference: *

		// 1.SocialMedia

		socialMedia = driver.findElement(By.xpath("//select[@name='socialMediaId']"));

		Select s4 = new Select(socialMedia);

		Thread.sleep(1500);

		String socialMediaIndNum = socIndNum;

		Thread.sleep(1000);

		Integer soMediaIndNumber = Integer.valueOf(socialMediaIndNum);

		Thread.sleep(1500);

		s4.selectByIndex(soMediaIndNumber);

		Thread.sleep(2000);

		// 2.chatbox

		chatMedia = driver.findElement(By.xpath("//select[@name='chatMediaId']"));

		Select s5 = new Select(chatMedia);

		Thread.sleep(2000);

		String chatMediaIndNum = chatIndNum;

		Integer chatMediaIndNumber = Integer.valueOf(chatMediaIndNum);

		Thread.sleep(1500);

		s5.selectByIndex(chatMediaIndNumber);

		Thread.sleep(2000);

		// 3.Advertisement

		addMedia = driver.findElement(By.xpath("//select[@name='adMediaId']"));

		Select s6 = new Select(addMedia);

		Thread.sleep(1500);

		String addMediaIndNum = addIndNum;

		Thread.sleep(1500);

		Integer addvertiseMediaIndNum = Integer.valueOf(addMediaIndNum);

		Thread.sleep(1500);

		s6.selectByIndex(addvertiseMediaIndNum);

		Thread.sleep(2000);

		// Scenario :12 = Please upload an Image of the Business Unit(Along with Name
		// board) *

		submitNameBoardImgBtn = driver.findElement(By.xpath("(//button[text()='Select a File'])[4]"));
		submitNameBoardImgBtn.click();

		Thread.sleep(4000);

		// Scenario :13 = Select your Mode of data correction: *

		dataCorrectionMode = driver.findElement(By.xpath("//select[@name='dataCorrectionMode']"));

		Select s7 = new Select(dataCorrectionMode);

		Thread.sleep(2000);

		Integer dataCorrectionModeIndNum = addvertiseMediaIndNum;

		Thread.sleep(1500);

		s7.selectByIndex(dataCorrectionModeIndNum);

		Thread.sleep(2000);

		// Scenario :14 = Generate OTP and Submit the Form

		WebElement submitOTPchkbox = driver.findElement(By.xpath("//input[@id='otp_chk_id']"));
		submitOTPchkbox.click();

		Thread.sleep(5000);

		// OtpTxtbox = m.getOtpTxtboxPom();

		Thread.sleep(1000);

		OtpTxtbox = driver.findElement(By.xpath("//input[@name='mainOTP']"));

		if (OtpTxtbox.isDisplayed()) {

			driver.manage().window().minimize();

			Thread.sleep(1500);

			System.out.println("Please enter the OTP sent to your current registered email id/mobile number");

			int submitOtp = s.nextInt();

			driver.manage().window().maximize();

			Thread.sleep(2000);

			String FormSubmitOTP = String.valueOf(submitOtp);

			OtpTxtbox.sendKeys(FormSubmitOTP);

			OtpTxtboxValue = OtpTxtbox.getAttribute("value");

			WebElement otpSubmitbutton = driver.findElement(By.xpath("//button[@id='mainOTPButton']"));
			otpSubmitbutton.click();

			Thread.sleep(1500);

			try {

				WebElement submitOtpErrMsg = driver.findElement(By.xpath("//p[text()='Please enter valid OTP.']"));

				if (submitOtpErrMsg.isDisplayed()) {

					System.out.println(submitOtpErrMsg.getText());

				}

			} catch (Exception e) {

				System.out.println("Thankyou For entering the Proper OTP");

			}

		} else {

			WebElement validateEmailorMobile = driver
					.findElement(By.xpath("//div[contains(text(),'Please validate email/mob')]"));

			if (validateEmailorMobile.isDisplayed()) {

				System.out.println(validateEmailorMobile.getText());
				System.exit(1);

			}

		}

		// validation

		WebElement sucMsg = driver.findElement(By.xpath("//span[@class='pl-3']"));

		if (sucMsg.isDisplayed()) {

			WebElement finalSubmitBtn = driver.findElement(By.cssSelector("button[name='form_save']"));
			finalSubmitBtn.click();

			Thread.sleep(20000);

			try {

				WebElement errorPAge = driver.findElement(By.xpath("//h1[contains(text(),'Something went wrong.')]"));

				if (errorPAge.isDisplayed()) {

					System.out.println("Please upload an Image of the Business Unit(Along with Name board)* ");

					Assert.assertTrue(false);
				}

			} catch (Exception e) {

				try {

					WebElement SuccessPAge = driver.findElement(By.xpath("//h1[text()='Thank You']"));

					System.out.println("License Renewal form submitted successfully");

				} catch (Exception e2) {

					System.out.println("Please Enter All the Mandatory fields Properly then only Form submit successfully");

					soft.assertTrue(false);
				}

			}

		}

	}

	@DataProvider(name = "datas")
	public Object[][] credentials() throws IOException {

		File f = new File(
				"C:\\Users\\balaji.p\\eclipse-workspace 2\\MFDemo\\DatasCollection\\Metro_License_Renewal_Forms_Inputs.xlsx");

		FileInputStream fin = new FileInputStream(f);

		Workbook b = new XSSFWorkbook(fin);

		Sheet s = b.getSheetAt(0); // Cre

		int rowsCount = s.getPhysicalNumberOfRows();

		Row r = s.getRow(0);

		short columnCount = r.getLastCellNum();

		Object data[][] = new Object[rowsCount - 1][columnCount];

		for (int i = 0; i < rowsCount - 1; i++) {

			Row r1 = s.getRow(i + 1);

			for (int j = 0; j < columnCount; j++) {

				Cell c = r1.getCell(j);

				data[i][j] = d.formatCellValue(c);

			}

		}

		return data;

	}

}
