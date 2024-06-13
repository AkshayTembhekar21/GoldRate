package com.gold.goldrate.controller.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gold.goldrate.entity.GoldRate;
import com.gold.goldrate.repo.GoldRateRepository;

@Service
public class GoldRateService {

    @Autowired
    private GoldRateRepository goldRateRepository;

    public void updateGoldRate() {
        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = "https://api.metalpriceapi.com/v1/latest?api_key=02ce92dc7c12ccb61f75be019555e3e8&base=USD&currencies=EUR";
        String jsonResponse = restTemplate.getForObject(apiUrl, String.class);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonResponse);

            String base = jsonNode.get("base").asText();
            Long timestamp = jsonNode.get("timestamp").asLong();
            Double rate = jsonNode.get("rates").get("EUR").asDouble(); // Adjust according to your JSON structure

            GoldRate existingGoldRate = goldRateRepository.findByTimestamp(timestamp);
            if (existingGoldRate != null) {
                if (!existingGoldRate.getRate().equals(rate)) {
                    // Update the existing record with the new rate
                    existingGoldRate.setRate(rate);
                    goldRateRepository.save(existingGoldRate);
                    System.out.println("Existing record updated with new rate.");
                } else {
                    System.out.println("Rate hasn't changed. No update performed.");
                }
            } else {
                // Create a new record
                GoldRate newGoldRate = new GoldRate(base, timestamp, rate);
                goldRateRepository.save(newGoldRate);
                System.out.println("New record created.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Handle JSON processing exception
        }
    }
}