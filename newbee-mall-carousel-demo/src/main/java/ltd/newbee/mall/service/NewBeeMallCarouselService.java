/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本系统已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2019-2020 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package ltd.newbee.mall.service;

import ltd.newbee.mall.entity.Carousel;
import ltd.newbee.mall.util.PageQueryUtil;
import ltd.newbee.mall.util.PageResult;

public interface NewBeeMallCarouselService {
    /**
     * 查询后台管理系统轮播图分页数据
     *
     * @param pageUtil
     * @return
     */
    PageResult getCarouselPage(PageQueryUtil pageUtil);

    /**
     * 新增一条轮播图记录
     *
     * @param carousel
     * @return
     */
    String saveCarousel(Carousel carousel);

    /**
     * 修改一条轮播图记录
     *
     * @param carousel
     * @return
     */
    String updateCarousel(Carousel carousel);

    /**
     * 根据主键查询轮播图记录
     *
     * @param id
     * @return
     */
    Carousel getCarouselById(Integer id);

    /**
     * 批量删除轮播图记录
     *
     * @param ids
     * @return
     */
    Boolean deleteBatch(Integer[] ids);
}
