package sua.assignment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
        @GetMapping("/")  //"/search" 경로로 접근하면
        public String searchForm(){
            return "searchForm";    //"searchForm"이라는 이름을 가진 html을 반환해주겠다는 뜻인듯?
        }

}
