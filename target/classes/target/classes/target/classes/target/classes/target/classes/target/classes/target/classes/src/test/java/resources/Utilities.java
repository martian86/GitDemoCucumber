package resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class Utilities {
	static RequestSpecification reqSpecObject;
	static PrintStream log;
	
	public RequestSpecification getRequestSpecs() throws IOException
	{
		if(log == null)
		{
			log = new PrintStream(new FileOutputStream("logging.txt"));
			reqSpecObject =  new RequestSpecBuilder()
					   .setBaseUri(getGlobalURI("baseURI"))
					   .addFilter(RequestLoggingFilter.logRequestTo(log))
					   .addFilter(ResponseLoggingFilter.logResponseTo(log))
					   .addQueryParam("key", "qaclick123").build();
		}
		
		return reqSpecObject;
		   

	}
	
	public ResponseSpecification getResponseSpecs()
	{
		   ResponseSpecification responseSpecObject =  new ResponseSpecBuilder().expectContentType(ContentType.JSON)
				   .expectStatusCode(200).build();
		   
		   return responseSpecObject;
	}
	
	public String getGlobalURI(String key) throws IOException
	{
		String basePath = System.getProperty("user.dir");
		String filePath = basePath+"\\src\\test\\java\\resources\\global.properties";
		
		Properties props = new Properties();
		FileInputStream fis = new FileInputStream(new File(filePath));
		props.load(fis);
		
		return props.getProperty("baseURI");
		
	}
	
	public String getJsonPath(String key, Response response)
	{
		
		JsonPath js = new JsonPath(response.asString());
		return js.getString(key);
		
	}

}
