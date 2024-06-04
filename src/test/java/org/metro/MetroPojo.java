package org.metro;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MetroPojo extends Base {


	/**
	 * @return the nameBoardFieldPom
	 */
	public WebElement getNameBoardFieldPom() {
		return NameBoardFieldPom;
	}

	/**
	 * @return the otpTxtboxPom
	 */
	public WebElement getOtpTxtboxPom() {
		return otpTxtboxPom;
	}

	/**
	 * @return the submitOtpErrMsgPom
	 */
	public WebElement getSubmitOtpErrMsgPom() {
		return submitOtpErrMsgPom;
	}

	/**
	 * @return the whatsappNumbertxtbox
	 */
	public WebElement getWhatsappNumbertxtbox() {
		return whatsappNumbertxtbox;
	}

	public MetroPojo() {
		PageFactory.initElements(driver,this);
	}
	
	@FindBy(xpath="(//input[@class='file-upload-input'])[4]")
	private WebElement NameBoardFieldPom;
	
	@FindBy(xpath="//input[@name='mainOTP']")
	private WebElement otpTxtboxPom;
	
	@FindBy(xpath="//p[text()='Please enter valid OTP.']")
	private WebElement submitOtpErrMsgPom;
	
	@FindBy(xpath="//input[@name='applicantWhatsApp']")
	private WebElement whatsappNumbertxtbox;

	
	
	
}
