package com.ron.combat.microserviceuser.mapper;

import com.ron.combat.microserviceuser.entity.Address;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface AddressMapper {

    /**
     * 保存
     */
    void save(Address address);

    /**
     * 查询
     * @param id
     * @return
     */
    Address get(Long id);
}
