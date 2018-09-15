package com.fatal.entity;

import com.fatal.enums.AgeEnum;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户实体对应表 user
 * </p>
 *
 * @author: Fatal
 * @date: 2018/9/15 0015 14:34
 */
@Data
@Accessors(chain = true)
public class User extends BaseEntity {

    private String name;
    private AgeEnum age;
    private String email;

}
