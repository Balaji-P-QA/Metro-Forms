package org.metro;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Base {

	public static WebDriver driver;

		public void launch() {
			
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();

		}
	
}
