package com.qamind.demo.pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Works for both demo apps:
 * - SauceDemo (staging): #user-name / #password / #login-button
 * - the-internet (production): #username / #password / button[type=submit]
 */
public class LoginPage {
  private final WebDriver driver;
  private final WebDriverWait wait;

  public LoginPage(WebDriver driver) {
    this.driver = driver;
    this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
  }

  public void open(String url) {
    driver.get(url);
  }

  public void login(String username, String password) {
    By user = firstPresent(By.id("user-name"), By.id("username"));
    By pass = By.id("password");
    By submit = firstPresent(By.id("login-button"), By.cssSelector("button[type='submit']"));

    wait.until(ExpectedConditions.visibilityOfElementLocated(user)).clear();
    driver.findElement(user).sendKeys(username);
    driver.findElement(pass).clear();
    driver.findElement(pass).sendKeys(password);
    driver.findElement(submit).click();
  }

  public boolean isLoggedIn() {
    return driver.getCurrentUrl().contains("inventory")
        || driver.getCurrentUrl().contains("/secure")
        || driver.getPageSource().toLowerCase().contains("logout");
  }

  private By firstPresent(By primary, By fallback) {
    if (!driver.findElements(primary).isEmpty()) return primary;
    return fallback;
  }
}
