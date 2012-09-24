package com.platzhaltr.predicatr.core;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

public class PredicatrVariableTest {

	private static Map<String, Object> variables;

	@BeforeClass
	public static void setup() {
		variables = new HashMap<String, Object>();
		variables.put("yes", true);
		variables.put("no", false);
		variables.put("com.platzhaltr", true);
		variables.put("com.acme", "foobar");
	}

	@Test
	public void testUnusedVariables() {
		assertTrue(Predicatr.parse("true | false", variables));
	}

	@Test
	public void testSingleVariable() {
		assertTrue(Predicatr.parse("true & yes", variables));
		assertTrue(Predicatr.parse("true | yes", variables));
	}

	@Test
	public void testMultipleVariable() {
		assertTrue(Predicatr.parse("yes | no", variables));
	}

	@Test
	public void testVariableWithNot() {
		assertTrue(Predicatr.parse("!no & true", variables));
	}

	@Test
	public void testDotInName() {
		assertTrue(Predicatr.parse("true & com.platzhaltr", variables));
	}

	@Test
	public void testExistence() {
		assertTrue(Predicatr.parse("true & com.platzhaltr?", variables));
	}

	@Test
	public void testNotExistence() {
		assertFalse(Predicatr.parse("!com.platzhaltr?", variables));
	}

	@Test
	public void testEquals() {
		assertTrue(Predicatr.parse("true & com.acme#foobar?", variables));
	}

	@Test
	public void testNotEquals() {
		assertFalse(Predicatr.parse("! com.acme#foobar?", variables));
	}

	@Test
	public void testEqualsOnNonExistingKey() {
		assertFalse(Predicatr.parse("true & com.foo#bar?", variables));
	}
}
