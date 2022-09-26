package com.hubertdostal.tmobile.homework;

import com.hubertdostal.tmobile.homework.model.dto.TaskDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Integration tests for {@link com.hubertdostal.tmobile.homework.controller.TaskController}
 *
 * @author hubert.dostal@gmail.com
 */
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TaskControllerTests {

    @LocalServerPort
    int randomServerPort;

    private RestTemplate restTemplate;

    private String baseUrl;

    @PostConstruct
    public void initTaskControllerTests() {
        restTemplate = new RestTemplate();
        baseUrl = "http://localhost:" + randomServerPort + "/api/tasks";
    }

    @Test
    public void testGetEmptyTaskListByAcquiredBy() throws URISyntaxException {
        URI uri = new URI(baseUrl + "/acquired_by/-1");

        ResponseEntity<List> result = restTemplate.getForEntity(uri, List.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).hasSize(0);

    }

    @Test
    public void testGetNonEmptyTaskListByAcquiredBy() throws URISyntaxException {
        URI uri = new URI(baseUrl + "/acquired_by/2");

        ResponseEntity<List> result = restTemplate.getForEntity(uri, List.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isNotEmpty();

    }

    @Test
    public void testGetEmptyTaskListByUserNote() throws URISyntaxException {
        URI uri = new URI(baseUrl + "/?userNote=SomeWronglyTypedWord");

        ResponseEntity<List> result = restTemplate.getForEntity(uri, List.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).hasSize(0);

    }

    @Test
    public void testGetNonEmptyTaskListByUserNote() throws URISyntaxException {
        URI uri = new URI(baseUrl + "/?userNote=REST");

        ResponseEntity<List> result = restTemplate.getForEntity(uri, List.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isNotEmpty();

    }

    @Test
    public void testWrongParamLeadsToWrongRequest() throws URISyntaxException {
        URI uri = new URI(baseUrl + "/?wrongName=wrongValue");
        assertThrows(HttpClientErrorException.class, () -> restTemplate.getForEntity(uri, List.class));
    }

    @Test
    public void testWrongPathLeadsToWrongRequest() throws URISyntaxException {
        URI uri = new URI(baseUrl + "/nonexistingPath");
        assertThrows(HttpClientErrorException.class, () -> restTemplate.getForEntity(uri, List.class));
    }

    @Test
    public void testCreateTaskSuccessful() throws URISyntaxException {
        URI uri = new URI(baseUrl);
        TaskDTO dto = new TaskDTO("New user note", "Task data", 1L, 2L);
        ResponseEntity<TaskDTO> response = restTemplate.postForEntity(uri, dto, TaskDTO.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNotNull();
    }

    @Test
    public void testCreateTaskUnuSuccessfulNonExistingAcqBy() throws URISyntaxException {
        URI uri = new URI(baseUrl);
        TaskDTO dto = new TaskDTO("New user note", "Task data", -1L, 2L);
        assertThrows(HttpClientErrorException.class, () -> restTemplate.postForEntity(uri, dto, TaskDTO.class));
    }

    @Test
    public void testCreateTaskUnuSuccessfulNonExistingCreatedBy() throws URISyntaxException {
        URI uri = new URI(baseUrl);
        TaskDTO dto = new TaskDTO("New user note", "Task data", 1L, -2L);
        assertThrows(HttpClientErrorException.class, () -> restTemplate.postForEntity(uri, dto, TaskDTO.class));
    }

    @Test
    public void testCreateTaskUnuSuccessfulMissingValues() throws URISyntaxException {
        URI uri = new URI(baseUrl);
        TaskDTO dto = new TaskDTO(null, "Task data", 1L, -2L);
        assertThrows(HttpClientErrorException.class, () -> restTemplate.postForEntity(uri, dto, TaskDTO.class));
    }

    @Test
    public void testUpdateTaskSuccessful() throws URISyntaxException {
        URI uri = new URI(baseUrl);
        TaskDTO dto = new TaskDTO("New user note", "Task data", 1L, 2L);
        dto.setId(1L);
        // unfortunately used version of RestTemplate doest not provide response info if it is Ok.. Solution would be to use
        // PATCH http method instead of PUT in controller
        restTemplate.put(uri, dto);
    }

    @Test
    public void testUpdateTaskUnsuccessfulNonExistingTask() throws URISyntaxException {
        URI uri = new URI(baseUrl);
        TaskDTO dto = new TaskDTO("New user note", "Task data", 1L, 2L);
        dto.setId(-1L);
        assertThrows(HttpClientErrorException.class, () -> restTemplate.put(uri, dto));
    }

    @Test
    public void testUpdateTaskUnsuccessfulNonAcqByUser() throws URISyntaxException {
        URI uri = new URI(baseUrl);
        TaskDTO dto = new TaskDTO("New user note", "Task data", -1L, 2L);
        dto.setId(1L);
        assertThrows(HttpClientErrorException.class, () -> restTemplate.put(uri, dto));
    }

    @Test
    public void testUpdateTaskUnsuccessfulNonCreatedByUser() throws URISyntaxException {
        URI uri = new URI(baseUrl);
        TaskDTO dto = new TaskDTO("New user note", "Task data", 1L, -2L);
        dto.setId(1L);
        assertThrows(HttpClientErrorException.class, () -> restTemplate.put(uri, dto));
    }

    @Test
    public void testUpdateTaskUnsuccessfulMissingValues() throws URISyntaxException {
        URI uri = new URI(baseUrl);
        TaskDTO dto = new TaskDTO(null, "Task data", 1L, -2L);
        dto.setId(1L);
        assertThrows(HttpClientErrorException.class, () -> restTemplate.put(uri, dto));
    }
}
