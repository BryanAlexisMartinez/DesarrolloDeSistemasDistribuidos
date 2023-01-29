package com.adee.samples.objectmapper.run;

import static com.adee.samples.objectmapper.run.ObjectMapperUtils.*;

import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		objectToJsonString();
		objectToJsonInFile();
		objectToBytes();
		jsonStrToObject();
		jsonInFileToObject();
		readJsonStringAsMap();
		readJsonArrayStringAsList();
		parseJsonStringAsJsonNode();
		createJsonNodeStr();
		demoUnknownField();
		demoNullPrimitiveValues();
		objectWithDateToJsonString();
		jsonStringWithDateToObject();
		demoCustomSerializer();
		demoCustomDeSerializer();
	}
}
