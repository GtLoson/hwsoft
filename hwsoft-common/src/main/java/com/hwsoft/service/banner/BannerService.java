package com.hwsoft.service.banner;

import com.hwsoft.common.version.AppOSType;
import com.hwsoft.model.banner.Banner;
import com.hwsoft.model.file.CustomFile;

import java.util.List;

/**
 * bannerService
 */
public interface BannerService {

    /**
     * 根据Id获取banner
     *
     * @param id id
     * @return banner
     */
    public Banner getBannerById(int id);

    /**
     * 根据类型获取banner
     *
     * @param appOSTypes os
     * @return banners
     */
    public List<Banner> getBannersByOSType(AppOSType... appOSTypes);

    /**
     * 添加banner
     *
     * @param picPath 文件
     * @param bannerName name
     * @param htmlTitle  网页标题
     * @param htmlURL    网页地址
     * @param osType     系统类型
     * @param enable     是否可用
     * @return 添加的banner
     */
    public Banner addBanner(String picPath,
                            String bannerName,
                            String htmlTitle,
                            String htmlURL,
                            String osType,
                            boolean enable,
                            Integer sortParameter);

    /**
     * 更新banner
     *
     * @param picPath    文件
     * @param bannerName    name
     * @param htmlTitle     title
     * @param htmlURL       url
     * @param osType        os
     * @param enable        enable
     * @param sortParameter sort
     * @return
     */
    public Banner updateBanner(Integer bannerId,
                               String picPath,
                               String bannerName,
                               String htmlTitle,
                               String htmlURL,
                               String osType,
                               boolean enable,
                               Integer sortParameter);

    /**
     * 根据系统版本获取总数量
     *
     * @return
     */
    public long getTotalCount(AppOSType... osTypes);

    /**
     * 获取对应系统的banner
     *
     * @param from     from
     * @param pageSize size
     * @param osTypes  os
     * @return
     */
    public List<Banner> listAll(int from, int pageSize, AppOSType... osTypes);

    /**
     * 启用banner
     *
     * @param bannerId id
     * @return
     */
    public Banner enableBanner(Integer bannerId);

    /**
     * 禁用banner
     *
     * @param bannerId id
     * @return
     */
    public Banner disableBanner(Integer bannerId);
    
    
    /**
     * 根据类型获取banner
     *
     * @param appOSTypes os
     * @return banners
     */
    public List<Banner> getBannersEnable();
}
