package com.rao.study.tools.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rao.study.tools.mybatisplus.po.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.core.annotation.Order;

//只需要创建自己的mapper继承Mybatis-plus的BaseMapper接口即可,不用写mapper.xml文件,也可以添加mapper.xml,在baseMapper中定义了很多有用的方法
//MapperScanner已经扫描这个接口了
public interface UserMapper extends BaseMapper<User> {
    IPage<User> selectPageVo(Page page, @Param("age") Integer state);
    void updateUser();
    void updateUser1();
}
