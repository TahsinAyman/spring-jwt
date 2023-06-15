
package com.fullstackbd.tahsin.backend.controller;

import com.fullstackbd.tahsin.backend.service.SSEPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@CrossOrigin(origins = "*")
public class SSEController {

    @Autowired
    private SSEPublisher ssePublisher;

    @GetMapping(value = "/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<String>> getEventStream() {
        return ssePublisher.getEventStream();
    }
}