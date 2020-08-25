package com.thumbing.contentserver.lockoperation;

import cn.hutool.core.util.ArrayUtil;
import com.thumbing.contentserver.cache.NickNameCache;
import com.thumbing.shared.annotation.AccessLock;
import com.thumbing.shared.entity.mongo.content.NickName;
import com.thumbing.shared.repository.mongo.content.INickNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/25 13:40
 */
@Component
public class NickNameLockOperation {
    @Autowired
    private INickNameRepository nickNameRepository;
    @Autowired
    private NickNameCache cache;

    @AccessLock(value = "com.thumbing.shared.entity.mongo.content.NickName")
    public Boolean storeAllNickNames(){
        Sort sort = Sort.by(Sort.Direction.ASC, NickName.Fields.sequence);
        List<NickName> nickNameList = nickNameRepository.findAll(sort);
        if(ArrayUtil.isEmpty(nickNameList)) return false;
        List<String> list = nickNameList.stream().map(nickName -> nickName.getName()).collect(Collectors.toList());
        cache.setNickNameList(list);
        return true;
    }
}
