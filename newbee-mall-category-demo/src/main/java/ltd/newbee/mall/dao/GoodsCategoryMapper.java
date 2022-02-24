/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本系统已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2019-2020 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package ltd.newbee.mall.dao;

import ltd.newbee.mall.entity.GoodsCategory;
import ltd.newbee.mall.util.PageQueryUtil;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GoodsCategoryMapper {

    /**
     * 删除一条记录
     *
     * @param categoryId
     * @return
     */
    int deleteByPrimaryKey(Long categoryId);

    /**
     * 保存一条新纪录
     *
     * @param record
     * @return
     */
    int insert(GoodsCategory record);

    /**
     * 保存一条新纪录
     *
     * @param record
     * @return
     */
    int insertSelective(GoodsCategory record);

    /**
     * 根据主键查询记录
     *
     * @param categoryId
     * @return
     */
    GoodsCategory selectByPrimaryKey(Long categoryId);

    /**
     * 根据分类等级和分类名称查询一条分类记录
     *
     * @param categoryLevel
     * @param categoryName
     * @return
     */
    GoodsCategory selectByLevelAndName(@Param("categoryLevel") Byte categoryLevel, @Param("categoryName") String categoryName);

    /**
     * 修改记录
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(GoodsCategory record);

    /**
     * 修改记录
     *
     * @param record
     * @return
     */
    int updateByPrimaryKey(GoodsCategory record);

    /**
     * 查询分页数据
     *
     * @param pageUtil
     * @return
     */
    List<GoodsCategory> findGoodsCategoryList(PageQueryUtil pageUtil);

    /**
     * 查询总数
     *
     * @param pageUtil
     * @return
     */
    int getTotalGoodsCategories(PageQueryUtil pageUtil);

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    int deleteBatch(Integer[] ids);

    /**
     * 根据父类分类id、分类等级和数量查询分类列表
     *
     * @param parentIds
     * @param categoryLevel
     * @param number
     * @return
     */
    List<GoodsCategory> selectByLevelAndParentIdsAndNumber(@Param("parentIds") List<Long> parentIds, @Param("categoryLevel") int categoryLevel, @Param("number") int number);
}