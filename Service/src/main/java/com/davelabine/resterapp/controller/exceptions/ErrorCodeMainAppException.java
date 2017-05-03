package com.davelabine.resterapp.controller.exceptions;

import com.google.common.collect.ImmutableMap;

import freemarker.template.*;
import javax.ws.rs.core.Response;
import java.util.Map;

/**
 * Created by davidl on 5/3/17.
 */
public class ErrorCodeMainAppException extends RuntimeException {

    protected static final long serialVersionUID = 1L;
    final private String body;
    final private Response.Status status;
    // TODO: inject freemarker config properly in ErrorCodeExceptionMapper
    // Yes, this is ugly.
    // There is an issue injecting eager singletons for FM config into multiple objects.  Didn't have time to get to
    // the bottom of what is happening...
    final private Configuration fmConfig;

    public ErrorCodeMainAppException(Configuration fmConfig, Response.Status status) {
        this.fmConfig = fmConfig;
        this.status = status;

        if (defaultErrorBodies.containsKey(status)) {
            body = defaultErrorBodies.get(status);
        } else if (defaultFamilyErrorBodies.containsKey(status.getFamily())) {
            body = defaultFamilyErrorBodies.get(status.getFamily());
        } else {
            body = null;
        }
    }

    public ErrorCodeMainAppException(Configuration fmConfig, Response.Status status, String body) {
        this.fmConfig = fmConfig;
        this.status = status;
        this.body = body;
    }

    public Configuration getFmConfig() { return fmConfig; }

    public Response.Status getStatus() {
        return status;
    }

    public String getBody() {
        return body;
    }

    private Map<Response.Status, String> defaultErrorBodies = ImmutableMap.<Response.Status, String>builder()
            .put(Response.Status.UNAUTHORIZED, "The request requires authentication but no Authentication header was provided.")
            .build();

    private Map<Response.Status.Family, String> defaultFamilyErrorBodies = ImmutableMap.<Response.Status.Family, String>builder()
            .put(Response.Status.Family.CLIENT_ERROR, "This request was malformed.")
            .put(Response.Status.Family.SERVER_ERROR, "The request was interrupted by an unknown error, please try again later.")
            .build();
}