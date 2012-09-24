package com.platzhaltr.predicatr.core;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

public class PredicatrVariableTest {

	private static Map<String, Boolean> variables;

	@BeforeClass
	public static void setup() {
		variables = new HashMap<String, Boolean>();
		variables.put("yes", true);
		variables.put("no", false);
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

}
