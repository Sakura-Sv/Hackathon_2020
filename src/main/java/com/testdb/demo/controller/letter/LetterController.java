package com.testdb.demo.controller.letter;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.testdb.demo.entity.letter.BaseLetter;
import com.testdb.demo.entity.letter.Letter;
import com.testdb.demo.service.letter.BaseLetterService;
import com.testdb.demo.service.letter.LetterService;
import com.testdb.demo.service.user.UserService;
import com.testdb.demo.utils.response.AjaxResponseBody;
import com.testdb.demo.utils.QiniuUtil;
import com.testdb.demo.utils.response.Result;
import com.testdb.demo.utils.response.ResultStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(value="/api/letter")
@AjaxResponseBody
public class LetterController {

    @Autowired
    LetterService ls;

    @Autowired
    UserService us;

    @Autowired
    BaseLetterService bls;

    /**
     * 获取一封信的详情信息
     * @param id
     * @return
     */
    @GetMapping
    @Cacheable(value="LetterController", key = "#root.method.name+#id", unless = "#id==null")
    public Result<Letter> getLetter(@RequestParam("id") Long id){
        if(ls.checkInvalidLetterId(id)){
            Result.failure(ResultStatus.WRONG_PARAMETERS.setMessage("不存在这封信！"));
        }
        return Result.success(ls.getLetter(id));
    }

    /**
     * 发布一封信
     * @param letter
     * @return
     */
    @PostMapping
    public Result<Void> postLetter(Authentication token, @RequestBody Letter letter) {
        if (letter == null || letter.getContent() == null || letter.getLetterType() == null) {
            return Result.failure(ResultStatus.WRONG_PARAMETERS);
        }
        ls.postLetter(token, letter);
        return Result.success();
    }

    /**
     * 随机返回一封信
     * @param letterType
     * @return
     */
    @GetMapping("/random")
    public Result<Letter> getLetter(Authentication token, @RequestParam("letterType") String letterType)
    {
        if(!letterType.equals("1") && !letterType.equals("2")){
            return Result.failure(ResultStatus.WRONG_PARAMETERS.setMessage("类型不正确！"));
        }
        return Result.success(ls.getRandomLetter(token, letterType));
    }

    /**
     * 获取当前用户信件预览表
     * @param index
     * @return
     */
    @GetMapping("/preview")
    public Result<Page<BaseLetter>> getLetterList(Principal principal,
                                                  @RequestParam(value = "index", defaultValue = "1") int index){
        return Result.success(bls.getBaseLetterList(principal.getName(), index));
    }

    /**
     * 获取其他人信件预览表
     * @param index
     * @param username
     * @return
     */
    @GetMapping("/others")
    public Result<Page<BaseLetter>> getOthersLetterList(@RequestParam(value = "index", defaultValue = "1") int index,
                                                        @RequestParam("username") String username){
        if(us.checkInvalidUser(username)){
            return Result.failure(ResultStatus.WRONG_PARAMETERS.setMessage("不存在此用户！"));
        }
        return Result.success(bls.getBaseLetterList(username, index));
    }

    /**
     * 获取附件图片发送token
     * @return
     */
    @GetMapping("/annex")
    public Result<String> getAnnexUploadToken(){
        return Result.success(QiniuUtil.getRandomKeyToken());
    }

}
