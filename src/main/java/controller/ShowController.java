package controller;

import model.Show;
import model.ShowType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import service.ServiceFactory;

import java.util.List;

@CrossOrigin
@Controller
@RequestMapping("show")
public class ShowController {

    @RequestMapping(value = "/getShowByType", method = RequestMethod.POST)
    @ResponseBody
    public List<Show> getShowByType(
            @RequestParam("showType") String showTypeString) {
        ShowType showType = ShowType.valueOf(showTypeString);
        return ServiceFactory.getShowManageService().getShowByType(showType);
    }

    @RequestMapping(value = "/getShowById", method = RequestMethod.POST)
    @ResponseBody
    public Show getShowById(
            @RequestParam("showId") String showId) {
        return ServiceFactory.getShowManageService().getShowById(Integer.parseInt(showId));
    }

}
