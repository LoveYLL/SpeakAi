package comy.config;//package comy.config;
//import cn.dev33.satoken.filter.SaServletFilter;
//import cn.dev33.satoken.stp.StpUtil;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//@Configuration
//public class SaTokenConfig {
//
//    @Bean
//    public SaServletFilter getSaServletFilter() {
//        return new SaServletFilter()
//                .addInclude("/**")                   // 拦截所有请求
//                .addExclude("/api/auth/login")       // 登录接口放行
//                .setAuth(r -> StpUtil.checkLogin()); // 登录校验
//    }
//}
