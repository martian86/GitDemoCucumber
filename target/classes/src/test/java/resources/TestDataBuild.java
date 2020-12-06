package resources;

import java.util.ArrayList;
import java.util.List;

import pojo.GoogleMapsAPI;
import pojo.Location;

public class TestDataBuild {
	
	
	public GoogleMapsAPI addPlacePayload(String name, String language, String address)
	{
		GoogleMapsAPI maps = new GoogleMapsAPI();
		
		
		List <String>types = new ArrayList<String>();
		types.add("shoe park");
		types.add("shop");
		
		
		Location location = new Location();
		location.setLat(-23.697463);
		location.setLng(39.427362);
		
		
		maps.setLocation(location);
		maps.setAccuracy(50);
		maps.setName(name);
		maps.setPhone_number("+91 84155 84455");
		maps.setAddress(address);
		maps.setWebsite("https://google.com");
		maps.setLanguage(language);
		maps.setTypes(types);
		
		
		return maps;
	}
	
	public String getPlaceIDForDeleteAPI(String place_id)
	{
		return "{\r\n" + 
				"    \"place_id\":\""+place_id+"\"\r\n" + 
				"}";
	}

}
