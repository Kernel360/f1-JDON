package kernel.jdon.util;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.io.ClassPathResource;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonFileReader {

	public static <T> T readJsonFile(String filePath, Class<T> valueType) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		InputStream inputStream = new ClassPathResource(filePath).getInputStream();
		return objectMapper.readValue(inputStream, valueType);
	}
}
