package com.fatal.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fatal.entity.User;
import com.fatal.service.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * @author: Fatal
 * @date: 2018/9/2 0002 12:23
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class PageTest {

    @Autowired
    private IUserService userService;

    /**
     * List<User>
     */
    @Test
    public void page() {
        IPage<User> page = userService.page(new Page<User>(1, 5), null);
        if (page != null) {
            List<User> records = page.getRecords();
            if (!CollectionUtils.isEmpty(records)) {
                records.forEach(System.out::println);
            }
        }
    }

    /**
     * List<Map<String, Object>>
     */
    @Test
    public void pageMaps() {
        IPage<Map<String, Object>> mapIPage = userService.pageMaps(new Page<>(1, 5), null);
        if (mapIPage != null) {
            List<Map<String, Object>> records = mapIPage.getRecords();
            if (!CollectionUtils.isEmpty(records)) {
                records.forEach(System.out::println);
            }
        }
    }
}
