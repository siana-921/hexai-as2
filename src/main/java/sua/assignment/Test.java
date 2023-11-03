package sua.assignment;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import sua.assignment.controller.Constants;
import sua.assignment.model.AirportDTO;

public class Test {

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/test")
    public AirportDTO[] getTest() {
        AirportDTO[] json = restTemplate.getForObject(Constants.JSON_URL, AirportDTO[].class);
        return json;
    }
}
