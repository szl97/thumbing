package com.thumbing.usermanagement.service.impl;

import cn.hutool.core.util.ArrayUtil;
import com.github.dozermapper.core.Mapper;
import com.thumbing.shared.auth.model.UserContext;
import com.thumbing.shared.entity.sql.black.BlackList;
import com.thumbing.shared.entity.sql.relation.Relation;
import com.thumbing.shared.entity.sql.relation.RelationApplyInfo;
import com.thumbing.shared.exception.BusinessException;
import com.thumbing.shared.jpa.Specifications;
import com.thumbing.shared.message.RelationApplyMsg;
import com.thumbing.shared.repository.sql.black.IBlackListRepository;
import com.thumbing.shared.repository.sql.relation.IRelationApplyInfoRepository;
import com.thumbing.shared.repository.sql.relation.IRelationRepository;
import com.thumbing.shared.utils.dozermapper.DozerUtils;
import com.thumbing.usermanagement.dto.input.RelationApplyHandlerInput;
import com.thumbing.usermanagement.dto.input.RelationApplyInput;
import com.thumbing.usermanagement.dto.input.RelationRemoveInput;
import com.thumbing.usermanagement.dto.output.RelationApplyDto;
import com.thumbing.usermanagement.dto.output.RelationDto;
import com.thumbing.usermanagement.sender.RelationApplySender;
import com.thumbing.usermanagement.service.IRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

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
    private Mapper mapper;
    @Autowired
    private RelationApplySender sender;

    /**
     * 申请好友：
     * 1.判断对方是否存在于你的黑名单中
     * 2.判断对方是否把你拉黑
     * 3.判断是否是重复申请
     * 4.判断是否已经是好友
     * 以上四种情况，直接返回
     * @param userContext
     * @param input
     * @return
     */
    @Override
    public Boolean applyRelation(UserContext userContext, RelationApplyInput input) {
        if(input.getTargetUserId().equals(userContext.getId())) throw new BusinessException("操作错误");
        //todo: 判断对方是否存在于你的黑名单中 + 判断对方是否把你拉黑
        Specification<BlackList> specification1 = Specifications.where(spec->{
            spec.eq(BlackList.Fields.userId, userContext.getId());
            spec.eq(BlackList.Fields.targetUserId, input.getTargetUserId());
        });
        Specification<BlackList> specification2 = Specifications.where(spec->{
            spec.eq(BlackList.Fields.targetUserId, userContext.getId());
            spec.eq(BlackList.Fields.userId, input.getTargetUserId());
        });
        BlackList check0 = blackListRepository.findOne(specification1.and(specification2)).orElse(null);
        if(check0 != null)  return true;
        //todo: 判断是否是重复申请
        RelationApplyInfo check1 = relationApplyInfoRepository.findByUserIdAndTargetUserId(userContext.getId(), input.getTargetUserId()).orElse(null);
        if(check1 != null) return true;
        //todo: 判断是否已经是好友 好友表的存储规则是Id1<Id2
        Long id1 = input.getTargetUserId() > userContext.getId() ? userContext.getId() : input.getTargetUserId();
        Long id2 = id1.equals(userContext.getId()) ? input.getTargetUserId() : userContext.getId();
        Relation check2 = relationRepository.findByUserIdOneAndUserIdTwo(id1, id2).orElse(null);
        if(check2 != null) return true;

        //todo: 以上条件都通过，保存好友申请到数据库
        RelationApplyInfo relationApplyInfo = new RelationApplyInfo();
        relationApplyInfo.setUserId(userContext.getId());
        relationApplyInfo.setTargetUserId(input.getTargetUserId());
        relationApplyInfo.setRemark(input.getRemark());
        relationApplyInfo = relationApplyInfoRepository.save(relationApplyInfo);

        //todo:发送消息到Data Center通知targetUser, 需发送该好友请求的ID和请求添加者的userName
        RelationApplyMsg msg = new RelationApplyMsg();
        msg.setDataId(relationApplyInfo.getId());
        msg.setToUserId(input.getTargetUserId());
        msg.setFromUserId(userContext.getId());
        msg.setFromUserName(userContext.getName());
        msg.setRemark(input.getRemark());
        msg.setTime(LocalDateTime.now());
        sender.send(msg);

        return true;
    }

    /**
     * 判断是否是对应请求的处理人，如果不是返回异常
     * 判断对方是否拉黑你
     * 判断是否已经是好友，若是，直接返回
     * @param userContext
     * @param input
     * @return
     */
    @Override
    public Boolean handleRelationApply(UserContext userContext, RelationApplyHandlerInput input) {
        //todo: 判断请求是否存在。以及是否是对应的处理人
        RelationApplyInfo applyInfo = relationApplyInfoRepository.findById(input.getId()).orElseThrow(()->new BusinessException("好友申请已不存在"));
        if(!applyInfo.getTargetUserId().equals(userContext.getId())) throw new BusinessException("操作错误");
        //todo: 判断对方是否拉黑你
        BlackList blackList = blackListRepository.findByUserIdAndTargetUserId(applyInfo.getUserId(), applyInfo.getTargetUserId()).orElse(null);
        if(blackList != null) throw new BusinessException("你存在于对方黑名单中，添加好友失败");
        //todo: 判断是否已经是好友关系
        Long id1 = applyInfo.getUserId() > userContext.getId() ? userContext.getId() : applyInfo.getUserId();
        Long id2 = id1.equals(userContext.getId()) ? applyInfo.getUserId() : userContext.getId();
        Relation check = relationRepository.findByUserIdOneAndUserIdTwo(id1, id2).orElse(null);
        //todo: 已经是好友直接返回
        if(check != null){
            applyInfo.setApprove(true);
            relationApplyInfoRepository.save(applyInfo);
            return true;
        }
        //todo: 不通过的处理
        else if(!input.isApprove()){
            applyInfo.setReject(true);
            relationApplyInfoRepository.save(applyInfo);
            return true;
        }
        //todo: 通过请求的处理
        else{

            //todo:异步请求生成新的会话

            applyInfo.setApprove(true);
            relationApplyInfoRepository.save(applyInfo);
            //todo: 保证好友关系表中 Id1<Id2
            Relation relation = new Relation();
            relation.setUserIdOne(id1);
            relation.setUserIdTwo(id2);
            if(id1.equals(userContext.getId())){
                relation.setNickNameOne(input.getNickName1());
                relation.setNickNameTwo(input.getNickName2());
            }
            else {
                relation.setNickNameOne(input.getNickName2());
                relation.setNickNameTwo(input.getNickName1());
            }
            relationRepository.save(relation);
            return true;
        }
    }

    @Override
    public Boolean removeRelation(UserContext userContext, RelationRemoveInput input) {
        Relation relation = relationRepository.findById(input.getId()).orElse(null);
        if(relation == null) return true;
        if(!relation.getUserIdOne().equals(userContext.getId()) && relation.getUserIdTwo().equals(userContext.getId())) throw  new BusinessException("非法操作");
        relationRepository.delete(relation);
        return true;
    }

    @Override
    public List<RelationDto> getAllRelation(UserContext userContext) {
        List<Relation> relations = relationRepository.findAllByUserIdOneOrUserIdTwo(userContext.getId(), userContext.getId());
        if(ArrayUtil.isEmpty(relations)) return null;
        return DozerUtils.mapList(mapper, relations, RelationDto.class, (relation, relationDto) -> {
            if(relation.getUserIdTwo().equals(userContext.getId())) {
                relationDto.setUserId(relation.getUserIdOne());
                relationDto.setUserName(relation.getNickNameOne());
            }
            else {
                relationDto.setUserId(relation.getUserIdTwo());
                relationDto.setUserName(relation.getNickNameTwo());
            }
        });
    }

    @Override
    public List<RelationApplyDto> getAllRelationApply(UserContext userContext) {
        List<RelationApplyInfo> relationApplyInfos = relationApplyInfoRepository.findAllByTargetUserId(userContext.getId());
        if(ArrayUtil.isEmpty(relationApplyInfos)) return null;
        return DozerUtils.mapList(mapper, relationApplyInfos, RelationApplyDto.class, (relationApply, relationApplyDto)->{
            if(relationApply.getUserInfo() != null) {
                relationApplyDto.setNickName(relationApply.getUserInfo().getUserName());
                relationApplyDto.setNickName(relationApply.getUserInfo().getNickName());
            }
        });
    }
}
