package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class TaskMapperTest {

    private TaskMapper taskMapper = new TaskMapper();

    @Test
    public void mapToTaskTest(){
        //Given
        TaskDto taskDto = new TaskDto(1L, "Clean","Clean room");

        //When
        Task task = taskMapper.mapToTask(taskDto);

        //Then
        assertEquals(task.getId(),taskDto.getId());
        assertEquals(task.getTitle(),taskDto.getTitle());
        assertEquals(task.getContent(),taskDto.getContent());
    }

    @Test
    public void mapToTaskDtoTest(){
        //Given
        Task task = new Task(1L, "Clean","Clean room");

        //When
        TaskDto taskDto = taskMapper.mapToTaskDto(task);

        //Then
        assertEquals(taskDto.getId(),task.getId());
        assertEquals(taskDto.getTitle(),task.getTitle());
        assertEquals(taskDto.getContent(),task.getContent());
    }

    @Test
    public void mapToTaskDtoListTest(){
        //Given
        List<Task> tasks = new ArrayList<>();
        Task task = new Task(1L, "Clean","Clean room");
        tasks.add(task);

        //When
        List<TaskDto> taskDtos = taskMapper.mapToTaskDtoList(tasks);

        //Then
        assertEquals(taskDtos.get(0).getId(),tasks.get(0).getId());
        assertEquals(taskDtos.get(0).getTitle(),tasks.get(0).getTitle());
        assertEquals(taskDtos.get(0).getContent(),tasks.get(0).getContent());
    }

}
