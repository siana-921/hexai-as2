package sua.assignment.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import sua.assignment.model.AirportDTO;
import org.springframework.beans.factory.annotation.Autowired;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class SearchController {
    @PostMapping("/search")
    public ResponseEntity<Object> searchForm(
            @RequestParam(name
                    = "query", required = false, defaultValue = "") String query,
            @RequestParam(name = "searchType", required = false, defaultValue = "code") String searchType,
            Model model
    ){
        //AirportDTO[] json = restTemplate.getForObject(Constants.JSON_URL, AirportDTO[].class);

        RestTemplate restTemplate = new RestTemplate();
        String json = restTemplate.getForObject(Constants.JSON_URL, String.class);
        model.addAttribute("jsonData", json);

        return new ResponseEntity<>(json, HttpStatus.OK);

    }
}
