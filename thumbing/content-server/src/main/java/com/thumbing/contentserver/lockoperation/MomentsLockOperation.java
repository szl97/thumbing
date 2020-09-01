package com.thumbing.contentserver.lockoperation;

import com.github.dozermapper.core.Mapper;
import com.thumbing.contentserver.cache.MomentsCache;
import com.thumbing.contentserver.dto.input.FetchMomentsInput;
import com.thumbing.contentserver.dto.input.MomentsIdInput;
import com.thumbing.contentserver.dto.output.MomentsDto;
import com.thumbing.shared.annotation.AccessLock;
import com.thumbing.shared.dto.output.PageResultDto;
import com.thumbing.shared.entity.mongo.BaseMongoEntity;
import com.thumbing.shared.entity.mongo.MongoCreationEntity;
import com.thumbing.shared.entity.mongo.MongoFullAuditedEntity;
import com.thumbing.shared.entity.mongo.content.Moments;
import com.thumbing.shared.exception.BusinessException;
import com.thumbing.shared.repository.mongo.content.IMomentsRepository;
import com.thumbing.shared.utils.dozermapper.DozerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/26 9:36
 */
@Component
@Transactional(propagation = Propagation.NESTED)
public class MomentsLockOperation {
    @Autowired
    private IMomentsRepository momentsRepository;
    @Autowired
    private MomentsCache momentsCache;
    @Autowired
    private Mapper mapper;
    @Autowired
    private MongoTemplate mongoTemplate;

    @AccessLock(value = {"com.thumbing.shared.entity.mongo.content.Moments"},
            className = "com.thumbing.contentserver.dto.input.FetchMomentsInput",
            fields = {"getPosition"})
    public PageResultDto<MomentsDto> getMomentsPage(FetchMomentsInput input){
        Sort sort = Sort.by(Sort.Direction.DESC, MongoCreationEntity.Fields.createTime);
        Page<Moments> page = momentsRepository.findAllByIsDelete(0, PageRequest.of(input.getPageNumber() - 1, input.getPageSize(), sort));
        List<Moments> list = page.toList();
        list.stream().forEach(
                moments -> {
                    momentsCache.addMomentsWhenInitialize(moments);
                }
        );
        return DozerUtils.mapToPagedResultDtoSync(mapper, page, MomentsDto.class);
    }

    @AccessLock(value = {"com.thumbing.shared.entity.mongo.content.Moments"},
            className = "com.thumbing.contentserver.dto.input.MomentsIdInput",
            fields = {"getId"})
    public Moments getMoments(MomentsIdInput idInput){
        Moments moments = momentsRepository.findByIdAndIsDelete(idInput.getId(), 0).orElseThrow(()->new BusinessException("帖子不存在"));
        momentsCache.storeMoments(moments);
        return moments;
    }

    @AccessLock(value = {"com.thumbing.shared.entity.mongo.content.Moments"},
            className = "com.thumbing.contentserver.dto.input.MomentsIdInput",
            fields = {"getId"})
    public Boolean deleteMoments(MomentsIdInput idInput){
        Query query = Query.query(Criteria.where(BaseMongoEntity.Fields.id).is(idInput.getId()));
        Update update = Update.update(MongoFullAuditedEntity.Fields.isDelete, 1);
        mongoTemplate.updateFirst(query, update, Moments.class);
        if(momentsCache.existMomentsInfo(idInput.getId())){
            momentsCache.removeMoments(idInput.getId());
        }else {
            momentsCache.removeInList(idInput.getId());
        }
        return true;
    }
}
