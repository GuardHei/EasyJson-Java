# EasyJson-Java
A Java library aiming to make the construction of Json with the least code.

## Purpose
When I was using JSON.simple, I found such code snippet is quite common in practice:
```java
    JSONObject obj = new JSONObject();
    obj.put("name", "foo");
    obj.put("num", new Integer(100));
    obj.put("balance", new Double(1000.21));
    obj.put("is_vip", new Boolean(true));
    
    String name = (String)obj.get("name");
    System.out.println(name);
```
Any obtaining of values requires a manual cast operation which is really annoying to me, especially when dealling with data containing loads of properties. Though in some situations, we can just use JSONParser to directly obtained an object, a final manual cast is still required. Therefore, I tried to build this library which provides an easier way to use JSON (at least to me).

## How To Use ?
1. Create an empty JSON data
```java
    // create a JSON object
    JsonObject jo = new JsonObject();
    
    // create a JSON array with the initial capacity of 0
    JsonArray ja = new JsonArray();
    
    // create a JSON array with the initial capacity of 4
    JsonArray ja1 = new JsonArray(4);
    
    // JsonData is an abstract class which is the superclass of JsonObject and JsonArray
    JsonData jd = new JsonObject();
    JsonData jd1 = new JsonArray();
```
2. Parse a String to create a JSON data
```java
    String objStr = "{\"key\":\"value\"}";
    
    // JsonData.toJsonObject(String string) returns JsonObject
    JsonObject jo = JsonData.toJsonObject(objStr);
    
    toJson
    JsonObject jo1 = JsonData.<JsonObject>toJsonData(objStr);
    
    // Use auto-deduction technique of Java to hide the generic parameter -- less code
    JsonObject jo2 = JsonData.toJsonData(objStr);
    
    // JsonData.toJsonArray(String string) returns JsonArray
    JsonArray ja = JsonData.toJsonArray(objStr);
    
    toJson
    JsonArray ja1 = JsonData.<JsonArray>toJsonData(objStr);
    
    // Use auto-deduction technique of Java to hide the generic parameter -- less code
    JsonArray ja2 = JsonData.toJsonData(objStr);
```
3. Convert JSON data into String
```java
    String str = jo.toString();
    System.out.println(str);
    System.out.println(ja);
```
4. Add properties to a JSON data
```java
    JsonObject jo = new JsonObject();
    jo.add("JsonObject", new JsonObject());
    jo.add("JsonArray", new JsonArray());
    jo.add("String", "string");
    jo.add("boolean", true);
    jo.add("int", 1);
    jo.add("long", 1l);
    jo.add("float", 1f);
    jo.add("double", 1d);
    jo.addNull("null");
    
    JsonArray ja = new JsonArray();
    ja.add("auto add to the end");
    ja.add(0, "add to the index");
    ja.addNull();
    ja.addNull(0);
```
5. Get properties from a JSON data
```java
    // Use auto-deduction technique of Java to hide the generic parameter -- less code
    int num1 = jo.get("int");
    
    // Or manually cast the object
    int num2 = jo.<Integer>get("int").intValue();
    
    // Or use given method
    int num3 = jo.getInt("int");
    
    // Use auto-deduction technique of Java to hide the generic parameter -- less code
    String str1 = ja.get(2);
    
    // Or manually cast the object
    String str2 = ja.<String>get(2);
    
    // Or use given method
    String str3 = ja.getString(2);
```

6. Other methods including
size(), replace() and its overload, clear(), isEmpty() and etc.

## Plan
1. Add Object-Mapping Parser which directly creates a instance of the named class
2. Add Object-Mapping Writer which directly creates a String in JSON format from a instance
