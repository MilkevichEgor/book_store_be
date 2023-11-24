package com.example.bookstorebe.socket;

import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * The RemoteAdd Filter class is a filter that can be used to modify the remote address
 * header of incoming requests. It wraps the original HttpServletRequest object and
 * provides methods to add custom headers or modify existing headers.
 */
public class RemoteAddrFilter implements Filter {

  @Override
  public void destroy() {
    destroy();
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response,
                       FilterChain chain) throws IOException, ServletException {
    HttpServletRequest req = (HttpServletRequest) request;
    HeaderMapRequestWrapper requestWrapper = new HeaderMapRequestWrapper(req);
    String remoteAdd = request.getRemoteAddr();
    requestWrapper.addHeader("remote_addr", remoteAdd);
    chain.doFilter(requestWrapper, response);
  }

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    
  }

  /**
   * The class is a wrapper class for HttpServletRequest that allows adding custom headers
   * and modifying existing headers.
   */
  public class HeaderMapRequestWrapper extends HttpServletRequestWrapper {
    public HeaderMapRequestWrapper(HttpServletRequest request) {
      super(request);
    }

    private Map<String, String> headerMap = new HashMap<>();

    public void addHeader(String name, String value) {
      headerMap.put(name, value);
    }

    @Override
    public String getHeader(String name) {
      String headerValue = super.getHeader(name);
      if (headerMap.containsKey(name)) {
        headerValue = headerMap.get(name);
      }
      return headerValue;
    }

    @Override
    public Enumeration<String> getHeaderNames() {
      List<String> names = Collections.list(super.getHeaderNames());
      for (String name : headerMap.keySet()) {
        names.add(name);
      }
      return Collections.enumeration(names);
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
      List<String> values = Collections.list(super.getHeaders(name));
      if (headerMap.containsKey(name)) {
        values.add(headerMap.get(name));
      }
      return Collections.enumeration(values);
    }

  }

}
