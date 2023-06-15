package com.fullstackbd.tahsin.backend.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SSEPayload<T> {
	private String type;
    private T payload;
    private String url;
    private Object body;
    private String method;
    private List<?> query;
    private List<?> params;
    private Integer status;
}