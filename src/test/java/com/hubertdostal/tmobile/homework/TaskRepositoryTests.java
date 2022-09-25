package com.hubertdostal.tmobile.homework;

import com.hubertdostal.tmobile.homework.model.Task;
import com.hubertdostal.tmobile.homework.repository.TaskRepository;
import com.hubertdostal.tmobile.homework.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test cases for JPA repository layer
 *
 * @author hubert.dostal@gmail.com
 */
@DataJpaTest
public class TaskRepositoryTests {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    public void shouldFindSomeTasks() {
        List<Task> tasks = taskRepository.findAll();
        assertThat(tasks).isNotEmpty();
    }

    @Test
    public void shouldFindNoTaskForNonExistingUser() {
        List<Task> tasks = taskRepository.findByAcquiredByUser(10000L);
        assertThat(tasks).isEmpty();
    }

    @Test
    public void shouldFindSomeTaskForExistingUser() {
        List<Task> tasks = taskRepository.findByAcquiredByUser(3L);
        assertThat(tasks).isNotEmpty();
    }

    @Test
    public void shouldFindSomeTaskByUserNote() {
        List<Task> tasks = taskRepository.findByUserNoteContaining("REST");
        // we created two task in init sql script that contains REST word
        assertThat(tasks).hasSize(2);
    }

    @Test
    public void shouldFindNoneTaskByUserNote() {
        List<Task> tasks = taskRepository.findByUserNoteContaining("SomeWrongWord");
        // we created two task in init sql script that contains REST word
        assertThat(tasks).isEmpty();
    }

    @Test
    public void shouldSaveTheTask(){
        Task task = new Task();
        task.setUserNote("Testing note");
        task.setTaskData("Testing task data");
        task.setAcquiredByUser(userRepository.findById(1L).get());
        task.setCreatedByUser(userRepository.findById(2L).get());
        task = taskRepository.save(task);
        assertThat(task.getId()).isNotNull();
    }

}
