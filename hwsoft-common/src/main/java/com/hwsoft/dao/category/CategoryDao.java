package com.hwsoft.dao.category;

import com.hwsoft.dao.BaseDao;
import com.hwsoft.model.category.Category;
import com.hwsoft.model.product.ClothesProduct;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by arvin on 16/4/3.
 */
@Repository("categoryDao")
public class CategoryDao extends BaseDao{

    @Override
    protected Class<?> entityClass() {
        return Category.class;
    }

    public Category save(Category category){
        return  super.add(category);
    }

    public Category update(Category category) {
        return super.update(category);
    }

    public Category get(Integer id) {
        return super.get(id);
    }

    public List<Category> getAll() {
        return super.loanAll();
    }

    public List<Category> getPaginationList(Integer begin, Integer offset) {
        return super.list(begin,offset);
    }

    public Category updateBatch(List<Category> categories) {
        return null;
    }

    public Category saveBatch(List<Category> categories) {
        return null;
    }
}
