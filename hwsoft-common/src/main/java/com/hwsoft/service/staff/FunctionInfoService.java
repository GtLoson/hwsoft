/**
 *
 */
package com.hwsoft.service.staff;

import com.hwsoft.model.staff.FunctionInfo;
import com.hwsoft.vo.staff.AuthorityVo;

import java.util.List;
import java.util.Map;

/**
 * @author tzh
 */
public interface FunctionInfoService {

	/**
	 * 根据id获取功能
	 * @param id
	 * @return
	 */
	FunctionInfo getFunctionInfoById(int id);
	
	/**
	 * 更新功能
	 * @param functionInfo
	 * @return
	 */
	FunctionInfo updateFunctionInfo(Integer functionInfoId,String name,String uri);
	
	/**
	 * 添加功能
	 * @param functionInfo
	 * @return
	 */
	FunctionInfo addFunctionInfo(String name,String uri,boolean enable,Integer parentId);
	
	
	/**
	 * 获取功能数量
	 * @return
	 */
	int getCountByParenId(Integer parentId);
	
	/**
	 * 根据父亲id分页查询孩子数据
	 * @param parentId
	 * @return
	 */
	public List<FunctionInfo> findPageFunctionInfosByParenId(Integer parentId, int from , int pageSize);
	/**
	 * 根据父亲id查询孩子数据
	 * @param parentId
	 * @return
	 */
	public List<FunctionInfo> findFunctionInfosByParenId(Integer parentId);
	
	/***
	 * 启用功能
	 * @return
	 */
	public FunctionInfo enable(int id);
	/***
	 * 禁用功能
	 * @param functionInfo
	 * @return
	 */
	public FunctionInfo disable(int id);
	/**
	 * 获取功能数量
	 * @return
	 */
	int getCount();
	
	/**
	 * 查询所有的功能信息
	 * @return
	 */
	public List<FunctionInfo> listAll();
	
	/**
	 * 查询所有的功能信息(不查询孩子节点信息)
	 * 只查询id和url
	 * @return
	 */
	public List<Map<String, Object>> listNoChildrenAll();
	
	/**
	 * 加载权限数据
	 * @return
	 */
	public List<AuthorityVo> loadResourceDefine(Integer functionInfoId);
	
}
