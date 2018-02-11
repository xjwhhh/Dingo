package controller;

import model.Show;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import service.ServiceFactory;

@CrossOrigin
@Controller
@RequestMapping("show")
public class ShowController {

    @RequestMapping(value = "/getShowByType", method = RequestMethod.POST)
    @ResponseBody
    public Show[] Register(
            @RequestParam("showType")String showType) {
        return null;
    }
}
