package com.thumbing.shared.utils.entity;

import com.thumbing.shared.auth.model.UserContext;
import com.thumbing.shared.constants.EntityConstants;
import com.thumbing.shared.utils.generateid.SnowFlake;
import com.thumbing.shared.utils.security.SecurityUtils;
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
    SecurityUtils securityUtils;

    //新增得时候字段得值注入 通过反射去查询是否存在字段 写入
    //需要监控新增得值 包括 id 创建人 创建时间
    @PrePersist
    public void createSet(Object entity) throws Exception {

        Long id = SnowFlake.getInstance().nextId();
        if (id == null) {
            throw new Exception("获取ID失败");
        }
        EntityUtils.setFieldValue(EntityConstants.CREATE_TIME_PROPERTY, LocalDateTime.now(), entity);
        EntityUtils.setFieldValue(EntityConstants.ID, id, entity);
        UserContext currentUser = securityUtils.getCurrentUser();
        if (currentUser != null) {
            EntityUtils.setFieldValue(EntityConstants.CREATE_ID_PROPERTY, currentUser.getId(), entity);
        }
    }


    //更新得时候字段值注入
    //需要监控新增得值 包括 修改人 修改时间
    @PreUpdate
    public void setUpdatedOn(Object entity) {
        EntityUtils.setFieldValue(EntityConstants.LAST_MODIFY_TIME_PROPERTY, LocalDateTime.now(), entity);
        UserContext currentUser = securityUtils.getCurrentUser();
        if (currentUser != null) {
            EntityUtils.setFieldValue(EntityConstants.LAST_MODIFY_ID, currentUser.getId(), entity);
        }
    }
}
