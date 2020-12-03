package stepdefinitions;

import static io.restassured.RestAssured.given;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utilities;
import static org.junit.Assert.*;
import java.io.IOException;


public class stepDefinition extends Utilities {
	
	public Response response;
	String responseString;
	RequestSpecification reqSpec;
	ResponseSpecification responseSpecObject;
	static String place_id;
	TestDataBuild testData = new TestDataBuild();
	
		@Given("Add place payload with {string} {string} {string}")
		public void add_place_payload_with(String name, String language, String address) throws IOException {
		    // Write code here that turns the phrase above into concrete actions
			//testData = new TestDataBuild();
			reqSpec= given().log().all().spec(getRequestSpecs())
					.body(testData.addPlacePayload(name, language, address));
			
		}

		@When("user calls {string} with HTTP {string} request")
		public void user_calls_with_http_request(String APIName, String method) {
		    // Write code here that turns the phrase above into concrete actions

			APIResources enumResource=APIResources.valueOf(APIName);
			if(method.equalsIgnoreCase("POST"))
			{
				response = reqSpec.when().post(enumResource.getResource())
						  .then().log().all()
						  .spec(getResponseSpecs()).extract().response();
			}
			else if(method.equalsIgnoreCase("GET"))
			{
				response = reqSpec.when().get(enumResource.getResource())
				.then().log().all().spec(getResponseSpecs()).extract().response();
			}
			else if(method.equalsIgnoreCase("UPDATE"))
			{
				response = reqSpec.when().put(enumResource.getResource())
				.then().log().all().spec(getResponseSpecs()).extract().response();
			}
			else if(method.equalsIgnoreCase("DELETE"))
			{
				response = reqSpec.when().delete(enumResource.getResource())
				.then().log().all().spec(getResponseSpecs()).extract().response();
			}
			
		   
		}
		@Then("the response API call is success with status code {int}")
		public void the_response_api_call_is_success_with_status_code(Integer int1) {
		    // Write code here that turns the phrase above into concrete actions
			assertEquals(response.getStatusCode(), 200);
		}
		@And("verify {string} in response body is {string}")
		public void verify_in_response_body_is(String key, String value) {
						
			//responseString = response.asString();
			//js = new JsonPath(responseString);
			assertEquals(getJsonPath(key, response), value);
			
			
		}
		
		@And("verify {string} and {string} maps to {string} using {string}")
		public void verify_place_id_and_name_maps_to_name_using(String placeID_key, String name_key, String expectedName, String APIName) throws IOException {
			
			place_id = getJsonPath("place_id", response);
			//APIResources enumResource=APIResources.valueOf(APIName);
			reqSpec= given().log().all().spec(getRequestSpecs())
					.queryParam(placeID_key, place_id);
					/*Response getReqResponse = reqSpec.when().get(enumResource.getResource())
			.then().log().all().extract().response();
			String actualName=getJsonPath(name_key, getReqResponse);*/
			
			user_calls_with_http_request(APIName, "GET");
			String actualName=getJsonPath(name_key, response);
			assertEquals(expectedName, actualName);
			
		}
		
		@Given("Body payload for delete API")
		public void body_payload() throws IOException {
			reqSpec = given().log().all()
					.spec(getRequestSpecs())
					.body(testData.getPlaceIDForDeleteAPI(place_id));
		}

		@When("user calls {string} using HTTP {string} method")
		public void user_calls_using_http_method(String APIName, String method) {
		    // Write code here that turns the phrase above into concrete actions
			user_calls_with_http_request(APIName, method);
		}
		@Then("the status_code should be {int}")
		public void the_status_code_should_be(int statusCode) {
		    // Write code here that turns the phrase above into concrete actions
			the_response_api_call_is_success_with_status_code(statusCode);
		}
		@Then("The place should be deleted with {string} in response is {string}")
		public void the_place_should_be_deleted_with_in_response_is(String status, String statusResponse) {
		    // Write code here that turns the phrase above into concrete actions
			verify_in_response_body_is(status, statusResponse);
		}




		
		}
