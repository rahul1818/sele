package com.qamind.demo;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/** Loads Staging or Production properties from src/test/resources/env/. */
public final class EnvConfig {
  private final Properties props = new Properties();
  private final String envName;

  public EnvConfig() {
    envName = System.getProperty("env", "staging").trim().toLowerCase();
    String path = "env/" + envName + ".properties";
    try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(path)) {
      if (in == null) {
        throw new IllegalStateException(
            "Missing env file: " + path + " (pass -Denv=staging or -Denv=production)");
      }
      props.load(in);
    } catch (IOException e) {
      throw new IllegalStateException("Could not load " + path, e);
    }
  }

  public String envName() {
    return props.getProperty("env.name", envName);
  }

  public String baseUrl() {
    return required("base.url");
  }

  public String username() {
    return required("username");
  }

  public String password() {
    return required("password");
  }

  public String expectedTitle() {
    return required("expected.title");
  }

  private String required(String key) {
    String value = props.getProperty(key);
    if (value == null || value.isBlank()) {
      throw new IllegalStateException("Missing property '" + key + "' for env=" + envName);
    }
    return value.trim();
  }
}
