/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本系统已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2019-2020 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package ltd.newbee.mall.dao;

import ltd.newbee.mall.entity.Carousel;
import ltd.newbee.mall.util.PageQueryUtil;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CarouselMapper {

    /**
     * 删除一条记录
     * @param carouselId
     * @return
     */
    int deleteByPrimaryKey(Integer carouselId);

    /**
     * 保存一条新纪录
     * @param record
     * @return
     */
    int insert(Carousel record);

    /**
     * 保存一条新纪录
     * @param record
     * @return
     */
    int insertSelective(Carousel record);

    /**
     * 根据主键查询记录
     * @param carouselId
     * @return
     */
    Carousel selectByPrimaryKey(Integer carouselId);

    /**
     * 修改记录
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Carousel record);

    /**
     * 修改记录
     * @param record
     * @return
     */
    int updateByPrimaryKey(Carousel record);

    /**
     * 查询分页数据
     * @param pageUtil
     * @return
     */
    List<Carousel> findCarouselList(PageQueryUtil pageUtil);

    /**
     * 查询总数
     * @param pageUtil
     * @return
     */
    int getTotalCarousels(PageQueryUtil pageUtil);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    int deleteBatch(Integer[] ids);

    /**
     * 查询固定数量的记录
     * @param number
     * @return
     */
    List<Carousel> findCarouselsByNum(@Param("number") int number);
}