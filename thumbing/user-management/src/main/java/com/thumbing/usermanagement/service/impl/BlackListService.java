package com.thumbing.usermanagement.service.impl;

import com.github.dozermapper.core.Mapper;
import com.thumbing.shared.auth.model.UserContext;
import com.thumbing.shared.repository.sql.black.IBlackListRepository;
import com.thumbing.shared.repository.sql.relation.IRelationApplyInfoRepository;
import com.thumbing.shared.repository.sql.relation.IRelationRepository;
import com.thumbing.usermanagement.dto.input.BlackListAddInput;
import com.thumbing.usermanagement.dto.input.BlackListRemoveInput;
import com.thumbing.usermanagement.dto.output.BlackListDto;
import com.thumbing.usermanagement.service.IBlackListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    Mapper mapper;
    @Override
    public BlackListDto addToBlackList(UserContext context, BlackListAddInput input) {
        return null;
    }

    @Override
    public Boolean removeInBlackList(UserContext context, BlackListRemoveInput input) {
        return null;
    }
}
