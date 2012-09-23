# README #

**predicatr** interprets text as a boolean expression, supporting basic operators (`&`,`|`, `!`).

```java
Predicatr.parse("true & false"); // false
Predicatr.parse("true & !false"); // true

Map<String, Float> variables = new HashMap<String, Boolean>();
variables.put("yes", true);
variables.put("no", false);
Predicatr.parse("yes | no", variables); // false
```

## Development ##

	git clone git://github.com/platzhaltr/predicatr.git
	cd predicatr
	mvn clean install
	