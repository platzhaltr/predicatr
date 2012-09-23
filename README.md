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

A working version of [lingwah](http://code.google.com/p/lingwah/) is needed. A _mavenized_ version can be found at [oschrenk/lingwah](https://github.com/oschrenk/lingwah).

```shell
git clone git://github.com/oschrenk/lingwah.git
cd lingwah
mvn clean install
```

After that you can install **predicatr**

```shell
git clone git://github.com/platzhaltr/predicatr.git
cd predicatr
mvn clean install
```
	