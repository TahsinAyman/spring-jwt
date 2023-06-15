package com.fullstackbd.tahsin.backend.model;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Email {
    private String to;
    private String subject;
    private String body;
}
