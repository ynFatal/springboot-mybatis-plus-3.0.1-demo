package com.fatal.entity;


import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 基础父类测试
 * </p>
 *
 * @author: Fatal
 * @date: 2018/9/15 0015 14:34
 */
@Data
@Accessors(chain = true)
public class BaseEntity {
  private Long id;

}
