package com.fullstackbd.tahsin.backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fullstackbd.tahsin.backend.model.SSEPayload;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Component;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;
import java.time.Duration;

@SuppressWarnings("deprecation")
@Component
public class SSEPublisher {

    private final EmitterProcessor<ServerSentEvent<String>> eventProcessor = EmitterProcessor.create();

    public void publishEvent(SSEPayload<?> event) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        String json = mapper.writeValueAsString(event);
        ServerSentEvent<String> sseEvent = ServerSentEvent.builder(json).build();
        eventProcessor.onNext(sseEvent);
    }

    public Flux<ServerSentEvent<String>> getEventStream() {
        return eventProcessor
                .concatMap(event -> Flux.just(event).delayElements(Duration.ofSeconds(1)))
                .share();
    }
}