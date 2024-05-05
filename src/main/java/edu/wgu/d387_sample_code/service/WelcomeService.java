package edu.wgu.d387_sample_code.service;

import java.util.Locale;
import java.util.ResourceBundle;

/* Retrieve Locale Messages */
public class WelcomeService {
    public String getWelcomeMessage(Locale locale) {
        ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);
        return bundle.getString("welcome");
    }
}


