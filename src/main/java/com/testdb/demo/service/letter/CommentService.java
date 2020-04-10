package com.testdb.demo.service.letter;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.testdb.demo.entity.letter.Comment;
import com.testdb.demo.entity.letter.Letter;
import com.testdb.demo.entity.user.BaseUser;
import com.testdb.demo.entity.user.Score;
import com.testdb.demo.mapper.letter.CommentMapper;
import com.testdb.demo.service.message.MessageService;
import com.testdb.demo.service.user.ScoreService;
import com.testdb.demo.service.user.UserService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
public class CommentService extends ServiceImpl<CommentMapper, Comment>{

    @Autowired
    private MessageService messageService;

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private LetterService letterService;

    private static final Integer COMMENT_TYPE = 2;

    @SneakyThrows
    public Boolean checkInvalidCommentId(long commentId){
        return this.getOne(new QueryWrapper<Comment>().select("id").eq("id", commentId)) == null;
    }

    @SneakyThrows
    public Page<Comment> getCommentList( int index, long aid){
        Page<Comment> page = new Page<>(index, 20);
        return page.setRecords(this.baseMapper.getCommentList(aid, page));
    }

    @SneakyThrows
    @Transactional
    public void postComment(Authentication token, Comment comment){
        BaseUser user = UserService.t2b(token);
        LocalDateTime postTime = LocalDateTime.now();
        comment.setNickname(user.getNickname());
        comment.setCommentTime(postTime);
        comment.setCommenterName(user.getUsername());

        Letter targetLetter = letterService
                .getOne(new QueryWrapper<Letter>()
                        .select("author", "letter_type", "preview")
                        .eq("id",comment.getAid()));

        this.save(comment);
        scoreService.addScore(targetLetter.getAuthor(), ScoreService.COMMENT_SCORE);

        messageService.sendMessage(targetLetter.getAuthor(),
                comment.getCommenterName(),
                user.getAvatar(),
                comment.getCommentTime(),
                getCommentTips(user.getNickname()),
                comment.getContent(),
                targetLetter.getPreview(),
                Integer.parseInt(targetLetter.getLetterType()),
                COMMENT_TYPE,
                comment.getAid(),
                comment.getAid());
    }

    public String getCommentTips(String nickname){
        return "用户"+nickname+"评论了你:";
    }

}
