package com.loserclub.generateid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Stan Sai
 * @date 2020-06-28
 */
@RestController
@RequestMapping("/generateId")
public class GenerateIdController {
    @Autowired
    private IdGenerator idGenerator;

    @GetMapping
    public Result GenerateId(){
        return  idGenerator.getId();
    }
}
