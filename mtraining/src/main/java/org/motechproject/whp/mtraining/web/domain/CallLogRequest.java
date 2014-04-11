package org.motechproject.whp.mtraining.web.domain;

import org.codehaus.jackson.annotate.JsonProperty;
import org.motechproject.mtraining.util.ISODateTimeUtil;

import java.util.List;
import java.util.UUID;

import static org.apache.commons.collections.CollectionUtils.isEmpty;
import static org.apache.commons.lang.StringUtils.isBlank;
import static org.apache.commons.lang.StringUtils.isNotBlank;

public class CallLogRequest extends IVRRequest {

    @JsonProperty
    private UUID courseId;
    @JsonProperty
    private String callStartTime;
    @JsonProperty
    private String callEndTime;

    @JsonProperty("content")
    private List<CallLogRecord> callLogRecords;

    public CallLogRequest() {
    }

    public CallLogRequest(Long callerId, String uniqueId, String sessionId, UUID courseId, List<CallLogRecord> callLogRecords, String callStartTime, String callEndTime) {
        super(callerId, uniqueId, sessionId);
        this.courseId = courseId;
        this.callLogRecords = callLogRecords;
        this.callStartTime = callStartTime;
        this.callEndTime = callEndTime;
    }

    public List<CallLogRecord> getCallLogRecords() {
        return callLogRecords;
    }

    public List<ValidationError> validate() {
        List<ValidationError> validationErrors = super.validate();

        // when callerId or sessionId or uniqueId is missing then no need to validate records,return error straight away
        if (!validationErrors.isEmpty()) {
            return validationErrors;
        }

        if (courseId == null) {
            validationErrors.add(new ValidationError(ResponseStatus.MISSING_COURSE_ID));
            return validationErrors;
        }

        if (isBlank(callStartTime) || isBlank(callEndTime)) {
            validationErrors.add(new ValidationError(ResponseStatus.INVALID_DATE_TIME));
            return validationErrors;
        }

        if (isCallStartTimeInvalid() || isCallEndTimeInvalid()) {
            validationErrors.add(new ValidationError(ResponseStatus.INVALID_DATE_TIME));
            return validationErrors;
        }
        if (isEmpty(callLogRecords)) {
            validationErrors.add(new ValidationError(ResponseStatus.MISSING_CALL_LOG_CONTENT));
            return validationErrors;
        }

        for (CallLogRecord callLogRecord : callLogRecords) {
            List<ValidationError> errors = callLogRecord.validate();
            if (!errors.isEmpty()) {
                validationErrors.addAll(errors);
            }
        }
        return validationErrors;
    }

    public UUID getCourseId() {
        return courseId;
    }

    public String getCallStartTime() {
        return callStartTime;
    }

    public String getCallEndTime() {
        return callEndTime;
    }

    private boolean isCallEndTimeInvalid() {
        return isNotBlank(callEndTime) && !ISODateTimeUtil.validate(callEndTime);
    }

    private boolean isCallStartTimeInvalid() {
        return isNotBlank(callStartTime) && !ISODateTimeUtil.validate(callStartTime);
    }
}