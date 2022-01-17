package org.rick.mysqlcrud.controller;


import org.rick.mysqlcrud.entity.UserEntity;
import org.rick.mysqlcrud.jpa.UserJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserJpa userJpa;

    /**
     * 查询用户列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<UserEntity> list(){
        return userJpa.findAll();
    }

    @RequestMapping(value = "/getByName/{name}", method = RequestMethod.GET)
    public List<UserEntity> getByName(@PathVariable("name") String name){
        return userJpa.findByName(name);
    }

    /**
     * 添加或更新用户
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public UserEntity save(UserEntity entity){
        return userJpa.save(entity);
    }

    /**
     * 删除用户
     */
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public String delete(Long id){
        if(userJpa.findById(id).isPresent()){
            userJpa.deleteById(id);
            return id + " exists and deleted !";
        }
        return id + " not exists !";
    }
}
