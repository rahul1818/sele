package com.qamind.demo.tests;

import com.qamind.demo.DriverFactory;
import com.qamind.demo.EnvConfig;
import com.qamind.demo.ScreenshotSupport;
import com.qamind.demo.pages.LoginPage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginSmokeTest {
  private WebDriver driver;
  private EnvConfig env;

  @BeforeMethod
  public void setUp() {
    env = new EnvConfig();
    driver = DriverFactory.create();
  }

  @AfterMethod(alwaysRun = true)
  public void tearDown(ITestResult result) {
    try {
      if (driver != null) {
        // Always capture — Hub Evidence matches Playwright HTML report screenshots.
        ScreenshotSupport.capture(driver, result.getMethod().getMethodName());
      }
    } finally {
      if (driver != null) {
        driver.quit();
      }
    }
  }

  @Test(description = "Login smoke for the selected environment")
  public void userCanLogin() {
    LoginPage page = new LoginPage(driver);
    page.open(env.baseUrl());

    Assert.assertTrue(
        driver.getTitle().equalsIgnoreCase(env.expectedTitle())
            || driver
                .getTitle()
                .toLowerCase()
                .contains(env.expectedTitle().toLowerCase().split(" ")[0])
            || driver.getPageSource().toLowerCase().contains("login"),
        "Unexpected title for env=" + env.envName() + ": " + driver.getTitle());

    page.login(env.username(), env.password());

    Assert.assertTrue(
        page.isLoggedIn(),
        "Login failed for env=" + env.envName() + " url=" + driver.getCurrentUrl());
  }
}
