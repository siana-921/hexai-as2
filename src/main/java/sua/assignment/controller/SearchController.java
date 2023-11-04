package sua.assignment.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import sua.assignment.model.AirportDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;





@RestController
public class SearchController {

    private static final Logger logger = LoggerFactory.getLogger(SearchController.class);
    @PostMapping("/search")
    public ResponseEntity<Map<String, AirportDTO>> searchForm(
            @RequestParam(name
                    = "category", required = true, defaultValue = "code") String category,
            @RequestParam(name = "val", required = true, defaultValue = "") String val,
            Model model
    ){
        logger.info(category);

    try {
        Map<String, AirportDTO> airports = fetchDataFromJson();
        Map<String, AirportDTO> searchResults = searchData(airports, category, val);

        logger.info("searchResult: {}", searchResults);
        return new ResponseEntity<>(searchResults, HttpStatus.OK);
    }catch(Exception e){
        e.printStackTrace();
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    }

    public Map<String, AirportDTO> fetchDataFromJson() {

        RestTemplate restTemplate = new RestTemplate();
        String jsondata = restTemplate.getForObject(Constants.JSON_URL, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Map<String, AirportDTO> data = objectMapper.readValue(jsondata, new TypeReference<Map<String, AirportDTO>>() {});
            return data;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Map<String, AirportDTO> searchData(Map<String, AirportDTO> airports, String category, String val) {
        Map<String, AirportDTO> searchResults = new HashMap<>();

        val = val.toLowerCase().replaceAll("\\s", "");

        for (Map.Entry<String, AirportDTO> entry : airports.entrySet()) {
            AirportDTO airport = entry.getValue();

            String fieldValue = "";
            if (category.equals("iata")) {
                fieldValue = airport.getIata().toLowerCase().replaceAll("\\s", "");
            } else if (category.equals("name")) {
                fieldValue = airport.getName().toLowerCase().replaceAll("\\s", "");
            } else if (category.equals("city")) {
                fieldValue = airport.getCity().toLowerCase().replaceAll("\\s", "");
            }

            //contains로 하니까 일부만 맞아도 검색되넹?
            if (fieldValue.contains(val)) {
                searchResults.put(entry.getKey(), airport);
            }
        }

        return searchResults;
    }

}
