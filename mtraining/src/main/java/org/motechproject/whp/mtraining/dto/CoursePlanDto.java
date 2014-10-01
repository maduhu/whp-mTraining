package org.motechproject.whp.mtraining.dto;


import org.codehaus.jackson.map.annotate.JsonView;
import org.joda.time.DateTime;
import org.motechproject.mtraining.domain.CourseUnitState;
import org.motechproject.whp.mtraining.domain.Location;
import org.motechproject.whp.mtraining.domain.views.PublishCourseView;

import java.util.List;

/**
 * DTO representation for CoursePlan class (WHP Course level logic)
 */
public class CoursePlanDto extends CourseUnitMetadataDto {

    @JsonView({PublishCourseView.class})
    private List<ModuleDto> modules;

    private Location location;

    private Integer duration;

    public CoursePlanDto() {
    }

    public CoursePlanDto(Integer id, String name, String description, CourseUnitState state, String filename,
                         DateTime creationDate, DateTime modificationDate, List<ModuleDto> modules,
                         Location location, Integer duration) {
        super(id, name, description, state, filename, creationDate, modificationDate);
        this.modules = modules;
        this.location = location;
        this.duration = (duration == null) ? 365 : duration;
    }

    public CoursePlanDto(Integer id, String name, CourseUnitState state, DateTime creationDate, DateTime modificationDate,
                         List<ModuleDto> modules, Location location, Integer duration) {
        super(id, name, state, creationDate, modificationDate);
        this.modules = modules;
        this.location = location;
        this.duration = (duration == null) ? 365 : duration;
    }

    public CoursePlanDto(long id, String name, CourseUnitState state, DateTime creationDate, DateTime modificationDate) {
        super(id, name, state, creationDate, modificationDate);
        duration = 365;
    }

    public List<ModuleDto> getModules() {
        return modules;
    }

    public void setModules(List<ModuleDto> modules) {
        this.modules = modules;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }
}
