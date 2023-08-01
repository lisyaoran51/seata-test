package org.example.accountService.config;

import org.springframework.stereotype.Component;

import io.seata.common.util.StringUtils;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import io.seata.core.context.RootContext;

@Component
public class SeataFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        //手动绑定XID
        String xid = request.getHeader(RootContext.KEY_XID);
        if(StringUtils.isNotBlank(xid)){
            RootContext.bind(xid);
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
