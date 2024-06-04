package org.metro;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
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
import org.forms.MetroLicenseRenewalForm;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
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

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Add {

	public WebDriver driver;
	public WebElement EligibleAddonValue;
	public List<WebElement> plusBtn;
	public Select sel;
	public JavascriptExecutor js;
	public String[] set;

	DataFormatter d = new DataFormatter();
	Scanner s = new Scanner(System.in);

	public ExtentReports extent;
	public ExtentTest test;

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

		driver.quit();

	}

	@BeforeTest
	private void reportsStart() {

		extent = new ExtentReports(System.getProperty("user.dir") + "//ReportSChhartAddON//report.index.html", true);
		extent.addSystemInfo("Tester Name", "Balaji.P");
		extent.addSystemInfo("Project Name", "Metro Add On Forms");

	}

	@AfterTest
	private void reportEnd() {

		extent.flush();

	}

	public static String getScreenshot(WebDriver driver, String ScreenshotName) throws IOException {

		String dateName = new SimpleDateFormat("yyyymmddhhmmss").format(new Date());

		TakesScreenshot tk = (TakesScreenshot) driver;

		File src = tk.getScreenshotAs(OutputType.FILE);

		String des = System.getProperty("user.dir") + "//FailedScreenshotAddon//" + ScreenshotName + dateName + ".png";

		File FinalDestination = new File(des);

		FileUtils.copyFile(src, FinalDestination);

		return des;
	}

	public void scrollDown(WebElement ref) {

		js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true)", ref);

	}

	public void scrollUp(WebElement ref) {

		js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(false)", ref);

	}

	@Test(priority = 1, dataProvider = "common")
	public void validation(String howToEnterApp, String MobileNum, String cardNum, String cardHoldNum, String updateYes,
			String newEmail, String EligbleAccesCount, String appliName1, String appliMobNum1, String appliEmail1,
			String appliName2, String appliMobNum2, String appliEmail2, String appliName3, String appliMobNum3,
			String appliEmail3, String appliName4, String appliMobNum4, String appliEmail4, String referCount,
			String cardHoldName, String cardHoldMobNum, String cardHoldEmail, String referCusName1,
			String referedCusMobNum1, String referCusEmaiID1, String referCusName2, String referedCusMobNum2,
			String referCusEmaiID2, String referCusName3, String referedCusMobNum3, String referCusEmaiID3,
			String referCusName4, String referedCusMobNum4, String referCusEmaiID4, String referCusName5,
			String referedCusMobNum5, String referCusEmaiID5) throws InterruptedException {

		test = extent.startTest("MetroAddOnForms");

		WebElement mobileNumber = driver.findElement(By.cssSelector("input[name='mobile']"));

		WebElement cardNumber = driver.findElement(By.cssSelector("input[name='cardNo']"));

		String HowToLikeEnteringInTheApplication = howToEnterApp;

		if (HowToLikeEnteringInTheApplication.equalsIgnoreCase("mobile")) {

			mobileNumber.sendKeys(MobileNum);
		} else {
			cardNumber.sendKeys(cardNum);
		}

		holding(1500);

		WebElement justClick = driver.findElement(By.xpath("//span[text()='OR']"));
		justClick.click();

		WebElement cardHolderNo = driver.findElement(By.name("cardHolderNo"));
		cardHolderNo.sendKeys(cardHoldNum);

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

		String UpdateEmail = updateYes;

		if (UpdateEmail.equalsIgnoreCase("no")) {

			WebElement emailCheckbox = driver.findElement(By.xpath("//input[@name='email_update_chk_id']"));
			emailCheckbox.click();

			holding(1000);

			WebElement EmailTxtbox = driver.findElement(By.xpath("//input[@name='applicantEmailId']"));
			EmailTxtbox.sendKeys(newEmail);

			WebElement otpCheckbox = driver.findElement(By.xpath("//input[@name='email_otp_chk_id']"));
			otpCheckbox.click();

			WebElement otpTxtbox = driver.findElement(By.xpath("//input[@id='emailOTP']"));

			minimize();

			holding(1000);

			System.out.println("Please enter the OTP");

			holding(10000);

			int otpValue = s.nextInt();

			maximize();

			holding(2000);

			String otpNum = String.valueOf(otpValue);
			otpTxtbox.sendKeys(otpNum);

			holding(1000);

			WebElement otpSubmitBtn = driver.findElement(By.xpath("//button[@id='emailOTPButton']"));
			otpSubmitBtn.click();

			holding(3000);

			try {

				WebElement otpErrMsg = driver.findElement(By.xpath("//p[text()='Please enter valid OTP.']"));

				if (otpErrMsg.isDisplayed()) {

					System.out.println(otpErrMsg.getText());

				}

			} catch (Exception e) {

				WebElement otpSucMsg = driver.findElement(By.className("successful-tick"));
				System.out.println("Thanks For Entered Proper OTP");

			}

		}

		String userLikeForm = "AddOn";

		if (userLikeForm.equalsIgnoreCase("REFER")) {

			try {

				WebElement addOnBtn = driver.findElement(By.xpath("//input[@class='add-on-btn']"));
				addOnBtn.click();

				EligibleAddonValue = driver.findElement(By.xpath("//p[contains(text(),'Eligible Add-on cards ')]"));

				Thread.sleep(2000);

				plusBtn = driver.findElements(By.xpath("//div[contains(@class,'add-more ml-auto addBtn')]"));

				Thread.sleep(1000);

				/* Please enter your limit of eligible */

				Integer EligbleAccesCountvalue = Integer.valueOf(EligbleAccesCount);
				int HowMuchEligbleLikeToYouGive = EligbleAccesCountvalue;

				for (int i = 0; i < HowMuchEligbleLikeToYouGive - 1; i++) {

					js = (JavascriptExecutor) driver;
					js.executeScript("arguments[0].click()", plusBtn.get(i));

					holding(1000);

					WebElement scroll1 = driver
							.findElement(By.xpath("//span[text()='Accept the T & C and Submit the Form']"));
					scrollDown(scroll1);

					holding(2000);
				}

				scrollUp(EligibleAddonValue);

				holding(2000);

				List<WebElement> applicantName = driver
						.findElements(By.xpath("//input[@placeholder='Applicant Name']"));

				List<WebElement> applicantMobileNumber = driver
						.findElements(By.xpath("//input[contains(@id,'addonApplicantMobile')]"));

				List<WebElement> applicantEmail = driver.findElements(By.xpath("//input[@type='email']"));

				List<WebElement> applicantGender = driver
						.findElements(By.xpath("//select[contains(@id,'addonApplicantGender')]"));

				List<WebElement> applicantPosition = driver
						.findElements(By.xpath("//select[contains(@id,'addonPositionId')]"));

				List<WebElement> selectFileBtn = driver.findElements(By.xpath("//button[text()='Select a File']"));

				for (int i = 0; i < HowMuchEligbleLikeToYouGive; i++) {

					if (i == 0) {

						set = new String[3];
						set[0] = appliName1; // Test One
						set[1] = appliMobNum1;
						set[2] = appliEmail1;

					} else if (i == 1) {

						set = new String[3];
						set[0] = appliName2;
						set[1] = appliMobNum2; // 9876543212
						set[2] = appliEmail2;

					} else if (i == 2) {

						set = new String[3];
						set[0] = appliName3;
						set[1] = appliMobNum3;
						set[2] = appliEmail3; // test2@mailinator.com

					} else if (i == 3) {

						set = new String[3];
						set[0] = appliName4;
						set[1] = appliMobNum4;
						set[2] = appliEmail4;

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

				WebElement FinalSubmitBtn = driver.findElement(By.xpath("//button[text()='Save']"));
				FinalSubmitBtn.click();

				holding(3000);

				String curUrl = driver.getCurrentUrl();

				if (curUrl.contains("thankyou")) {

					System.out.println("ThankYou for added the Eligbles of Your Metro Cards");

				} else {
					System.out.println("Please fill All Mandator Field");

				}

			} catch (NoSuchElementException e3) {

				System.out.println("You have Already Added Your Card Nominiees");

			}

		} else {

			WebElement referAFriendBtn = driver.findElement(By.xpath("//input[@class='referral-btn']"));
			referAFriendBtn.click();

			List<WebElement> referPlusBtn = driver
					.findElements(By.xpath("//div[contains(@class,'add-more ml-auto addBtn')]"));

			// give below 4
			Integer referCounts = Integer.valueOf(referCount);
			int HowMuchFriendYouRefer = referCounts;

			holding(2000);

			for (int i = 0; i < HowMuchFriendYouRefer - 1; i++) {

				holding(4000);
				js = (JavascriptExecutor) driver;
				js.executeScript("arguments[0].click()", referPlusBtn.get(i));
				holding(1000);
				WebElement saveBtn = driver.findElement(By.xpath("//button[text()='Save']"));
				scrollDown(saveBtn);
				holding(2000);
			}

			WebElement cardHolderName = driver.findElement(By.xpath("//input[@placeholder='Card Holder Name']"));
			cardHolderName.sendKeys(cardHoldName);
			String cardHolderNameValue = cardHolderName.getAttribute("value");

			WebElement cardHolderMobNum = driver
					.findElement(By.xpath("//input[@placeholder='Card Holder Mobile Number']"));
			cardHolderMobNum.sendKeys(cardHoldMobNum);
			String cardHolderMobNumValue = cardHolderMobNum.getAttribute("value");

			WebElement cardHolderEmail = driver.findElement(By.xpath("//input[@placeholder='Card Holder E-mail ID']"));
			cardHolderEmail.sendKeys(cardHoldEmail);
			String cardHolderEmailValue = cardHolderEmail.getAttribute("value");

			for (int i = 0; i < HowMuchFriendYouRefer; i++) {

				if (i == 0) {

					set = new String[3];
					set[0] = referCusName1;
					set[1] = referedCusMobNum1;
					set[2] = referCusEmaiID1;

				} else if (i == 1) {

					set = new String[3];
					set[0] = referCusName2;
					set[1] = referedCusMobNum2;
					set[2] = referCusEmaiID2;

				} else if (i == 2) {

					set = new String[3];
					set[0] = referCusName3;
					set[1] = referedCusMobNum3;
					set[2] = referCusEmaiID3;

				} else if (i == 3) {

					set = new String[3];
					set[0] = referCusName4;
					set[1] = referedCusMobNum4;
					set[2] = referCusEmaiID4;

				} else if (i == 4) {

					set = new String[3];
					set[0] = referCusName5;
					set[1] = referedCusMobNum5;
					set[2] = referCusEmaiID5;

				}

				List<WebElement> referedCustomerName = driver
						.findElements(By.xpath("//input[contains(@id,'referredCustomerName')]"));
				referedCustomerName.get(i).sendKeys(set[0]);

				holding(1500);

				List<WebElement> referedCustomerMobileNum = driver
						.findElements(By.xpath("//input[@placeholder='Mobile Number']"));
				referedCustomerMobileNum.get(i).sendKeys(set[1]);

				holding(1500);

				List<WebElement> referedCustomerEmaiID = driver
						.findElements(By.xpath("//input[contains(@name,'referredCustomerEmail')]"));
				referedCustomerEmaiID.get(i).sendKeys(set[2]);

				holding(2000);
			}

			WebElement saveBtn = driver.findElement(By.xpath("//button[text()='Save']"));
			saveBtn.click();

			holding(2000);

			String curUrl = driver.getCurrentUrl();

			if (curUrl.contains("thankyou")) {

				System.out.println("Thank You for Refer your Friend");

			} else {

				System.out.println("Please fill All Mandatory Fields");

			}

		}

	}

	@AfterMethod
	private void listening(ITestResult result) throws IOException {

		if (result.getStatus() == ITestResult.FAILURE) {

			test.log(LogStatus.FAIL, result.getName());
			test.log(LogStatus.FAIL, result.getThrowable());

			String ScreenshotPath = null;
			try {
				ScreenshotPath = Add.getScreenshot(driver, result.getName());
			} catch (IOException e) {
				e.printStackTrace();
			}
			test.log(LogStatus.FAIL, test.addScreenCapture(ScreenshotPath));
		} else if (result.getStatus() == ITestResult.SKIP) {

			test.log(LogStatus.SKIP, result.getName());
			test.log(LogStatus.SKIP, result.getThrowable());

		} else if (result.getStatus() == ITestResult.SUCCESS) {

			test.log(LogStatus.PASS, result.getName());

		}
	}

	@DataProvider(name = "common")
	public Object[][] general() throws IOException {

		File f = new File(
				"C:\\Users\\balaji.p\\eclipse-workspace 2\\MFDemo\\DatasCollection\\Metro_License_Renewal_Forms_Inputs.xlsx");

		FileInputStream fin = new FileInputStream(f);

		Workbook b = new XSSFWorkbook(fin);

		Sheet s = b.getSheetAt(2);

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

	@DataProvider(name = "Add ON Eligible")
	public Object[][] addOn() throws IOException {

		File f = new File(
				"C:\\Users\\balaji.p\\eclipse-workspace 2\\MFDemo\\DatasCollection\\Metro_License_Renewal_Forms_Inputs.xlsx");

		FileInputStream fin = new FileInputStream(f);

		Workbook b = new XSSFWorkbook(fin);

		Sheet s = b.getSheetAt(3);

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

	@DataProvider(name = "Refer Friend")
	public Object[][] referFriend() throws IOException {

		File f = new File(
				"C:\\Users\\balaji.p\\eclipse-workspace 2\\MFDemo\\DatasCollection\\Metro_License_Renewal_Forms_Inputs.xlsx");

		FileInputStream fin = new FileInputStream(f);

		Workbook b = new XSSFWorkbook(fin);

		Sheet s = b.getSheetAt(4);

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
