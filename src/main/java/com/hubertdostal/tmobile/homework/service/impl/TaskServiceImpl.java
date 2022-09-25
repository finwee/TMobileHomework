package com.hubertdostal.tmobile.homework.service.impl;

import com.hubertdostal.tmobile.homework.exception.TaskNotFoundException;
import com.hubertdostal.tmobile.homework.exception.UserNotFoundException;
import com.hubertdostal.tmobile.homework.model.Task;
import com.hubertdostal.tmobile.homework.model.User;
import com.hubertdostal.tmobile.homework.model.dto.TaskDTO;
import com.hubertdostal.tmobile.homework.repository.TaskRepository;
import com.hubertdostal.tmobile.homework.repository.UserRepository;
import com.hubertdostal.tmobile.homework.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Default implementation of {@link TaskService} using JPA repositories {@link TaskRepository} and {@link UserRepository}
 *
 * @author huber.dostal@gmail.com
 */
@Service
public class TaskServiceImpl implements TaskService {
    private static final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public TaskDTO createNewTask(@Valid @NotNull final TaskDTO taskDTO) throws UserNotFoundException {
        Optional<User> acquiredByUser = userRepository.findById(taskDTO.getAcquiredByUserId());
        if (acquiredByUser.isEmpty()) {
            logger.error("No user found in repository for ID: {}!", taskDTO.getAcquiredByUserId());
            throw new UserNotFoundException(taskDTO.getAcquiredByUserId(), "acquiredBy");
        }
        Optional<User> createdByUser = userRepository.findById(taskDTO.getCreatedByUserId());
        if (createdByUser.isEmpty()) {
            logger.error("No user found in repository for ID: {}!", taskDTO.getCreatedByUserId());
            throw new UserNotFoundException(taskDTO.getCreatedByUserId(), "createdBy");
        }

        logger.info("Both user (acquiredBy and createdBy) found in repository, going to create and save new task");

        Task taskToSave = new Task();
        taskToSave.setUserNote(taskDTO.getUserNote());
        taskToSave.setTaskData(taskDTO.getTaskData());
        taskToSave.setAcquiredByUser(acquiredByUser.get());
        taskToSave.setCreatedByUser(createdByUser.get());

        Task savedTask = taskRepository.save(taskToSave);

        logger.info("New task successfully saved to repository with ID: {}", savedTask.getId());
        taskDTO.setId(savedTask.getId());

        return taskDTO;
    }

    @Override
    public TaskDTO updateTask(@Valid @NotNull final TaskDTO taskDTO) throws TaskNotFoundException, UserNotFoundException {
        if (taskDTO.getId() == null) {
            logger.error("No ID provided for updated task!");
            throw new TaskNotFoundException(null);
        }

        Optional<User> acquiredByUser = userRepository.findById(taskDTO.getAcquiredByUserId());
        if (acquiredByUser.isEmpty()) {
            logger.error("No user found in repository for ID: {}!", taskDTO.getAcquiredByUserId());
            throw new UserNotFoundException(taskDTO.getAcquiredByUserId(), "acquiredBy");
        }

        return taskRepository.findById(taskDTO.getId()).map(task -> {
                    task.setTaskData(taskDTO.getTaskData());
                    task.setUserNote(taskDTO.getUserNote());
                    task.setAcquiredByUser(acquiredByUser.get());
                    taskRepository.save(task);

                    logger.info("Task with ID: '{}' successfully updated at repository", taskDTO.getId());
                    return taskDTO;
                }
        ).orElseThrow(() -> new TaskNotFoundException(taskDTO.getId()));

    }

    @Override
    public List<TaskDTO> getTasksByAcquiredBy(@NotNull Long acquiredBy) {
        if (acquiredBy == null) {
            logger.error("Input parameter acquiredBy is null, cannot continue");
            throw new IllegalArgumentException("No value parameter acquiredBy provided!");
        }
        logger.info("Going to fetch tasks for parameter acquiredBy: '{}'", acquiredBy);

        List<TaskDTO> result = new ArrayList<>();

        taskRepository.findByAcquiredByUser(acquiredBy).forEach(task ->
                    result.add(new TaskDTO(task))
        );

        logger.info("For parameter acquiredBy: '{}' was found number of tasks: '{}'", acquiredBy, result.size());
        return result;
    }

    @Override
    public List<TaskDTO> getTasksByUserNote(@NotNull final String userNote) {
        if (!StringUtils.hasLength(userNote)) {
            logger.error("Input parameter userNote has no content, cannot continue");
            throw new IllegalArgumentException("No value parameter userNote provided!");
        }
        logger.info("Going to fetch tasks for parameter acquiredBy: '{}'", userNote);

        List<TaskDTO> result = new ArrayList<>();

        taskRepository.findByUserNoteContaining(userNote).forEach(task ->
                    result.add(new TaskDTO(task))
        );

        logger.info("For parameter userNote: '{}' was found number of tasks: '{}'", userNote, result.size());
        return result;
    }

}
