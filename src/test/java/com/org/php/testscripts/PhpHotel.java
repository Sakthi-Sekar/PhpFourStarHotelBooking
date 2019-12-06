package com.org.php.testscripts;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.org.php.constants.ConstantPaths;
import com.org.php.helpers.WaitHelperClass;
import com.org.php.testbase.BaseClass;
import com.org.php.utils.ExcelLoadData;
import com.org.php.utils.NullCellValueException;

public class PhpHotel extends BaseClass {

	PhpHotelValidation validate;

	Properties userProperty;
	String actualURL, expectedURL, actualText, detail, destination, actualHotelName, expectedHotelName, actualAddress,
			expectedAddress, actualDesignation, expectedDesignation, actualCheckinDate, expectedCheckinDate,
			actualCheckoutDate, expectedCheckoutDate, actualAdultCount, expectedAdultCount, actualChildCount,
			expectedChildCount;

	@Test
	public void identifyFourStarHotel() throws Exception, NullCellValueException {
		property = pageManager.loadpropertyFile(ConstantPaths.LOCATORS_FILE);
		userProperty = pageManager.loadpropertyFile(ConstantPaths.CONFIG_FILE);
		validate = new PhpHotelValidation();
		// Enter destination

		/*
		 * driver.switchTo().frame("chat-widget"); log.info("Moved to the iframe");
		 * helper.getElement(driver, property, "loc.chatnow").click();
		 * log.info("Clicked on the Chat now"); //Thread.sleep(3000); //
		 * helper.isElementVisible(driver, property, "loc.minimize");
		 * helper.getElement(driver, property, "loc.minimize").click();
		 * log.info("Minimized"); driver.switchTo().parentFrame();
		 * log.info("Back to parent frame");
		 */
		// WaitHelperClass wait = new WaitHelperClass();
		Actions action = new Actions(driver);

		destination = id.getCellData(ConstantPaths.PHPDATA_FILE, "PhpTestData", "Destination", "TC_01");
		helper.getElement(driver, property, "loc.destination").sendKeys(destination);
		WaitHelperClass.waitForElement();
		action.sendKeys(Keys.ENTER).build().perform();

		// Today Date
		LocalDate today = LocalDate.now();
		log.info("Today is : " + today);

		// Check in date
		String Date01 = id.getCellData(ConstantPaths.PHPDATA_FILE, "PhpTestData", "CheckInDays", "TC_01");
		int datenumber1 = Integer.parseInt(Date01);
		LocalDate checkinDate = today.plusDays(datenumber1);
		log.info("after 10 days : " + checkinDate);

		helper.getElement(driver, property, "loc.checkin").click();
		helper.getElement(driver, property, "loc.checkin").sendKeys(checkinDate + "");

		// checkout date
		String Date02 = id.getCellData(ConstantPaths.PHPDATA_FILE, "PhpTestData", "CheckOutDays", "TC_01");

		int datenumber2 = Integer.parseInt(Date02);
		LocalDate checkoutDate = checkinDate.plusDays(datenumber2);
		log.info("Checkout date is:" + checkoutDate);

		// Passing the checkout date in checkout date field
		helper.getElement(driver, property, "loc.checkout").sendKeys(checkoutDate + "");
		log.info("Checkout date is entered as:" + checkoutDate);

		// Increase adults and child counts
		for (int count = 0; count <= 1; count++) {
			helper.getElement(driver, property, "loc.buttonincrease").click();
			helper.getElement(driver, property, "loc.button.child").click();

		}
		log.info("Adults count is 4 and Child count is 2 now");

		// click on search
		helper.getElement(driver, property, "loc.search").click();
		log.info("Searching...");

		// click on view more
		helper.getElement(driver, property, "loc.gotit").click();
		log.info("clicked on Got It");
		for (int viewcount = 0; viewcount < 4; viewcount++) {

			WebElement viewmore = helper.getElement(driver, property, "loc.view");
			pageManager.scrollAndView(driver, viewmore);
			viewmore.click();

		}
		log.info("Click to view more");

		// No.of four star rating hotel prices
		helper.getElements(driver, property, "fourstarprice");
		List<WebElement> list = helper.getElements(driver, property, "fourstarprice");
		System.out.println(list.size());

		WebElement[] priceListArray = new WebElement[list.size()];

		String[] NoOfPrices = new String[list.size()];
		double[] Prices = new double[list.size()];
		priceListArray = list.toArray(priceListArray);

		int removeUSD = 0;

		for (WebElement webelement : priceListArray) {
			NoOfPrices[removeUSD] = webelement.getText();
			Prices[removeUSD] = Double.parseDouble(NoOfPrices[removeUSD].replace("USD ", ""));
			removeUSD++;
		}
		log.info(Arrays.toString(Prices));

		double[] pricesAftersort = new double[list.size()];
		for (int index = 0; index < Prices.length; index++) {
			pricesAftersort[index] = Prices[index];
		}
		Arrays.sort(pricesAftersort);
		log.info(Arrays.toString(NoOfPrices));
		log.info(Arrays.toString(pricesAftersort));
		System.out.println(pricesAftersort[0]);
		int finalIndex = 0;
		for (int index = 0; index < Prices.length; index++) {

			if (Double.toString(Prices[index]) == Double.toString(pricesAftersort[0])) {
				finalIndex = index;

			}
		}

		detail = property.getProperty("details").replace("xxx", finalIndex + 1 + "");
		WebElement detailsbutton = helper.getElement(driver, detail);
		pageManager.scrollAndView(driver, detailsbutton);
		detailsbutton.click();

		// Validating the details of four star rating hotel

		validate.phpHotelDetailsValidation();
	}

}
