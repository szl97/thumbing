package com.loserclub.pushdata.common.jpa;

import com.loserclub.pushdata.common.constants.EntityConstants;
import com.loserclub.pushdata.common.service.IGenerateIdService;
import com.loserclub.pushdata.common.utils.entity.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;


/**
 * @author Stan Sai
 * @date 2020-06-28
 */
@Component
public class AutoFillEntityListener {

    @Autowired
    IGenerateIdService generateIdService;

    //新增得时候字段得值注入 通过反射去查询是否存在字段 写入
    //需要监控新增得值 包括 id 创建人 创建时间
    @PrePersist
    public void createSet(Object entity) throws Exception {

        GenerateIdResult idResult = generateIdService.GenerateId();

        if(idResult == null || !idResult.isSuccess() || idResult.getId() == null){
            throw new Exception("获取ID失败");
        }

        EntityUtils.setFieldValue(EntityConstants.CREATE_TIME_PROPERTY, LocalDateTime.now(), entity);

        EntityUtils.setFieldValue(EntityConstants.ID, idResult.getId(), entity);

    }


    //更新得时候字段值注入
    //需要监控新增得值 包括 修改人 修改时间
    @PreUpdate
    public void setUpdatedOn(Object entity) {

        EntityUtils.setFieldValue(EntityConstants.LAST_MODIFY_TIME_PROPERTY, LocalDateTime.now(), entity);
    }
}
