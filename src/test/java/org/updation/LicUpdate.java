package org.updation;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.forms.MetroAddOnForm;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.github.bonigarcia.wdm.WebDriverManager;

public class LicUpdate {

	public WebDriver driver;
	public JavascriptExecutor js;
	DataFormatter d = new DataFormatter();

	public ExtentReports extent;
	public ExtentTest test;

	@BeforeClass
	private void setUp() {

		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();

		driver.get("http://192.168.10.30:8180/");

		driver.manage().window().maximize();

	}

	@BeforeTest
	private void reportsStart() {

		extent = new ExtentReports(System.getProperty("user.dir") + "//ReportSOnUpdateForm//report.index.html", true);
		extent.addSystemInfo("Tester Name", "Balaji.P");
		extent.addSystemInfo("Project Name", "Metro License Updation Forms");

	}

	@AfterTest
	private void reportEnd() {

		extent.flush();

	}

	@AfterMethod
	private void listening(ITestResult result) throws IOException {

		if (result.getStatus() == ITestResult.FAILURE) {

			test.log(LogStatus.FAIL, result.getName());
			test.log(LogStatus.FAIL, result.getThrowable());

			String ScreenshotPath = null;
			try {
				ScreenshotPath = LicUpdate.getScreenshot(driver, result.getName());
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

	public static String getScreenshot(WebDriver driver, String ScreenshotName) throws IOException {

		String dateName = new SimpleDateFormat("yyyymmddhhmmss").format(new Date());

		TakesScreenshot tk = (TakesScreenshot) driver;

		File src = tk.getScreenshotAs(OutputType.FILE);

		String des = System.getProperty("user.dir") + "//FailedScreenshotUpdateForm//" + ScreenshotName + dateName
				+ ".png";

		File FinalDestination = new File(des);

		FileUtils.copyFile(src, FinalDestination);

		return des;
	}

	@Test
	public void Updation() throws InterruptedException {

		test = extent.startTest("License Updation Test");

		String AreYouMetroCustomer = "yes";

		if (!AreYouMetroCustomer.equalsIgnoreCase("yes")) {

			WebElement Nobtn = driver.findElement(By.cssSelector("button[id='btnNo']"));
			Nobtn.click();

			Thread.sleep(2000);

			String curUrl = driver.getCurrentUrl();

			if (curUrl.contains("registration")) {

				System.out.println("User entered in to metro Customer Regisration page");

			} else {

				Assert.assertFalse("if we click the No button it shouldn't Redirect to Registration page", false);
				test.log(LogStatus.FAIL, "if we click the No button it shouldn't Redirect to Registration page");
			}

		} else {

			WebElement yesBtn = driver.findElement(By.cssSelector("button[id='btnYes']"));
			yesBtn.click();

			Thread.sleep(2000);

			WebElement Welcome = driver.findElement(By.xpath("//h1[text()='Welcome']"));

			if (Welcome.isDisplayed()) {

				Assert.assertTrue(true);

				/**/ System.out.println("please enter user card Number and Card Holder Number");

			}

			WebElement cardNumTxtbox = driver.findElement(By.xpath("//input[@id='txtCustomerNumber']"));
			cardNumTxtbox.sendKeys("1526011500"); // 6037210600A

			String enteredcardNumber = cardNumTxtbox.getAttribute("value");

			Thread.sleep(2000);

			WebElement cardHolderNum = driver.findElement(By.xpath("//input[@id='txtCustomerCardholderNumber']"));
			cardHolderNum.sendKeys("1");

			Thread.sleep(2000);

			WebElement finishBtn = driver.findElement(By.xpath("//a[text()='Finish']"));
			finishBtn.click();

			Thread.sleep(8000);

			try {

				WebElement AddressUpdateForm = driver
						.findElement(By.xpath("//h2[contains(text(),'Update Your Address')]"));
				AddressUpdateForm.isDisplayed();

				Thread.sleep(2000);

				String wouldYouLikeToUpdateAddress = "yes";

				if (wouldYouLikeToUpdateAddress.equalsIgnoreCase("yes")) {

					WebElement cardNumber = driver.findElement(By.xpath("//input[@name='CardNumber']"));
					boolean sameCardNumber = cardNumber.equals(enteredcardNumber);
					Thread.sleep(2000);

					WebElement companyName = driver.findElement(By.xpath("//input[@id='CompanyName']"));
					companyName.sendKeys("test@123");
					String campanyNameval = companyName.getAttribute("value");
					System.out.println(campanyNameval);
					Thread.sleep(2000);

					WebElement yourName = driver.findElement(By.xpath("//input[@placeholder='Your Name']"));
					yourName.sendKeys("test");
					String yourNameval = yourName.getAttribute("value");
					System.out.println(yourNameval);
					Thread.sleep(2000);

					WebElement mobileNumber = driver.findElement(By.xpath("//input[@id='MobileNumber']"));
					mobileNumber.sendKeys("9876543209");
					String mobileNumberval = mobileNumber.getAttribute("value");
					System.out.println(mobileNumberval);
					Thread.sleep(2000);

					WebElement emailId = driver.findElement(By.xpath("//input[@name='EmailId']"));
					emailId.sendKeys("test@mailinator.com");
					String emailIdval = emailId.getAttribute("value");
					System.out.println(emailIdval);
					Thread.sleep(2000);

					WebElement addressTxtbox = driver.findElement(By.xpath("//input[@id='Address']"));
					addressTxtbox.sendKeys("test address");
					String addressTxtboxval = addressTxtbox.getAttribute("value");
					System.out.println(addressTxtboxval);
					Thread.sleep(2000);

					WebElement nearestLandmarkTxtbox = driver
							.findElement(By.xpath("//input[@placeholder='Nearest Landmark']"));
					nearestLandmarkTxtbox.sendKeys("test landmark");
					String nearestLandmarkTxtboxval = nearestLandmarkTxtbox.getAttribute("value");
					System.out.println(nearestLandmarkTxtboxval);
					Thread.sleep(2000);

					WebElement cityTxtbox = driver.findElement(By.xpath("//input[@name='City']"));
					cityTxtbox.sendKeys("test city");
					String cityTxtboxval = cityTxtbox.getAttribute("value");
					System.out.println(cityTxtboxval);
					Thread.sleep(2000);

					WebElement pincodeTxtbox = driver.findElement(By.xpath("//input[@name='PinCode']"));
					pincodeTxtbox.sendKeys("234561");
					String pincodeTxtboxval = pincodeTxtbox.getAttribute("value");
					System.out.println(pincodeTxtboxval);
					Thread.sleep(2000);

					WebElement acceptCheckbox = driver.findElement(By.xpath("//input[@id='acceptance']"));
					acceptCheckbox.click();
					Thread.sleep(2000);

					WebElement submitBtn = driver.findElement(By.xpath("//button[@id='submit']"));
					submitBtn.click();

					Thread.sleep(8000);

					String curUrl = driver.getCurrentUrl();
					System.out.println(curUrl);

					if (curUrl.contains("ThankYou")) {

						if ((!campanyNameval.isEmpty()) && (!yourNameval.isEmpty()) && (!mobileNumberval.isEmpty())
								&& (!emailIdval.isEmpty()) && (!addressTxtboxval.isEmpty())
								&& (!nearestLandmarkTxtboxval.isEmpty()) && (!cityTxtboxval.isEmpty())
								&& (!pincodeTxtboxval.isEmpty())) {

							WebElement successMsg = driver.findElement(By.xpath("//div[@class='text-center']"));
							System.out.println(successMsg.getText());

						} else {

							Assert.assertTrue("some Fields are empty but we able to submit the form", false);

						}

					} else {

						if ((campanyNameval.isEmpty()) || (yourNameval.isEmpty()) || (mobileNumberval.isEmpty())
								|| (emailIdval.isEmpty()) || (addressTxtboxval.isEmpty())
								|| (nearestLandmarkTxtboxval.isEmpty()) || (cityTxtboxval.isEmpty())
								|| (pincodeTxtboxval.isEmpty())) {

							System.out.println("Please Fill all Mandatory Fields");

						} else {

							Assert.assertTrue("All Fields are Filled But Form Couldn't able to submit", false);

						}

					}

				}

			} catch (Exception e) {

				try {

					WebElement declarationForm = driver
							.findElement(By.xpath("//button[text()='Sign a Declaration Form']"));

					if (declarationForm.isDisplayed()) {

						String AcceptDeclarationForm = "yes";

						if (AcceptDeclarationForm.equalsIgnoreCase("yes")) {

							declarationForm.click();
							Thread.sleep(2000);

							WebElement cardHolderName = driver.findElement(By.xpath("//input[@name='Name']"));
							cardHolderName.sendKeys("tester");
							String cardHolderNameVal = cardHolderName.getAttribute("value");
							Thread.sleep(2000);

							WebElement cardHolderEmail = driver.findElement(By.xpath("//input[@id='EmailId']"));
							cardHolderEmail.sendKeys("tester001@mailinator.com");
							String cardHolderEmailVal = cardHolderEmail.getAttribute("value");
							Thread.sleep(2000);

							WebElement acceptTCcheckbox = driver
									.findElement(By.xpath("(//input[@name='IsAccepted'])[1]"));
							acceptTCcheckbox.click();
							boolean checkboxEnable = acceptTCcheckbox.isSelected();
							Thread.sleep(2000);

							WebElement submitBtn = driver.findElement(By.xpath("//button[text()='Submit']"));
							submitBtn.click();
							Thread.sleep(5000);

							String curUrl = driver.getCurrentUrl();

							if (curUrl.contains("ThankYou")) {

								if ((!cardHolderNameVal.isEmpty())&&(!cardHolderEmailVal.isEmpty())&&(checkboxEnable==true)) {

									WebElement successMsg = driver.findElement(By.xpath("//div[@class='text-center']"));
									System.out.println(successMsg.getText());

								} else {

									Assert.assertTrue(
											"Mandatory fields are empty shouldn't able to submit the Declaration form successfully",
											false);

									test.log(LogStatus.FAIL,
											"Mandatory fields are empty shouldn't able to submit the Declaration form successfully");
								}

							} else {

								if ((cardHolderNameVal.isEmpty()) || (cardHolderEmailVal.isEmpty())||(checkboxEnable==false)) {

									System.out.println("Please fill All the Mandatory Fields in Declaration form");

								} else {

									Assert.assertTrue(
											"All the Mandatory fields are entered successfully but can't able to submit the form successfully",
											false);

								}

							}

						}

					}

				} catch (Exception e2) {

					System.out.println("User Have Already accept the Declaration Form");

				} finally {

					String UpdateLicenseRenewedForm = "no";

					if (UpdateLicenseRenewedForm.equalsIgnoreCase("yes")) {

						WebElement licenseUpdateBtn = driver
								.findElement(By.xpath("//button[text()='Upload Renewed License']"));
						licenseUpdateBtn.click();

						Thread.sleep(2000);

						WebElement cardNumber = driver.findElement(By.xpath("//input[@name='CardNumber']"));
						boolean sameCardNumber = cardNumber.equals(enteredcardNumber);

						Thread.sleep(2000);

						WebElement companyName = driver.findElement(By.xpath("//input[@id='CompanyName']"));
						companyName.sendKeys("test company");
						String campanyNameval = companyName.getAttribute("value");
						System.out.println(campanyNameval);
						Thread.sleep(2000);

						WebElement yourName = driver.findElement(By.xpath("//input[@placeholder='Your Name']"));
						yourName.sendKeys("tester");
						String yourNameval = yourName.getAttribute("value");
						System.out.println(yourNameval);
						Thread.sleep(2000);

						WebElement mobileNumber = driver.findElement(By.xpath("//input[@id='MobileNumber']"));
						mobileNumber.sendKeys("9876543232");
						String mobileNumberval = mobileNumber.getAttribute("value");
						System.out.println(mobileNumberval);
						Thread.sleep(2000);

						WebElement emailId = driver.findElement(By.xpath("//input[@name='EmailId']"));
						emailId.sendKeys("tester@mailinator.com");
						String emailIdval = emailId.getAttribute("value");
						System.out.println(emailIdval);
						Thread.sleep(2000);

						WebElement addressTxtbox = driver.findElement(By.xpath("//input[@id='Address']"));
						addressTxtbox.sendKeys("tester address");
						String addressTxtboxval = addressTxtbox.getAttribute("value");
						System.out.println(addressTxtboxval);
						Thread.sleep(2000);

						WebElement nearestLandmarkTxtbox = driver
								.findElement(By.xpath("//input[@placeholder='Nearest Landmark']"));
						nearestLandmarkTxtbox.sendKeys("tester landmark");
						String nearestLandmarkTxtboxval = nearestLandmarkTxtbox.getAttribute("value");
						System.out.println(nearestLandmarkTxtboxval);
						Thread.sleep(2000);

						WebElement cityTxtbox = driver.findElement(By.xpath("//input[@name='City']"));
						cityTxtbox.sendKeys("city");
						String cityTxtboxval = cityTxtbox.getAttribute("value");
						System.out.println(cityTxtboxval);
						Thread.sleep(2000);

						WebElement pincodeTxtbox = driver.findElement(By.xpath("//input[@name='PinCode']"));
						pincodeTxtbox.sendKeys("123456");
						String pincodeTxtboxval = pincodeTxtbox.getAttribute("value");
						System.out.println(pincodeTxtboxval);
						Thread.sleep(2000);

						WebElement licenseTypedropDown = driver
								.findElement(By.xpath("//select[@id='BusinessLicenseType']"));
						Select s = new Select(licenseTypedropDown);
						s.selectByIndex(2);
						String licenseTypedropDownval = licenseTypedropDown.getAttribute("value");
						System.out.println(licenseTypedropDownval);
						Thread.sleep(2000);

						WebElement licenseNuberTxtbox = driver
								.findElement(By.xpath("//input[@name='BusinessLicenseNumber']"));
						licenseNuberTxtbox.sendKeys(""); // 
						String licenseNuberTxtboxval = licenseNuberTxtbox.getAttribute("value");
						System.out.println(licenseNuberTxtboxval);
						Thread.sleep(2000);

						WebElement licenseIssueDate = driver.findElement(By.xpath("//input[@name='LicenseIssueDate']"));
						licenseIssueDate.sendKeys("05-01-2023");
						String licenseIssueDateval = licenseIssueDate.getAttribute("value");
						System.out.println(licenseIssueDateval);
						Thread.sleep(2000);

						WebElement licenseValidityDate = driver
								.findElement(By.xpath("//input[@name='BusinessLicenseValidityDate']"));
						licenseValidityDate.sendKeys("12-8-2023");
						String licenseValidityDateval = licenseValidityDate.getAttribute("value");
						System.out.println(licenseValidityDateval);
						Thread.sleep(2000);

						WebElement chooseFile = driver.findElement(By.xpath("//input[@id='File']"));
						js = (JavascriptExecutor) driver;
						js.executeScript("arguments[0].click()", chooseFile);
						Thread.sleep(8000);
						String chooseFileval = chooseFile.getAttribute("value");
						System.out.println(chooseFileval);

						WebElement submitBtn = driver.findElement(By.xpath("//button[text()='Submit Details']"));
						submitBtn.click();

						Thread.sleep(5000);

						String curUrl = driver.getCurrentUrl();

						if (curUrl.contains("ThankYou")) {

							if ((!campanyNameval.isEmpty()) && (!yourNameval.isEmpty()) && (!mobileNumberval.isEmpty())
									&& (!emailIdval.isEmpty()) && (!addressTxtboxval.isEmpty())
									&& (!nearestLandmarkTxtboxval.isEmpty()) && (!cityTxtboxval.isEmpty())
									&& (!pincodeTxtboxval.isEmpty()) && (!licenseTypedropDownval.isEmpty())
									&& (!licenseNuberTxtboxval.isEmpty()) && (!licenseIssueDateval.isEmpty())
									&& (!licenseValidityDateval.isEmpty()) && (!chooseFileval.isEmpty())) {

								WebElement successMsg = driver.findElement(By.xpath("//div[@class='text-center']"));
								System.out.println(successMsg.getText());

							} else {

								Assert.assertTrue("some Fields are empty but we able to submit the form", false);
							}

						} else {

							if ((campanyNameval.isEmpty()) || (yourNameval.isEmpty()) || (mobileNumberval.isEmpty())
									|| (emailIdval.isEmpty()) || (addressTxtboxval.isEmpty())
									|| (nearestLandmarkTxtboxval.isEmpty()) || (cityTxtboxval.isEmpty())
									|| (pincodeTxtboxval.isEmpty()) || (licenseTypedropDownval.isEmpty())
									|| (licenseNuberTxtboxval.isEmpty()) || (licenseIssueDateval.isEmpty())
									|| (licenseValidityDateval.isEmpty()) || (chooseFileval.isEmpty())) {

								System.out.println("Please Fill all Mandatory Fields");

							} else {

								Assert.assertTrue("All Fields are Filled But Form Couldn't able to submit", false);

							}

						}

					}

				}

			}

		}

	}

}
