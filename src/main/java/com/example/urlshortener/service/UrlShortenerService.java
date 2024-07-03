package com.example.urlshortener.service;

import com.example.urlshortener.model.UrlMapping;
import com.example.urlshortener.repository.UrlMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UrlShortenerService {

    @Autowired
    private UrlMappingRepository urlMappingRepository;

    public String shortenUrl(String longUrl) {
        String shortUrl = generateShortUrl();
        
        UrlMapping urlMapping = new UrlMapping();
        urlMapping.setShortUrl(shortUrl);
        urlMapping.setDestinationUrl(longUrl);
        
        urlMappingRepository.save(urlMapping);
        
        return shortUrl;
    }

    public String getDestinationUrl(String shortUrl) {
        UrlMapping urlMapping = urlMappingRepository.findByShortUrl(shortUrl);
        if (urlMapping != null) {
            return urlMapping.getDestinationUrl();
        } else {
            throw new RuntimeException("Short URL not found: " + shortUrl);
        }
    }

    public boolean updateDestinationUrl(String shortUrl, String newDestinationUrl) {
        UrlMapping urlMapping = urlMappingRepository.findByShortUrl(shortUrl);
        if (urlMapping != null) {
            urlMapping.setDestinationUrl(newDestinationUrl);
            urlMappingRepository.save(urlMapping);
            return true;
        } else {
            return false;
        }
    }

    public boolean updateExpiry(String shortUrl, int daysToAdd) {
        UrlMapping urlMapping = urlMappingRepository.findByShortUrl(shortUrl);
        if (urlMapping != null) {
            LocalDateTime currentExpiry = urlMapping.getExpiresAt();
            LocalDateTime newExpiry = currentExpiry != null ? currentExpiry.plusDays(daysToAdd) : LocalDateTime.now().plusDays(daysToAdd);
            urlMapping.setExpiresAt(newExpiry);
            urlMappingRepository.save(urlMapping);
            return true;
        } else {
            return false;
        }
    }

    private String generateShortUrl() {
        // Generate short URL logic (for example, using base62 encoding)
        // Implement your logic here
        return "http://localhost:8080/" + generateRandomString(8);
    }

    private String generateRandomString(int length) {
        // Generate random alphanumeric string
        // Implement your logic here
        return "abcdef1234"; // Dummy implementation
    }
}
