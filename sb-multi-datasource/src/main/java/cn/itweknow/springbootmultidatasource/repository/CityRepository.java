package cn.itweknow.springbootmultidatasource.repository;

import cn.itweknow.springbootmultidatasource.dao.City;
import cn.itweknow.springbootmultidatasource.dao.mapper.dbone.CityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author ganchaoyang
 * @date 2019/1/22 18:57
 * @description
 */
@Repository("cityRepository")
public class CityRepository {

    @Autowired
    private CityMapper cityMapper;

    /**
     * 根据id查找城市
     * @param id
     * @return
     */
    public City findById(int id) {
        return cityMapper.selectByPrimaryKey(id);
    }

}
