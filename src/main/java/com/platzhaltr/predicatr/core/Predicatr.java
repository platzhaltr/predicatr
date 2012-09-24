package com.platzhaltr.predicatr.core;

import java.util.Collections;
import java.util.Map;

import com.googlecode.lingwah.ParseContext;
import com.googlecode.lingwah.ParseResults;
import com.googlecode.lingwah.Parser;

/**
 * The Class Predicatr.
 * 
 * @author Oliver Schrenk <oliver.schrenk@gmail.com>
 */
public class Predicatr {

	/** The Constant PARSER. */
	static final Parser PARSER = PredicatrGrammar.INSTANCE.expr;

	/**
	 * Parses the.
	 * 
	 * @param expression
	 *            the expression
	 * @return true, if successful
	 */
	public static boolean parse(final String expression) {
		return parse(expression, Collections.<String, Object> emptyMap());
	}

	/**
	 * Parses the.
	 * 
	 * @param expression
	 *            the expression
	 * @param variables
	 *            the variables
	 * @return true, if successful
	 */
	public static boolean parse(final String expression,
			final Map<String, Object> variables) {
		final ParseResults parseResults = ParseContext
				.parse(PARSER, expression);
		if (!parseResults.success()) {
			throw parseResults.getError();
		}
		return PredicatrProcessor.process(variables, parseResults);
	}
}