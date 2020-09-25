package codebase.rampupproject;

import org.json.JSONException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

//import org.springframework.http.HttpHeaders;
//import org.springframework.http.ResponseEntity;
//import org.springframework.http.HttpHeaders;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import org.json.JSONObject;
//import org.json.simple.JSONObject;
import org.json.JSONArray;
//import org.json.simple.parser.ParseException;
//import org.json.simple.parser.JSONParser;

@SpringBootApplication
@RestController
public class RampUpProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(RampUpProjectApplication.class, args);
	}

	@GetMapping("/myghibli")
	public String myghibli() throws JSONException {
		Scanner myObj = new Scanner(System.in);

		System.out.println("What kind of landscape would you like? Enter r for river or h for hill");
		String locationType = myObj.nextLine();
		locationType = locationType.equals("r") ? "River" : "Hill";

		System.out.println("What kind of friend would you like? Enter c for cat or t for totoro");
		String friendType = myObj.nextLine();
		friendType = friendType.equals("c") ? "Cat" : "Totoro";

		String location = getLocation(locationType);
		String friend = getFriend(friendType);

		return "Your ghibli home is " + location + " and your ghibli friend is " + friend + "!";
	}

	private static String getLocation(String locationType) throws JSONException {
		final String uri = "https://ghibliapi.herokuapp.com/locations";

		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.getForObject(uri, String.class);
		JSONArray locations = new JSONArray(result);
		List<String> names = new ArrayList<>();

		for (int i = 0; i < locations.length(); i++) {
			JSONObject location = locations.getJSONObject(i);
			if (location.getString("terrain").equals(locationType)) {
				names.add(location.getString("name"));
			}
		}

		return names.get((int) (Math.random() * names.size()));
	}

	private static String getFriend(String friendType) throws JSONException {
		final String totoro = "https://ghibliapi.herokuapp.com/species/74b7f547-1577-4430-806c-c358c8b6bcf5";
		final String cat = "https://ghibliapi.herokuapp.com/species/603428ba-8a86-4b0b-a9f1-65df6abef3d3";

		String species = friendType.equals("Cat") ? cat : totoro;

		final String uri = "https://ghibliapi.herokuapp.com/people";

		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.getForObject(uri, String.class);
		JSONArray friends = new JSONArray(result);
		List<String> names = new ArrayList<>();

		for (int i = 0; i < friends.length(); i++) {
			JSONObject friend = friends.getJSONObject(i);
			if (friend.getString("species").equals(species)) {
				names.add(friend.getString("name"));
			}
		}

		return names.get((int) (Math.random() * names.size()));
	}

}
