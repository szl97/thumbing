package com.thumbing.pushdata.nodeserver.handlers.center;

import com.thumbing.pushdata.common.message.GroupData;
import com.thumbing.pushdata.nodeserver.handlers.GroupDataHandler;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


/**
 * @author Stan Sai
 * @date 2020-06-23
 */
@Slf4j
@Component
@Data
public class GroupDataFromCenterHandler extends GroupDataHandler implements ICenterDataHandler<GroupData> {

}
