package com.davelabine.resterapp.controller.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by davidl on 5/3/17.
 */
@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {
    @Override
    public Response toResponse(Throwable e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        pw.println("<H1>Oops... something went wrong!</H1><P>");
        pw.println("<B>" + e.getMessage() + "</B><BR>");
        e.printStackTrace(pw);
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(sw.toString()).build();
    }

}
