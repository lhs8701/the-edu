package joeuncamp.dabombackend;

import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = {"Index Controller"})
@Controller
public class IndexController {
    //    @ApiIgnore
    @GetMapping("/swagger")
    public String redirect(){
        return "redirect:/swagger-ui/index.html";
    }
}
