package org.motechproject.whp.mtraining.service.impl;

import org.motechproject.whp.mtraining.domain.Location;
import org.motechproject.whp.mtraining.repository.LocationDataService;
import org.motechproject.whp.mtraining.repository.ProviderDataService;
import org.motechproject.whp.mtraining.service.CoursePlanService;
import org.motechproject.whp.mtraining.service.LocationService;
import org.motechproject.whp.mtraining.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service("locationService")
public class LocationServiceImpl implements LocationService {

    @Autowired
    CoursePlanService coursePlanService;

    @Autowired
    private LocationDataService locationDataService;

    @Autowired
    private ProviderService providerService;

    @Override
    public Location createLocation(Location location) {
        return locationDataService.create(location);
    }

    @Override
    public Location updateLocation(Location location) {
        return locationDataService.update(location);
    }

    @Override
    public void deleteLocation(Location location) {
        locationDataService.delete(location);
    }

    @Override
    public List<Location> getAllLocations() {
        return locationDataService.retrieveAll();
    }

    @Override
    public Location getLocationById(long id) {
        return locationDataService.findLocationById(id);
    }

    @Override
    public Location getStateByName(String stateName) {
        return locationDataService.findStateByName(stateName, Location.STATE_LEVEL);
    }

    @Override
    public Location getBlockByName(String blockName) {
        return locationDataService.findBlockByName(blockName, Location.BLOCK_LEVEL);
    }

    @Override
    public boolean doesStateExist (String stateName) {
        return (getStateByName(stateName) != null);
    }

    @Override
    public List<Location> getBlockLocations() {
        return locationDataService.retrieveLocationsByLevel(Location.BLOCK_LEVEL);
    }

    @Override
    public List<Location> getStateLocations() {
        return locationDataService.retrieveLocationsByLevel(Location.STATE_LEVEL);
    }

    @Override
    public List<Location> getUnusedLocationsByCourse() {
        List<Location> locations = new LinkedList<>();

        for (Location location : locationDataService.retrieveAll()) {
            if (coursePlanService.getCoursePlanByLocation(location.getId()) == null) {
                locations.add(location);
            }
        }

        return locations;
    }

}
