package com.example.tune_trade;

import androidx.room.TypeConverter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringListConverter {
    @TypeConverter
    public String fromStringList(List<String> stringList) {
        StringBuilder sb = new StringBuilder();
        for (String s : stringList) {
            sb.append(s);
            sb.append(",");
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1); // Remove the last comma
        }
        return sb.toString();
    }

    @TypeConverter
    public List<String> toStringList(String data) {
        return new ArrayList<>(Arrays.asList(data.split(",")));
    }
}
