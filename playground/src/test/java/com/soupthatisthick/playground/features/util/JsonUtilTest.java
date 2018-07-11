package com.soupthatisthick.playground.features.util;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeCreator;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.soupthatisthick.playground.features.log.model.HttpServletRequestSnapshot;
import com.soupthatisthick.playground.features.log.model.ListLogsRequest;
import com.soupthatisthick.playground.features.log.model.LogEntity;
import com.soupthatisthick.playground.util.json.JsonUtil;
import com.soupthatisthick.playground.util.logger.Logger;
import com.soupthatisthick.playground.util.podam.PodamUtil;
import org.junit.Test;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import static com.soupthatisthick.playground.util.logger.Logger.LOG;
import static org.junit.Assert.assertEquals;

public class JsonUtilTest {

	private class NotSerializable {
		public final String name;

		private NotSerializable(String name) {
			this.name = name;
		}
	}

	public class IsSerializable implements Serializable {
		public final String name;

		private IsSerializable(String name) {
			this.name = name;
		}
	}


	@Test
	public void testJson() {
		ListLogsRequest listLogsRequest = PodamUtil.manufacture(ListLogsRequest.class);
		LogEntity logEntity1 = new LogEntity();
		logEntity1.setRequest(JsonUtil.toJson(listLogsRequest, true));
		logEntity1.setMethod(RequestMethod.GET);

		final String asJson1 = JsonUtil.toJson(logEntity1, true);

		final LogEntity logEntity2 = JsonUtil.toObject(asJson1, LogEntity.class);

		final String asJson2 = JsonUtil.toJson(logEntity2, true);

		assertEquals(asJson1, asJson2);

		LOG.debug("\n{}", asJson1);
	}

	@Test
	public void testJsonNode() throws IOException {
		ListLogsRequest listLogsRequest = PodamUtil.manufacture(ListLogsRequest.class);
		LogEntity logEntity1 = new LogEntity();
		logEntity1.setRequest(JsonUtil.toJson(listLogsRequest, true));
		logEntity1.setMethod(RequestMethod.GET);

		final String asJson1 = JsonUtil.toJson(logEntity1, true);

		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode = objectMapper.readTree(asJson1);

		LOG.debug("\n{}", jsonNode.toString());
	}



	@Test
	public void toJsonNode() {
		Object[] objects = new Object[] {
			null,
			"Hello World",
			new NotSerializable("Not Serializable"),
			new IsSerializable("Is Serializable")
		};

		JsonNode nodes[] = new JsonNode[objects.length];
		for(int i=0; i<nodes.length; i++) {
			try {
				nodes[i] = JsonUtil.toJsonNode(objects[i]);
			} catch (Exception e) {
				LOG.error("While creating node[" + i + "]: " + e.getMessage(), e);
			}
		}

		for(int i=0; i<nodes.length; i++) {
			LOG.debug("\n{}", nodes[i]);
		}

	}

	@Test
	public void httpServletRequest() {
		HttpServletRequestSnapshot request = PodamUtil.manufacture(HttpServletRequestSnapshot.class);
		LOG.debug("{}", JsonUtil.toJson(request, true, true));
	}
}
