package com.example.urlshortener.controller;

import com.example.urlshortener.service.UrlShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/url")
public class UrlShortenerController {

    @Autowired
    private UrlShortenerService urlShortenerService;

    @PostMapping("/shorten")
    public ResponseEntity<Map<String, String>> shortenUrl(@RequestBody Map<String, String> requestBody) {
        String longUrl = requestBody.get("longUrl");
        String shortUrl = urlShortenerService.shortenUrl(longUrl);
        
        Map<String, String> response = new HashMap<>();
        response.put("shortUrl", shortUrl);
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{shortUrl}")
    public void redirectToFullUrl(HttpServletResponse response, @PathVariable String shortUrl) throws IOException {
        String destinationUrl = urlShortenerService.getDestinationUrl(shortUrl);
        response.sendRedirect(destinationUrl);
    }

    @PostMapping("/update")
    public ResponseEntity<Map<String, Boolean>> updateDestinationUrl(@RequestBody Map<String, String> requestBody) {
        String shortUrl = requestBody.get("shortUrl");
        String newDestinationUrl = requestBody.get("newDestinationUrl");
        boolean success = urlShortenerService.updateDestinationUrl(shortUrl, newDestinationUrl);
        Map<String, Boolean> response = new HashMap<>();
        response.put("success", success);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/updateExpiry")
    public ResponseEntity<Map<String, Boolean>> updateExpiry(@RequestBody Map<String, Object> requestBody) {
        String shortUrl = (String) requestBody.get("shortUrl");
        int daysToAdd = (int) requestBody.get("daysToAdd");
        boolean success = urlShortenerService.updateExpiry(shortUrl, daysToAdd);
        Map<String, Boolean> response = new HashMap<>();
        response.put("success", success);
        return ResponseEntity.ok(response);
    }
}
