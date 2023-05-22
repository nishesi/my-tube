package ru.itis.nishesi.MyTube.restcontrollers;

import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.DateTimeException;
import java.time.ZoneId;

@Log4j2
@RestController
@RequestMapping("/api/timezone")
public class UserInfController {
    @PostMapping
    public ResponseEntity<?> setTimeZone(@RequestParam("timezone") String timezone, HttpSession session) {
        try {
            ZoneId zoneId = ZoneId.of(timezone);
            session.setAttribute("zoneId", zoneId);

            return ResponseEntity.accepted().build();
        } catch (DateTimeException ex) {
            if (log.isWarnEnabled())
                log.warn("Time zone can not be parsed:" + timezone);
            return ResponseEntity.unprocessableEntity().build();
        }
    }
}