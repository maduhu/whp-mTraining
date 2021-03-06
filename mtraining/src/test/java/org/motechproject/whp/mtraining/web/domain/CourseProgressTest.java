package org.motechproject.whp.mtraining.web.domain;

import org.junit.Test;
import org.motechproject.whp.mtraining.domain.CourseProgress;
import org.motechproject.whp.mtraining.domain.Flag;
import org.motechproject.whp.mtraining.util.ISODateTimeUtil;
import org.motechproject.whp.mtraining.BookmarkBuilder;
import org.motechproject.whp.mtraining.validator.CourseProgressValidator;

import static junit.framework.Assert.assertTrue;

public class CourseProgressTest {

    @Test
    public void testValidationErrors() throws Exception {
        Flag flag = new BookmarkBuilder().buildFlag();
        CourseProgress courseProgressWithMissingStartTime = new CourseProgress(null, flag, 1000, "STARTED");
        CourseProgress courseProgressWithInvalidStartTime = new CourseProgress("fbdjsbfjdsbjkfdhjks", flag, 1000, "STARTED");
        CourseProgress courseProgressWithInvalidBookmark = new CourseProgress(ISODateTimeUtil.nowAsStringInTimeZoneUTC(), null, 1000, "STARTED");
        CourseProgress courseProgressWithBookmarkWithNullCourse = new CourseProgress(ISODateTimeUtil.nowAsStringInTimeZoneUTC(), new BookmarkBuilder().withCourse(0).buildFlag(), 1000, "STARTED");
        CourseProgress courseProgressWithInvalidStatus = new CourseProgress(null, flag, 1000, null);

        assertTrue(CourseProgressValidator.validate(courseProgressWithMissingStartTime).contains(new ValidationError(ResponseStatus.MISSING_COURSE_START_TIME)));
        assertTrue(CourseProgressValidator.validate(courseProgressWithInvalidStartTime).contains(new ValidationError(ResponseStatus.INVALID_DATE_TIME)));
        assertTrue(CourseProgressValidator.validate(courseProgressWithInvalidBookmark).contains(new ValidationError(ResponseStatus.INVALID_FLAG)));
        assertTrue(CourseProgressValidator.validate(courseProgressWithBookmarkWithNullCourse).contains(new ValidationError(910, "Missing Content Id or Version for: Course")));
        assertTrue(CourseProgressValidator.validate(courseProgressWithInvalidStatus).contains(new ValidationError(ResponseStatus.INVALID_COURSE_STATUS)));
    }
}
