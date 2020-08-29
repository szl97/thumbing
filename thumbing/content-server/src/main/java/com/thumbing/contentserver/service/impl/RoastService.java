package com.thumbing.contentserver.service.impl;

import com.github.dozermapper.core.Mapper;
import com.thumbing.contentserver.cache.RoastCache;
import com.thumbing.contentserver.dto.input.FetchRoastInput;
import com.thumbing.contentserver.dto.input.PublishRoastInput;
import com.thumbing.contentserver.dto.input.RoastIdInput;
import com.thumbing.contentserver.dto.input.ThumbRoastInput;
import com.thumbing.contentserver.dto.output.RoastDto;
import com.thumbing.contentserver.lockoperation.RoastLockOperation;
import com.thumbing.contentserver.sender.PushDataSender;
import com.thumbing.contentserver.service.IRoastService;
import com.thumbing.shared.auth.model.UserContext;
import com.thumbing.shared.dto.output.PageResultDto;
import com.thumbing.shared.entity.mongo.MongoCreationEntity;
import com.thumbing.shared.entity.mongo.content.Roast;
import com.thumbing.shared.entity.mongo.record.PushDataRecord;
import com.thumbing.shared.exception.BusinessException;
import com.thumbing.shared.message.PushDataTypeEnum;
import com.thumbing.shared.repository.mongo.content.IRoastRepository;
import com.thumbing.shared.service.impl.BaseMongoService;
import com.thumbing.shared.utils.dozermapper.DozerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/28 8:51
 */
@Service
@Transactional
public class RoastService extends BaseMongoService<Roast, IRoastRepository> implements IRoastService {
    @Autowired
    private RoastCache roastCache;
    @Autowired
    private RoastLockOperation lockOperation;
    @Autowired
    private PushDataSender sender;
    @Autowired
    private Mapper mapper;

    @Override
    public List<RoastDto> fetchRoasts(UserContext context) {
        List<Roast> roasts = roastCache.getRoasts();
        return DozerUtils.mapList(mapper, roasts, RoastDto.class);
    }

    @Override
    public Boolean publishRoast(PublishRoastInput input, UserContext context) {
        Roast roast = new Roast();
        roast.setContent(input.getContent());
        roast.setThumbingNum(0);
        roast.setUserId(context.getId());
        roast.setCreateTime(LocalDateTime.now());
        repository.save(roast);
        roastCache.addRoast(roast);
        return true;
    }

    @Override
    public Boolean deleteRoast(RoastIdInput input, UserContext context) {
        Roast roast;
        if(roastCache.existRoastInfo(input.getId())){
            roast = roastCache.getRoastNoChangedInfo(input.getId());
        }
        else {
            roast = repository.findByIdAndIsDelete(input.getId(), 0).orElseThrow(()->new BusinessException("心情吐槽不存在"));
        }
        if(!roast.getUserId().equals(context.getId())) throw new BusinessException("当前用户无法删除");
        if(!lockOperation.deleteMoments(input)) return deleteRoast(input, context);
        return true;
    }

    @Override
    public Boolean thumbRoast(ThumbRoastInput input, UserContext context) {
        Long userId = confirmRoastThumbsInRedis(input);
        roastCache.changeThumbs(input.getId(), context.getId());
        //todo:发送到消息队列，record-server和data-center接收
        PushDataRecord msg = new PushDataRecord();
        msg.setDataId(input.getId());
        msg.setRead(false);
        msg.setCreateTime(LocalDateTime.now());
        msg.setToUserId(userId);
        msg.setPushType(PushDataTypeEnum.RT);
        sender.sendThumb(msg);
        return true;
    }

    @Override
    public PageResultDto<RoastDto> getMine(FetchRoastInput input, UserContext context) {
        Sort sort = Sort.by(Sort.Direction.DESC, MongoCreationEntity.Fields.createTime);
        PageRequest pageRequest = PageRequest.of(input.getPageNumber(), input.getPageSize(), sort);
        Page<Roast> page = repository.findAllByUserIdAndIsDelete(context.getId(), 0, pageRequest);
        return DozerUtils.mapToPagedResultDtoSync(mapper,page, RoastDto.class);
    }

    private Long confirmRoastThumbsInRedis(RoastIdInput input) {
        if (!(roastCache.existThumbingUser(input.getId()) && roastCache.existRoastThumbsNum(input.getId()))) {
            if(lockOperation.getRoast(input) == null) return confirmRoastThumbsInRedis(input);
        }
        Roast roast = roastCache.getRoastNoChangedInfo(input.getId());
        if(roast == null) throw new BusinessException("心情吐槽未找到");
        return roast.getUserId();
    }
}
