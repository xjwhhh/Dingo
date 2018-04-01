package controller;

import model.ResultMessage;
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

    @RequestMapping(value = "/publishShow", method = RequestMethod.POST)
    @ResponseBody
    public ResultMessage publishShow(
            @RequestParam("showJson") String showJson,
            @RequestParam("one") String one,
            @RequestParam("two") String two,
            @RequestParam("three") String three) {
        return showManageServiceBean.publishShow(showJson, one, two, three);
    }

    @RequestMapping(value = "/getShowByType", method = RequestMethod.POST)
    @ResponseBody
    public List<Show> getShowByType(
            @RequestParam("showType") String showTypeString) {
        return showManageServiceBean.getPreSaleShowByType(showTypeString);
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
