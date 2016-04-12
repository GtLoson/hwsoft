package com.hwsoft.service.category;

import com.hwsoft.model.category.Category;

import java.util.List;

/**
 * Created by Administrator on 2016/4/12.
 */
public interface CategoryService {

    /**
     * 保存一个Category对象
     * @param category
     * @return
     */
    public Category save(Category category);

    /**
     * 修改Category对象
     * @param category
     * @return
     */
    public Category update(Category category);

    /**
     * 根据ID获取一个Category对象
     * @param id
     * @return
     */
    public Category get(Integer id);

    /**
     * 查询所有
     * @return
     */
    public List<Category> getAll();

    /**
     * 分页查询
     * @param begin
     * @param offset
     * @return
     */
    public List<Category> getPaginationList(Integer begin,Integer offset);

    /**
     * 批量更新
     * @param categories
     * @return
     */
    public Integer updateBatch(List<Category> categories);

    /**
     * 批量保存
     * @param categories
     * @return
     */
    public Integer saveBatch(List<Category> categories);
}
