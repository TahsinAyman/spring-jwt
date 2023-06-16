package com.fullstackbd.tahsin.backend.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailCheckerServiceImplemetation implements EmailCheckerService {
	public Boolean checkEmailExists(String email) {
		String apiUrl = "http://192.168.0.122:8000/?email=" + email;
		System.out.println(apiUrl);
		try {
			URL url = new URL(apiUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");

			int responseCode = connection.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String line;
				StringBuilder response = new StringBuilder();

				while ((line = reader.readLine()) != null) {
					response.append(line);
				}
				reader.close();
				System.out.println(response.toString());
				if (response.toString().equals("true")) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		} catch (IOException e) {
			return false;
		}
	}
//	@Autowired
//	private EmailVerifierClient emailVerifierClient;
//	public Boolean checkEmailExists(String email) {
//		System.out.println(emailVerifierClient.verifyEmail("mail4tahsin@gmail.com"));
//		return emailVerifierClient.verifyEmail(email);
//	}
}
