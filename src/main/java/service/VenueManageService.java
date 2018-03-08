package service;

import model.*;

import java.util.List;

public interface VenueManageService {

    ResultMessage register(String account, String password);

    Venue login(String account, String password);

    Venue getVenueById(int venueId);

    ResultMessage applyForUpdate(String venueJson);

    ResultMessage publishPlan(String showJson);

    List<VenueApplication> getApplication(VenueApplicationType venueApplicationType);

    ResultMessage approveApplication(int venueApplicationId);

    List<Venue> getAllVenues();
}
