package com.hwsoft.service.banner.impl;

import com.hwsoft.common.version.AppOSType;
import com.hwsoft.dao.banner.BannerDao;
import com.hwsoft.exception.banner.BannerException;
import com.hwsoft.model.banner.Banner;
import com.hwsoft.model.file.CustomFile;
import com.hwsoft.service.banner.BannerService;
import com.hwsoft.spring.MessageSource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * bannerService IMPL
 */
@Service("bannerService")
public class BannerServiceImpl implements BannerService {

    private static Log logger = LogFactory.getLog(BannerServiceImpl.class);

    @Autowired
    private BannerDao bannerDao;

    @Autowired
    private MessageSource messageSource;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Banner getBannerById(int id) {
        return bannerDao.getById(id);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Banner> getBannersByOSType(AppOSType... appOSTypes) {
        return null;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public long getTotalCount(AppOSType... osTypes) {
        return bannerDao.getTotalCount(osTypes);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Banner> listAll(int from, int pageSize, AppOSType... osTypes) {
        return bannerDao.listByOSType(from, pageSize, osTypes);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
    public Banner addBanner(String picPath,
                            String bannerName,
                            String htmlTitle,
                            String htmlURL,
                            String osType,
                            boolean enable,
                            Integer sortParameter) {
        checkBannerParameter(bannerName, "app.banner.name.can.not.be.null");
        checkBannerParameter(osType, "app.banner.osType.can.not.be.null");
        checkBannerParameter(htmlTitle, "app.banner.html.name.can.not.be.null");
        checkBannerParameter(htmlURL, "app.banner.html.location.can.not.be.null");
        logger.info("upload file start process");
        logger.info("upload file end process");

        Date now = new Date();
        Banner banner = new Banner();
        banner.setName(bannerName.trim());
        banner.setEnable(enable);
        banner.setHtmlTitle(htmlTitle);
        banner.setHtmlURL(htmlURL);
        banner.setImageURI(picPath);
        banner.setCreateTime(now);
        banner.setUpdateTime(now);
        banner.setSortParameter(sortParameter);
        banner.setOsType(AppOSType.valueOf(osType));
        bannerDao.addBanner(banner);
        return banner;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
    public Banner updateBanner(Integer bannerId,
                               String picPath,
                               String bannerName,
                               String htmlTitle,
                               String htmlURL,
                               String osType,
                               boolean enable,
                               Integer sortParameter) {
        checkBannerParameter(bannerName, "app.banner.name.can.not.be.null");
        checkBannerParameter(osType, "app.banner.osType.can.not.be.null");
        checkBannerParameter(htmlTitle, "app.banner.html.name.can.not.be.null");
        checkBannerParameter(htmlURL, "app.banner.html.location.can.not.be.null");

        Banner banner = getBannerById(bannerId);
        if (null == banner) {
            throw new BannerException(messageSource.getMessage("app.banner.not.existing"));
        }
        if (null != picPath) {
            banner.setImageURI(picPath);
        }

        Date now = new Date();
        banner.setName(bannerName.trim());
        banner.setEnable(enable);
        banner.setHtmlTitle(htmlTitle.trim());
        banner.setHtmlURL(htmlURL.trim());
        banner.setUpdateTime(now);
        banner.setSortParameter(sortParameter);
        banner.setOsType(AppOSType.valueOf(osType));
        bannerDao.updateBanner(banner);

        return banner;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
    public Banner enableBanner(Integer bannerId) {
        Banner banner = getBannerById(bannerId);
        if (null == banner) {
            throw new BannerException(messageSource.getMessage("app.banner.not.existing"));
        }
        banner.setEnable(true);
        bannerDao.updateBanner(banner);
        return banner;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
    public Banner disableBanner(Integer bannerId) {
        Banner banner = getBannerById(bannerId);
        if (null == banner) {
            throw new BannerException(messageSource.getMessage("app.banner.not.existing"));
        }
        banner.setEnable(false);
        bannerDao.updateBanner(banner);
        return banner;
    }

    /**
     * 校验banner参数
     *
     * @param parameter 参数
     * @param key       异常的key
     */
    private void checkBannerParameter(String parameter, String key) {
        if (StringUtils.isEmpty(parameter)) {
            logger.error("请求参数不正确：" + messageSource.getMessage(key) + "传入值：【" + parameter + "】");
            throw new BannerException(messageSource.getMessage(key));
        }
    }

	/* (non-Javadoc)
	 * @see com.hwsoft.service.banner.BannerService#getBannersEnable()
	 */
	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public List<Banner> getBannersEnable() {
		return bannerDao.getBannersEnable();
	}
}
