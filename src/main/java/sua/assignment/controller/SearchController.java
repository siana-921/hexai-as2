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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    /*
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, AirportDTO> airportData = objectMapper.readValue(json, new TypeReference<Map<String, AirportDTO>>() {});

        // 검색 조건에 따라 검색 결과를 저장할 리스트
        List<AirportDTO> searchResults = new ArrayList<>();

        for (AirportDTO airport : airportData.values()) {
            // 여기에서 검색 조건에 따라 필터링하고 결과를 검색Results 리스트에 추가
            if (category.equals("code") && airport.getIcao().equalsIgnoreCase(val)) {
                searchResults.add(airport);
            } else if (category.equals("name") && airport.getName().contains(val)) {
                searchResults.add(airport);
            } else if (category.equals("country") && airport.getCountry().equalsIgnoreCase(val)) {
                searchResults.add(airport);
            }
        }

*/
        //return new ResponseEntity<>(json, HttpStatus.OK);

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

            if (fieldValue.contains(val)) {
                searchResults.put(entry.getKey(), airport);
            }
        }

        return searchResults;
    }

}
