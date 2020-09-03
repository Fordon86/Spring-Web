package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class TrelloMapperTest {

    private TrelloMapper trelloMapper = new TrelloMapper();

    @Test
    public void mapToBoardsTest(){
        //Given
        List<TrelloListDto> trelloLists = new ArrayList<>();
        trelloLists.add(new TrelloListDto("10", "list", false));

        List<TrelloBoardDto> trelloBoardDtoList = new ArrayList<>();
        trelloBoardDtoList.add(new TrelloBoardDto("1", "test", trelloLists));

        //When
        List<TrelloBoard> trelloBoardList = trelloMapper.mapToBoards(trelloBoardDtoList);

        //Then
        assertEquals(1,trelloBoardList.size());
        assertEquals("test", trelloBoardDtoList.get(0).getName());
        assertEquals("1", trelloBoardDtoList.get(0).getId());
        assertEquals(1, trelloBoardDtoList.get(0).getLists().size());
        assertEquals("list", trelloBoardDtoList.get(0).getLists().get(0).getName());
        assertEquals("10", trelloBoardDtoList.get(0).getLists().get(0).getId());
        assertEquals(false, trelloBoardDtoList.get(0).getLists().get(0).isClosed());
    }

    @Test
    public void mapToBoardsDto(){
        //Given
        List<TrelloList> trelloLists = new ArrayList<>();
        trelloLists.add(new TrelloList("20", "list", false));

        List<TrelloBoard> trelloBoardList = new ArrayList<>();
        trelloBoardList.add(new TrelloBoard("1", "test", trelloLists));

        //When
        List<TrelloBoardDto> trelloBoardLists = trelloMapper.mapToBoardsDto(trelloBoardList);

        //Then
        assertEquals(1,trelloBoardLists.size());
        assertEquals("test", trelloBoardList.get(0).getName());
        assertEquals("1", trelloBoardList.get(0).getId());
        assertEquals(1, trelloBoardList.get(0).getLists().size());
        assertEquals("list", trelloBoardList.get(0).getLists().get(0).getName());
        assertEquals("20", trelloBoardList.get(0).getLists().get(0).getId());
        assertEquals(false, trelloBoardList.get(0).getLists().get(0).isClosed());
    }

    @Test
    public void mapToCard(){
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("Card", "xyz", "1", "2");

        //When
        TrelloCard trelloCard = trelloMapper.mapToCard(trelloCardDto);

        //The
        assertEquals(trelloCard.getName(), trelloCardDto.getName());
        assertEquals(trelloCard.getDescription(), trelloCardDto.getDescription());
        assertEquals(trelloCard.getPos(), trelloCardDto.getPos());
        assertEquals(trelloCard.getListId(), trelloCardDto.getListId());
    }

    @Test
    public void mapToCardDto(){
        //Given
        TrelloCard trelloCard = new TrelloCard("Card", "xyz", "1", "2");

        //When
        TrelloCardDto trelloCardDto = trelloMapper.mapToCardDto(trelloCard);

        //The
        assertEquals(trelloCardDto.getName(), trelloCard.getName());
        assertEquals(trelloCardDto.getDescription(), trelloCard.getDescription());
        assertEquals(trelloCardDto.getPos(), trelloCard.getPos());
        assertEquals(trelloCardDto.getListId(), trelloCard.getListId());
    }

}
