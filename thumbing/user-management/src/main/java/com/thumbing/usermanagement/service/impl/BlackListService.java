package com.thumbing.usermanagement.service.impl;

import cn.hutool.core.util.ArrayUtil;
import com.github.dozermapper.core.Mapper;
import com.thumbing.shared.auth.model.UserContext;
import com.thumbing.shared.entity.sql.black.BlackList;
import com.thumbing.shared.entity.sql.relation.Relation;
import com.thumbing.shared.entity.sql.relation.RelationApplyInfo;
import com.thumbing.shared.exception.BusinessException;
import com.thumbing.shared.repository.sql.black.IBlackListRepository;
import com.thumbing.shared.repository.sql.relation.IRelationApplyInfoRepository;
import com.thumbing.shared.repository.sql.relation.IRelationRepository;
import com.thumbing.shared.utils.dozermapper.DozerUtils;
import com.thumbing.usermanagement.dto.input.BlackListAddInput;
import com.thumbing.usermanagement.dto.input.BlackListRemoveInput;
import com.thumbing.usermanagement.dto.output.BlackListDto;
import com.thumbing.usermanagement.service.IBlackListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/7 18:30
 */
@Service
@Transactional
public class BlackListService implements IBlackListService {
    @Autowired
    private IRelationRepository relationRepository;
    @Autowired
    private IRelationApplyInfoRepository relationApplyInfoRepository;
    @Autowired
    private IBlackListRepository blackListRepository;
    @Autowired
    private Mapper mapper;

    /**
     * 判断是否已经拉黑，若是，直接返回
     * 判断是否是好友，若是，先删除
     * 判断是否有好友申请，若是，先删除
     * @param context
     * @param input
     * @return
     */
    @Override
    public Boolean addToBlackList(UserContext context, BlackListAddInput input) {
        if(input.getTargetUserId().equals(context.getId())) throw new BusinessException("操作错误");
        //todo:判断是否已经拉黑，若是，直接返回
        BlackList blackList = blackListRepository.findByUserIdAndTargetUserId(context.getId(), input.getTargetUserId()).orElse(null);
        if(blackList != null) return true;
        //todo:判断是否是好友，若是，先删除
        Long id1 = Math.min(context.getId(), input.getTargetUserId());
        Long id2 = Math.max(context.getId(), input.getTargetUserId());
        Relation relation = relationRepository.findByUserIdOneAndUserIdTwo(id1, id2).orElse(null);
        if(relation != null) relationRepository.delete(relation);
        //todo:判断是否有好友申请，若是，先删除
        RelationApplyInfo relationApplyInfo = relationApplyInfoRepository.findByUserIdAndTargetUserId(input.getTargetUserId(), context.getId()).orElse(null);
        if(relationApplyInfo != null) relationApplyInfoRepository.delete(relationApplyInfo);
        //todo:添加到黑名单
        BlackList addBlackList = new BlackList();
        addBlackList.setUserId(context.getId());
        addBlackList.setTargetUserId(input.getTargetUserId());
        blackListRepository.save(addBlackList);
        return true;
    }

    @Override
    public Boolean removeInBlackList(UserContext context, BlackListRemoveInput input) {
        BlackList blackList = blackListRepository.findById(input.getId()).orElseThrow(()->new BusinessException("操作错误"));
        if(!blackList.getUserId().equals(context.getId())) throw new BusinessException("操作错误");
        blackListRepository.delete(blackList);
        return true;
    }

    @Override
    public List<BlackListDto> getAllBlackList(UserContext context) {
        List<BlackList> blackLists = blackListRepository.findAllByUserId(context.getId());
        if(ArrayUtil.isEmpty(blackLists)) return null;
        return DozerUtils.mapList(mapper, blackLists, BlackListDto.class, (blackList, blackListDto)->{
            blackListDto.setUserId(blackList.getTargetUserId());
            if(blackList.getTargetUserInfo() != null){
                blackListDto.setUserName(blackList.getTargetUserInfo().getUserName());
                blackListDto.setNickName(blackList.getTargetUserInfo().getNickName());
            }
        });
    }
}
