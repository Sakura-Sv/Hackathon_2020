package com.testdb.demo.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.testdb.demo.entity.letter.Comment;
import com.testdb.demo.service.CommentService;
import com.testdb.demo.utils.AjaxResponseBody;
import com.testdb.demo.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/comment")
@AjaxResponseBody
public class CommentController {

    @Autowired
    CommentService cs;

    @GetMapping
    public Result<Page<Comment>> getCommentList(@RequestParam int index,
                                                @RequestParam long letterId){
        return Result.success(cs.getCommentList(index, letterId));
    }

    @PostMapping
    public Result<Void> postComment(Principal principal, @RequestBody Comment comment){
        comment.setCommenterName(principal.getName());
        comment.setCommentTime(LocalDateTime.now());
        cs.save(comment);
        return Result.success();
    }

}
