package service;

import model.Show;
import model.Venue;

import java.util.List;

public interface VenueManageService {

    void register(String account,String password);

    void applyForUpdate(Venue venue);

    void publishPlan(Show show);

    void examineApplication();

    List<Venue> getAllVenues();
}
