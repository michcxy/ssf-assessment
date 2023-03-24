package ibf2022.batch2.ssf.frontcontroller.services;

import java.io.IOException;
import java.io.StringReader;
import java.security.SecureRandom;
import java.util.LinkedList;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.client.RestTemplate;

import ibf2022.batch2.ssf.model.User;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Service
public class AuthenticationService {

	public static final String AUTHENTICATION = "https://auth.chuklee.com/api/authenticate";

	// TODO: Task 2
	// DO NOT CHANGE THE METHOD'S SIGNATURE
	// Write the authentication method in here
	public List<ObjectError> authenticate(String username, String password) throws Exception {
		List<ObjectError> errors = new LinkedList<>();
        FieldError error;
		JsonArrayBuilder arrBuilder = Json.createArrayBuilder();

		JsonObject userObj = Json.createObjectBuilder()
            .add("username", username)
            .add("password", password)
            .build();

		RequestEntity<String> req = RequestEntity.post(AUTHENTICATION)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.body(userObj.toString());

		ResponseEntity<String> resp;
		RestTemplate template = new RestTemplate();
		
		try {
			resp = template.exchange(req, String.class);
		} catch (Exception ex) {
			throw ex;
		}

		String payload = resp.getBody();
		JsonReader reader = Json.createReader(new StringReader(payload));
		JsonObject json = reader.readObject();
			
        return errors;
	}


	// TODO: Task 3
	// DO NOT CHANGE THE METHOD'S SIGNATURE
	// Write an implementation to disable a user account for 30 mins
	public void disableUser(String username) {
	}

	// TODO: Task 5
	// DO NOT CHANGE THE METHOD'S SIGNATURE
	// Write an implementation to check if a given user's login has been disabled
	public boolean isLocked(String username) {
		return false;
	}
}
