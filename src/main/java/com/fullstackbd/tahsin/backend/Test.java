package com.fullstackbd.tahsin.backend;

public class Test {
	public static void main(String[] args) {
		String url = "/api/v1/person/fetch/1";
		System.out.println("/api/v1/person/fetch/\\d+");
		System.out.println(url.matches("/api/v1/person/fetch/\\d+"));
	}
}
