package com.hwsoft.dao.banner;

import com.hwsoft.dao.BaseDao;
import com.hwsoft.model.banner.Banner;
import com.hwsoft.model.category.Category;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * banner DAO
 */
@Repository("bannerDao")
public class BannerDao extends BaseDao {
    @Override
    protected Class<?> entityClass() {
        return Banner.class;
    }

    public Banner save(Banner banner){
        return  super.add(banner);
    }

    public Banner update(Banner banner) {
        return super.update(banner);
    }

    public Banner get(Integer id) {
        return super.get(id);
    }

    public List<Banner> getAll() {
        return super.loanAll();
    }

    public List<Banner> getPaginationList(Integer begin, Integer offset) {
        return super.list(begin,offset);
    }

    public Banner updateBatch(List<Banner> banners) {
        return null;
    }

    public Banner saveBatch(List<Banner> banners) {
        return null;
    }
}
