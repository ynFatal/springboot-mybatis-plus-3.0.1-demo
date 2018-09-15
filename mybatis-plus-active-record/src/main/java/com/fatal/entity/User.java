package com.fatal.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 用户实体对应表 user
 * </p>
 *
 * @author Fatal
 * @since 2018-08-11
 */
@Data
@Accessors(chain = true)
//@TableName(value = "user")
public class User extends Model<User> {

    private Long id;

    private String name;

    private Integer age;

//    @TableField("email")
    private String email;

    @TableField("hobby_name")
    private String hobbyName;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
