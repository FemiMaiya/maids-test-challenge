package com.maids.test.librarymanagementsystem.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
@RequiredArgsConstructor
public class HelperUtil {

    public String getDate() {

        log.info("GETTING DATE....");
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate currentDate = LocalDate.now();

        String date = currentDate.format(myFormatObj);

        log.info("Date returned: " + date);
        return date;
    }

    public String getTime() {

        log.info("GETTING TIME....");
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime currentTime = LocalTime.now();

        String time = currentTime.format(myFormatObj);

        log.info("Time returned: " + time);
        return time;
    }
}
