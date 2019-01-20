package com.rao.study.tools.jsqlparser.expression;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.ExpressionVisitorAdapter;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.expression.operators.relational.ItemsList;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectVisitorAdapter;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class ExpressionVisitorAdapterTest {

    @Test
    public void testInExpressionProblem() throws JSQLParserException{
        final List exprList = new ArrayList();

        //jsqlparser使用的时javacc编译器生成的代码
        Select select = (Select) CCJSqlParserUtil.parse("select * from foo where x in (?,?,?)");
        PlainSelect plainSelect = (PlainSelect) select.getSelectBody();
        Expression where = plainSelect.getWhere();
        where.accept(new ExpressionVisitorAdapter() {

            @Override
            public void visit(InExpression expr) {
                super.visit(expr);
                exprList.add(expr.getLeftExpression());
                exprList.add(expr.getLeftItemsList());
                exprList.add(expr.getRightItemsList());
            }
        });

        System.out.println(exprList);
        System.out.println(exprList.get(0));
        System.out.println(exprList.get(0) instanceof Expression);
        System.out.println(exprList.get(1));
        System.out.println(exprList.get(2) instanceof ItemsList);
    }

    @Test
    public void testInExpression() throws JSQLParserException {
        final List exprList = new ArrayList();
        Select select = (Select) CCJSqlParserUtil.
                parse("select * from foo where (a,b) in (select a,b from foo2)");
        PlainSelect plainSelect = (PlainSelect) select.getSelectBody();
        Expression where = plainSelect.getWhere();
        where.accept(new ExpressionVisitorAdapter() {

            @Override
            public void visit(InExpression expr) {
                super.visit(expr);
                exprList.add(expr.getLeftExpression());
                exprList.add(expr.getLeftItemsList());
                exprList.add(expr.getRightItemsList());
            }
        });

        assertNull(exprList.get(0));
        assertTrue(exprList.get(1) instanceof ItemsList);
        assertTrue(exprList.get(2) instanceof ItemsList);
    }

    @Test
    public void testSubSelectExpressionProblem() throws JSQLParserException {
        Select select = (Select) CCJSqlParserUtil.
                parse("SELECT * FROM t1 WHERE EXISTS (SELECT * FROM t2 WHERE t2.col2 = t1.col1)");
        PlainSelect plainSelect = (PlainSelect) select.getSelectBody();
        Expression where = plainSelect.getWhere();
        ExpressionVisitorAdapter adapter = new ExpressionVisitorAdapter();
        adapter.setSelectVisitor(new SelectVisitorAdapter());
        try {
            where.accept(adapter);
        } catch (NullPointerException npe) {
            fail();
        }
    }

}
