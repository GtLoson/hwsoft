/**
 *
 */
package com.hwsoft.service.staff.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hwsoft.dao.staff.FunctionInfoDao;
import com.hwsoft.exception.staff.StaffException;
import com.hwsoft.model.staff.FunctionInfo;
import com.hwsoft.service.staff.FunctionInfoService;
import com.hwsoft.spring.MessageSource;
import com.hwsoft.vo.staff.AuthorityVo;

/**
 * @author tzh
 */
@Service("functionInfoService")
public class FunctionInfoServiceImpl implements FunctionInfoService {

    @Autowired
    private FunctionInfoDao functionInfoDao;

    @Autowired
    private MessageSource messageSource;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = java.lang.RuntimeException.class)
    public FunctionInfo addFunctionInfo(String name, String uri, boolean enable, Integer parentId) {
        //根据提交的功能名字去查询有无此功能
        if (StringUtils.isEmpty(name)) {
            throw new StaffException(messageSource.getMessage("function.info.name.is.not.null"));
        }
        if (StringUtils.isEmpty(uri)) {
            throw new StaffException(messageSource.getMessage("function.info.uri.is.not.null"));
        }
        FunctionInfo functionInfo = new FunctionInfo();
        functionInfo.setName(name);
        functionInfo.setEnable(enable);
        functionInfo.setCreateTime(new Date());
        functionInfo.setUri(uri);
        functionInfo.setParentId(parentId);
        return functionInfoDao.addFunctionInfo(functionInfo);
    }

    /**
     * 根据 parentId查询 总记录数
     *
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public int getCountByParenId(Integer parentId) {
        return functionInfoDao.getCountByParenId(parentId);
    }

    /**
     * 根据父亲id分页查询孩子数据
     *
     * @param parentId
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<FunctionInfo> findPageFunctionInfosByParenId(Integer parentId, int from, int pageSize) {
        return functionInfoDao.findPageFunctionInfosByParenId(parentId, from, pageSize);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<FunctionInfo> findFunctionInfosByParenId(Integer parentId) {
        return functionInfoDao.findFunctionInfosByParenId(parentId);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public FunctionInfo getFunctionInfoById(int id) {

        return functionInfoDao.getById(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = java.lang.RuntimeException.class)
    public FunctionInfo updateFunctionInfo(Integer functionInfoId, String name, String uri) {
        FunctionInfo functionInfo = functionInfoDao.getById(functionInfoId);
        if (null == functionInfo) {
            throw new RuntimeException("functionInfo.is.not.exsit");
        }
        if (StringUtils.isEmpty(name)) {
            throw new StaffException(messageSource.getMessage("function.info.name.is.not.null"));
        }
        if (StringUtils.isEmpty(uri)) {
            throw new StaffException(messageSource.getMessage("function.info.uri.is.not.null"));
        }
        functionInfo.setName(name);
        functionInfo.setUri(uri);
        return functionInfoDao.updateFunctionInfo(functionInfo);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = java.lang.RuntimeException.class)
    public FunctionInfo enable(int id) {
        FunctionInfo temFunctionInfo = functionInfoDao.getById(id);
        if (temFunctionInfo == null) {
            throw new RuntimeException("functioninfp.name.exists");
        }
        temFunctionInfo.setEnable(true);
        return functionInfoDao.updateFunctionInfo(temFunctionInfo);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = java.lang.RuntimeException.class)
    public FunctionInfo disable(int id) {
        FunctionInfo temFunctionInfo = functionInfoDao.getById(id);
        if (temFunctionInfo == null) {
            throw new RuntimeException("functioninfp.name.exists");
        }
        temFunctionInfo.setEnable(false);
        return functionInfoDao.updateFunctionInfo(temFunctionInfo);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public int getCount() {
        return functionInfoDao.getCount();
    }

    /**
     * 查询所有的功能信息
     *
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<FunctionInfo> listAll() {
        return functionInfoDao.listAll();
    }

    /**
     * 查询所有的功能信息(不查询孩子节点信息)
     *
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Map<String, Object>> listNoChildrenAll() {

        return functionInfoDao.listNoChildrenAll();
    }

    /* (non-Javadoc)
     * @see com.hwsoft.service.staff.FunctionInfoService#loadResourceDefine()
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = java.lang.RuntimeException.class)
    public List<AuthorityVo> loadResourceDefine(Integer functionInfoId) {
        return functionInfoDao.loadResourceDefine(functionInfoId);
    }

}
