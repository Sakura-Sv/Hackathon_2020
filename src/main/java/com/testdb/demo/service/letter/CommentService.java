package com.testdb.demo.service.letter;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.testdb.demo.entity.letter.Comment;
import com.testdb.demo.entity.letter.Letter;
import com.testdb.demo.entity.user.BaseUser;
import com.testdb.demo.mapper.letter.CommentMapper;
import com.testdb.demo.service.message.MessageService;
import com.testdb.demo.service.user.ScoreService;
import com.testdb.demo.service.user.UserService;
import com.testdb.demo.utils.CacheUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@CacheConfig(cacheNames = "CommentService")
public class CommentService extends ServiceImpl<CommentMapper, Comment>{

    @Autowired
    private MessageService messageService;

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private LetterService letterService;

    @Autowired
    private CacheUtil cacheUtil;

    private static final Integer COMMENT_TYPE = 2;

    /**
     * 检查回复的id是否指向有效回复
     * @param commentId
     * @return
     */
    @SneakyThrows
    @Cacheable(key = "#root.method.name+#commentId", unless = "#commentId==null")
    public Boolean checkInvalidCommentId(long commentId){
        return commentMapper.selectOne(new QueryWrapper<Comment>().select("id").eq("id", commentId)) == null;
    }

    /**
     * 获取文章的评论列表
     * @param index 页码
     * @param aid 文章id
     * @return
     */
    @SneakyThrows
    @Cacheable(key = "#root.method.name+#aid+'end'+#index", unless = "#aid==null")
    public Page<Comment> getCommentList(int index, long aid){
        Page<Comment> page = new Page<>(index, 20);
        return page.setRecords(this.baseMapper.getCommentList(aid, page));
    }

    /**
     * 发送评论
     * @param token 用户信息
     * @param comment 评论
     */
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
        //插入评论
        commentMapper.insert(comment);
        // 增加积分
        scoreService.addScore(targetLetter.getAuthor(), ScoreService.COMMENT_SCORE);
        // 清理目标文章评论的缓存
        cacheUtil.cleanMoodList(comment.getAid().toString());
        // 发送通知
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
