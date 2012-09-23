# README #

**predicatr** interprets text as a boolean expression, supporting basic operators (`&`,`|`, `!`).

	Predicatr.parse("true & false"); // false
	Predicatr.parse("true & !false"); // true

	Map<String, Float> variables = new HashMap<String, Boolean>();
	variables.put("yes", true);
	variables.put("no", false);
	Predicatr.parse("yes | no", variables); // false

## Development ##

Aa working version of [lingwah](http://code.google.com/p/lingwah/) is needed. I took the liberty of creating a mavenized version and fix a very small bug (in a test case).

	git clone git://github.com/oschrenk/lingwah.git
	cd lingwah
	mvn clean install

After that you can install **predicatr**

	git clone git://github.com/platzhaltr/predicatr.git
	cd predicatr
	mvn clean install

	