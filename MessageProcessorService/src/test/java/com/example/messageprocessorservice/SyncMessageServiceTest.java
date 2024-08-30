package com.example.messageprocessorservice;

import com.example.messageprocessorservice.exception.CountErrorException;
import com.example.messageprocessorservice.exception.SyncMessageSizeException;
import com.example.messageprocessorservice.model.SyncMessage;
import com.example.messageprocessorservice.repository.SyncMessageRepository;
import com.example.messageprocessorservice.service.SyncMessageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class SyncMessageServiceTest {


    @InjectMocks
    SyncMessageService syncMessageService;

    @Mock
    SyncMessageRepository syncMessageRepository;

    @Test
    public void testGetMessagesReturnsMessages() {
        //given
        List<SyncMessage> messages = new ArrayList<>();
        messages.add(new SyncMessage());
        when(syncMessageRepository.findAll()).thenReturn(messages);

        //when
        List<SyncMessage> result = syncMessageService.getMessages();

        //then
        assertEquals(1, result.size());
    }

    @Test
    public void testGetMessagesThrowsExceptionWhenEmpty() {
        //given
        when(syncMessageRepository.findAll()).thenReturn(new ArrayList<>());

        //when
        SyncMessageSizeException exception = assertThrows(SyncMessageSizeException.class, () -> {
            syncMessageService.getMessages();
        });

        //then
        assertEquals("Список не содержит данных!", exception.getMessage());
    }

    @Test
    public void testGenerateMessage() {
        //given
        int count = 1;
        List<SyncMessage> messages = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            SyncMessage message = new SyncMessage();
            message.setMessage("Message " + i);
            message.setSentTime(LocalDateTime.now());
            message.setProcessedTime(null);
            message.setSyncActive(false);
            messages.add(message);
        }
        when(syncMessageRepository.saveAll(anyList())).thenReturn(messages);

        //when
        syncMessageService.generateMessage(count);

        //then
        assertEquals(1, messages.size());
    }

    @Test
    public void testGenerateMessageWithInvalidCount() {
        // given
        Integer invalidCount = -1;

        // when & then
        CountErrorException exception = assertThrows(CountErrorException.class, () -> {
            syncMessageService.generateMessage(invalidCount);
        });

        //then
        assertEquals("Количество собщений должно быть больше 0 и не может быть пустым!", exception.getMessage());
    }
}
