package com.loserclub.generateid;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
/**
 * @author Stan Sai
 * @date 2020-06-28
 */
@Slf4j
@Service
public class IdGenerator {

    public Result getId(){
        try {
            Long id = SnowFlake.getInstance().nextId();
            return new Result(true, id);
        }
        catch (Exception e){
            return new Result(false, null);
        }
    }
}
