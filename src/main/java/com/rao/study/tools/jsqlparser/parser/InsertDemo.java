package com.rao.study.tools.jsqlparser.parser;

import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.insert.Insert;
import org.junit.Test;

public class InsertDemo {

    @Test
    public void testInsert()throws Exception{
        Statement statement = CCJSqlParserUtil.parse("insert INTO teach(name,stu_id) values('fff',1),('ddd',2)");
        Insert insert = (Insert)statement;
        System.out.println(insert.getColumns());
        System.out.println(insert.getTable());
        System.out.println(insert.getItemsList());
    }
}
