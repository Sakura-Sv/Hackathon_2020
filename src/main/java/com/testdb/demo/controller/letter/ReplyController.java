package com.testdb.demo.controller.letter;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.testdb.demo.entity.letter.Reply;
import com.testdb.demo.service.letter.CommentService;
import com.testdb.demo.service.letter.ReplyService;
import com.testdb.demo.utils.response.AjaxResponseBody;
import com.testdb.demo.utils.response.Result;
import com.testdb.demo.utils.response.ResultStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reply")
@AjaxResponseBody
public class ReplyController {

    @Autowired
    ReplyService rs;

    @Autowired
    CommentService cs;

    @GetMapping
    public Result<Page<Reply>> getCommentList(@RequestParam(value = "index", defaultValue = "1") int index,
                                                @RequestParam long pid) {
        if (cs.checkInvalidCommentId(pid)) {
            return Result.failure(ResultStatus.WRONG_PARAMETERS.setMessage("不存在这条评论！"));
        }
        return Result.success(rs.getReplyList(index, pid));
    }


    @PostMapping
    public Result<Void> reply(Authentication token,
                              @RequestBody Reply reply){
        if(reply.getCommentId() == null ||
        reply.getPid() == null ||
        reply.getTargetUsername() == null ||
        cs.checkInvalidCommentId(reply.getCommentId())) {
            return Result.failure(ResultStatus.WRONG_PARAMETERS);
        }
        rs.reply(token, reply);
        return Result.success();
    }

}
