package com.fatal.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Fatal
 * @since 2018-09-14
 */
@Data
@Accessors(chain = true)
public class User {
    private Long id;
    private String name;
    private Integer age;
    private String email;
    private Long roleId;
}
