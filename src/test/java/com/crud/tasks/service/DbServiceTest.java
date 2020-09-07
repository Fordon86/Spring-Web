package com.crud.tasks.service;

import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DbServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private DbService dbService = new DbService();

    @Test
    public void getAllTaskTest() {
        //Given
        List<Task> taskList = new ArrayList<>();
        Task task1 = mock(Task.class);
        Task task2 = mock(Task.class);
        Task task3 = mock(Task.class);
        taskList.add(task1);
        taskList.add(task2);
        taskList.add(task3);

        when(taskRepository.findAll()).thenReturn(taskList);
        List<Task> result = dbService.getAllTasks();

        //When & //Then
        assertEquals(3, result.size());
    }

    @Test
    public void saveTaskTest (){
        //Given
        Task task1 = new Task();

        when(taskRepository.save(task1)).thenReturn(task1);
        //When
        Task result = dbService.saveTask(task1);

        //Then
        assertEquals(task1, result);
    }

    @Test
    public void getTaskTest (){
        //When
        dbService.getTask(1L);

        //When & //Then
        Mockito.verify(taskRepository).findById(1L);
    }

    @Test
    public void deleteTask() {
        //When
        dbService.deleteTask(2L);

        //Then
        Mockito.verify(taskRepository).deleteById(2L);
    }

}
