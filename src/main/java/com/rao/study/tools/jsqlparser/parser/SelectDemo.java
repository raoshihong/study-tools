package com.rao.study.tools.jsqlparser.parser;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.Statements;
import net.sf.jsqlparser.statement.select.*;
import net.sf.jsqlparser.util.TablesNamesFinder;
import org.junit.Test;

import java.util.List;

public class SelectDemo {

    @Test
    public void test() throws JSQLParserException {
        String sql = "select * from table";
        Statement statement = CCJSqlParserUtil.parse(sql);
        System.out.println(statement);

        String sqls = "select * from table1;Select * from table2";
        Statements statements = CCJSqlParserUtil.parseStatements(sqls);
        List<Statement> statements1  = statements.getStatements();
        System.out.println(statements1);

    }

    @Test
    public void testJoin() throws Exception{
        String sql = "select * from user u left join application a on u.id = a.u_id left join agent ag on ag.user_id = u.id";
        Statement statement = CCJSqlParserUtil.parse(sql);

        System.out.println(statement);

        //获取查询语句
        Select select = (Select)statement;
        PlainSelect plainSelect = (PlainSelect) select.getSelectBody();

        //获取select查询项
        List<SelectItem> selectItems = plainSelect.getSelectItems();
        System.out.println(selectItems);

        //获取from子项,不包括left join/right join/inner join
        FromItem fromItem = plainSelect.getFromItem();
        System.out.println(fromItem);

        //获取join部分
        List<Join> joins = plainSelect.getJoins();
        System.out.println(joins);

        //获取join中的on
        for (Join join:joins){
            System.out.println(join.getOnExpression());
        }

        //获取表名
        TablesNamesFinder namesFinder = new TablesNamesFinder();
        List<String> names = namesFinder.getTableList(statement);
        System.out.println(names);
    }

    @Test
    public void testSubSelect() throws Exception{
        String sql = "select u.name,u.id from user u left join (select * from application) a on u.id = a.u_id";
        Statement statement = CCJSqlParserUtil.parse(sql);

        System.out.println(statement);

        //获取查询语句
        Select select = (Select)statement;
        PlainSelect plainSelect = (PlainSelect) select.getSelectBody();

        //获取select查询项
        List<SelectItem> selectItems = plainSelect.getSelectItems();
        System.out.println(selectItems);

        //获取from子项,不包括left join/right join/inner join
        FromItem fromItem = plainSelect.getFromItem();
        System.out.println(fromItem);

        //获取join部分
        List<Join> joins = plainSelect.getJoins();
        System.out.println(joins);

        //获取join中的on
        for (Join join:joins){
            System.out.println(join.getOnExpression());
        }

        //获取表名
        TablesNamesFinder namesFinder = new TablesNamesFinder();
        List<String> names = namesFinder.getTableList(statement);
        System.out.println(names);
    }

    @Test
    public void testSelectWhere() throws Exception{
        String sql = "select * from user u where u.id = 10";
        Statement statement = CCJSqlParserUtil.parse(sql);

        System.out.println(statement);

        //获取查询语句
        Select select = (Select)statement;
        PlainSelect plainSelect = (PlainSelect) select.getSelectBody();

        //获取where条件
        Expression expression = plainSelect.getWhere();
        System.out.println(expression);

        EqualsTo equalsTo = (EqualsTo) expression;
        System.out.println(equalsTo.getLeftExpression());
        System.out.println(equalsTo.getRightExpression());
    }

    @Test
    public void testTable()throws Exception{
        String sql = "select * from User u where u.id = 10";
        Statement statement = CCJSqlParserUtil.parse(sql);
        Select select = (Select)statement;
        TablesNamesFinder namesFinder = new TablesNamesFinder();
        List<String> strings = namesFinder.getTableList(statement);
        System.out.println(strings);
    }

    @Test
    public void testGroup()throws Exception{
        String sql = "select * from User u where u.id = 10 group by u.name,u.mobile";
        Statement statement = CCJSqlParserUtil.parse(sql);
        Select select = (Select)statement;
        PlainSelect plainSelect = (PlainSelect) select.getSelectBody();
        List<Expression> expressions = plainSelect.getGroupByColumnReferences();
        System.out.println(expressions);
    }

    @Test
    public void testOrder()throws Exception{
        String sql = "select * from User u order by u.id,u.creat_date";
        Statement statement = CCJSqlParserUtil.parse(sql);
        Select select = (Select)statement;
        PlainSelect plainSelect = (PlainSelect) select.getSelectBody();
        List<OrderByElement> orderByElements = plainSelect.getOrderByElements();
        System.out.println(orderByElements);

        for (OrderByElement orderByElement:orderByElements){
            System.out.println(orderByElement.getExpression());
        }
    }
}
