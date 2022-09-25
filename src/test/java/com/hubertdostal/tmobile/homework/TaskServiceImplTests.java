package com.hubertdostal.tmobile.homework;

import com.hubertdostal.tmobile.homework.exception.TaskNotFoundException;
import com.hubertdostal.tmobile.homework.exception.UserNotFoundException;
import com.hubertdostal.tmobile.homework.model.dto.TaskDTO;
import com.hubertdostal.tmobile.homework.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Test cases for service layer
 *
 * @author hubert.dostal@gmail.com
 */
@SpringBootTest
public class TaskServiceImplTests {

    @Autowired
    private TaskService taskService;

    @Test
    public void shouldSaveTheTask() throws UserNotFoundException {
        TaskDTO taskDTO = new TaskDTO("Note", "Data", 1L, 2L);
        taskDTO = taskService.createNewTask(taskDTO);
        assertThat(taskDTO).isNotNull();
    }

    @Test
    public void shouldNotSaveTheTaskDueMissingValues() throws UserNotFoundException {
        TaskDTO taskDTO = new TaskDTO(null, "Data", 1L, 2L);
        assertThrows(DataIntegrityViolationException.class, () -> taskService.createNewTask(taskDTO));
    }

    @Test
    public void shouldNotSaveTheTaskDueNonExistingCreatedByUser() throws UserNotFoundException {
        TaskDTO taskDTO = new TaskDTO("note", "Data", 1L, 200L);
        assertThrows(UserNotFoundException.class, () -> taskService.createNewTask(taskDTO));
    }

    @Test
    public void shouldNotSaveTheTaskDueNonExistingAcquiredByUser() throws UserNotFoundException {
        TaskDTO taskDTO = new TaskDTO("note", "Data", -1L, 2L);
        assertThrows(UserNotFoundException.class, () -> taskService.createNewTask(taskDTO));
    }

    @Test
    public void shouldUpdateExistingTask() throws UserNotFoundException, TaskNotFoundException {
        TaskDTO taskDTO = new TaskDTO("note", "Data", 1L, 2L);
        taskDTO.setId(1L);
        taskDTO = taskService.updateTask(taskDTO);
        // check that id was not changed
        assertThat(taskDTO.getId()).isEqualTo(1L);
        assertThat(taskDTO.getUserNote()).isEqualTo("note");
        assertThat(taskDTO.getTaskData()).isEqualTo("Data");
        assertThat(taskDTO.getAcquiredByUserId()).isEqualTo(1L);
        assertThat(taskDTO.getCreatedByUserId()).isEqualTo(2L);
    }

    @Test
    public void shouldNotUpdateNonExistingTask() throws UserNotFoundException {
        TaskDTO taskDTO = new TaskDTO("note", "Data", 1L, 2L);
        taskDTO.setId(-1L);
        assertThrows(TaskNotFoundException.class, () -> taskService.updateTask(taskDTO));
    }

    @Test
    public void shouldNotUpdateExistingTaskDueConstraints() throws UserNotFoundException {
        TaskDTO taskDTO = new TaskDTO(null, "Data", 1L, 2L);
        taskDTO.setId(1L);
        assertThrows(DataIntegrityViolationException.class, () -> taskService.updateTask(taskDTO));
    }

    @Test
    public void shouldNotUpdateExistingTaskDueNonExistingAcqUser() throws UserNotFoundException {
        TaskDTO taskDTO = new TaskDTO("Note", "Data", -1L, 2L);
        taskDTO.setId(1L);
        assertThrows(UserNotFoundException.class, () -> taskService.updateTask(taskDTO));
    }

    @Test
    public void shouldNotUpdateExistingTaskDueNonExistingCreateByUser() throws UserNotFoundException {
        TaskDTO taskDTO = new TaskDTO("Note", "Data", 1L, -2L);
        taskDTO.setId(1L);
        assertThrows(UserNotFoundException.class, () -> taskService.updateTask(taskDTO));
    }

    @Test
    public void shouldFindTasksForAcquiredBy() {
        List<TaskDTO> tasks = taskService.getTasksByAcquiredBy(3L);
        assertThat(tasks).isNotEmpty();
    }

    @Test
    public void shouldFindNoTasksForAcquiredBy() {
        List<TaskDTO> tasks = taskService.getTasksByAcquiredBy(2L);
        assertThat(tasks).isEmpty();
    }

    @Test
    public void shouldFindTasksByUserNote() {
        List<TaskDTO> tasks = taskService.getTasksByUserNote("REST");
        assertThat(tasks).isNotEmpty();
    }

    @Test
    public void shouldFindNoTasksByUserNote() {
        List<TaskDTO> tasks = taskService.getTasksByUserNote("DummyWordNonExisting");
        assertThat(tasks).isEmpty();
    }
}
