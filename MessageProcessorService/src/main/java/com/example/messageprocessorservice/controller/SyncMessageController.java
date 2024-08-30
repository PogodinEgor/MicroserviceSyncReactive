package com.example.messageprocessorservice.controller;

import com.example.messageprocessorservice.model.SyncMessage;
import com.example.messageprocessorservice.model.SyncMessageResponse;
import com.example.messageprocessorservice.service.SyncMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sync")
@RequiredArgsConstructor
public class SyncMessageController {
    private final SyncMessageService syncMessageService;

    @PostMapping("/generate")
    public ResponseEntity<?> generateMessage(@RequestParam(required = false) Integer count){
        syncMessageService.generateMessage(count);
        return ResponseEntity.ok(new SyncMessageResponse("Сообщения успешно сгенерированны!"));
    }

    @PatchMapping ("/send")
    public void sendMessagesToQueue(){
        syncMessageService.sendMessagesToQueue();
    }

    @GetMapping("getAll")
    public ResponseEntity<?> getAllMessages(){
        return ResponseEntity.ok(syncMessageService.getMessages());
    }
}
