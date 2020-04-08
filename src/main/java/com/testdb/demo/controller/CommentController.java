package com.testdb.demo.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.testdb.demo.entity.letter.Comment;
import com.testdb.demo.entity.user.User;
import com.testdb.demo.service.CommentService;
import com.testdb.demo.service.LetterService;
import com.testdb.demo.utils.AjaxResponseBody;
import com.testdb.demo.utils.Result;
import com.testdb.demo.utils.ResultStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/comment")
@AjaxResponseBody
public class CommentController {

    @Autowired
    CommentService cs;

    @Autowired
    LetterService ls;

//    @GetMapping
//    public Result<Page<Comment>> getCommentList(@RequestParam(value = "index", defaultValue = "1") int index,
//                                                @RequestParam long motherId,
//                                                @RequestParam(value = "level", defaultValue = "1") int level){
//        if(ls.checkInvalidLetterId(motherId)){
//            return Result.failure(ResultStatus.WRONG_PARAMETERS.setMessage("不存在这封信！"));
//        }
//        else if(cs.checkInvalidCommentId(motherId)){
//            return Result.failure(ResultStatus.WRONG_PARAMETERS.setMessage("不存在这条评论！"));
//        }
//        return Result.success(cs.getCommentList(index, motherId, level));
//    }

    @PostMapping
    public Result<Void> postComment(Authentication token, @RequestBody Comment comment){

        if(comment.getAid() == null || ls.checkInvalidLetterId(comment.getAid())){
            return Result.failure(ResultStatus.WRONG_PARAMETERS);
        }
        cs.postComment(token, comment);

        return Result.success();
    }

}
