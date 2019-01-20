package com.rao.study.tools.jsqlparser.expression;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.operators.arithmetic.BitwiseAnd;
import net.sf.jsqlparser.expression.operators.arithmetic.Concat;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import org.junit.Test;

public class ExpressionPrecedenceTest {

    @Test
    public void testGetSign()throws JSQLParserException {
        Expression expr = CCJSqlParserUtil.parseExpression("1&2||3");
        System.out.println(expr instanceof Concat);
        Concat concat = (Concat) expr;
        System.out.println(concat.getStringExpression());
        System.out.println(concat.getLeftExpression() instanceof BitwiseAnd);
        System.out.println(concat.getRightExpression() instanceof LongValue);
    }
}
