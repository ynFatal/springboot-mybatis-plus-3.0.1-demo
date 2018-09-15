package com.fatal.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * User 实体
 * @author: Fatal
 * @date: 2018/9/12 0012 13:07
 */
@Data
@Accessors(chain = true)
public class User {

    private Long id;

    private String name;

    private Integer age;

    private String email;

}
