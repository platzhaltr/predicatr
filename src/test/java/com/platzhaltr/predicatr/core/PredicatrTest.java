package com.platzhaltr.predicatr.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import com.platzhaltr.predicatr.core.Predicatr;

public class PredicatrTest {

	private static Map<String, Boolean> variables;

	@BeforeClass
	public static void setup() {
		variables = new HashMap<String, Boolean>();
		variables.put("yes", true);
		variables.put("no", false);
	}

	@Test
	public void testIdempotence() {
		assertTrue(Predicatr.parse("true & true"));
		assertTrue(Predicatr.parse("true | true"));
		assertFalse(Predicatr.parse("false & false"));
		assertFalse(Predicatr.parse("false | false"));
	}

	@Test
	public void testAbsorption() {
		assertTrue(Predicatr.parse("true & (true | false)"));
		assertFalse(Predicatr.parse("false & (false | true)"));

		assertTrue(Predicatr.parse("true | (true & false)"));
		assertFalse(Predicatr.parse("false | (false & true)"));
	}

	@Test
	public void testBasics() {
		assertTrue(Predicatr.parse("true & true"));
		assertFalse(Predicatr.parse("true & false"));
		assertFalse(Predicatr.parse("false & false"));
		assertFalse(Predicatr.parse("false & true"));

		assertTrue(Predicatr.parse("true | true"));
		assertTrue(Predicatr.parse("true | false"));
		assertFalse(Predicatr.parse("false | false"));
		assertTrue(Predicatr.parse("false | true"));
	}

	@Test
	public void testNot() {
		assertTrue(Predicatr.parse("!false"));
		assertFalse(Predicatr.parse("!true"));
		assertFalse(Predicatr.parse("!!false"));
		assertTrue(Predicatr.parse("!!true"));
	}

	@Test
	public void testAssociativityOfAnd() {
		assertEquals(Predicatr.parse("true & (true & true)"),
				Predicatr.parse("(true & true) & true"));
		assertEquals(Predicatr.parse("true & (true & false)"),
				Predicatr.parse("(true & false) & true"));
		assertEquals(Predicatr.parse("true & (false & false)"),
				Predicatr.parse("(false & false) & true"));
		assertEquals(Predicatr.parse("true & (false & true)"),
				Predicatr.parse("(false & true) & true"));

		assertEquals(Predicatr.parse("false & (true & true)"),
				Predicatr.parse("(true & true) & false"));
		assertEquals(Predicatr.parse("false & (true & false)"),
				Predicatr.parse("(true & false) & false"));
		assertEquals(Predicatr.parse("false & (false & false)"),
				Predicatr.parse("(false & false) & false"));
		assertEquals(Predicatr.parse("false & (false & true)"),
				Predicatr.parse("(false & true) & false"));
	}

	@Test
	public void testAssociativityOfOr() {
		assertEquals(Predicatr.parse("true | (true | true)"),
				Predicatr.parse("(true | true) | true"));
		assertEquals(Predicatr.parse("true | (true | false)"),
				Predicatr.parse("(true | false) | true"));
		assertEquals(Predicatr.parse("true | (false | false)"),
				Predicatr.parse("(false | false) | true"));
		assertEquals(Predicatr.parse("true | (false | true)"),
				Predicatr.parse("(false | true) | true"));

		assertEquals(Predicatr.parse("false | (true | true)"),
				Predicatr.parse("(true | true) | false"));
		assertEquals(Predicatr.parse("false | (true | false)"),
				Predicatr.parse("(true | false) | false"));
		assertEquals(Predicatr.parse("false | (false | false)"),
				Predicatr.parse("(false | false) | false"));
		assertEquals(Predicatr.parse("false | (false | true)"),
				Predicatr.parse("(false | true) | false"));
	}

	@Test
	public void testCommutativityOfAnd() {
		assertEquals(Predicatr.parse("true & true"),
				Predicatr.parse("true & true"));
		assertEquals(Predicatr.parse("true & false"),
				Predicatr.parse("false & true"));
		assertEquals(Predicatr.parse("false & true"),
				Predicatr.parse("true & false"));
		assertEquals(Predicatr.parse("false & false"),
				Predicatr.parse("false & false"));
	}

	@Test
	public void testCommutativityOfOr() {
		assertEquals(Predicatr.parse("true | true"),
				Predicatr.parse("true | true"));
		assertEquals(Predicatr.parse("true | false"),
				Predicatr.parse("false | true"));
		assertEquals(Predicatr.parse("false | true"),
				Predicatr.parse("true | false"));
		assertEquals(Predicatr.parse("false | false"),
				Predicatr.parse("false | false"));
	}

	@Test
	public void testDistributivityOfAndOverOr() {
		assertEquals(Predicatr.parse("true & (true | true)"),
				Predicatr.parse("(true & true) | (true & true)"));

		assertEquals(Predicatr.parse("true & (true | false)"),
				Predicatr.parse("(true & true) | (true & false)"));

		assertEquals(Predicatr.parse("true & (false | false)"),
				Predicatr.parse("(true & false) | (true & false)"));

		assertEquals(Predicatr.parse("true & (false | true)"),
				Predicatr.parse("(true & false) | (true & true)"));

		assertEquals(Predicatr.parse("false & (true | true)"),
				Predicatr.parse("(false & true) | (false & true)"));

		assertEquals(Predicatr.parse("false & (true | false)"),
				Predicatr.parse("(false & true) | (false & false)"));

		assertEquals(Predicatr.parse("false & (false | false)"),
				Predicatr.parse("(false & false) | (false & false)"));

		assertEquals(Predicatr.parse("false & (false | true)"),
				Predicatr.parse("(false & false) | (false & true)"));
	}

	@Test
	public void testDistributivityOfOrOverAnd() {
		assertEquals(Predicatr.parse("true | (true & true)"),
				Predicatr.parse("(true | true) & (true | true)"));

		assertEquals(Predicatr.parse("true | (true & false)"),
				Predicatr.parse("(true | true) & (true | false)"));

		assertEquals(Predicatr.parse("true | (false & false)"),
				Predicatr.parse("(true | false) & (true | false)"));

		assertEquals(Predicatr.parse("true | (false & true)"),
				Predicatr.parse("(true | false) & (true | true)"));

		assertEquals(Predicatr.parse("false | (true & true)"),
				Predicatr.parse("(false | true) & (false | true)"));

		assertEquals(Predicatr.parse("false | (true & false)"),
				Predicatr.parse("(false | true) & (false | false)"));

		assertEquals(Predicatr.parse("false | (false & false)"),
				Predicatr.parse("(false | false) & (false | false)"));

		assertEquals(Predicatr.parse("false | (false & true)"),
				Predicatr.parse("(false | false) & (false | true)"));
	}

	@Test
	public void testGrouping() {
		assertTrue(Predicatr.parse("true & (true & true)"));
		assertFalse(Predicatr.parse("true & (true & false)"));
		assertFalse(Predicatr.parse("true & (false & true)"));
		assertFalse(Predicatr.parse("true & (false & false)"));

		assertFalse(Predicatr.parse("false & (true & true)"));
		assertFalse(Predicatr.parse("false & (true & false)"));
		assertFalse(Predicatr.parse("false & (false & true)"));
		assertFalse(Predicatr.parse("false & (false & false)"));
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
	public void testGroupWithNot() {
		assertFalse(Predicatr.parse("!(true & true )"));
	}
}
