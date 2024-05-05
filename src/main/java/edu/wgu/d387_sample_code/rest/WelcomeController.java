package edu.wgu.d387_sample_code.rest;

import edu.wgu.d387_sample_code.service.WelcomeService;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PreDestroy;
import java.util.Locale;
import java.util.concurrent.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
@Log
public class WelcomeController {
    private final ExecutorService executorService;
    private final WelcomeService welcomeService;

    public WelcomeController() {
        this.executorService = Executors.newFixedThreadPool(2);
        this.welcomeService = new WelcomeService();
    }

    /* Welcome Endpoint */
    @GetMapping("/welcome")
    public String[] getWelcomeMessages() {
        Future<String> englishFuture = executorService.submit(() -> welcomeService.getWelcomeMessage(Locale.ENGLISH));
        Future<String> frenchFuture = executorService.submit(() -> welcomeService.getWelcomeMessage(Locale.FRANCE));

        String[] messagesArray = new String[2];

        try {
            messagesArray[0] = englishFuture.get();
            messagesArray[1] = frenchFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            log.severe("Error retrieving welcome messages: " + e.getMessage());
            messagesArray[0] = "Error retrieving English message";
            messagesArray[1] = "Error retrieving French message";
        }
        return messagesArray;
    }

    /* Cleanup/Shutdown Method */
    @PreDestroy
    public void shutDownExecutorService() {
        log.info("Shutting down ExecutorService");
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            log.severe("Error during ExecutorService shutdown: " + e.getMessage());
            executorService.shutdownNow();
        }
    }
}
