package cn.makinnet.springboot.common.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;

public class AccessRecorderFilter implements Filter {

    private Logger logger = LoggerFactory.getLogger(AccessRecorderFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String uri = request.getRequestURI();

        if(uri.endsWith(".jpg") || uri.endsWith(".css") || uri.endsWith(".js") || uri.endsWith(".png")
                || uri.endsWith(".woff") || uri.endsWith(".woff2") || uri.endsWith(".jpeg") || uri.endsWith(".map")){
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        String ua = request.getHeader("user-agent");
        String ip = request.getRemoteAddr();
        Long st = new Date().getTime();
        filterChain.doFilter(servletRequest, servletResponse);
        Long et = new Date().getTime();
        Long time = et - st;
        logger.info("uri:{},ip:{},time:{},user-agent:{}", uri, ip,time, ua);

    }

    @Override
    public void destroy() {

    }
}
