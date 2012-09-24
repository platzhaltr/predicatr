package com.platzhaltr.predicatr.core;

import java.util.List;
import java.util.Map;

import com.googlecode.lingwah.AbstractProcessor;
import com.googlecode.lingwah.Match;
import com.googlecode.lingwah.ParseResults;
import com.googlecode.lingwah.annotations.Processes;

/**
 * 
 * @author Oliver Schrenk <oliver.schrenk@gmail.com>
 * 
 */
@Processes(PredicatrGrammar.class)
public class PredicatrProcessor extends AbstractProcessor {

	static final PredicatrGrammar grammar = PredicatrGrammar.INSTANCE;

	private final Map<String, Object> variables;

	public PredicatrProcessor(final Map<String, Object> variables) {
		this.variables = variables;
	}

	public void completeOr(final Match match) {
		final List<Match> children = match.getChildrenByType(grammar.expr);
		final boolean left = (Boolean) getResult(children.get(0));
		final boolean right = (Boolean) getResult(children.get(1));
		putResult(left | right);
	}

	public void completeAnd(final Match match) {
		final List<Match> children = match.getChildrenByType(grammar.expr);
		final boolean left = (Boolean) getResult(children.get(0));
		final boolean right = (Boolean) getResult(children.get(1));
		putResult(left & right);
	}

	public void completeNot(final Match match) {
		final List<Match> children = match.getChildrenByType(grammar.expr);
		final boolean bool = (Boolean) getResult(children.get(0));
		putResult(!bool);
	}

	public void completeOperator(final Match match) {
		putResult(getResult(match.getChildren().get(0)));
	}

	public void completeGroup(final Match match) {
		putResult(getResult(match.getChildByType(grammar.expr)));
	}

	public void completePredicate(final Match match) {
		final String key = match.getText();
		final boolean bool = Boolean.valueOf(key);

		putResult(bool);
	}

	public void completeExistence(final Match match) {
		final String key = match.getChildren().get(0).getText();
		final boolean exists = variables.get(key) != null;

		putResult(exists);
	}

	public void completeEquals(final Match match) {
		final List<Match> children = match.getChildren();
		final String key = children.get(0).getText();
		final String value = children.get(2).getText();
		final boolean equals = variables.get(key).equals(value);

		putResult(equals);
	}

	public void completeVariable(final Match expr) {
		final String key = expr.getText();
		final boolean bool = (Boolean) variables.get(key);

		putResult(bool);
	}

	public void completeExpr(final Match expr) {
		putResult(getResult(expr.getChildren().get(0)));
	}

	public static boolean process(final Map<String, Object> variables,
			final ParseResults results) {
		return (Boolean) new PredicatrProcessor(variables).getResult(results
				.getLongestMatch());
	}

}
