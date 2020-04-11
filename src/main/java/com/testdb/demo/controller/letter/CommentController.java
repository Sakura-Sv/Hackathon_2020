package com.testdb.demo.controller.letter;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.testdb.demo.entity.letter.Comment;
import com.testdb.demo.service.letter.CommentService;
import com.testdb.demo.service.letter.LetterService;
import com.testdb.demo.utils.response.AjaxResponseBody;
import com.testdb.demo.utils.response.Result;
import com.testdb.demo.utils.response.ResultStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;


@RestController
@RequestMapping("/api/comment")
@AjaxResponseBody
public class CommentController {

    @Autowired
    CommentService cs;

    @Autowired
    LetterService ls;

    /**
     * 查看评论列表
     * @param index
     * @param pid
     * @return
     */
    @GetMapping
    public Result<Page<Comment>> getCommentList(@RequestParam(value = "index", defaultValue = "1") int index,
                                                @RequestParam long pid) {

        if (ls.checkInvalidLetterId(pid)) {
            return Result.failure(ResultStatus.WRONG_PARAMETERS.setMessage("不存在这封信！"));
        }

        return Result.success(cs.getCommentList(index, pid));
    }

    /**
     * 发表评论
     * @param comment
     * @return
     */
    @PostMapping
    public Result<Void> postComment(Authentication token, @RequestBody Comment comment) {

        if (comment.getContent() == null || comment.getAid() == null || ls.checkInvalidLetterId(comment.getAid())) {
            return Result.failure(ResultStatus.WRONG_PARAMETERS);
        }
        cs.postComment(token, comment);

        return Result.success();
    }

    /**
     * 获取评论数
     * @param aid
     * @return
     */
    @GetMapping("/count")
    public Result<Integer> getCommentNum(@RequestParam Long aid) {
        if (aid == null || ls.checkInvalidLetterId(aid)) {
            return Result.failure(ResultStatus.WRONG_PARAMETERS.setMessage("没有找到这封信QwQ"));
        }
        return Result.success(cs.count(new QueryWrapper<Comment>().eq("aid", aid)));
    }

}
