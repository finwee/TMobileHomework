package com.hubertdostal.tmobile.homework.exception;

/**
 * Exception to notify that no {@link com.hubertdostal.tmobile.homework.model.Task} exist for ID (notFoundId) value
 *
 * @author hubert.dostal@gmail.com
 */
public class TaskNotFoundException extends Exception {

    /**
     * Id for which no {@link com.hubertdostal.tmobile.homework.model.Task} exists
     */
    private final Long notFoundId;

    public TaskNotFoundException(Long notFoundId) {
        super("Task not found for ID : " + notFoundId);
        this.notFoundId = notFoundId;
    }

    public Long getNotFoundId() {
        return notFoundId;
    }
}
