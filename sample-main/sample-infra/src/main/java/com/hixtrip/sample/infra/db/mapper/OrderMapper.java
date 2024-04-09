package com.hixtrip.sample.infra.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hixtrip.sample.infra.db.dataobject.OrderDO;
import org.apache.ibatis.annotations.Mapper;

/**
* 订单表 Mapper
*
* @author lyf
* @since 2024-04-09 13:00
*/
@Mapper
public interface OrderMapper extends BaseMapper<OrderDO> {
}
