package com.hubertdostal.tmobile.homework.model.dto;

import com.hubertdostal.tmobile.homework.model.Task;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * Data transfer object to carry values of {@link Task}.
 *
 * @author hubert.dostal@gmail.com
 */
public class TaskDTO {
    /**
     * Task id (may be null for new task)
     */
    private Long id;

    /**
     * User note (mandatory)
     */
    @NotEmpty(message = "{validation.task.userNote.empty}")
    @Size(max = 255, message = "{validation.task.userNote.tooLong}")
    private String userNote;

    /**
     * Task data (mandatory)
     */
    @NotEmpty(message = "{validation.task.taskDat.empty}")
    private String taskData;

    /**
     * Id of User who acquired task (mandatory)
     */
    @NotNull(message = "{validation.task.acquiredByUserId.null}")
    private Long acquiredByUserId;

    /**
     * Id of User who created task (mandatory)
     */
    @NotNull(message = "{validation.task.createdByUserId.null}")
    private Long createdByUserId;

    public TaskDTO() {
    }

    /**
     * Construct instace of {@link TaskDTO} from {@link Task}
     * @param task task to be "copied" to DTO
     */
    public TaskDTO(Task task){
        this.id = task.getId();
        this.taskData = task.getTaskData();
        this.userNote = task.getUserNote();
        this.acquiredByUserId = task.getAcquiredByUser() != null ? task.getAcquiredByUser().getId() : null;
        this.createdByUserId = task.getCreatedByUser() != null ? task.getAcquiredByUser().getId() : null;
    }

    public TaskDTO(String userNote, String taskData, Long acquiredByUserId, Long createdByUserId) {
        this.userNote = userNote;
        this.taskData = taskData;
        this.acquiredByUserId = acquiredByUserId;
        this.createdByUserId = createdByUserId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserNote() {
        return userNote;
    }

    public void setUserNote(String userNote) {
        this.userNote = userNote;
    }

    public String getTaskData() {
        return taskData;
    }

    public void setTaskData(String taskData) {
        this.taskData = taskData;
    }

    public Long getAcquiredByUserId() {
        return acquiredByUserId;
    }

    public void setAcquiredByUserId(Long acquiredByUserId) {
        this.acquiredByUserId = acquiredByUserId;
    }

    public Long getCreatedByUserId() {
        return createdByUserId;
    }

    public void setCreatedByUserId(Long createdByUserId) {
        this.createdByUserId = createdByUserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TaskDTO taskDto = (TaskDTO) o;

        return Objects.equals(getId(), taskDto.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TaskDTO{" +
                "id=" + id +
                ", userNote='" + userNote + '\'' +
                ", acquiredByUserId=" + acquiredByUserId +
                ", createdByUserId=" + createdByUserId +
                '}';
    }
}
