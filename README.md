# README #

**predicatr** interprets text as a boolean expression, supporting basic operators (`&`,`|`, `!`).

```java
Predicatr.parse("true & false"); // false
Predicatr.parse("true & !false"); // true

Map<String, Object> variables = new HashMap<String, Object>();
variables.put("yes", true);
variables.put("no", false);
Predicatr.parse("yes | no", variables); // true
```

You can also check for the existence of a key or for a specific value

```java
Map<String, Object> variables = new HashMap<String, Object>();
variables.put("com.platzhaltr", "property");
Predicatr.parse("com.acme?", variables); // false
Predicatr.parse("com.platzhaltr#property?", variables); // true
```

## Development ##

	git clone git://github.com/platzhaltr/predicatr.git
	cd predicatr
	mvn clean install
	