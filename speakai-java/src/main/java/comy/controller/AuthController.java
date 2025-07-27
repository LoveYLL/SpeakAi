package comy.controller;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import comy.dao.LoginRequest;
import comy.dao.User;
import comy.mapper.UserMapper;
import comy.result.Result;
import comy.result.ResultCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
@CrossOrigin
@RestController
@RequestMapping("/api/auth")
@Tag(name = "认证模块", description = "用户登录认证功能")
public class AuthController {
    @Autowired
    private UserMapper userMapper;
    @Operation(summary = "用户登录", description = "通过用户名和密码登录，返回 token 用于后续请求认证")
    @PostMapping("/login")
    public Result<?> login(@RequestBody LoginRequest req) {
        // 从数据库根据用户名查找用户
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("username", req.getUsername()));
        if (user != null && user.getPassword().equals(md5(req.getPassword()))) {  // 使用 MD5 校验密码
            StpUtil.login(user.getUserId());
            String token = StpUtil.getTokenValue();
            return Result.success(token);
        }
        return Result.failure(ResultCode.FAILURE, "用户名或密码错误");
    }
    @Operation(summary = "用户注册", description = "通过用户名和密码注册，返回注册结果")
    @PostMapping("/register")
    public Result<?> register(@RequestBody LoginRequest req) {
        User existingUser = userMapper.selectOne(new QueryWrapper<User>().eq("username", req.getUsername()));
        if (existingUser != null) {
            return Result.failure(ResultCode.FAILURE, "用户名已存在");
        }
        User newUser = new User();
        newUser.setUsername(req.getUsername());
        newUser.setPassword(md5(req.getPassword()));
        int insertResult = userMapper.insert(newUser);
        if (insertResult > 0) {
            return Result.success("注册成功");
        }
        return Result.failure(ResultCode.FAILURE, "注册失败");
    }
    private String md5(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 加密算法不可用", e);
        }
    }
}
