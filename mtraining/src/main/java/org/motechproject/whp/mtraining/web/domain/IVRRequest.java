package org.motechproject.whp.mtraining.web.domain;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang.StringUtils.isBlank;
import static org.motechproject.whp.mtraining.web.domain.ResponseStatus.MISSING_CALLER_ID;
import static org.motechproject.whp.mtraining.web.domain.ResponseStatus.MISSING_SESSION_ID;
import static org.motechproject.whp.mtraining.web.domain.ResponseStatus.MISSING_UNIQUE_ID;

public class IVRRequest {
    private Long callerId;
    private String sessionId;
    private String uniqueId;

    public IVRRequest() {
    }

    public IVRRequest(Long callerId, String sessionId, String uniqueId) {
        this.callerId = callerId;
        this.sessionId = sessionId;
        this.uniqueId = uniqueId;
    }

    public Long getCallerId() {
        return callerId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public List<ValidationError> validate() {
        List<ValidationError> validationErrors = new ArrayList<>();
        if (isCallerIdMissing()) {
            validationErrors.add(new ValidationError(MISSING_CALLER_ID.getCode()));
        }
        if (isSessionIdMissing()) {
            validationErrors.add(new ValidationError(MISSING_SESSION_ID.getCode()));
        }
        if (isUniqueIdMissing()) {
            validationErrors.add(new ValidationError(MISSING_UNIQUE_ID.getCode()));
        }
        return validationErrors;
    }

    protected boolean isCallerIdMissing() {
        return callerId == null;
    }

    protected boolean isSessionIdMissing() {
        return isBlank(sessionId);
    }

    protected boolean isUniqueIdMissing() {
        return isBlank(uniqueId);
    }

    public void setCallerId(Long callerId) {
        this.callerId = callerId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }
}
