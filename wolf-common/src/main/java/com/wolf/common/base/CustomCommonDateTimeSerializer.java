package com.wolf.common.base;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 前端展示用的通用的时间格式
 *
 * @author honglou
 * @date 2019-08-12 15:16
 */
public class CustomCommonDateTimeSerializer extends JsonSerializer<LocalDateTime> {
    private static final String DATE_TIME_FORMATTER_MILLI = "yyyy-MM-dd HH:mm";

    public CustomCommonDateTimeSerializer() {
    }

    public void serialize(LocalDateTime value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        if (value == null) {
            jgen.writeNull();
        } else {
            jgen.writeString(DateTimeFormatter.ofPattern(DATE_TIME_FORMATTER_MILLI).format(value));
        }

    }
}
