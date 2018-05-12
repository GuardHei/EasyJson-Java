package pers.guardhei.easyjson;

import java.util.ArrayList;

public class JsonArray extends JsonData {

    private ArrayList<Object> array;

    public JsonArray() {
        array = new ArrayList<Object>();
    }

    public JsonArray(int capacity) {
        array = new ArrayList<Object>(capacity);
    }

    public void add(JsonObject value) {
        array.add(value);
    }

    public void add(int index, JsonObject value) {
        array.add(index, value);
    }

    public void add(JsonArray value) {
        array.add(value);
    }

    public void add(int index, JsonArray value) {
        array.add(index, value);
    }

    public void add(String value) {
        array.add(value);
    }

    public void add(int index, String value) {
        array.add(index, value);
    }

    public void add(boolean value) {
        array.add(value);
    }

    public void add(int index, boolean value) {
        array.add(index, value);
    }

    public void add(int value) {
        array.add(value);
    }

    public void add(int index, int value) {
        array.add(index, value);
    }

    public void add(long value) {
        array.add(value);
    }

    public void add(int index, long value) {
        array.add(index, value);
    }

    public void add(float value) {
        array.add(value);
    }

    public void add(int index, float value) {
        array.add(index, value);
    }

    public void add(double value) {
        array.add(value);
    }

    public void add(int index, double value) {
        array.add(index, value);
    }

    public void addNull() {
        array.add(null);
    }

    public void addNull(int index) {
        array.add(index, null);
    }

    public void replace(int index, JsonObject value) {
        array.remove(index);
        array.add(index, value);
    }

    public void replace(int index, JsonArray value) {
        array.remove(index);
        array.add(index, value);
    }

    public void replace(int index, String value) {
        array.remove(index);
        array.add(index, value);
    }

    public void replace(int index, boolean value) {
        array.remove(index);
        array.add(index, value);
    }

    public void replace(int index, int value) {
        array.remove(index);
        array.add(index, value);
    }

    public void replace(int index, long value) {
        array.remove(index);
        array.add(index, value);
    }

    public void replace(int index, float value) {
        array.remove(index);
        array.add(index, value);
    }

    public void replace(int index, double value) {
        array.remove(index);
        array.add(index, value);
    }

    public void remove(int index) {
        array.remove(index);
    }

    @Override
    public void clear() {
        array.clear();
    }

    public <T> T get(int index) {
        T t;
        try {
            t = (T) array.get(index);
        } catch (ClassCastException e) {
            t = null;
            System.out.println("Incompatible type for the value of the index [" + index + "]");
            e.printStackTrace();
        }
        return t;
    }

    public JsonObject getObject(int index) {
        return get(index);
    }

    public JsonArray getArray(int index) {
        return get(index);
    }

    public String getString(int index) {
        return get(index);
    }

    public boolean getBoolean(int index) {
        return get(index);
    }

    public int getInt(int index) {
        return get(index);
    }

    public long getLong(int index) {
        return get(index);
    }

    public float getFloat(int index) {
        return get(index);
    }

    public double getDouble(int index) {
        return get(index);
    }

    public ArrayList<Object> toArrayList() {
        return array;
    }

    @Override
    public int size() {
        return array.size();
    }

    @Override
    public boolean isEmpty() {
        return array.isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("[");
        for (int i = 0, size = array.size(); i < size; i ++) {
            Object obj = array.get(i);
            String objString;
            if (obj == null) {
                objString = "null";
            } else {
                objString = obj.toString();
                if (obj instanceof String) {
                    objString = "\"" + objString + "\"";
                }
            }
            jsonBuilder.append(objString);
            if (i != size - 1) {
                jsonBuilder.append(",");
            }
        }
        jsonBuilder.append("]");
        return jsonBuilder.toString();
    }

    @Override
    public JsonType getType() {
        return JsonType.ARRAY;
    }
}
