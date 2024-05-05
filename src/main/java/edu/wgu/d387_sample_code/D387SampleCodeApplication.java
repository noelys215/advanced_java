package edu.wgu.d387_sample_code;

import edu.wgu.d387_sample_code.service.WelcomeService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Locale;

@SpringBootApplication
public class D387SampleCodeApplication {

    public static void main(String[] args) {
        SpringApplication.run(D387SampleCodeApplication.class, args);

        WelcomeService welcomeService = new WelcomeService();

        /* Retrieve messages in English or French */
        String englishMessage = welcomeService.getWelcomeMessage(Locale.ENGLISH);
        String frenchMessage = welcomeService.getWelcomeMessage(Locale.FRANCE);

        /* Array to store messages */
        String[] messagesArray = {englishMessage, frenchMessage};

        /* Array for Front End */
        for (String message : messagesArray) {
            System.out.println("Welcome Message: " + message);
        }
    }
}
