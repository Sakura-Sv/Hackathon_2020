package com.testdb.demo.controller.mood;

import com.testdb.demo.entity.mood.BaseMood;
import com.testdb.demo.entity.mood.Mood;
import com.testdb.demo.entity.mood.WeekMood;
import com.testdb.demo.service.mood.MoodService;
import com.testdb.demo.utils.response.AjaxResponseBody;
import com.testdb.demo.utils.response.Result;
import com.testdb.demo.utils.response.ResultStatus;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value="/api/mood")
@AjaxResponseBody
public class MoodController {

    @Autowired
    MoodService ms;

    /**
     * 获取一周心情
     * @return
     */
    @GetMapping
    public Result<Map<String, WeekMood>> getMoodList(Principal principal){
        return Result.success(ms.getMoodList(principal.getName()));
    }

    /**
     * 发布今日心情
     * @param mood
     * @return
     */
    @PostMapping
    @SneakyThrows
    public Result<Void> addMood(Principal principal,
                                @RequestBody Mood mood) {
        if (mood.getDescription() == null ||
                mood.getMoodType() == null ||
                ms.checkInvalidMoodType(mood.getMoodType())) {
            return Result.failure(ResultStatus.WRONG_PARAMETERS);
        }
        int status = ms.addMood(mood, principal.getName());
        switch (status) {
            case 2:
                return Result.failure(ResultStatus.WRONG_PARAMETERS.setMessage("你的一句话太长啦！"));
            case 1:
                return Result.failure(ResultStatus.FAILURE.setMessage("今天已经记录过心情啦！"));
            default:
                return Result.success();
        }
    }

    /**
     * 随机返回两个跟自己今日心情相同类型的心情
     * @param moodType
     * @return
     */
    @GetMapping("/others")
    public Result<List<BaseMood>> getRandomMood(Principal principal, @RequestParam("moodType") String moodType){
        List<BaseMood> results =ms.getRandomMood(principal.getName(),moodType);
        if(results == null) {
            return Result.failure(ResultStatus.FAILURE.setMessage("今天还没有小伙伴儿发布类似的心情喔！"));
        }
        return Result.success(results);
    }

}
