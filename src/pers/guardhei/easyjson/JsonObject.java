package pers.guardhei.easyjson;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class JsonObject extends JsonData {

    private HashMap<String, Object> dictionary;

    public JsonObject() {
        dictionary = new HashMap<String, Object>();
    }

    public void add(String key, JsonObject value) {
        dictionary.put(key, value);
    }

    public void add(String key, JsonArray value) {
        dictionary.put(key, value);
    }

    public void add(String key, String value) {
        dictionary.put(key, value);
    }

    public void add(String key, boolean value) {
        dictionary.put(key, value);
    }

    public void add(String key, int value) {
        dictionary.put(key, value);
    }

    public void add(String key, long value) {
        dictionary.put(key, value);
    }

    public void add(String key, float value) {
        dictionary.put(key, value);
    }

    public void add(String key, double value) {
        dictionary.put(key, value);
    }

    public void addNull(String key) {
        dictionary.put(key, null);
    }

    public void replace(String key, JsonObject value) {
        dictionary.replace(key, value);
    }

    public void replace(String key, JsonArray value) {
        dictionary.replace(key, value);
    }

    public void replace(String key, boolean value) {
        dictionary.replace(key, value);
    }

    public void replace(String key, int value) {
        dictionary.replace(key, value);
    }

    public void replace(String key, long value) {
        dictionary.replace(key, value);
    }

    public void replace(String key, float value) {
        dictionary.replace(key, value);
    }

    public void replace(String key, double value) {
        dictionary.replace(key, value);
    }

    public void remove(String key) {
        dictionary.remove(key);
    }

    @Override
    public void clear() {
        dictionary.clear();
    }

    public <T> T get(String key) {
        T t;
        try {
            t = (T) dictionary.get(key);
        } catch (ClassCastException e) {
            t = null;
            System.out.println("Incompatible type for the value of the key [" + key + "]");
            e.printStackTrace();
        }
        return t;
    }

    public JsonObject getObject(String key) {
        return get(key);
    }

    public JsonArray getArray(String key) {
        return get(key);
    }

    public String getString(String key) {
        return get(key);
    }

    public boolean getBoolean(String key) {
        return get(key);
    }

    public int getInt(String key) {
        return get(key);
    }

    public long getLong(String key) {
        return get(key);
    }

    public float getFloat(String key) {
        return get(key);
    }

    public double getDouble(String key) {
        return get(key);
    }

    public Set<Map.Entry<String, Object>> getAll() {
        return dictionary.entrySet();
    }

    public HashMap<String, Object> toHashMap() {
        return dictionary;
    }

    @Override
    public int size() {
        return dictionary.size();
    }

    public Set<String> keys() {
        return dictionary.keySet();
    }

    public Collection<Object> values() {
        return dictionary.values();
    }

    @Override
    public boolean isEmpty() {
        return dictionary.isEmpty();
    }

    public boolean containsKey(String key) {
        return dictionary.containsKey(key);
    }

    @Override
    public String toString() {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{");
        int i = 0;
        int size = dictionary.size();
        for (Map.Entry<String, Object> entry : dictionary.entrySet()) {
            Object obj = entry.getValue();
            String objString;
            if (obj == null) {
                objString = "null";
            } else {
                objString = obj.toString();
                if (obj instanceof String) {
                    objString = "\"" + objString + "\"";
                }
            }
            jsonBuilder.append("\"" + entry.getKey() + "\":" + objString);
            if (i != size - 1) {
                jsonBuilder.append(",");
            }
            i ++;
        }
        jsonBuilder.append("}");
        return jsonBuilder.toString();
    }

    @Override
    public JsonType getType() {
        return JsonType.OBJECT;
    }
}