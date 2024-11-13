package com.example.asm_java5.Service;

import com.example.asm_java5.Dao.CategoryDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Service
public class GlobalInterceptor implements HandlerInterceptor {
    @Autowired
    CategoryDao categoryDao;

    @Override
    public boolean preHandle(HttpServletRequest req , HttpServletResponse resp , Object handler) throws Exception{
    req.setAttribute("uri", req.getRequestURI());
    return true;
    }
    @Override
    public void postHandle(HttpServletRequest req, HttpServletResponse res,
                           Object handler, ModelAndView mv) throws Exception {
        req.setAttribute("categories", categoryDao.findAll());
    }
}
