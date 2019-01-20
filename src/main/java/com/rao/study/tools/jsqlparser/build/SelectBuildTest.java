package com.rao.study.tools.jsqlparser.build;

import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.util.TablesNamesFinder;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class SelectBuildTest {

    @Test
    public void selectUpdateBuildTest() throws Exception{
        Select statement = (Select) CCJSqlParserUtil.parse("select * from user");
        PlainSelect selectBody = (PlainSelect) statement.getSelectBody();
        System.out.println(selectBody.getSelectItems());
        System.out.println(selectBody.getFromItem());

        //修改where条件表达式
        Expression expression = CCJSqlParserUtil.parseCondExpression("id=92 and name='ssss'", false);
        selectBody.setWhere(expression);

        //修改select选项表达式
        List<SelectItem> selectItems = new ArrayList<>();
        SelectExpressionItem selectItem1 = new SelectExpressionItem();
        selectItem1.setAlias(new Alias("total"));
        selectItem1.setExpression(CCJSqlParserUtil.parseCondExpression("count(*)",false));
        selectItems.add(selectItem1);

        SelectExpressionItem selectExpressionItem = new SelectExpressionItem();
        selectExpressionItem.setAlias(new Alias("name"));
        selectExpressionItem.setExpression(CCJSqlParserUtil.parseCondExpression("name",false));

        selectItems.add(selectExpressionItem);

        selectBody.setSelectItems(selectItems);

        TablesNamesFinder  namesFinder = new TablesNamesFinder();
        List<String> strings = namesFinder.getTableList(statement);
        System.out.println(strings);


        System.out.println(selectBody.getForUpdateTable());
        System.out.println(statement.toString());

    }

    @Test
    public void selectTest()throws Exception{

        //整个select语句分为
        //select selectItem from fromItem where exressionItem

        Select select = new Select();
        PlainSelect plainSelect = new PlainSelect();

        //设置selectItem部分
        List<SelectItem> selectItems1 = new ArrayList<>();

        SelectExpressionItem selectExpressionItem = new SelectExpressionItem();
        selectExpressionItem.setAlias(new Alias("userId"));
        selectExpressionItem.setExpression(CCJSqlParserUtil.parseCondExpression("user_id",false));
        selectItems1.add(selectExpressionItem);

        SelectExpressionItem selectExpressionItem1 = new SelectExpressionItem();
        selectExpressionItem1.setAlias(new Alias("userName"));
        selectExpressionItem1.setExpression(CCJSqlParserUtil.parseCondExpression("user_name",false));
        selectItems1.add(selectExpressionItem1);

        plainSelect.setSelectItems(selectItems1);

        //设置fromeItem部分
        Table fromItem = new Table();
        fromItem.setName("User");
        fromItem.setAlias(new Alias("u"));
        plainSelect.setFromItem(fromItem);


        //设置where表达式部分
        plainSelect.setWhere(CCJSqlParserUtil.parseCondExpression("u.id = 10"));

        select.setSelectBody(plainSelect);

        System.out.println(select);

    }

}
