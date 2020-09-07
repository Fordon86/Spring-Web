package com.crud.tasks.controller;

import com.crud.tasks.domain.*;
import com.crud.tasks.mapper.CreatedTrelloCardDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.crud.tasks.trello.facade.TrelloFacade;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.is;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
class TaskControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DbService dbService;

    @MockBean
    private TaskMapper taskMapper;

    @Test
    public void shouldFetchEmptyTaskController() throws Exception {
        //Given
        List<Task> taskList = new ArrayList<>();
        when(dbService.getAllTasks()).thenReturn(taskList);

        //When & Given
        mockMvc.perform(get("/v1/task/getTasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200)) // or is Ok()
                .andExpect(jsonPath("$",hasSize(0)));
    }

    @Test
    public void shouldReturnTaskController() throws Exception {
        //Given
        Task task1 = new Task(1L, "clean", "clean room");
        TaskDto taskDto1 = new TaskDto(1L, "clean", "clean room");
        Optional <Task> optionalTask = Optional.of(task1);
        when(dbService.getTask(1L)).thenReturn(optionalTask);
        when(taskMapper.mapToTaskDto(task1)).thenReturn(taskDto1);

        //When & Given
        mockMvc.perform(get("/v1/task/getTask")
                .param("taskId","1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200)) // or is Ok()
                .andExpect(jsonPath("$.title",is("clean")))
                .andExpect(jsonPath("$.content",is("clean room")));
    }

    @Test
    public void shouldCreatTaskController() throws Exception {
        //Given
        TaskDto taskDto1 = new TaskDto(1L, "clean", "clean room");

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto1);

        when(taskMapper.mapToTask(any(TaskDto.class))).thenReturn(mock(Task.class));

        //When
        mockMvc.perform(post("/v1/task/createTask")
                .content(jsonContent)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200)); // or is Ok()

        //Then
        Mockito.verify(dbService).saveTask(any(Task.class));
    }

    @Test
    public void shouldDeleteTaskController() throws Exception {

        //When
        mockMvc.perform(delete("/v1/task/deleteTask")
                .param("taskId","1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200)); // or is Ok()

        //Then
        Mockito.verify(dbService).deleteTask(1L);
    }

    @Test
    public void shouldUpdateTaskController() throws Exception {

        //Given
        TaskDto taskDto1 = new TaskDto(1L, "clean", "clean room");
        Task task1 = new Task(1L, "clean", "clean room");

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto1);

        when(dbService.saveTask(any(Task.class))).thenReturn(task1);
        when(taskMapper.mapToTask(any(TaskDto.class))).thenReturn(mock(Task.class));
        when(taskMapper.mapToTaskDto(task1)).thenReturn(taskDto1);

        //When
        mockMvc.perform(put("/v1/task/updateTask")
                .content(jsonContent)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200)) // or is Ok()
                .andExpect(jsonPath("$.title",is("clean")))
                .andExpect(jsonPath("$.content",is("clean room")));

        //Then
        Mockito.verify(dbService).saveTask(any(Task.class));
    }

}