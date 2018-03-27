package service;

import model.*;

import java.util.List;

public interface VenueManageService {

    ResultMessage register(String account, String password);

    Venue login(String code, String password);

    Venue getVenueById(int venueId);

    ResultMessage applyForUpdate(String venueJson, String one, String two, String three);

    List<VenueApplication> getApplication(VenueApplicationType venueApplicationType);

    ResultMessage approveApplication(int venueApplicationId);

    List<Venue> getAllVenues();

    List<VenueFinance> getVenueFinanceList(int venueId);

}
