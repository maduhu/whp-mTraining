package org.motechproject.whp.mtraining.reports.domain;

import org.joda.time.DateTime;
import org.motechproject.mtraining.util.ISODateTimeUtil;
import org.motechproject.whp.mtraining.web.domain.ResponseStatus;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.Embedded;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@PersistenceCapable(table = "bookmark_request", identityType = IdentityType.APPLICATION)
public class BookmarkRequest {

    @Persistent(valueStrategy = IdGeneratorStrategy.INCREMENT)
    @PrimaryKey
    private Long id;
    @Persistent(column = "caller_id")
    private Long callerId;
    @Persistent(column = "unique_id")
    private String uniqueId;
    @Persistent(column = "session_id")
    private String sessionId;
    @Persistent(column = "response_code")
    private int responseCode;
    @Persistent(column = "response_message")
    private String responseMessage;
    @Persistent(column = "created_on")
    @Temporal(TemporalType.TIMESTAMP)
    private DateTime createdOn;
    @Persistent(column = "provider_remedy_id")
    private String remedyId;

    @Persistent(column = "request_type")
    private BookmarkRequestType requestType;


    @Embedded(members = {
            @Persistent(name = "courseId", columns = @Column(name = "course_id")),
            @Persistent(name = "courseVersion", columns = @Column(name = "course_version")),
            @Persistent(name = "moduleId", columns = @Column(name = "module_id")),
            @Persistent(name = "moduleVersion", columns = @Column(name = "module_version")),
            @Persistent(name = "chapterId", columns = @Column(name = "chapter_id")),
            @Persistent(name = "chapterVersion", columns = @Column(name = "chapter_version")),
            @Persistent(name = "messageId", columns = @Column(name = "message_id")),
            @Persistent(name = "messageVersion", columns = @Column(name = "message_version")),
            @Persistent(name = "dateModified", columns = @Column(name = "bookmark_modified_on")),
    })
    @Persistent
    private BookmarkReport bookmarkReport;

    public BookmarkRequest(Long callerId, String uniqueId, String sessionId, ResponseStatus responseStatus, BookmarkRequestType requestType) {
        this.callerId = callerId;
        this.uniqueId = uniqueId;
        this.sessionId = sessionId;
        this.responseCode = responseStatus.getCode();
        this.responseMessage = responseStatus.getMessage();
        this.requestType = requestType;
        this.createdOn = ISODateTimeUtil.nowInTimeZoneUTC();
    }

    public BookmarkRequest(String remedyId, Long callerId, String uniqueId, String sessionId, ResponseStatus responseStatus, BookmarkRequestType requestType, BookmarkReport bookmarkReport) {
        this(callerId, uniqueId, sessionId, responseStatus, requestType);
        this.remedyId = remedyId;
        this.bookmarkReport = bookmarkReport;
    }

    public Long getCallerId() {
        return callerId;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public String getSessionId() {
        return sessionId;
    }


}