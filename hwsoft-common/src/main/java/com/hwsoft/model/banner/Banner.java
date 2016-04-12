/**
 *
 */
package com.hwsoft.model.banner;

import com.hwsoft.common.version.AppOSType;
import com.hwsoft.model.BaseModel;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * bannder 应用banner信息
 *
 * @author tzh
 */
@Entity
@Table(name = "banner")
public class Banner extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;// id

    /**
     * 名称
     */
    @Column(name = "name", nullable = true)
    private String name;// 名称

    /**
     * 系统版本
     */
    @Column(name = "app_os_type", nullable = true)
    @Enumerated(EnumType.STRING)
    private AppOSType osType;// 系统版本

    /**
     * 图片地址
     */
    @Column(name = "image_uri", nullable = true)
    private String imageURI;// 图片地址

    /**
     * 落地页地址，如微信落地页地址
     */
    @Column(name = "html_url",columnDefinition="text", nullable = true)
    private String htmlURL;// 点击之后的落地页地址

    /**
     * 落地页标题
     */
    @Column(name = "html_title", nullable = true)
    private String htmlTitle;// 网页标题

    /**
     * 是否可用
     */
    @Column(name = "enable", nullable = true)
    private boolean enable = true;// 是否可用


    /**
     * 排序参数
     */
    @Column(name = "sort_parameter", nullable = true)
    private Integer sortParameter;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AppOSType getOsType() {
        return osType;
    }

    public void setOsType(AppOSType osType) {
        this.osType = osType;
    }

    public String getImageURI() {
        return imageURI;
    }

    public void setImageURI(String imageURI) {
        this.imageURI = imageURI;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHtmlTitle() {
        return htmlTitle;
    }

    public void setHtmlTitle(String htmlTitle) {
        this.htmlTitle = htmlTitle;
    }

    public String getHtmlURL() {
        return htmlURL;
    }

    public void setHtmlURL(String htmlURL) {
        this.htmlURL = htmlURL;
    }

    public Integer getSortParameter() {
        return sortParameter;
    }

    public void setSortParameter(Integer sortParameter) {
        this.sortParameter = sortParameter;
    }
}


