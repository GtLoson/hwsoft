/**
 *
 */
package com.hwsoft.model.staff;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author tzh
 */
@Entity
@Table(name = "function_info")
public class FunctionInfo implements Serializable, Cloneable {

    private static final long serialVersionUID = 6356327763020636442L;

    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    /**
     * 功能名称
     */
    @Column(name = "name", nullable = false, length = 32)
    private String name;

    /**
     * 访问地址
     */
    @Column(name = "uri", nullable = false, length = 128, unique = true)
    private String uri;

    /**
     * 创建时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time", nullable = false)
    private Date createTime;

    /**
     * 是否启用
     */
    @Column(name = "is_enable", nullable = false)
    private boolean enable;

    /**
     * 父亲节点
     */
    @Column(name = "parent_id", columnDefinition = "int DEFAULT 0")
    private Integer parentId;

    /**
     * 孩子功能集合
     */
    @OneToMany(targetEntity = FunctionInfo.class, cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER, mappedBy = "parentId")
    private List<FunctionInfo> children;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    /**
     * @return the createTime
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime the createTime to set
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public List<FunctionInfo> getChildren() {
        return children;
    }

    public void setChildren(List<FunctionInfo> children) {
        this.children = children;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#clone()
     */
    @Override
    public FunctionInfo clone() throws CloneNotSupportedException {
        FunctionInfo fi = (FunctionInfo) super.clone();
        if (children != null && !children.isEmpty()) {
            List<FunctionInfo> list = new ArrayList<FunctionInfo>();
            for (FunctionInfo f : children) {
                list.add(f.clone());
            }
            fi.children = list;
        }
        return fi;
    }

}
