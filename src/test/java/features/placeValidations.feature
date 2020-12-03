Feature: Validating Place APIs

	
	@AddPlace
	Scenario Outline: Verify if place is successfully added using AddPlace API
	Given Add place payload with "<NameofPerson>" "<Language>" "<Address>"
	When user calls "AddPlaceAPI" with HTTP "POST" request
	Then the response API call is success with status code 200
	And verify "status" in response body is "OK"
	And verify "scope" in response body is "APP"
	And verify "place_id" and "name" maps to "<NameofPerson>" using "GetPlaceAPI"
	
	
	Examples:
	|NameofPerson		|Language		|Address	|
	|asdfg qwerty		|Kannada		|Bangalore	|
	#|Sachin Tendulkar	|Marathi		|Mumbai		|
	#|Sourav Ganguly		|Bengali		|Kolkata	|
	#|Marcus Stoinis		|English		|Melbourne	|

	@DeletePlace
	Scenario: Verify if delete API functionality is working
	
	Given Body payload for delete API
	When user calls "DeletePlaceAPI" using HTTP "POST" method
	Then the status_code should be 200
	And  The place should be deleted with "status" in response is "OK"