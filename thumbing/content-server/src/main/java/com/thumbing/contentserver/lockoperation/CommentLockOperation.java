package com.thumbing.contentserver.lockoperation;

import com.thumbing.contentserver.cache.CommentCache;
import com.thumbing.shared.repository.mongo.content.ICommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/25 13:53
 */
@Component
public class CommentLockOperation {
    @Autowired
    private ICommentRepository commentRepository;
    @Autowired
    private CommentCache commentCache;
}
