package com.adee.samples.objectmapper.run;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.adee.samples.objectmapper.model.Country;
import com.adee.samples.objectmapper.model.Info;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class ObjectMapperUtils {

	private static ObjectMapper objectMapper = new ObjectMapper();
	private static Country country = new Country("India", 135260000000L, 29, true);
	private static String countryStr = "{\"name\":\"India\",\"population\":135260000000,\"numberOfProvinces\":29,\"developed\":true}";

	public static void objectToJsonString() throws JsonProcessingException {
		String countryAsString = objectMapper.writeValueAsString(country);
		System.out.println("objectToJsonString : " + countryAsString + "\n");
	}

	public static void objectToJsonInFile() throws IOException {
		objectMapper.writeValue(new File("target/country.json"), country);
	}

	public static void objectToBytes() throws JsonProcessingException {
		byte[] countryAsBytes = objectMapper.writeValueAsBytes(country);
		System.out.println("objectToBytes : " + countryAsBytes + "\n");
	}

	public static void jsonStrToObject() throws IOException {
		Country countryFromString = objectMapper.readValue(countryStr, Country.class);
		System.out.println("jsonStrToObject : " + countryFromString + "\n");
	}

	public static void jsonInFileToObject() throws IOException {
		Country countryFromFile = objectMapper.readValue(new File("target/country.json"), Country.class);
		System.out.println("jsonInFileToObject : " + countryFromFile + "\n");
	}

	public static void readJsonStringAsMap() throws IOException {
		Map<String, Object> jsonStringToMap = objectMapper.readValue(countryStr,
				new TypeReference<Map<String, Object>>() {
				});
		System.out.println("JsonStringToMap : " + jsonStringToMap + "\n");
	}

	public static void readJsonArrayStringAsList() throws IOException {
		String countryArrayStr = "[{\"name\":\"India\",\"population\":135260000000,"
				+ "\"numberOfProvinces\":29,\"developed\":true},{\"name\":\"SomeCountry\","
				+ "\"population\":123456789000,\"numberOfProvinces\":45," + "\"developed\":true}]";
		List<Country> countryArrayAsList = objectMapper.readValue(countryArrayStr, new TypeReference<List<Country>>() {
		});
		System.out.println("JsonArrayStringToList " + countryArrayAsList + "\n");
	}

	public static void parseJsonStringAsJsonNode() throws IOException {
		JsonNode jsonNode = objectMapper.readTree(countryStr);
		String name = jsonNode.get("name").asText();
		Long population = jsonNode.get("population").asLong();
		Integer provinces = jsonNode.get("numberOfProvinces").asInt();
		boolean isDeveloped = jsonNode.get("developed").asBoolean();
		System.out.println("parseJsonStringAsJsonNode - Name = " + name + " Population = " + population + " Provinces "
				+ provinces + " isDeveloped " + isDeveloped + "\n");
	}

	public static void createJsonNodeStr() throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		ObjectNode root = objectMapper.createObjectNode();
		root.put("asText", "SampleString");
		root.put("asBoolean", false);
		ArrayNode array = root.putArray("asArray");
		Country country = new Country("India", 135260000000L, 29, true);
		Country countryFromFile = objectMapper.readValue(new File("target/country.json"), Country.class);
		array.addPOJO(country);
		array.addPOJO(countryFromFile);
		System.out
				.println("createJsonNodeStr " + objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(root));
	}

	public static void demoUnknownField() throws IOException {
		String countryStrUnknownField = "{\"name\":\"India\",\"population\":135260000000,"
				+ "\"numberOfProvinces\":29,\"developed\":true, " + "\"extraField\":\"some-value\"}";
		Country countryUnknownField;
		try {
			countryUnknownField = objectMapper.readValue(countryStrUnknownField, Country.class);
		} catch (Exception ex) {
			System.out.println("demoUnknownField : Exception - Message " + "\n");
			ex.printStackTrace();
		}
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		countryUnknownField = objectMapper.readValue(countryStrUnknownField, Country.class);
		System.out.println("demoUnknownField " + countryUnknownField + "\n");
	}

	public static void demoNullPrimitiveValues() throws IOException {
		Country countryPrimitiveNull = null;
		objectMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, true);
		String countryStrPrimitiveNull = "{\"name\":\"India\"," + "\"population\":135260000000,\"numberOfProvinces\""
				+ ":null,\"developed\":true}";
		try {
			countryPrimitiveNull = objectMapper.readValue(countryStrPrimitiveNull, Country.class);
			System.out.println("demoNullPrimitiveValues " + countryPrimitiveNull + "\n");
		} catch (Exception ex) {
			System.out.println("demoNullPrimitiveValues : Exception - Message ");
			ex.printStackTrace();
		}
	}

	public static void objectWithDateToJsonString() throws JsonProcessingException {
		DateFormat df = new SimpleDateFormat("EEE MMM dd HH:mm:ssZ yyyy");
		objectMapper.setDateFormat(df);
		String objWithDateAsJsonString = objectMapper.writeValueAsString(new Info(country, new Date()));
		System.out.println("objectWithDateToJsonString " + objWithDateAsJsonString + "\n");
		// Prints {"country":{"name":"India","population":135260000000,
		// "numberOfProvinces":29,"developed":true},
		// "now":"Wed Jun 10 08:50:42+0530 2020"}
	}

	public static void jsonStringWithDateToObject() throws IOException {
		String infoAsString = "{\"country\":{\"name\":\"India\","
				+ "\"population\":135260000000,\"numberOfProvinces\":29,"
				+ "\"developed\":true},\"now\":\"Tue Jan 01 01:01:01+0230 2020\"}";
		Info info = objectMapper.readValue(infoAsString, Info.class);
		System.out.println("jsonStringWithDateToObject " + info.getNow() + "\n");
		// Prints Wed Jan 01 04:01:01 IST 2020
	}

	public static void demoCustomSerializer() throws JsonProcessingException {

		class CustomCountrySerializer extends StdSerializer<Country> {

			private static final long serialVersionUID = 1L;

			public CustomCountrySerializer() {
				this(null);
			}

			public CustomCountrySerializer(Class<Country> clazz) {
				super(clazz);
			}

			@Override
			public void serialize(Country country, JsonGenerator jsonGenerator, SerializerProvider serializer)
					throws IOException {
				jsonGenerator.writeStartObject();
				jsonGenerator.writeStringField("country_name_only_field", country.getName());
				jsonGenerator.writeEndObject();
			}
		}
		ObjectMapper oMapper = new ObjectMapper();
		SimpleModule simpleModule = new SimpleModule("CustomCountrySerializer", new Version(1, 0, 0, null, null, null));
		simpleModule.addSerializer(Country.class, new CustomCountrySerializer());
		oMapper.registerModule(simpleModule);
		String countryJsonFromCustomSerializer = oMapper.writeValueAsString(country);
		System.out.println("demoCustomSerializer : " + countryJsonFromCustomSerializer);
	}

	public static void demoCustomDeSerializer() throws JsonMappingException, JsonProcessingException {
		class CustomCountryDeserializer extends StdDeserializer<Country> {

			private static final long serialVersionUID = 1L;

			public CustomCountryDeserializer() {
				this(null);
			}

			public CustomCountryDeserializer(Class<?> clazz) {
				super(clazz);
			}

			@Override
			public Country deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
					throws IOException {
				Country country = new Country();
				JsonNode jsonNode = jsonParser.getCodec().readTree(jsonParser);
				JsonNode customNameNode = jsonNode.get("customObjectName");
				String name = customNameNode.asText();
				country.setName(name);
				country.setNumberOfProvinces(Integer.MAX_VALUE);
				country.setPopulation(Long.MAX_VALUE);
				return country;
			}
		}
		String incompleteCountryJsonStr = "{\"customObjectName\":\"India\"}";
		ObjectMapper oMapper = new ObjectMapper();
		SimpleModule simpleModule = new SimpleModule("CustomCountrySerializer", new Version(1, 0, 0, null, null, null));
		simpleModule.addDeserializer(Country.class, new CustomCountryDeserializer());
		oMapper.registerModule(simpleModule);
		Country country = oMapper.readValue(incompleteCountryJsonStr, Country.class);
		System.out.println("demoCustomDeSerializer : " + country);
	}
}
