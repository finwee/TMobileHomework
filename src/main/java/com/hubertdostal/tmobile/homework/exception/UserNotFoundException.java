package com.hubertdostal.tmobile.homework.exception;

/**
 * Exception to notify that no {@link com.hubertdostal.tmobile.homework.model.User} exist for ID (notFoundId) value.
 *
 * @author hubert.dostal@gmail.com
 */
public class UserNotFoundException extends Exception {

    /**
     * Id for which no {@link com.hubertdostal.tmobile.homework.model.User} exists
     */
    private final Long notFoundId;

    /**
     * Field name that carry out invalid Id
     */
    private final String fieldName;

    public UserNotFoundException(Long notFoundId, String fieldName) {
        super("User not found by field: " + fieldName + " value: " + notFoundId);
        this.notFoundId = notFoundId;
        this.fieldName = fieldName;
    }

    public Long getNotFoundId() {
        return notFoundId;
    }

    public String getFieldName() {
        return fieldName;
    }
}
