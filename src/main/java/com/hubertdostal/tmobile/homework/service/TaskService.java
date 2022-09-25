package com.hubertdostal.tmobile.homework.service;

import com.hubertdostal.tmobile.homework.exception.TaskNotFoundException;
import com.hubertdostal.tmobile.homework.exception.UserNotFoundException;
import com.hubertdostal.tmobile.homework.model.dto.TaskDTO;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Service layer interface for {@link com.hubertdostal.tmobile.homework.model.Task} entities
 *
 * @author huber.dostal@gmail.com
 */
public interface TaskService {

    /**
     * Creates and saves new instance of {@link com.hubertdostal.tmobile.homework.model.Task}
     *
     * @param taskDTO DTO object that carry values for new task
     * @return instance of {@link TaskDTO} enriched with new Id under which task was stored
     * @throws UserNotFoundException exception thrown when task data (acquireBy or createdBy) contains user id that does
     *                               not exist.
     */
    TaskDTO createNewTask(@Valid @NotNull final TaskDTO taskDTO) throws UserNotFoundException;

    /**
     * Updated existing instance of {@link com.hubertdostal.tmobile.homework.model.Task}
     *
     * @param taskDTO DTO object that carry values for new task
     * @return updated instance of {@link TaskDTO}
     * @throws TaskNotFoundException exception thrown when no task exists for given task id
     * @throws UserNotFoundException exception thrown when task data (acquireBy or createdBy) contains user id that does
     *                               not exist.
     */
    TaskDTO updateTask(@Valid @NotNull final TaskDTO taskDTO) throws TaskNotFoundException, UserNotFoundException;

    /**
     * Fetch stored instances of {@link com.hubertdostal.tmobile.homework.model.Task} by its acquireBy value
     *
     * @param acquiredBy value for search condition
     * @return list of {@link TaskDTO} fulfilling condition
     */
    List<TaskDTO> getTasksByAcquiredBy(@NotNull final Long acquiredBy);

    /**
     * Fetch stored instances of {@link com.hubertdostal.tmobile.homework.model.Task} by its userNote value (fulltext search)
     *
     * @param userNote value for search condition
     * @return list of {@link TaskDTO} fulfilling condition
     */
    List<TaskDTO> getTasksByUserNote(@NotNull final String userNote);

}
