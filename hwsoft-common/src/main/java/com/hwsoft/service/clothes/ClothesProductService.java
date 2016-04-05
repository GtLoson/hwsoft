package com.hwsoft.service.clothes;

import com.hwsoft.model.product.ClothesProduct;

import java.util.List;

/**
 * Created by loson on 16/4/3.
 */
public interface ClothesProductService {
    /**
     * 保存一个ClothesProduct对象
     * @param clothes
     * @return
     */
    public ClothesProduct save(ClothesProduct clothes);

    /**
     * 修改ClothesProduct对象
     * @param clothes
     * @return
     */
    public ClothesProduct update(ClothesProduct clothes);

    /**
     * 根据ID获取一个ClothesProduct对象
     * @param id
     * @return
     */
    public ClothesProduct get(Integer id);

    /**
     * 查询所有
     * @return
     */
    public List<ClothesProduct> getAll();

    /**
     * 分页查询
     * @param begin
     * @param offset
     * @return
     */
    public List<ClothesProduct> getPaginationList(Integer begin,Integer offset);

    /**
     * 批量更新
     * @param clothes
     * @return
     */
    public Integer updateBatch(List<ClothesProduct> clothes);

    /**
     * 批量保存
     * @param clothes
     * @return
     */
    public Integer saveBatch(List<ClothesProduct> clothes);
}
