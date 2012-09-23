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

	private final Map<String, Boolean> variables;

	public PredicatrProcessor(final Map<String, Boolean> variables) {
		this.variables = variables;
	}

	public void completeOr(final Match expr) {
		final List<Match> children = expr.getChildrenByType(grammar.expr);
		final boolean left = (Boolean) getResult(children.get(0));
		final boolean right = (Boolean) getResult(children.get(1));
		putResult(left | right);
	}

	public void completeAnd(final Match expr) {
		final List<Match> children = expr.getChildrenByType(grammar.expr);
		final boolean left = (Boolean) getResult(children.get(0));
		final boolean right = (Boolean) getResult(children.get(1));
		putResult(left & right);
	}

	public void completeNot(final Match expr) {
		final List<Match> children = expr.getChildrenByType(grammar.expr);
		final boolean bool = (Boolean) getResult(children.get(0));
		putResult(!bool);
	}

	public void completeOperator(final Match op) {
		putResult(getResult(op.getChildren().get(0)));
	}

	public void completeGroup(final Match expr) {
		putResult(getResult(expr.getChildByType(grammar.expr)));
	}

	public void completePredicate(final Match expr) {
		final String key = expr.getText();
		final boolean bool = Boolean.valueOf(key);

		putResult(bool);
	}

	public void completeVariable(final Match expr) {
		final String key = expr.getText();
		final boolean bool = variables.get(key);

		putResult(bool);
	}

	public void completeExpr(final Match expr) {
		putResult(getResult(expr.getChildren().get(0)));
	}

	public static boolean process(final Map<String, Boolean> variables,
			final ParseResults results) {
		return (Boolean) new PredicatrProcessor(variables).getResult(results
				.getLongestMatch());
	}

}
