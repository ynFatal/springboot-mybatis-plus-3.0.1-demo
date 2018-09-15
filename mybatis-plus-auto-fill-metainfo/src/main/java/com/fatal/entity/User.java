package com.fatal.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: Fatal
 * @date: 2018/9/15 0015 12:33
 */
@Data
@TableName(value = "user")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    
    private Long id;
    
    private String name;
    
    private Integer age;
    
    private String email;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String operator;
}
