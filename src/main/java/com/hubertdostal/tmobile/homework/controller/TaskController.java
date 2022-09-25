package com.hubertdostal.tmobile.homework.controller;

import com.hubertdostal.tmobile.homework.exception.TaskNotFoundException;
import com.hubertdostal.tmobile.homework.exception.UserNotFoundException;
import com.hubertdostal.tmobile.homework.model.dto.TaskDTO;
import com.hubertdostal.tmobile.homework.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Controller to serve HTTP requests related to {@link com.hubertdostal.tmobile.homework.model.Task} entity.
 * Currently, it supports creating new instances of {@link com.hubertdostal.tmobile.homework.model.Task},
 * updating existing {@link com.hubertdostal.tmobile.homework.model.Task} and fetching instances of {@link com.hubertdostal.tmobile.homework.model.Task}
 * based on fulltext search by userNote and acquireBy of {@link com.hubertdostal.tmobile.homework.model.Task}
 *
 * @author hubert.dostal@gmail.com
 */
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    /**
     * Injected instance of {@link TaskService} used for business layer operations
     */
    @Autowired
    private TaskService taskService;

    /**
     * POST method to save new instance of {@link com.hubertdostal.tmobile.homework.model.Task}
     * @param newTask DTO representation of task
     * @return DTO representation of task with newly generated ID
     */
    @PostMapping
    ResponseEntity<TaskDTO> saveNewTask(@Valid @RequestBody TaskDTO newTask) {
        logger.info("Request to create and save new Task received");
        try {
            newTask = taskService.createNewTask(newTask);
            logger.info("Task successfully created, returning HTTP status: '{}'", HttpStatus.CREATED.value());
            return new ResponseEntity<>(newTask, HttpStatus.CREATED);
        } catch (UserNotFoundException e) {
            logger.error("Exception during creation of new Task!", e);
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * PUT method to update instance of {@link com.hubertdostal.tmobile.homework.model.Task}
     * @param updatedTask DTO representation of task
     * @return DTO representation of updated task
     */
    @PutMapping
    ResponseEntity<TaskDTO> updateTask(@Valid @RequestBody TaskDTO updatedTask) {
        logger.info("Request to update existing Task received");
        try {
            updatedTask = taskService.updateTask(updatedTask);
            logger.info("Task successfully updated, returning HTTP status: '{}'", HttpStatus.OK.value());
            return new ResponseEntity<>(updatedTask, HttpStatus.OK);
        } catch (TaskNotFoundException | UserNotFoundException e)  {
            logger.error("Exception during update of existing Task!", e);
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * GET method to search existing stored instances of {@link com.hubertdostal.tmobile.homework.model.Task}
     * by its parameter acquiredBy
     *
     * @param acquiredBy value of acquiredBy parameter of {@link com.hubertdostal.tmobile.homework.model.Task}
     * @return list of {@link TaskDTO} fulfilling the condition
     */
    @GetMapping("/acquired_by/{acquiredBy}")
    ResponseEntity<List<TaskDTO>> findTasksForAcquiredBy(@PathVariable Long acquiredBy) {
        logger.info("Going to fetch tasks based on Acquired by value. Searched acquiredBy value: '{}'", acquiredBy);
        return new ResponseEntity<>(taskService.getTasksByAcquiredBy(acquiredBy), HttpStatus.OK);
    }


    /**
     * GET method to search existing stored instances of {@link com.hubertdostal.tmobile.homework.model.Task} with fulltext
     * search based on userNote part
     *
     * @param userNote string that will be used in fulltext search
     * @return list of {@link TaskDTO} fulfilling the condition
     */
    @GetMapping
    ResponseEntity<List<TaskDTO>> findTasksForAcquiredBy(@RequestParam String userNote) {
        logger.info("Going to fetch tasks based on User note fulltext search. Searched text: '{}'", userNote);
        return new ResponseEntity<>(taskService.getTasksByUserNote(userNote), HttpStatus.OK);
    }
}
