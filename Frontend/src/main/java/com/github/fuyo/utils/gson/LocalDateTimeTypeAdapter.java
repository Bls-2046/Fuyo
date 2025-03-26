package com.github.fuyo.utils.gson;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeTypeAdapter extends TypeAdapter<LocalDateTime> {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @Override
    public void write(JsonWriter out, LocalDateTime value) throws IOException {
        if (value == null) {
            out.nullValue(); // 如果值为 null，写入 null
        } else {
            // 将 LocalDateTime 格式化为字符串并写入 JSON
            out.value(FORMATTER.format(value));
        }
    }

    @Override
    public LocalDateTime read(JsonReader in) throws IOException {
        if (in.peek() == null) {
            return null; // 如果 JSON 值为 null，返回 null
        }
        // 读取 JSON 字符串并解析为 LocalDateTime
        String dateTimeString = in.nextString();
        return LocalDateTime.parse(dateTimeString, FORMATTER);
    }
}
