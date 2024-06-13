package com.gold.goldrate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gold.goldrate.controller.service.GoldRateService;

@RestController
public class GoldRateController {

    @Autowired
    private GoldRateService goldRateService;

    @GetMapping("/update-gold-rate")
    @CrossOrigin(origins = "http://localhost:3000")
    public String updateGoldRate() {
        goldRateService.updateGoldRate();
        return "Gold rate updated successfully!";
    }
}
