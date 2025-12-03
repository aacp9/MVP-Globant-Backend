package cl.aacp9.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import com.fasterxml.jackson.core.type.TypeReference;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TestUtil {
	private static ObjectMapper mapper = new ObjectMapper(); // createObjectMapper();

	  public static String toJson(Object object) {
		    try {
		      return mapper.writeValueAsString(object);
		    } catch (Exception e) {
		      throw new RuntimeException("Error serializing object to JSON", e);
		    }
		  }
	
	public static <T> T loadObjectFromResource(String resourcePath, Class<T> valueType)
		      throws IOException {
		    try (InputStream inputStream =
		        TestUtil.class.getClassLoader().getResourceAsStream(resourcePath)) {
		      if (inputStream == null) {
		        throw new IllegalArgumentException("The resource was not found: " + resourcePath);
		      }
		      return mapper.readValue(inputStream, valueType);
		    }
		  }
	
	public static <T> T loadObjectFromResource(String resourcePath, TypeReference<T> typeReference) {
	    try (InputStream inputStream =
	        Objects.requireNonNull(TestUtil.class.getClassLoader().getResourceAsStream(resourcePath))) {
	      return mapper.readValue(inputStream, typeReference);
	    } catch (Exception e) {
	      throw new RuntimeException("Error loading object from resource: " + resourcePath, e);
	    }
	  }


	
}
