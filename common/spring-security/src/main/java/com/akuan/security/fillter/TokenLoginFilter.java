package com.akuan.security.fillter;

import com.akuan.common.jwt.JwtHelper;
import com.akuan.common.result.Result;
import com.akuan.common.result.ResultCodeEnum;
import com.akuan.common.utils.ResponseUtil;
import com.akuan.security.custom.CustomUser;
import com.alibaba.fastjson2.JSON;
import com.atguigu.vo.system.LoginVo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * <p>
 * 登录过滤器，继承UsernamePasswordAuthenticationFilter，对用户名密码进行登录校验
 * </p>
 */
public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter {

    private  RedisTemplate redisTemplate;
    // 构造方法
    public TokenLoginFilter(AuthenticationManager authenticationManager,RedisTemplate redisTemplate){
        this.setAuthenticationManager(authenticationManager);
        this.setPostOnly(false);
        //指定登录接口及提交方式，可以指定任意路径
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/admin/system/index/login","POST"));;
        this.redisTemplate = redisTemplate;

    }

    // 登录认证过程
    // 获取输入的用户名和密码，调用方法认证
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
            throws AuthenticationException {
        try {
            // 获取用户信息
            LoginVo loginVo = new ObjectMapper().readValue(req.getInputStream(), LoginVo.class);

            //封装对象
            Authentication authenticationToken = new UsernamePasswordAuthenticationToken(loginVo.getUsername(), loginVo.getPassword());

            //调用方法
            return this.getAuthenticationManager().authenticate(authenticationToken);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 认证成功调用的方法
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
        // 获取当前用户
        CustomUser customUser = (CustomUser) auth.getPrincipal();

        // 生成token
        String token = JwtHelper.createToken(customUser.getSysUser().getId(), customUser.getSysUser().getUsername());

        redisTemplate.opsForValue().set(customUser.getUsername(),JSON.toJSONString(customUser.getAuthorities()));
        // 返回
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        ResponseUtil.out(response, Result.ok(map));
    }

    // 认证失败调用的方法
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException e) throws IOException, ServletException {

        if(e.getCause() instanceof RuntimeException) {
            ResponseUtil.out(response, Result.build(null, ResultCodeEnum.DATA_ERROR));
        } else {
            ResponseUtil.out(response, Result.build(null, ResultCodeEnum.LOGIN_AUTH));
        }
    }

}
