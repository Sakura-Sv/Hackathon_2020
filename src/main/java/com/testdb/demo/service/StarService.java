package com.testdb.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StarService {

    @Autowired
    private MessageService messageService;

//    public void star(String username, String targetName, long motherId){
//        messageService.star(username, targetName, motherId);
//    }

    public Long countStar(long letterId){
        return messageService.countStar(letterId);
    }

}
