package com.testdb.demo.service.user;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.testdb.demo.entity.user.*;
import com.testdb.demo.mapper.user.AddressMapper;
import com.testdb.demo.mapper.user.AvatarMapper;
import com.testdb.demo.mapper.user.UserMapper;
import com.testdb.demo.service.email.EmailServiceImpl;
import com.testdb.demo.utils.DateTimeUtil;
import com.testdb.demo.utils.UuidMaker;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;

@Service
@CacheConfig(cacheNames = "UserService")
public class UserService extends ServiceImpl<UserMapper, User> {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private AvatarMapper avatarMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private EmailServiceImpl emailServiceImpl;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ScoreService scoreService;

    @Transactional
    @SneakyThrows
    public void signUp(User user) {
        // 加密密码
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        user.setConfirmCode(UuidMaker.getUuid());
        // 设置不可用
        user.setEnable(false);
        // 插入
        userMapper.insert(user);
        Integer userId = userMapper.selectIdByUsername(user.getUsername());
        userMapper.createUser(userId.toString(),"2");
        avatarMapper.insert(new Avatar(user.getUsername()));
        scoreService.save(new Score(user.getUsername(), ScoreService.BASE_SCORE));
        sendConfirmMessage(user);
    }

    @SneakyThrows
    public void sendConfirmMessage(User user) {

        String to = "1204736871@qq.com";
        String subject = "听风: 为" + user.getUsername() + "激活账户";
        String context = "<a href=\"https://thenebula.cn/confirm/?confirmCode=" + user.getConfirmCode() +
                "\">确认激活请点击这里，如果不是您的信息烦请无视！谢谢！"+"</a>";
        emailServiceImpl.sendHtmlMail(to, subject, context);

    }

    @SneakyThrows
    @Cacheable(key = "#root.method.name+#username", unless = "#username==null")
    public BaseUser getBaseInfo(String username){
        return userMapper.selectUserBaseInfo(username);
    }

    @Transactional
    @SneakyThrows
    public int confirmUser(String confirmCode){

        //2 验证码已过期 1 用户已激活 0 成功

        User user = userMapper.selectOne(new QueryWrapper<User>().eq("confirm_code", confirmCode));
        if(Duration.between(LocalDateTime.now(), user.getCreatedTime()).toDays()>=1){
            if(!user.getEnable()){
                this.remove(new UpdateWrapper<User>().eq("username", user.getUsername()));
            }
            return 2;
        }
        if( !user.getEnable() ){
            user.setEnable(true);
            userMapper.update(user, new QueryWrapper<User>().eq("username", user.getUsername()));
            return 0;
        }
        return 1;
    }

    @SneakyThrows
    @Cacheable(key = "#root.method.name+#username", unless = "#username==null")
    public Boolean checkInvalidUser(String username){
        return userMapper.selectOne(new QueryWrapper<User>().select("id").eq("username", username)) == null;
    }

    @Transactional
    @SneakyThrows
    @CacheEvict(key="'getBaseInfo'+#username")
    public void updateUserInfo(String username,
                               JSONObject jsonParam){

        String sex = jsonParam.getString("sex");
        Date birthday = jsonParam.getDate("birthday");
        String description = jsonParam.getString("description");
        String nickname = jsonParam.getString("nickname");
        String address = jsonParam.getString("address");
        String foreignAddress = jsonParam.getString("foreignAddress");

        User user = userMapper.selectOne(new QueryWrapper<User>().eq("username",username));

        if(sex != null) {
            user.setSex(sex);
        }
        if(birthday != null) {
            user.setBirthday(DateTimeUtil.toLocalDateViaInstant(birthday));
        }
        if(description != null) {
            user.setDescription(description);
        }
        if(nickname != null) {
            user.setNickname(nickname);
        }
        if(address != null) {
            String newAddress = addressMapper.getCityById(address);
            user.setAddress(newAddress.replace(",", " "));
        }
        if(foreignAddress != null) {
            user.setAddress(foreignAddress);
        }

        userMapper.updateUserInfo(user);
    }

    @SneakyThrows
    @SuppressWarnings("unchecked")
    public void getConfirmCode(String username){
        String confirmCode = UuidMaker.randomCode();

        redisTemplate.opsForHash()
                .put("confirmCode", username,
                        new ConfirmCode(confirmCode, LocalDateTime.now()));

        String to = "1204736871@qq.com";
        String subject = "为" + username + "找回密码";
        String context = "<P>您的验证码为： <b>" + confirmCode + "<b/></P>";

        emailServiceImpl.sendHtmlMail(to, subject, context);
    }

    @SneakyThrows
    @Transactional
    @SuppressWarnings("unchecked")
    public int findPassword(String username, String newPassword, String confirmCode){

        // 3 没有发起找回密码请求 2 验证码已过期 1 验证码错误 0 成功

        ConfirmCode dbConfirmCode = (ConfirmCode) redisTemplate.opsForHash()
                .get("confirmCode", username);
        if(dbConfirmCode == null){
            return 3;
        }

        if(Duration.between( dbConfirmCode.getCreateTime(),
                LocalDateTime.now() )
                .toMinutes() > 10){
            return 2;
        }

        if(confirmCode.equals(dbConfirmCode.getConfirmCode())){
            this.update(new UpdateWrapper<User>()
                    .eq("username", username)
                    .set("password", passwordEncoder.encode(newPassword))
                    .set("be_refresh", 1));
            redisTemplate.opsForHash().delete("confirmCode", username);
            return 0;
        }

        return 1;
    }

    @SneakyThrows
    @Transactional
    public Boolean changePassword(String username, String oldPassword, String newPassword){

        Boolean pass = passwordEncoder.matches(oldPassword,
                this.getOne(new QueryWrapper<User>()
                .select("password").eq("username",username))
                .getPassword());

        if(pass){
            this.update(new UpdateWrapper<User>()
                    .eq("username", username)
                    .set("password", passwordEncoder.encode(newPassword))
                    .set("be_refresh", 1));
            return false;
        }

        return true;
    }

    @SneakyThrows
    public static BaseUser t2b(Authentication token){
        return (BaseUser)token.getPrincipal();
    }
}
