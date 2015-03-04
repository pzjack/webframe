/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinglicom.frame.sys.web;

import com.dinglicom.frame.sys.entity.SysUserAccount;
import com.dinglicom.frame.sys.service.impl.SysUserAccountServiceImpl;
import java.net.URI;
import java.util.Set;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * @author panzhen
 */
@RestController
@RequestMapping(value = "/account")
public class AccountController {

    private final static Logger LOG = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private Validator validator;
    @Autowired
    private SysUserAccountServiceImpl userService;

    @RequestMapping(method = RequestMethod.POST, value="/create", consumes = "application/json")
    public ResponseEntity<?> create(@RequestBody SysUserAccount user, UriComponentsBuilder uriBuilder) {
        // 调用JSR303 Bean Validator进行校验, 异常将由RestExceptionHandler统一处理.
        Set constraintViolations = validator.validate(user);
        if (!constraintViolations.isEmpty()) {
            throw new ConstraintViolationException(constraintViolations);
        }
        // 保存任务
        user = userService.save(user);

        // 按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
        Long id = user.getId();
        URI uri = uriBuilder.path("/account/" + id).build().toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uri);
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }
    
}
