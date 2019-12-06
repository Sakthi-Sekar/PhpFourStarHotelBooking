package com.org.php.testscripts;

import java.io.IOException;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.org.php.constants.ConstantPaths;
import com.org.php.testbase.BaseClass;
import com.org.php.utils.ColumnNameWriter;
import com.org.php.utils.ExcelLoadData;
import com.org.php.utils.ExcelWriteData;

public class HotelIdentifier extends BaseClass {
	Properties userProperty;
	String actualURL, expectedURL, actualText, testCity;
	String expectedText;

	@Test
	public void identifyFourStarHotel() throws Exception {
		property = pageManager.loadpropertyFile(ConstantPaths.LOCATORS_FILE);
		userProperty = pageManager.loadpropertyFile(ConstantPaths.CONFIG_FILE);
		ExcelLoadData load = new ExcelLoadData(ConstantPaths.PHPTESTDATA_FILE);
		ExcelWriteData write = new ExcelWriteData(ConstantPaths.PHPTESTDATA_FILE);
		ColumnNameWriter xlwriter = new ColumnNameWriter();
		// xlwriter.writeColumnName(ConstantPaths.PHPTESTDATA_FILE, "checkindate",
		// "phpdata", 2);

		// HomePage validation
		actualURL = driver.getCurrentUrl();
		expectedURL = userProperty.getProperty("url");
		Assert.assertEquals(actualURL, expectedURL, "URL doesn't match");
		log.info("Url is matched :" + actualURL);

		// click on hotels
		helper.getElement(driver, property, "loc.hotel").click();

		// Enter destination

		driver.switchTo().frame("chat-widget");
		log.info("Moved to the iframe");
		helper.getElement(driver, property, "loc.chatnow").click();
		log.info("Clicked on the Chat now");
		Thread.sleep(3000);
		// helper.isElementVisible(driver, property, "loc.minimize");
		helper.getElement(driver, property, "loc.minimize").click();
		log.info("Minimized");
		driver.switchTo().parentFrame();
		log.info("Back to parent frame");

		Actions action = new Actions(driver);
		WebElement element = helper.getElement(driver, property, "loc.destination");
		testCity = load.getCellData("phpdata", "Testdata", 2);
		helper.getElement(driver, property, "loc.destination").sendKeys(testCity);
		Thread.sleep(2000);
		action.sendKeys(Keys.ENTER).build().perform();
		element.click();
		//action.sendKeys(Keys.TAB).build().perform();

//		testCity = load.getCellData("phpdata", "Testdata", 2);
//		helper.getElement(driver, property, "loc.destination").sendKeys(testCity);
//		helper.getElement(driver, property, "loc.clickcity").click();
//		log.info("City is entered as Bangalore in the destination");

		// Today Date
		LocalDate today = LocalDate.now();
		log.info("Today is : " + today);

		// Check in date
		String date1 = load.getCellData("phpdata", "No.of.days", 2);
		int datenumber1 = Integer.parseInt(date1);
		LocalDate checkinDate = today.plusDays(datenumber1);
		log.info("after 10 days : " + checkinDate);

		// Store the check in date in excel
		helper.getElement(driver, property, "loc.checkin").click();
		helper.getElement(driver, property, "loc.checkin").sendKeys(checkinDate + "");
		log.info("Passed the checkin date as:" + checkinDate);
		write.setCellData("phpdata", "checkindate", 2, checkinDate.toString());
		log.info("Checkin date is stored in excel");

		// Checkout date
		String date2 = load.getCellData("phpdata", "No.of.days", 3);
		int datenumber2 = Integer.parseInt(date2);
		LocalDate checkoutDate = checkinDate.plusDays(datenumber2);
		log.info("Checkout date is:" + checkoutDate);

		// Passing the checkout date in checkout date field
		helper.getElement(driver, property, "loc.checkout").sendKeys(checkoutDate + "");
		log.info("Checkout date is entered as:" + checkoutDate);

		// Increase adults and child counts
		for (int i = 0; i <= 1; i++) {
			helper.getElement(driver, property, "loc.buttonincrease").click();
			helper.getElement(driver, property, "loc.button.child").click();

		}
		log.info("Adults count is 4 and Child count is 2 now");

		// click on search
		helper.getElement(driver, property, "loc.search").click();

		// verify the redirected page
		actualText = helper.getElement(driver, property, "loc.redirect.text").getText();
		expectedText = load.getCellData("phpdata", "ExpText", 2);
		Assert.assertEquals(actualText, expectedText, "No text is matching");
		log.info("The user redirected into a correct page");

		// Click on modify button
		helper.getElement(driver, property, "loc.modify").click();

		// Click on view more for all 4star ratings
		pageManager.scrollAndView(driver, helper.getElement(driver, property, "click.viewmore"));
		helper.isElementVisible(driver, property, "click.viewmore").click();

		List<WebElement> list = helper.getElements(driver, "click.viewmore");
		for (int i = 0; i < list.size(); i++) {
			{
				pageManager.scrollAndView(driver, helper.getElement(driver, property, "click.viewmore"));
				list.get(i).click();
				break;

			}
		}

		// Click on 4star hotel
		helper.getElements(driver, "allfourstar");
		List<WebElement> li = helper.getElements(driver, "allfourstar");
		for (int i = 0; i < li.size(); i++) {
			if (i == 1)
				li.get(i).click();
			break;
		}
		helper.getElement(driver, property, "loc.details").click();
		log.info("Click on details of lowest price hotel");

	}
}