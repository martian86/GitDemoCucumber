package stepdefinitions;

import java.io.IOException;

import io.cucumber.java.Before;
import stepdefinitions.stepDefinition;

public class Hooks {
	
	@Before("@DeletePlace")
	public void getTestData() throws IOException
	{
		stepDefinition step = new stepDefinition();
			if(stepDefinition.place_id==null)
			{
				step.add_place_payload_with("Aniruddha", "Marathi", "GSK Colony");
				step.user_calls_with_http_request("AddPlaceAPI", "POST");
				step.verify_place_id_and_name_maps_to_name_using("place_id", "name", "Aniruddha", "GetPlaceAPI");
			}
	}

}
