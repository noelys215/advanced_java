package edu.wgu.d387_sample_code.service;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/* Retrieve Locale Messages */
public class WelcomeService {
    public String getWelcomeMessage(String language, String country) {
        Locale locale = new Locale(language, country);

        try {
            ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);
            String message = bundle.getString("welcome");
            System.out.println("Retrieved message: " + message + " for locale: " + locale);
            return message;
        } catch (MissingResourceException e) {
            System.err.println("Missing resource for locale " + locale + ": " + e.getMessage());
            return "Error: Missing message for locale " + locale;
        }
    }
}


