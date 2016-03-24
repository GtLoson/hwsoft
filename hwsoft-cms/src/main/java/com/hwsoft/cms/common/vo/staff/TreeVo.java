package com.hwsoft.cms.common.vo.staff;

import java.io.Serializable;
import java.util.List;


/**
 * jquery easiui tree 展示所需要的数据
 * @author tzh
 *
 */
public class TreeVo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2588122316440692052L;

	public static final String STATE_OPNE = "open";
	
	public static final String STATE_CLOSED = "closed";
	
	/**
	 * 树节点的id
	 */
	private Integer id;
	
	
	/**
	 * 树节点暂时的文字
	 */
	private String text;
	
	
	/**
	 * 状态，open/closed
	 */
	private String state = STATE_CLOSED;
	
	/**
	 * 点击节点访问的路径
	 */
	private String url;
	
	
	/**
	 * 孩子节点
	 */
	private List<TreeVo> children;
	
	
	/**
	 * 图标吧？？？
	 */
	private String iconCls;
	
	/**
	 * 默认不选中
	 */
	private boolean checked = false;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<TreeVo> getChildren() {
		return children;
	}

	public void setChildren(List<TreeVo> children) {
		this.children = children;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

}
