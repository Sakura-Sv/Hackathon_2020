package com.testdb.demo.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.testdb.demo.entity.letter.Comment;
import com.testdb.demo.service.CommentService;
import com.testdb.demo.service.LetterService;
import com.testdb.demo.service.ReplyService;
import com.testdb.demo.utils.AjaxResponseBody;
import com.testdb.demo.utils.Result;
import com.testdb.demo.utils.ResultStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/comment")
@AjaxResponseBody
public class CommentController {

    @Autowired
    CommentService cs;

    @Autowired
    LetterService ls;

    @GetMapping
    public Result<Page<Comment>> getCommentList(@RequestParam(value = "index", defaultValue = "1") int index,
                                                @RequestParam long pid) {

        if (ls.checkInvalidLetterId(pid)) {
            return Result.failure(ResultStatus.WRONG_PARAMETERS.setMessage("不存在这封信！"));
        }

        return Result.success(cs.getCommentList(index, pid));
    }

    @PostMapping
    public Result<Void> postComment(Authentication token, @RequestBody Comment comment){

        if(comment.getAid() == null || ls.checkInvalidLetterId(comment.getAid())){
            return Result.failure(ResultStatus.WRONG_PARAMETERS);
        }
        cs.postComment(token, comment);

        return Result.success();
    }

}
