package com.org.php.testscripts;

import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.org.php.constants.ConstantPaths;
import com.org.php.helpers.GetExcelDataById;
import com.org.php.helpers.PageActions;
import com.org.php.helpers.PageManager;
import com.org.php.helpers.WaitHelperClass;
import com.org.php.helpers.WriteExcelOperation;
import com.org.php.reports.LogReports;
import com.org.php.utils.NullCellValueException;

public class PhpHotelValidation extends PhpHotel {

	public PageActions helper = new PageActions();
	public LogReports log = new LogReports();
	public WebDriver driver;
	public Properties property;
	public PageManager pageManager = new PageManager();
	public WaitHelperClass wait = new WaitHelperClass();
	public GetExcelDataById id = new GetExcelDataById();
	public WriteExcelOperation write = new WriteExcelOperation();

	Properties userProperty;
	String actualURL, expectedURL, actualText, detail, destination, actualHotelName, expectedHotelName, actualAddress,
			expectedAddress, actualDesignation, expectedDesignation, actualCheckinDate, expectedCheckinDate,
			actualCheckoutDate, expectedCheckoutDate, actualAdultCount, expectedAdultCount, actualChildCount,
			expectedChildCount;

	public void phpHotelDetailsValidation() throws Exception, NullCellValueException {
		property = pageManager.loadpropertyFile(ConstantPaths.LOCATORS_FILE);
		userProperty = pageManager.loadpropertyFile(ConstantPaths.CONFIG_FILE);

		// int datenumber1 = Integer.parseInt(Date01);

		// HotelName
		actualHotelName = helper.getElement(driver, property, "loc.hotelname").getAttribute("value");
		System.out.println(actualHotelName);
		expectedHotelName = id.getCellData(ConstantPaths.PHPDATA_FILE, "PhpTestData", "ExpectedHotelName", "TC_01");
		System.out.println(expectedHotelName);
		Assert.assertEquals(actualHotelName, expectedHotelName, "No match found");
		log.info("Hotel name matches");

		// Address
		actualAddress = helper.getElement(driver, property, "loc.address").getAttribute("value");
		expectedAddress = id.getCellData(ConstantPaths.PHPDATA_FILE, "PhpTestData", "ExpectedAddress", "TC_01");
		Assert.assertEquals(actualAddress, expectedAddress, "No match found");
		log.info("Address matches");

		// Destination
		actualAddress = helper.getElement(driver, property, "loc.destination.verify").getAttribute("value");
		expectedAddress = id.getCellData(ConstantPaths.PHPDATA_FILE, "PhpTestData", "ExpectedDestinaton", "TC_01");
		Assert.assertEquals(actualAddress, expectedAddress, "No match found");
		log.info("Destination matches");

		// CheckIn
		actualCheckinDate = helper.getElement(driver, property, "loc.checkin.verify").getAttribute("value");
		expectedCheckinDate = id.getCellData(ConstantPaths.PHPDATA_FILE, "PhpTestData", "ExpCheckIn", "TC_01");
		int ExpCheckInDate = Integer.parseInt(expectedCheckinDate);
		Assert.assertEquals(actualCheckinDate, ExpCheckInDate, "No match found");
		log.info("CheckIn Date matches");

		// CheckOutDate
		actualCheckoutDate = helper.getElement(driver, property, "loc.checkout.verify").getAttribute("value");
		expectedCheckoutDate = id.getCellData(ConstantPaths.PHPDATA_FILE, "PhpTestData", "ExpCheckOut", "TC_01");
		int ExpCheckOutDate = Integer.parseInt(expectedCheckoutDate);
		Assert.assertEquals(actualCheckoutDate, ExpCheckOutDate, "No match found");
		log.info("CheckOut Date matches");

		// Adults
		actualAdultCount = helper.getElement(driver, property, "loc.adult.verify").getAttribute("value");
		expectedAdultCount = id.getCellData(ConstantPaths.PHPDATA_FILE, "PhpTestData", "ExpAdults", "TC_01");
		int ExpAdultCount = Integer.parseInt(expectedAdultCount);
		Assert.assertEquals(actualAdultCount, ExpAdultCount, "No match found");
		log.info("No. of Adult counts matches");

		// Child
		actualChildCount = helper.getElement(driver, property, "loc.child.verify").getAttribute("value");
		log.info("actualChildCount");
		expectedChildCount = id.getCellData(ConstantPaths.PHPDATA_FILE, "PhpTestData", "ExpChild", "TC_01");
		int ExpChildCount = Integer.parseInt(expectedChildCount);
		Assert.assertEquals(actualChildCount, ExpChildCount, "No match found");
		log.info("No. of Child count matches");
	}

}
