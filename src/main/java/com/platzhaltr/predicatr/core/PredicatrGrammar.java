package com.platzhaltr.predicatr.core;

import com.googlecode.lingwah.Grammar;
import com.googlecode.lingwah.Parser;
import com.googlecode.lingwah.parser.ParserReference;

/**
 * EBNF of the grammar
 * 
 * <code><pre>
 * blank   = ' ' | '\t' | '\n' | '\r';
 * whitespace = (blank, {blank});
 * char = 'a'|'b' ... |'z'|'A'|'B'| ... |'Z'|'.';
 * variable = char, { char };
 * true = 'true';
 * false = 'false';
 * predicate = 'true'|'false';
 * and = (expr, [whitespace], '&', [whitespace], expr) | (expr, [whitespace], expr);
 * or = expr, [whitespace], '|', [whitespace], expr;
 * group = '(', [whitespace], expr, [whitespace], ')';
 * expr = predicate | variable | and | or | group | '!' expr;
 * </pre></code>
 * 
 * Based on the original calculator grammar example by Ted Stockwell
 * 
 * @author Oliver Schrenk <oliver.schrenk@gmail.com>
 * 
 */
public class PredicatrGrammar extends Grammar {

	public static final PredicatrGrammar INSTANCE = new PredicatrGrammar();

	// these fields need to be public because lingwah uses reflection to
	// access/invoke these

	//@formatter:off
	public final Parser whitespace = oneOrMore(cho(oneOrMore(regex("[ \t\n\f\r]"))));
	public final Parser predicate = cho(str("true"), str("false"));
	public final Parser key = seq(oneOrMore(regex("[a-zA-z\\.]")));
	public final Parser variable = seq(oneOrMore(regex("[a-zA-z\\.]")));
	public final Parser value = seq(oneOrMore(anyChar()));

	public final ParserReference expr = ref();

	public final Parser and = seq(expr, str('&'), expr).separatedBy(
			opt(whitespace));
	public final Parser or = seq(expr, str('|'), expr).separatedBy(
			opt(whitespace));
	public final Parser not = seq(str('!'), expr).separatedBy(opt(whitespace));
	public final Parser operator = first(and, or, not);

	public final Parser existence = seq(key, str('?'));
	public final Parser equals = seq(key, str('#'), value, str('?'));

	public final Parser group = seq(str('('), expr, str(')')).separatedBy(
			opt(whitespace));
	{
		expr.define(cho(predicate, variable, existence, equals, operator, group));
	}
	//@formatter:off

	private PredicatrGrammar() {
		init();
	}

}
