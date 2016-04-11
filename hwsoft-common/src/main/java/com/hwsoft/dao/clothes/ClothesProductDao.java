package com.hwsoft.dao.clothes;

import com.hwsoft.dao.BaseDao;
import com.hwsoft.model.product.ClothesProduct;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by arvin on 16/4/3.
 */
@Repository("clothesProductDao")
public class ClothesProductDao extends BaseDao{

    @Override
    protected Class<?> entityClass() {
        return ClothesProduct.class;
    }

    public ClothesProduct save(ClothesProduct clothes){
        return  super.add(clothes);
    }

    public ClothesProduct update(ClothesProduct clothes) {
        return super.update(clothes);
    }

    public ClothesProduct get(Integer id) {
        return super.get(id);
    }

    public List<ClothesProduct> getAll() {
        return super.loanAll();
    }

    public List<ClothesProduct> getPaginationList(Integer begin, Integer offset) {
        return super.list(begin,offset);
    }

    public ClothesProduct updateBatch(List<ClothesProduct> clothes) {
        return null;
    }

    public ClothesProduct saveBatch(List<ClothesProduct> clothes) {
        return null;
    }
}
