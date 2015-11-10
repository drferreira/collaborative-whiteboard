package br.org.cw.filters;

import br.org.tutty.collaborative_whiteboard.cw.service.SecurityService;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "/app/pages/index.html", dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE, DispatcherType.ASYNC})
public class LoginFilter implements Filter {
    private static final String HOME_URL = "/app/pages/session/home/index.html";

    @Inject
    private SecurityService securityService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        if (securityService.isLogged(httpServletRequest.getSession())) {
            String contextPath = httpServletRequest.getContextPath();
            httpServletResponse.sendRedirect(contextPath + HOME_URL);
        } else {
            chain.doFilter(request, response);
            return;
        }
    }

    @Override
    public void destroy() {

    }
}
