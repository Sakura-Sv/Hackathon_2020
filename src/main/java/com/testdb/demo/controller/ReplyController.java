package com.testdb.demo.controller;

import com.testdb.demo.entity.letter.Reply;
import com.testdb.demo.service.CommentService;
import com.testdb.demo.service.ReplyService;
import com.testdb.demo.utils.AjaxResponseBody;
import com.testdb.demo.utils.Result;
import com.testdb.demo.utils.ResultStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reply")
@AjaxResponseBody
public class ReplyController {

    @Autowired
    ReplyService rs;

    @Autowired
    CommentService cs;

    @PostMapping
    public Result<Void> reply(Authentication token,
                              @RequestBody Reply reply){
        if(reply.getCommentId() == null ||
        reply.getPid() == null ||
        cs.checkInvalidCommentId(reply.getCommentId())) {
            return Result.failure(ResultStatus.WRONG_PARAMETERS);
        }
        rs.reply(token, reply);
        return Result.success();
    }

}
