package com.yrnet.viewweb.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yrnet.viewweb.common.entity.QueryRequestPage;
import com.yrnet.viewweb.system.entity.Dept;

import java.util.List;
import java.util.Map;

/**
 * @author dengbp
 */
public interface IDeptService extends IService<Dept> {

    /**
     * 获取部门树（下拉选使用）
     *
     * @return 部门树集合
     */
    Map<String, Object> findDepts(QueryRequestPage request, Dept dept);


    /**
     * 获取部门树（供Excel导出）
     *
     * @param dept    部门对象（传递查询参数）
     * @param request QueryRequest
     * @return List<Dept>
     */
    List<Dept> findDepts(Dept dept, QueryRequestPage request);

    /**
     * 新增部门
     *
     * @param dept 部门对象
     */
    void createDept(Dept dept);

    /**
     * 修改部门
     *
     * @param dept 部门对象
     */
    void updateDept(Dept dept);

    /**
     * 删除部门
     *
     * @param deptIds 部门 ID集合
     */
    void deleteDepts(String[] deptIds);

}
