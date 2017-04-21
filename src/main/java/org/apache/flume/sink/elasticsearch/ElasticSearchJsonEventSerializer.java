package org.apache.flume.sink.elasticsearch;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.conf.ComponentConfiguration;
import org.elasticsearch.common.io.BytesStream;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.gson.Gson;

public class ElasticSearchJsonEventSerializer implements ElasticSearchEventSerializer {
	  private static final Logger logger = LoggerFactory
		      .getLogger(ElasticSearchSink.class);
	  
	@Override
	public void configure(Context arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void configure(ComponentConfiguration arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public BytesStream getContentBuilder(Event event) throws IOException {
	    XContentBuilder builder = jsonBuilder().startObject();
	    appendBody(builder, event);
	    appendHeaders(builder, event);
	    return builder;
	}
	
	  private void appendBody(XContentBuilder builder, Event event)
		      throws IOException {
		String eventBody = new String(event.getBody());
		logger.debug("appendBody: " + eventBody);
		Gson s = new Gson();
		Map<String, Object> bodys = s.fromJson(eventBody, Map.class);
		if (bodys != null) {
			logger.debug("appendBody map: " + bodys);
			for (Map.Entry<String, Object> entry : bodys.entrySet()) {
				ContentBuilderUtil.appendField(builder, entry.getKey(), entry.getValue());
			}
		}
	  }

	  private void appendHeaders(XContentBuilder builder, Event event)
	      throws IOException {
	    Map<String, String> headers = event.getHeaders();
	    for (String key : headers.keySet()) {
	    	if (headers.containsKey("timestamp") || headers.containsKey("timestamp")) {
		        String timestamp = headers.get("timestamp");
		        if (!StringUtils.isBlank(timestamp)
		            && StringUtils.isBlank(headers.get("@timestamp"))) {
		          long timestampMs = Long.parseLong(timestamp);
		          builder.field("@timestamp", new Date(timestampMs));
		        }
	    	} else
	        ContentBuilderUtil.appendField(builder, key,
	        	headers.get(key).getBytes(charset));
	    }
	  }

}
