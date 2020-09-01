package com.thumbing.contentserver.lockoperation;

import com.thumbing.contentserver.cache.RoastCache;
import com.thumbing.contentserver.dto.input.RoastIdInput;
import com.thumbing.shared.annotation.AccessLock;
import com.thumbing.shared.entity.mongo.BaseMongoEntity;
import com.thumbing.shared.entity.mongo.MongoFullAuditedEntity;
import com.thumbing.shared.entity.mongo.content.Roast;
import com.thumbing.shared.exception.BusinessException;
import com.thumbing.shared.repository.mongo.content.IRoastRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/27 17:00
 */
@Component
@Transactional(propagation = Propagation.NESTED)
public class RoastLockOperation {
    @Autowired
    private IRoastRepository roastRepository;
    @Autowired
    private RoastCache roastCache;
    @Autowired
    private MongoTemplate mongoTemplate;

    @AccessLock(value = {"com.thumbing.shared.entity.mongo.content.Roast"},
            className = "com.thumbing.contentserver.dto.input.RoastIdInput",
            fields = {"getId"})
    public Roast getRoast(RoastIdInput idInput){
        Roast roast = roastRepository.findByIdAndIsDelete(idInput.getId(), 0).orElseThrow(()->new BusinessException("心情吐槽不存在"));
        roastCache.storeRoast(roast);
        return roast;
    }

    @AccessLock(value = {"com.thumbing.shared.entity.mongo.content.Roast"},
            className = "com.thumbing.contentserver.dto.input.RoastIdInput",
            fields = {"getId"})
    public Boolean deleteMoments(RoastIdInput idInput){
        Query query = Query.query(Criteria.where(BaseMongoEntity.Fields.id).is(idInput.getId()));
        Update update = Update.update(MongoFullAuditedEntity.Fields.isDelete, 1);
        mongoTemplate.updateFirst(query, update, Roast.class);
        if(roastCache.existRoastInfo(idInput.getId())){
            roastCache.removeRoast(idInput.getId());
        }else {
            roastCache.removeInCollection(idInput.getId());
        }
        return true;
    }
}
