package com.thumbing.contentserver.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dozermapper.core.Mapper;
import com.thumbing.contentserver.cache.MomentsCache;
import com.thumbing.contentserver.config.ElasticSearchConfig;
import com.thumbing.contentserver.dto.input.*;
import com.thumbing.contentserver.dto.output.MomentsDto;
import com.thumbing.contentserver.elasticsearch.ElasticBaseEntity;
import com.thumbing.contentserver.elasticsearch.ElasticUtils;
import com.thumbing.contentserver.lockoperation.MomentsLockOperation;
import com.thumbing.contentserver.sender.PushDataSender;
import com.thumbing.contentserver.service.IMomentsService;
import com.thumbing.shared.auth.model.UserContext;
import com.thumbing.shared.dto.output.PageResultDto;
import com.thumbing.shared.entity.mongo.content.Moments;
import com.thumbing.shared.entity.mongo.content.enums.ContentType;
import com.thumbing.shared.entity.mongo.record.PushDataRecord;
import com.thumbing.shared.exception.BusinessException;
import com.thumbing.shared.message.PushDataTypeEnum;
import com.thumbing.shared.repository.mongo.content.IMomentsRepository;
import com.thumbing.shared.service.impl.BaseMongoService;
import com.thumbing.shared.utils.dozermapper.DozerUtils;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/26 14:21
 */
@Service
@Transactional
public class MomentsService extends BaseMongoService<Moments, IMomentsRepository> implements IMomentsService {
    @Autowired
    private ElasticUtils elasticUtils;
    @Autowired
    private MomentsCache momentsCache;
    @Autowired
    private MomentsLockOperation lockOperation;
    @Autowired
    private PushDataSender sender;
    @Autowired
    private Mapper mapper;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public PageResultDto<MomentsDto> fetchMoments(FetchMomentsInput input, UserContext context) {
        if (momentsCache.existMomentsList() && input.getPosition() > 0) {
            int length = momentsCache.sizeOfMomentsList().intValue();
            if (length > 0) {
                int to = Math.min(length, input.getPosition());
                int from = Math.max(to - input.getPageSize(), 0);
                List<Moments> moments = momentsCache.getMomentsList(from, to);
                Collections.reverse(moments);
                List<MomentsDto> dtoList = DozerUtils.mapList(mapper, moments, MomentsDto.class);
                return new PageResultDto<>(MomentsCache.maxLength, dtoList, from - 1);
            }
        }
        return null;
    }

    @Override
    public MomentsDto getMoments(MomentsIdInput input) {
        Moments moments = confirmMomentsInRedis(input);
        MomentsDto momentsDto = mapper.map(moments, MomentsDto.class);
        return momentsDto;
    }

    @SneakyThrows
    @Override
    public Boolean publishMoments(PublishMomentsInput input, UserContext context) {
        Moments moments = mapper.map(input, Moments.class);
        moments.setNickNameSequence(0);
        moments.setUserId(context.getId());
        moments.setCreateTime(LocalDateTime.now());
        moments.setThumbingNum(0);
        moments.setCommentsNum(0);
        moments = repository.save(moments);
        momentsCache.addMoments(moments);
        ElasticBaseEntity elasticBaseEntity = new ElasticBaseEntity();
        elasticBaseEntity.setContentId(moments.getId());
        elasticBaseEntity.setContent(moments.getContent());
        elasticBaseEntity.setName(ContentType.MOMENTS);
        elasticBaseEntity.setDateTime(moments.getCreateTime());
        elasticBaseEntity.setTags(objectMapper.writeValueAsString(moments.getTagIds()));
        elasticUtils.indexDoc(ElasticSearchConfig.indexName, objectMapper.writeValueAsString(elasticBaseEntity));
        return true;
    }

    @SneakyThrows
    @Override
    public Boolean deleteMoments(MomentsIdInput input, UserContext context) {
        Moments moments;
        if(momentsCache.existMomentsInfo(input.getId())){
            moments = momentsCache.getMomentsNoChangedInfo(input.getId());
        }
        else {
            moments = repository.findByIdAndIsDelete(input.getId(), 0).orElseThrow(()->new BusinessException("帖子不存在"));
        }
        if(!moments.getUserId().equals(context.getId())) throw new BusinessException("当前用户无法删除");
        if(!lockOperation.deleteMoments(input)) return deleteMoments(input, context);
        elasticUtils.deleteDoc(ElasticSearchConfig.indexName, ContentType.ARTICLE.value(), input.getId());
        return true;
    }

    @Override
    public Boolean thumbMoments(ThumbMomentsInput input, UserContext context) {
        Long userId = confirmMomentsThumbsInRedis(input);
        momentsCache.changeThumbs(input.getId(), input.isAdd(), context.getId());
        //todo:发送到消息队列，record-server和data-center接收
        PushDataRecord msg = new PushDataRecord();
        msg.setDataId(input.getId());
        msg.setRead(false);
        msg.setCreateTime(LocalDateTime.now());
        msg.setToUserId(userId);
        msg.setPushType(PushDataTypeEnum.MT);
        sender.sendThumb(msg);
        return true;
    }

    @SneakyThrows
    @Override
    public Boolean updateMoments(UpdateMomentsInput input, UserContext context) {
        Moments moments = confirmMomentsInRedis(input);
        if(!moments.getUserId().equals(context.getId())) throw new BusinessException("当前用户无法修改");
        momentsCache.changeContent(input.getId(), input.getContent());
        elasticUtils.updateDocContent(ElasticSearchConfig.indexName, ContentType.MOMENTS.value(), input.getId(), input.getContent());
        return true;
    }

    private Long confirmMomentsThumbsInRedis(MomentsIdInput input) {
        if (!(momentsCache.existThumbingUser(input.getId()) && momentsCache.existMomentsThumbsNum(input.getId()))) {
            if(lockOperation.getMoments(input) == null) return confirmMomentsThumbsInRedis(input);
        }
        Moments moments = momentsCache.getMomentsNoChangedInfo(input.getId());
        if(moments == null) throw new BusinessException("帖子未找到");
        return moments.getUserId();
    }

    private Moments confirmMomentsInRedis(MomentsIdInput input) {
        if (momentsCache.existMomentsInfo(input.getId())) {
            return momentsCache.getMomentsNoChangedInfo(input.getId());
        } else {
            Moments moments = lockOperation.getMoments(input);
            if (moments == null) confirmMomentsInRedis(input);
            return moments;
        }
    }
}
