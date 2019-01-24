package com.rao.study.tools.mybatisplus.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rao.study.tools.mybatisplus.aop.Cost;
import com.rao.study.tools.mybatisplus.aop.Count;
import com.rao.study.tools.mybatisplus.mapper.UserMapper;
import com.rao.study.tools.mybatisplus.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MyBatisPlusService {
    @Autowired
    private UserMapper userMapper;

    public void testInsert(){
        User user = new User();
        user.setLastName("asdsa");
        user.setAge(20);
        user.setEmail("ssdf@qq.com");
        user.setGender(true);
        userMapper.insert(user);
    }

    public void testUpdate(){
        User user = new User();
        user.setId(1);
        user.setLastName("fffff");
        user.setAge(30);
        user.setEmail("ffff@qq.com");
        user.setGender(false);
        userMapper.updateById(user);
    }

    public void testSelect(){
        User user = userMapper.selectById(1);
        System.out.println(user.getLastName());
    }

    public void testQueryWrapAnd(){
        List<User> users =  userMapper.selectList(new QueryWrapper<User>().eq("age",20).eq("gender",true));//两个条件连起来表示and
        System.out.println(users);
    }

    public void testQeuryWrapOr(){
        List<User> users =  userMapper.selectList(new QueryWrapper<User>().eq("age",20).or().eq("gender",true));//两个条件连起来表示and
        System.out.println(users);
    }

    public void testUpdateWrap(){
        User user = new User();
        user.setGender(false);
        user.setEmail("bmnbnmbn@163.com");
        userMapper.update(user,new UpdateWrapper<User>().eq("id",1));
    }

    public void testPage(){
        IPage<User> page = new Page<>(1,2);
        page =  userMapper.selectPage(page,null);

        System.out.println(page.getCurrent());
        System.out.println(page.getRecords());
        System.out.println(page.getPages());
        System.out.println(page.getTotal());
    }

    //自定义
    public void testCustom(){
        Page<User> page = new Page<>(1,1);
        IPage<User> userIPage = userMapper.selectPageVo(page,20);
        System.out.println(userIPage.getCurrent());
        System.out.println(userIPage.getRecords());
        System.out.println(userIPage.getPages());
        System.out.println(userIPage.getTotal());
        userMapper.updateUser();
    }

//    @Transactional
    public void testTransaction(){
        userMapper.updateUser();

        int i=10/0;
    }

    @Cost
    @Transactional(rollbackFor = Exception.class)
    @Order(2)
    public void testTransactionAndAspect() throws Exception{
        userMapper.updateUser1();
        System.out.println("testTransactionAndAspect");
//        int i=10/0;
        throw new Exception();
//        testCount();
    }

    @Count
    public void testCount(){
        User user = new User();
        user.setLastName("fgdhgfdhfgs");
        user.setAge(60);
        user.setEmail("ffaaaaaaaa@qq.com");
        user.setGender(false);
        userMapper.insert(user);
    }

}
