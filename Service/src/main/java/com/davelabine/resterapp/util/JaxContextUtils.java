package com.davelabine.resterapp.util;

/**
 * Created by davidl on 5/2/17.
 */
/**
 * Created by davidl on 5/2/17.
 */
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import static javax.ws.rs.core.HttpHeaders.*;

public final class JaxContextUtils {
    private static final Logger logger = LoggerFactory.getLogger(JaxContextUtils.class);

    private JaxContextUtils() {
    }

    public static HttpServletRequest getHttpServletRequest() {
        return ResteasyProviderFactory.getContextData(HttpServletRequest.class);
    }

    public static long getContentLength(HttpServletRequest request) {
        if (request != null && request.getHeader(CONTENT_LENGTH) != null ) {
            return Long.valueOf(request.getHeader(CONTENT_LENGTH));
        }
        logger.warn("Can't get content-length HTTP header.");
        return -1;
    }


    @SuppressWarnings("unchecked")
    public static <T> T getContextProperty(HttpServletRequest request, String key) {
        request = getHttpServletRequest();
        if (request != null) {
            try {
                return (T) request.getAttribute(key);
            } catch (Exception ex) {
                logger.warn("Error when retrieving context object", ex);
            }
        }
        return null;
    }

    public static <T> void setContextProperty(HttpServletRequest request, String key, T value) {
        request = getHttpServletRequest();
        if (request != null) {
            try {
                request.setAttribute(key, value);
            } catch (Exception ex) {
                logger.warn("Error when setting context object", ex);
            }
        }
    }

    public static SecurityContext getSecurityContext() {
        return ResteasyProviderFactory.getContextData(SecurityContext.class);
    }
}