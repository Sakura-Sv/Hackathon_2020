package com.testdb.demo.controller.message;

import com.testdb.demo.entity.user.Message;
import com.testdb.demo.service.message.MessageService;
import com.testdb.demo.utils.response.AjaxResponseBody;
import com.testdb.demo.utils.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/message")
@AjaxResponseBody
public class MessageController {

    @Autowired
    MessageService messageService;

    @GetMapping
    public Result<List<Message>> getMessageList(Principal principal){
        return Result.success(messageService.getMessageList(principal.getName()));
    }

}
