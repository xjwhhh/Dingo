package service;

import model.ResultMessage;
import model.Show;
import model.Venue;

import java.util.List;

public interface VenueManageService {

    ResultMessage register(String account,String password);

    Venue login(String account,String password);

    Venue getVenueById(int venueId);

    ResultMessage applyForUpdate(String  venueJson);

    ResultMessage publishPlan(String showJson);

    ResultMessage examineApplication(int venueApplicationId);

    List<Venue> getAllVenues();
}
