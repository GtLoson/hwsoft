package com.hwsoft.service.banner;


import com.hwsoft.model.banner.Banner;

import java.util.List;

/**
 * bannerService
 */
public interface BannerService {

    /**
     * 保存一个Category对象
     * @param banner
     * @return
     */
    public Banner save(Banner banner);

    /**
     * 修改Category对象
     * @param banner
     * @return
     */
    public Banner update(Banner banner);

    /**
     * 根据ID获取一个Category对象
     * @param id
     * @return
     */
    public Banner get(Integer id);

    /**
     * 查询所有
     * @return
     */
    public List<Banner> getAll();

    /**
     * 分页查询
     * @param begin
     * @param offset
     * @return
     */
    public List<Banner> getPaginationList(Integer begin,Integer offset);

    /**
     * 批量更新
     * @param banners
     * @return
     */
    public Integer updateBatch(List<Banner> banners);

    /**
     * 批量保存
     * @param banners
     * @return
     */
    public Integer saveBatch(List<Banner> banners);
}
