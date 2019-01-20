package com.rao.study.tools.jsqlparser.parser;

import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.delete.Delete;
import org.junit.Test;

public class DeleteDemo {

    @Test
    public void testDelete()throws Exception{
        String sql = "delete from User where u.id  =10";
        Statement statement = CCJSqlParserUtil.parse(sql);
        Delete delete = (Delete)statement;
        System.out.println(delete.getTable());

        System.out.println(delete.getWhere());
    }
}
