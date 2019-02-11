package pers.guardhei.easyjson;

import java.lang.reflect.*;
import java.util.HashMap;
import java.util.Map;

public abstract class JsonData {

    public abstract void clear();
    public abstract int size();
    public abstract boolean isEmpty();
    public abstract JsonType getType();

    public static <T> T toJson(String valueString) {
        JsonData json = null;
        valueString = valueString.trim();
        try {
            JsonType type = getType(valueString);
            if (type == JsonType.OBJECT) {
                json = parseObject(valueString);
            } else if (type == JsonType.ARRAY) {
                json = parseArray(valueString);
            } else {
                new JsonDataException("Cannot parse the json from : " + valueString).printStackTrace();
            }
        } catch (JsonDataException e) {
            e.printStackTrace();
        }
        return (T) json;
    }

    public static JsonObject toJsonObject(String valueString) {
        valueString = valueString.trim();
        return toJson(valueString);
    }

    public static JsonArray toJsonArray(String valueString) {
        valueString = valueString.trim();
        return toJson(valueString);
    }

    private static <T> T parseValue(String valueString) {
        try {
            JsonType type = getType(valueString);
            switch (type) {
                case OBJECT: return (T) parseObject(valueString);
                case ARRAY: return (T) parseArray(valueString);
                case STRING: return (T) parseString(valueString);
                case BOOLEAN: return (T) parseBoolean(valueString);
                case INT: return (T) parseInt(valueString);
                case DOUBLE: return (T) parseDouble(valueString);
            }
        } catch (JsonDataException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    private static void parseValue(String key, String valueString, JsonObject json) {
        valueString = valueString.trim();
        try {
            JsonType type = getType(valueString);
            switch (type) {
                case OBJECT: json.add(key, parseObject(valueString)); break;
                case ARRAY: json.add(key, parseArray(valueString)); break;
                case STRING: json.add(key, parseString(valueString)); break;
                case BOOLEAN: json.add(key, parseBoolean(valueString)); break;
                case INT: json.add(key, parseInt(valueString)); break;
                case DOUBLE: json.add(key, parseDouble(valueString)); break;
                case NULL: json.addNull(key); break;
            }
        } catch (JsonDataException e) {
            e.printStackTrace();
        }
    }
    
    private static void parseValue(String valueString, JsonArray json) {
        valueString = valueString.trim();
        try {
            JsonType type = getType(valueString);
            switch (type) {
                case OBJECT: json.add(parseObject(valueString)); break;
                case ARRAY: json.add(parseArray(valueString)); break;
                case STRING: json.add(parseString(valueString)); break;
                case BOOLEAN: json.add(parseBoolean(valueString)); break;
                case INT: json.add(parseInt(valueString)); break;
                case DOUBLE: json.add(parseDouble(valueString)); break;
                case NULL: json.addNull(); break;
            }
        } catch (JsonDataException e) {
            e.printStackTrace();
        }
    }

    private static JsonObject parseObject(String valueString) {
        valueString = valueString.trim();
        valueString = valueString.substring(1, valueString.length() - 1);
        JsonObject json = new JsonObject();
        int commaIndex;
        int colonIndex;
        do {
            commaIndex = firstIndexOfMarkInLevel(valueString, ',');
            String part = valueString.substring(0, commaIndex != -1 ? commaIndex : valueString.length());
            if (!valueString.isEmpty()) {
                colonIndex = firstIndexOfMarkInLevel(part, ':');
                String key = parseString(part.substring(0, colonIndex));
                parseValue(key, part.substring(colonIndex + 1), json);
                valueString = valueString.substring(commaIndex + 1);
            }
        } while (commaIndex != -1);
        return json;
    }

    private static JsonArray parseArray(String valueString) {
        valueString = valueString.trim();
        valueString = valueString.substring(1, valueString.length() - 1);
        JsonArray json = new JsonArray();
        int commaIndex;
        do {
            commaIndex = firstIndexOfMarkInLevel(valueString, ',');
            String part = valueString.substring(0, commaIndex != -1 ? commaIndex : valueString.length());
            if (!valueString.isEmpty()) {
                parseValue(part, json);
                valueString = valueString.substring(commaIndex + 1);
            }
        } while (commaIndex != -1);
        return json;
    }

    private static String parseString(String valueString) {
        valueString = valueString.trim();
        return valueString.substring(1, valueString.length() - 1);
    }

    private static Boolean parseBoolean(String valueString) {
        valueString = valueString.trim();
        return Boolean.valueOf(valueString);
    }

    private static Integer parseInt(String valueString) {
        valueString = valueString.trim();
        return Integer.valueOf(valueString);
    }

    private static Long parseLong(String valueString) {
        valueString = valueString.trim();
        return Long.valueOf(valueString);
    }

    private static Float parseFloat(String valueString) {
        valueString = valueString.trim();
        return Float.valueOf(valueString);
    }

    private static Double parseDouble(String valueString) {
        valueString = valueString.trim();
        return Double.valueOf(valueString);
    }

    private static JsonType getType(String valueString) throws JsonDataException {
        valueString = valueString.trim();
        char start = valueString.charAt(0);
        char end = valueString.charAt(valueString.length() - 1);
        if (start == '{' && end == '}') {
            return JsonType.OBJECT;
        }
        if (start == '[' && end == ']') {
            return JsonType.ARRAY;
        }
        if (start == '\"' && end == '\"') {
            return JsonType.STRING;
        }
        if (valueString.contains(".")) {
            return JsonType.DOUBLE;
        }
        if (start >= 48 && start <= 57) {
            return JsonType.INT;
        }
        if (valueString.equals("true") || valueString.equals("false")) {
            return JsonType.BOOLEAN;
        }
        if (valueString.equals("null")) {
            return JsonType.NULL;
        }
        throw new JsonDataException("Cannot parse the type of the value : " + valueString);
    }

    private static int firstIndexOfMark(String valueString, char mark) {
        char[] chars = valueString.toCharArray();
        boolean inString = false;
        int numOfSlash = 0;
        for (int i = 0, length = chars.length; i < length; i ++) {
            char c = chars[i];
            if (c == '\\') {
                numOfSlash += 1;
            } else {
                if (c == '\"') {
                    if (numOfSlash % 2 == 0) {
                        inString = !inString;
                    }
                } else if (c == mark && !inString) {
                    return i;
                }
                numOfSlash = 0;
            }
        }
        return -1;
    }

    private static int firstIndexOfMarkInLevel(String valueString, char mark) {
        char[] chars = valueString.toCharArray();
        boolean inString = false;
        int numOfSlash = 0;
        int level = 0;
        for (int i = 0, length = chars.length; i < length; i ++) {
            char c = chars[i];
            if (c == '\\') {
                numOfSlash += 1;
            } else {
                switch (c) {
                    case '{': if (!inString) level += 1; break;
                    case '}': if (!inString) level -= 1; break;
                    case '[': if (!inString) level += 1; break;
                    case ']': if (!inString) level -= 1; break;
                    case '\"':
                        if (numOfSlash % 2 == 0) {
                            inString = !inString;
                        }
                        break;
                        default:
                            if (c == mark && !inString && level == 0) {
                                return i;
                            }
                }
                numOfSlash = 0;
            }
        }
        return -1;
    }

    public static <T> T toInstance(String json, Class<T> tClass) {
        return toInstance((JsonData) toJson(json), tClass);
    }

    public static <T> T toInstance(JsonData json, Class<T> tClass) {
        if (json instanceof JsonObject) {
            return toObject((JsonObject) json, tClass);
        }
        if (json instanceof JsonArray) {
            return toArray((JsonArray) json, tClass);
        }
        new JsonDataException("Given JsonData is nor JsonObject nor JsonArray : " + json.getClass().getName()).printStackTrace();
        return null;
    }

    public static <T> T toObject(String json, Class<T> tClass) {
        return toObject(toJsonObject(json), tClass);
    }

    public static <T> T toObject(JsonObject json, Class<T> tClass) {
        T t;
        try {
            t = tClass.newInstance();
            Field[] fields = t.getClass().getFields();
            HashMap<String, Field> fieldMap = new HashMap<String, Field>();
            for (int i = 0, length = fields.length; i < length; i ++) {
                Field field = fields[i];
                fieldMap.put(field.getName(), field);
            }
            for (Map.Entry<String, Object> entry : json.getAll()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                Field field = fieldMap.get(key);
                if (fieldMap.containsKey(key)) {
                    if (value instanceof JsonObject) {
                        Class type = field.getType();
                        field.set(t, toObject((JsonObject) value, type));
                    } else if (value instanceof JsonArray) {
                        Class type = field.getType();
                        field.set(t, toArray((JsonArray) value, type));
                    } else if (value instanceof String) {
                        Class fClass = field.getType();
                        if (fClass.isEnum()) {
                            field.set(t, Enum.valueOf(fClass, (String) value));
                        } else {
                            field.set(t, value);
                        }
                    } else {
                        field.set(t, value);
                    }
                } else {
                    t = null;
                    new JsonDataException("Cannot find corresponding field in type : " + key + " in " + tClass.getName()).printStackTrace();
                }
            }
        } catch (InstantiationException | IllegalAccessException e) {
            t = null;
            e.printStackTrace();
        }
        return t;
    }

    public static <T> T toArray(String json, Class<T> tClass) {
        return toArray(toJsonArray(json), tClass);
    }

    public static <T> T toArray(JsonArray json, Class<T> tClass) {
        T t;
        Class cClass;
        if (tClass.isArray()) {
            cClass = tClass.getComponentType();
            t = (T) Array.newInstance(cClass, json.size());
            for (int i = 0, length = Array.getLength(t); i < length; i ++) {
                Object value = json.get(i);
                if (value instanceof JsonObject) {
                    Array.set(t, i, toObject((JsonObject) value, cClass));
                } else if (value instanceof JsonArray) {
                    Array.set(t, i, toArray((JsonArray) value, cClass));
                } else if (value instanceof String) {
                    if (cClass.isEnum()) {
                        Array.set(t, i, Enum.valueOf(cClass, (String) value));
                    } else {
                        Array.set(t, i, cClass.cast(json.get(i)));
                    }
                } else {
                    Array.set(t, i, cClass.cast(json.get(i)));
                }
            }
        } else {
            t = null;
            new JsonDataException("Given type is not Array : " + tClass.getName()).printStackTrace();
        }
        return t;
    }
}