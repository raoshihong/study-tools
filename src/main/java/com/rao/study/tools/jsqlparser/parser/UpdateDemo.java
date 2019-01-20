package com.rao.study.tools.jsqlparser.parser;

import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.update.Update;
import org.junit.Test;

import java.util.List;

public class UpdateDemo {

    @Test
    public void testUpdateItem()throws Exception{
        String sql = "update Student set name = 'fff' where id = 10";
        Statement statement = CCJSqlParserUtil.parse(sql);
        Update update = (Update) statement;
        System.out.println(update);
        List<Column> columns = update.getColumns();
        System.out.println(columns);

        System.out.println(update.getWhere());

        System.out.println(update.getTables());

        System.out.println(update.getExpressions());
    }
}
