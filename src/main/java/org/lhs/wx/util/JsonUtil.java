package org.lhs.wx.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Date;

/**
 * Created by longhuashen on 17/5/2.
 */
public class JsonUtil {

    private static Logger logger= LoggerFactory.getLogger("jsonUtil");

    private static ObjectMapper objectMapper=new ObjectMapper();

    public static String toJson(Object object){
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("",e);
        }
    }


    public static <T> T fromString(String string,Class<T> clz){

        try {
            return objectMapper.readValue(string, clz);
        } catch (IOException e) {
            throw new RuntimeException("",e);
        }
    }


    public class CustomDateSerializer extends JsonSerializer<Date> {

        @Override
        public void serialize(Date value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeString(new DateTime(value).toString("yyyy-MM-dd HH:mm:ss"));
        }
    }





}
