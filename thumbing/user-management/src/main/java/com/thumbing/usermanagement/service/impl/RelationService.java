package com.thumbing.usermanagement.service.impl;

import com.github.dozermapper.core.Mapper;
import com.thumbing.shared.auth.model.UserContext;
import com.thumbing.shared.entity.sql.black.BlackList;
import com.thumbing.shared.entity.sql.relation.Relation;
import com.thumbing.shared.entity.sql.relation.RelationApplyInfo;
import com.thumbing.shared.exception.BusinessException;
import com.thumbing.shared.repository.sql.black.IBlackListRepository;
import com.thumbing.shared.repository.sql.relation.IRelationApplyInfoRepository;
import com.thumbing.shared.repository.sql.relation.IRelationRepository;
import com.thumbing.usermanagement.dto.input.RelationApplyHandlerInput;
import com.thumbing.usermanagement.dto.input.RelationApplyInput;
import com.thumbing.usermanagement.dto.input.RelationRemoveInput;
import com.thumbing.usermanagement.dto.output.RelationDto;
import com.thumbing.usermanagement.service.IRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/7 17:42
 */
@Service
@Transactional
public class RelationService implements IRelationService {
    @Autowired
    private IRelationRepository relationRepository;
    @Autowired
    private IRelationApplyInfoRepository relationApplyInfoRepository;
    @Autowired
    private IBlackListRepository blackListRepository;
    @Autowired
    Mapper mapper;

    @Override
    public Boolean applyRelation(UserContext userContext, RelationApplyInput input) {
        BlackList check0 = blackListRepository.findByUserIdAndTargetUserId(userContext.getId(), input.getTargetUserId()).orElse(null);
        if(check0 != null) throw new BusinessException("对方存在于你的黑名单中");
        BlackList check01 = blackListRepository.findByUserIdAndTargetUserId(input.getTargetUserId(), userContext.getId()).orElse(null);
        if(check01 != null) return true;
        RelationApplyInfo check1 = relationApplyInfoRepository.findByUserIdAndTargetUserId(userContext.getId(), input.getTargetUserId()).orElse(null);
        if(check1 != null) return true;
        Long id1 = input.getTargetUserId() > userContext.getId() ? userContext.getId() : input.getTargetUserId();
        Long id2 = id1 == userContext.getId() ? input.getTargetUserId() : userContext.getId();
        Relation check2 = relationRepository.findByUserIdOneOrUserIdTwo(id1, id2).orElse(null);
        if(check2 != null) throw new BusinessException("操作错误，和对方已经是好友");
        RelationApplyInfo relationApplyInfo = new RelationApplyInfo();
        relationApplyInfo.setUserId(userContext.getId());
        relationApplyInfo.setTargetUserId(input.getTargetUserId());
        relationApplyInfo.setRemark(input.getRemark());
        relationApplyInfoRepository.save(relationApplyInfo);

        //todo:发送消息到Data Center通知targetUser

        return true;
    }

    @Override
    public RelationDto handleRelationApply(UserContext userContext, RelationApplyHandlerInput input) {
        RelationApplyInfo applyInfo = relationApplyInfoRepository.findById(input.getId()).orElseThrow(()->new BusinessException("好友申请已不存在"));
        if(applyInfo.getTargetUserId() != userContext.getId()) throw new BusinessException("操作错误");
        Long id1 = applyInfo.getUserId() > userContext.getId() ? userContext.getId() : applyInfo.getUserId();
        Long id2 = id1 == userContext.getId() ? applyInfo.getUserId() : userContext.getId();
        Relation check = relationRepository.findByUserIdOneOrUserIdTwo(id1, id2).orElse(null);
        if(check != null){
            applyInfo.setApprove(true);
            relationApplyInfoRepository.save(applyInfo);
            return null;
        }
        else if(!input.isApprove()){
            applyInfo.setReject(true);
            relationApplyInfoRepository.save(applyInfo);
            return null;
        }
        else{

            //todo:异步请求生成新的会话

            applyInfo.setApprove(true);
            relationApplyInfoRepository.save(applyInfo);
            Relation relation = new Relation();
            relation.setUserIdOne(id1);
            relation.setUserIdTwo(id2);
            if(id1 == userContext.getId()){
                relation.setNickNameOne(input.getNickName1());
                relation.setNickNameTwo(input.getNickName2());
            }
            else {
                relation.setNickNameOne(input.getNickName2());
                relation.setNickNameTwo(input.getNickName1());
            }
            relation = relationRepository.save(relation);
            return mapper.map(relation, RelationDto.class);
        }
    }

    @Override
    public Boolean removeRelation(UserContext userContext, RelationRemoveInput input) {
        Relation relation = relationRepository.findById(input.getId()).orElse(null);
        if(relation == null) return true;
        if(relation.getUserIdOne() != userContext.getId() && relation.getUserIdTwo() != userContext.getId()) throw  new BusinessException("非法操作");
        relationRepository.delete(relation);
        return true;
    }
}
