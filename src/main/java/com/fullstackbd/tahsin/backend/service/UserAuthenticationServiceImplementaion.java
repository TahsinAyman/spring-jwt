package com.fullstackbd.tahsin.backend.service;

import java.util.Date;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fullstackbd.tahsin.backend.entity.Role;
import com.fullstackbd.tahsin.backend.entity.Services;
import com.fullstackbd.tahsin.backend.entity.Token;
import com.fullstackbd.tahsin.backend.entity.User;
import com.fullstackbd.tahsin.backend.model.AuthenticationRequest;
import com.fullstackbd.tahsin.backend.model.AuthenticationResponse;
import com.fullstackbd.tahsin.backend.model.RegisterRequest;
import com.fullstackbd.tahsin.backend.repository.RoleRepository;
import com.fullstackbd.tahsin.backend.repository.TokenRepository;
import com.fullstackbd.tahsin.backend.repository.UserRepository;

@Service
public class UserAuthenticationServiceImplementaion implements UserAuthenticationService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private JwtService jwtService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private AuthenticationManager authManager;
	@Autowired
	private TokenRepository tokenRepository;
	@Autowired
	private EmailService emailService;
	@Autowired
	private EmailCheckerService emailCheckerService;
	@Autowired
	private RoleRepository roleRepository;
	
	
	@Override
	public String uniqueEncryptedTextGenerator() {
		Date date = new Date();
		Long timestampInMillSeconds = date.getTime();
		String timestampInString = timestampInMillSeconds.toString();
		String encryptedText = DatatypeConverter.printHexBinary(timestampInString.getBytes());
		return encryptedText;
	}
	
	@Override
	@CacheEvict("users")
	public void register(RegisterRequest request, String emailVerifyUrl) throws Exception {
		if (!emailCheckerService.checkEmailExists(request.getEmail())) {
			throw new Exception("Mail dosent exist");
		}
		Role userRole = roleRepository.findByName("USER");
		User user = User.builder().firstName(request.getFirstName()).lastName(request.getLastName())
				.email(request.getEmail()).isEmailVerified(false)
				.password(passwordEncoder.encode(request.getPassword()))
				.verificationCode(this.uniqueEncryptedTextGenerator())
				.roles(List.of(userRole))
				.build();
		for (Services service: userRole.getServices()) {
			user.addService(service);
		}
		userRepository.save(user);
		String url = emailVerifyUrl + user.getVerificationCode();
		emailService.sendEmail(user.getEmail(), "Verify Your Email",
				"<a href=\"" + url + "\">" + "Click here to verify your Email Address." + "</a>");
	}

	@Override
	public AuthenticationResponse authenticate(AuthenticationRequest request) throws Exception {
		authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		User user = userRepository.findByEmail(request.getEmail())
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
		String jwtToken = jwtService.generateToken(user);
		Token token = tokenRepository.findByUser(user).orElseThrow(() -> new Exception("Token Not Found"));
		token.setToken(jwtToken);
		token.setIsValid(true);
		tokenRepository.save(token);
		return AuthenticationResponse.builder().token(jwtToken).build();
	}

	@Override
	public Boolean logout(String token) throws Exception {
		return jwtService.logout(token);
	}

	@Override
	public void verifyEmail(String verificationCode) throws Exception {
		User user = userRepository.findByVerificationCode(verificationCode).orElseThrow(() -> new Exception("Invalid verificaion code"));
		if (!user.getIsEmailVerified() == true) {
			user.setIsEmailVerified(true);
			String jwtToken = jwtService.generateToken(user);
			Token token = Token
					.builder()
					.token(jwtToken)
					.user(user)
					.isValid(true)
					.build();
			tokenRepository.save(token);
			userRepository.save(user);
		} else {
			throw new Exception("Email is already Verified");
		}

	}

}
