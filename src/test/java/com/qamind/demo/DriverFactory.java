package com.qamind.demo;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public final class DriverFactory {
  private DriverFactory() {}

  public static WebDriver create() {
    WebDriverManager.chromedriver().setup();
    ChromeOptions options = new ChromeOptions();
    boolean headless =
        !"false".equalsIgnoreCase(System.getProperty("headless", "true"))
            && !"0".equals(System.getenv("HEADLESS"));
    if (headless) {
      options.addArguments("--headless=new");
    }
    options.addArguments("--window-size=1280,800", "--disable-gpu", "--no-sandbox");
    return new ChromeDriver(options);
  }
}
