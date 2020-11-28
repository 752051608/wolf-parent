package com.wolf.common.filter;


import com.alibaba.fastjson.JSONObject;
import com.wolf.common.base.ResultCode;
import com.wolf.common.util.JsonResult;
import com.wolf.common.util.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

/**
 * 验证用户账号是否被BOSS端删除,删除后无法执行用户相关操作（例如：下单、支付等操作）
 * Created by wanggang on 2017/10/12.
 */
@Slf4j
@Order(1)
@WebFilter(filterName = "customerFilter", urlPatterns = "/*")
public class CustomerFilter implements Filter {

    private static String DEL_FLAG = "1";
    private static String IS_FLAG = "1";//冻结

    private static List<String> includePattern = new ArrayList<>(10);
    private static List<String> excludePattern = new ArrayList<>(10);

    @Value("${filter.disable}")
    private String filterDisable;

    @Value("${filter.include-url-list}")
    private String filterIncludeUrlList;

    @Value("${filter.excluded-url-list}")
    private String filterExcludedUrlList;


    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    public static final String OPTIONS = "OPTIONS";
    public static final String INSTANCES = "instances";
    public static final String ACTUATOR = "actuator";

    @Override
    public void init(FilterConfig filterConfig) {
        if (StringUtils.isNotBlank(filterIncludeUrlList)) {
            includePattern = Arrays.asList(filterIncludeUrlList.split(","));
        }
        if (StringUtils.isNotBlank(filterExcludedUrlList)) {
            excludePattern = Arrays.asList(filterExcludedUrlList.split(","));
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String requestURI = request.getRequestURI();
        String authorization = request.getHeader("Authorization");
        String methodType = request.getMethod();
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE,PUT");
        response.addHeader("Access-Control-Allow-Headers", "Authorization");
        response.addHeader("Access-Control-Allow-Headers", "mobile");
        response.addHeader("Access-Control-Allow-Headers", "content-type");
        if (requestURI.indexOf("/boss") > -1) {
            return;
        }
        if (requestURI.indexOf(INSTANCES) > -1 || requestURI.indexOf(ACTUATOR) > -1) {
            return;
        }
        if (OPTIONS.equals(methodType)) {
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }
        Enumeration<String> enu = request.getParameterNames();
        StringBuilder stringBuilder = new StringBuilder();
        while (enu.hasMoreElements()) {
            String name = enu.nextElement();
//            stringBuilder.append("\n                                                                                                                                ")
            stringBuilder.append(name).append('=').append(request.getParameter(name)).append(",");
        }
        log.info("请求地址：{},参数:{}", requestURI, stringBuilder.toString());
//        log.info("获取到的请求方法为：{}", methodType);
        if ("Y".equals(filterDisable.toUpperCase()) || "Bearer ".equals(authorization) || isContains(requestURI, excludePattern) || !isContains(requestURI, includePattern)) {
            long st1 = System.currentTimeMillis();
            chain.doFilter(request, response);
            long duration = System.currentTimeMillis() - st1;
            log.info("===request:{}, rettime=={}ms当前地址不进行拦截", requestURI, duration);
            return;
        }

        // FIXME 未登陆,前端加了Bearer,没带空格,后端先处理下
        if ("Bearer".equals(authorization)) {
            response.setStatus(HttpServletResponse.SC_OK);
            long st1 = System.currentTimeMillis();
            chain.doFilter(request, response);
            long duration = System.currentTimeMillis() - st1;
            if (duration > 1000) {
                log.info("===request:{}, rettime=={}ms未登陆,前端加了Bearer,没带空格,后端先处理下", requestURI, duration);
            }
            return;
        }

        Long customeId = null;
        if (null == authorization) {
            responseJson(response, ResultCode.INVALID_TOKEN, "token为空");
            return;
        }
        String token = authorization.substring(7);
        try {
            customeId = jwtTokenUtil.getUserIdFromToken(token);
            if (customeId == null) {
                responseJson(response, ResultCode.INVALID_TOKEN, "登录状态已失效，请重新登录");
                return;
            }
            log.info("token customerId：{}", customeId);
        } catch (ExpiredJwtException e) {
            responseJson(response, ResultCode.INVALID_TOKEN, "登录状态已失效，请重新登录");
            log.info("解析token失效，token为：{}", authorization);
            return;
        }
        Claims claims = jwtTokenUtil.getClaims(token);
        //TODO 刷新后生成新的token
//            jwtTokenUtil.refreshHeadToken(token);
        request.setAttribute("claims", claims);
        long st1 = System.currentTimeMillis();
        chain.doFilter(request, response);
        long duration = System.currentTimeMillis() - st1;
        if (duration > 1000) {
            log.info("|||||||SLOW NOTICE||||||||===request:{}, rettime=={}ms", requestURI, duration);
        }
        return;

    }

    /**
     * 返回前端的错误提示
     *
     * @param errorMsg
     * @param response
     */
    public void responseJson(HttpServletResponse response, String errorCode, String errorMsg) throws IOException {
        response.setStatus(HttpStatus.OK.value());
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Headers", "Authorization");
        response.addHeader("Access-Control-Allow-Headers", "mobile");
        response.addHeader("Access-Control-Allow-Headers", "content-type");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        if (StringUtils.isNotEmpty(errorCode)) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", errorCode);
            if (errorCode.equals(ResultCode.INVALID_TOKEN)) {
                jsonObject.put("errorMsg", "token为空或失效");
            }
            response.getWriter().write(JSONObject.toJSONString(JsonResult.buildFailed(jsonObject)));
        } else {
            response.getWriter().write(JSONObject.toJSONString(JsonResult.buildFailed(errorMsg)));
        }
        response.getWriter().flush();
        response.getWriter().close();
    }

    @Override
    public void destroy() {

    }

    /**
     * 判断当前请求地址是否包含过滤地址
     *
     * @param container
     * @param list
     * @return
     */
    private boolean isContains(String container, List<String> list) {
        boolean result = false;
        for (int i = 0; i < list.size(); i++) {
            if (container.indexOf(list.get(i)) > -1) {
                result = true;
            }
        }
        return result;
    }
}























































































































































