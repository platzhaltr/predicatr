package com.platzhaltr.predicatr.core;

import java.util.Collections;
import java.util.Map;

import com.googlecode.lingwah.ParseContext;
import com.googlecode.lingwah.ParseResults;
import com.googlecode.lingwah.Parser;

public class Predicatr {
	static final Parser PARSER = PredicatrGrammar.INSTANCE.expr;

	public static boolean parse(final String expression) {
		return parse(expression, Collections.<String, Boolean> emptyMap());
	}

	public static boolean parse(final String expression,
			final Map<String, Boolean> variables) {
		final ParseResults parseResults = ParseContext
				.parse(PARSER, expression);
		if (!parseResults.success()) {
			throw parseResults.getError();
		}
		return PredicatrProcessor.process(variables, parseResults);
	}
}