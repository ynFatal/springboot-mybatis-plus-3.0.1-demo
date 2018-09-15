package com.fatal.config;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.AbstractSqlInjector;
import com.baomidou.mybatisplus.core.injector.SqlRunnerInjector;
import com.baomidou.mybatisplus.core.injector.methods.Insert;
import com.baomidou.mybatisplus.core.injector.methods.UpdateById;
import com.baomidou.mybatisplus.extension.injector.methods.*;
import org.apache.ibatis.session.Configuration;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 *  SQL 逻辑删除注入器
 * </p>
 * @author: Fatal
 * @date: 2018/9/6 0006 11:02
 * @desc: 这个类从框架中复制出来的
 */
public class LogicSqlInjector  extends AbstractSqlInjector {

    /**
     * 获得方法集合。
     * 是否带逻辑删除，两者选一即可。例：
     * LogicUpdateById() 和 UpdateById()
     * （1）LogicUpdateById：
     *          UPDATE user SET name=?, age=?, email=? WHERE id=? AND is_delete=0
     * （2）UpdateById：
     *          UPDATE user SET name=?, age=?, email=?, is_delete=? WHERE id=?
     * @return
     */
    @Override
    public List<AbstractMethod> getMethodList() {
        return Stream.of(
                new Insert(),
                new LogicDelete(),
                new LogicDeleteByMap(),
                new LogicDeleteById(),
                new LogicDeleteBatchByIds(),
                new LogicUpdate(),
//                new LogicUpdateById(),
                new UpdateById(),
                new LogicSelectById(),
                new LogicSelectBatchByIds(),
                new LogicSelectByMap(),
                new LogicSelectOne(),
                new LogicSelectCount(),
                new LogicSelectMaps(),
                new LogicSelectMapsPage(),
                new LogicSelectObjs(),
                new LogicSelectList(),
                new LogicSelectPage()
        ).collect(Collectors.toList());
    }


    @Override
    public void injectSqlRunner(Configuration configuration) {
        new SqlRunnerInjector().inject(configuration);
    }
}