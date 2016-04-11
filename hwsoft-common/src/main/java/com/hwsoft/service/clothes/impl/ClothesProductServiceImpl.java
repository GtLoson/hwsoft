package com.hwsoft.service.clothes.impl;

import com.hwsoft.dao.clothes.ClothesProductDao;
import com.hwsoft.model.product.ClothesProduct;
import com.hwsoft.service.clothes.ClothesProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by arvin on 16/4/3.
 */
@Service("clothesProductService")
public class ClothesProductServiceImpl implements ClothesProductService {

    Logger  logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    ClothesProductDao clothesProductDao;

    @Override
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
    public ClothesProduct save(ClothesProduct clothes){
        return clothesProductDao.save(clothes);
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor=RuntimeException.class)
    public ClothesProduct update(ClothesProduct clothes) {
        return clothesProductDao.update(clothes);
    }

    @Override
    @Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
    public ClothesProduct get(Integer id) {
        return null;
    }

    @Override
    @Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
    public List<ClothesProduct> getAll() {
        return null;
    }

    @Override
    @Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
    public List<ClothesProduct> getPaginationList(Integer begin, Integer offset) {
        return null;
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor=RuntimeException.class)
    public Integer updateBatch(List<ClothesProduct> clothes) {
        for (ClothesProduct cloth:clothes){
            try{
                this.update(cloth);
            }catch (Exception e){
                logger.error("批量更新异常：",e);
                continue;
            }
        }
        return null;
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor=RuntimeException.class)
    public Integer saveBatch(List<ClothesProduct> clothes) {
        for (ClothesProduct cloth:clothes){
            try{
                System.out.println(cloth.toString());
                this.save(cloth);
            }catch (Exception e){
                logger.error("批量保存异常：",e);
                continue;
            }
        }
        return null;
    }

}
