package org.example.springboot.controller;

import org.example.springboot.common.Result;
import org.example.springboot.service.UserService;
import org.example.springboot.shujvku.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        try{
            User loginUser = userService.login(user.getUsername(),user.getPassword());
            if(loginUser!=null){
                //【漏洞代码】敏感信息泄露
                return Result.success(loginUser);//直接返回
                /*
                 修复：登录成功后，将用户信息存入 Session
                HttpSession session = request.getSession();
                session.setAttribute("loginUser", loginUser);

                loginUser.setPassword(null); // 脱敏处理
                return Result.success(loginUser);
                 */
            }
            else{
                return Result.error("用户名或密码错误");
            }
        }
        catch (Exception e){
            return Result.error(e.getMessage());
        }
    }

    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        try{
            User registeredUser=userService.register(user);
            registeredUser.setPassword(null);
            return Result.success(registeredUser);
        }catch(Exception e){
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public Result getUserById(@PathVariable Integer id) {
        try {
            User user = userService.getUserById(id);
            if (user != null) {
                user.setPassword(null);
                return Result.success(user);
            } else {
                return Result.error("用户不存在");
            }
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
