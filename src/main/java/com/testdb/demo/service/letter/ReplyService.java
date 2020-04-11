package com.testdb.demo.service.letter;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.testdb.demo.entity.letter.Comment;
import com.testdb.demo.entity.letter.Letter;
import com.testdb.demo.entity.letter.Reply;
import com.testdb.demo.entity.user.BaseUser;
import com.testdb.demo.entity.user.User;
import com.testdb.demo.mapper.letter.ReplyMapper;
import com.testdb.demo.service.message.MessageService;
import com.testdb.demo.service.user.UserService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
public class ReplyService extends ServiceImpl<ReplyMapper, Reply> {

    @Autowired
    private CommentService commentService;

    @Autowired
    private LetterService letterService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    private static final Integer REPLY_TYPE = 3;

    /**
     * 回复评论 / 回复回复
     * @param token
     * @param reply
     */
    @SneakyThrows
    @Transactional
    public void reply(Authentication token, Reply reply){
        BaseUser user = UserService.t2b(token);
        if(reply.getContent().length() >= 18){
            reply.setPreview(reply.getContent().substring(0,18)+"...");
        }
        else {
            reply.setPreview(reply.getContent());
        }
        reply.setCommenterName(user.getUsername());
        reply.setNickname(user.getNickname());
        reply.setCommentTime(LocalDateTime.now());
        reply.setTargetNickname(userService
                .getOne(new QueryWrapper<User>()
                        .select("nickname")
                        .eq("username", reply.getTargetUsername()))
                .getNickname());
        Letter sourceLetter = letterService
                .getOne(new QueryWrapper<Letter>()
                        .select("letter_type")
                        .eq("id", reply.getAid()));
        Comment sourceComment = commentService
                .getOne(new QueryWrapper<Comment>()
                        .select("content")
                        .eq("id", reply.getCommentId()));
        this.save(reply);

        messageService.sendMessage(reply.getTargetUsername(),
                user.getUsername(),
                user.getAvatar(),
                reply.getCommentTime(),
                getReplyTips(user.getNickname()),
                reply.getContent(),
                sourceComment.getPreview(),
                Integer.parseInt(sourceLetter.getLetterType()),
                REPLY_TYPE,
                reply.getCommentId(),
                reply.getPid());

    }

    public String getReplyTips(String nickname){
        return "用户" + nickname + "回复了你的评论";
    }

    /**
     * 获取回复列表
     * @param index
     * @param pid 目标评论
     * @return
     */
    public Page<Reply> getReplyList(int index, long pid){
        Page<Reply> page = new Page<>(index, 20);
        return page.setRecords(this.baseMapper.getReplyList(pid, page));
    }

}
