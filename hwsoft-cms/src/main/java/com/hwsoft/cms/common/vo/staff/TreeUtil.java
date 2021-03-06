package com.hwsoft.cms.common.vo.staff;



import com.hwsoft.model.staff.FunctionInfo;
import com.hwsoft.model.staff.StaffRole;

import java.util.ArrayList;
import java.util.List;


public class TreeUtil {
	
	
	/**
	 * 将function对象转换成 树
	 * @param functionInfos
	 * @return
	 */
	public static List<TreeVo> functionInfoToTreeVo(List<FunctionInfo> functionInfos,Integer id){
		List<TreeVo> treeVos = new ArrayList<TreeVo>();
		for(FunctionInfo info : functionInfos){
			TreeVo treeVo = new TreeVo();
			treeVo.setId(info.getId());
			
			if(null == info.getId() && null == id){
				treeVo.setState(TreeVo.STATE_OPNE);
				treeVo.setChecked(true);
			}
			
			if(null != info.getId() && null != id && info.getId().equals(id)){
				treeVo.setState(TreeVo.STATE_OPNE);
				treeVo.setChecked(true);
			}
			
			treeVo.setText(info.getName());
			treeVo.setUrl(info.getUri());
			List<TreeVo> childrenList = null;
			if(null != info.getChildren() && info.getChildren().size() != 0){
				childrenList = functionInfoToTreeVo(info.getChildren(),id);
			}
			treeVo.setChildren(childrenList);
			treeVos.add(treeVo);
		}
		return treeVos;
		
	}
	
	/**
	 * 将function对象转换成 树
	 * @param functionInfos
	 * @return
	 */
	public static List<TreeVo> functionInfoToTreeVo(List<FunctionInfo> functionInfos
			,List<StaffRole> staffRoles){
		
		List<TreeVo> treeVos = new ArrayList<TreeVo>();
		for(FunctionInfo info : functionInfos){
			TreeVo treeVo = new TreeVo();
			treeVo.setId(info.getId());
			treeVo.setText(info.getName());
			treeVo.setUrl(info.getUri());
			List<TreeVo> childrenList = null;
			for(StaffRole staffRole:staffRoles) {
				if (null != staffRole.getFunctionInfos()) {
					for (FunctionInfo functionInfo : staffRole.getFunctionInfos()) {
						if (functionInfo.getId().intValue() == info.getId().intValue()) {
							treeVo.setChecked(true);
							break;
						}
					}
				}
			}
			if(null != info.getChildren() && info.getChildren().size() != 0){
				childrenList = functionInfoToTreeVo(info.getChildren(),staffRoles);
			} 
			
			treeVo.setChildren(childrenList);
			treeVos.add(treeVo);
		}
		return treeVos;
	}


	/**
	 * 将function对象转换成 树
	 * @param functionInfos
	 * @return
	 */
	public static List<TreeVo> functionInfoToTreeVo(List<FunctionInfo> functionInfos
			,StaffRole staffRole){

		List<TreeVo> treeVos = new ArrayList<TreeVo>();
		for(FunctionInfo info : functionInfos){
			TreeVo treeVo = new TreeVo();
			treeVo.setId(info.getId());
			treeVo.setText(info.getName());
			treeVo.setUrl(info.getUri());
			List<TreeVo> childrenList = null;
				if (null != staffRole.getFunctionInfos()) {
					for (FunctionInfo functionInfo : staffRole.getFunctionInfos()) {
						if (functionInfo.getId().intValue() == info.getId().intValue()) {
							treeVo.setChecked(true);
							break;
						}
					}
				}
			if(null != info.getChildren() && info.getChildren().size() != 0){
				childrenList = functionInfoToTreeVo(info.getChildren(),staffRole);
			}

			treeVo.setChildren(childrenList);
			treeVos.add(treeVo);
		}
		return treeVos;
	}
}
