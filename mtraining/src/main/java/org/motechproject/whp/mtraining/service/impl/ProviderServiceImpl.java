package org.motechproject.whp.mtraining.service.impl;

import org.motechproject.whp.mtraining.domain.CourseProgress;
import org.motechproject.whp.mtraining.domain.Flag;
import org.motechproject.whp.mtraining.domain.Location;
import org.motechproject.whp.mtraining.domain.Provider;
import org.motechproject.whp.mtraining.repository.ProviderDataService;
import org.motechproject.whp.mtraining.service.CourseProgressService;
import org.motechproject.whp.mtraining.service.FlagService;
import org.motechproject.whp.mtraining.service.LocationService;
import org.motechproject.whp.mtraining.service.ProviderService;
import org.motechproject.whp.mtraining.web.domain.ProviderStatus;
import org.motechproject.whp.mtraining.web.domain.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.motechproject.whp.mtraining.web.domain.ResponseStatus.NOT_WORKING_PROVIDER;
import static org.motechproject.whp.mtraining.web.domain.ResponseStatus.OK;
import static org.motechproject.whp.mtraining.web.domain.ResponseStatus.UNKNOWN_PROVIDER;

@Service("providerService")
public class ProviderServiceImpl implements ProviderService {

    @Autowired
    private ProviderDataService providerDataService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private CourseProgressService courseProgressService;

    @Autowired
    private FlagService flagService;

    @Override
    public Provider createProvider(Provider provider) {
        provider.setLocation(getLocationFromDatabase(provider.getLocation()));
        return providerDataService.create(provider);
    }

    @Override
    public Provider updateProvider(Provider provider) {
        Provider providerToUpdate = getProviderById(provider.getId());
        providerToUpdate.setRemediId(provider.getRemediId());
        providerToUpdate.setCallerId(provider.getCallerId());
        providerToUpdate.setProviderStatus(provider.getProviderStatus());
        providerToUpdate.setLocation(getLocationFromDatabase(provider.getLocation()));
        return providerDataService.update(providerToUpdate);
    }

    @Override
    public Provider updateProviderbyRemediId(String remediId, Provider provider) {
        Provider providerToUpdate = getProviderByRemediId(remediId);
        if (providerToUpdate != null) {
            providerToUpdate.setCallerId(provider.getCallerId());
            providerToUpdate.setProviderStatus(provider.getProviderStatus());
            providerToUpdate.setRemediId(provider.getRemediId());
            providerToUpdate.setLocation(getLocationFromDatabase(provider.getLocation()));
            return providerDataService.update(providerToUpdate);
        } else {
            return null;
        }
    }

    @Override
    public void deleteProvider(Provider provider) {
        providerDataService.delete(provider);
    }

    @Override
    public Provider getProviderById(long id) {
        return providerDataService.findProviderById(id);
    }

    @Override
    public Provider getProviderByCallerId(Long callerId) {
        List<Provider> providers = providerDataService.findProviderByCallerId(callerId);
        return (providers.size() > 0) ? providers.get(0) : null;
    }

    @Override
    public Provider getProviderByRemediId(String remediId) {
        return providerDataService.findProviderByRemediId(remediId);
    }

    @Override
    public Provider getProviderByLocation(Long id) {
        return providerDataService.findProviderByLocationId(id);
    }

    @Override
    public List<Provider> getAllProviders() {
        return providerDataService.retrieveAll();
    }

    @Override
    public void resetCourseProgresses(String contentId) {
        for (Provider provider : getAllProviders()) {
            CourseProgress courseProgress = courseProgressService.getCourseProgressForProvider(provider.getCallerId());
            if (courseProgress != null && courseProgress.getFlag() != null) {
                Flag flag = flagService.getFlagById(courseProgress.getFlag().getId());
                if (flag.getCourseIdentifier() != null && flag.getCourseIdentifier().getContentId().equals(contentId)) {
                    courseProgress.setFlag(null);
                    courseProgressService.updateCourseProgress(courseProgress);
                    flagService.deleteFlag(flag.getId());
                    courseProgressService.deleteCourseProgress(courseProgress);
                }
            }
        }
    }

    public ResponseStatus validateProvider(Long callerId) {
        Provider provider = getProviderByCallerId(callerId);
        if (provider == null)
            return UNKNOWN_PROVIDER;
        if (provider.getProviderStatus() == ProviderStatus.NOT_WORKING_PROVIDER)
            return NOT_WORKING_PROVIDER;
        return OK;
    }

    private Location getLocationFromDatabase(Location location) {
        if (location != null) {
            Location locationInDb = (location.getId() > 0) ? locationService.getLocationById(location.getId()) :
                    locationService.getLocationByState(location.getState());
            if (locationInDb == null) {
                return locationService.createLocation(location);
            }
            return locationInDb;
        }
        return null;
    }
}
