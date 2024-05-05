package edu.wgu.d387_sample_code.rest;

import edu.wgu.d387_sample_code.service.TimeConversionService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class TimeController {
    private final TimeConversionService timeConversionService = new TimeConversionService();

    @GetMapping("/presentation-time")
    public String[] getPresentationTimes() {
        ZonedDateTime now = ZonedDateTime.now();
        return timeConversionService.convertToTimeZones(now);
    }
}

