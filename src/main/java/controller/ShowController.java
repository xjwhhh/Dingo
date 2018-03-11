package controller;

import model.Show;
import model.ShowType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import service.bean.ShowManageServiceBean;

import java.util.List;

@CrossOrigin
@Controller
@RequestMapping("show")
public class ShowController {

    @Autowired
    ShowManageServiceBean showManageServiceBean;

    @RequestMapping(value = "/getShowByType", method = RequestMethod.POST)
    @ResponseBody
    public List<Show> getShowByType(
            @RequestParam("showType") String showTypeString) {
//        ShowType showType = ShowType.v;
        return showManageServiceBean.getShowByType(showTypeString);
    }

    @RequestMapping(value = "/getShowById", method = RequestMethod.POST)
    @ResponseBody
    public Show getShowById(
            @RequestParam("showId") String showId) {
        return showManageServiceBean.getShowById(Integer.parseInt(showId));
    }

    @RequestMapping(value = "/getShowByVenueId", method = RequestMethod.POST)
    @ResponseBody
    public List<Show> getShowByVenueId(
            @RequestParam("venueId") String venueId) {
        return showManageServiceBean.getShowByVenueId(Integer.parseInt(venueId));
    }

}
