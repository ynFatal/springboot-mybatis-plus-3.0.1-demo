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
 * 如果实体类名和属性与数据库字段对应(驼峰与下划线的关系)，那么MP默认帮我们映射。
 * 否则需要手动映射，表名用@TableName，属性用@TableField
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
