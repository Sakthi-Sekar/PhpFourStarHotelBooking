package com.org.php.helpers;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class IframeHandle {
	public PageActions helper = new PageActions();

	/**
	 * in this method i'm handling the iframe
	 * 
	 * @param driver
	 * @return
	 */

	public WebElement handleIframe(WebDriver driver) throws Exception {
		try {
			List<WebElement> elements = driver.findElements(By.tagName("iframe"));

			int numberOfTags = elements.size();

			driver.switchTo().defaultContent();
		} catch (

		Exception e) {

		}
		return null;

	}
}
