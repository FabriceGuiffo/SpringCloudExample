package com.example.microservicearchitecture.Controller;

import com.example.microservicearchitecture.ConfigDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PropController {
    @Autowired
    private ConfigDetails configDetails;

    @GetMapping("/chiffre")
    public String showProperty(){
        return "La valeur de la propriete est: "+configDetails.getCompteur();
    }
}
