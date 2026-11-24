package com.qamind.demo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

/**
 * Saves a PNG into target/surefire-reports/screenshots so the GitHub Actions
 * artifact (and QAMind Hub) can show Evidence like a Playwright HTML report.
 */
public final class ScreenshotSupport {
  private ScreenshotSupport() {}

  public static Path capture(WebDriver driver, String testMethodName) {
    if (!(driver instanceof TakesScreenshot)) {
      throw new IllegalStateException("Driver cannot take screenshots.");
    }
    Path dir = Paths.get("target", "surefire-reports", "screenshots");
    try {
      Files.createDirectories(dir);
      Path file = dir.resolve(sanitize(testMethodName) + ".png");
      byte[] bytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
      Files.write(file, bytes);
      return file;
    } catch (IOException e) {
      throw new IllegalStateException("Could not save screenshot for " + testMethodName, e);
    }
  }

  private static String sanitize(String name) {
    String cleaned = String.valueOf(name).replaceAll("[^A-Za-z0-9._-]+", "_");
    return cleaned.isBlank() ? "screenshot" : cleaned;
  }
}
