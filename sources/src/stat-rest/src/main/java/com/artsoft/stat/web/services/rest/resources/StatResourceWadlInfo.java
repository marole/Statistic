/*
 * ArtSoft 2014.
 */

package com.artsoft.stat.web.services.rest.resources;


import java.io.IOException;
import java.net.URL;

import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.artsoft.stat.web.services.rest.api.StatResourceUnexpectedException;
import com.artsoft.stat.web.services.rest.api.StatResourceWadl;
import com.artsoft.stat.web.services.rest.common.Constants;


/**
 * The 'meta JAX-RS service' provides client access to information about available JAX-RS services
 * provided by this application.
 *
 * @author Marcin Olejarczyk
 */
public class StatResourceWadlInfo implements StatResourceWadl
{
    private static final Log logger = LogFactory.getLog(StatResourceWadlInfo.class);


    @Override
    public Response getExternal(final String path)
    {
        if (logger.isInfoEnabled()) {
            logger.info("-------------> Request received for wadl info with path paramater '" + path + "'.");
        }

        boolean isFileOk = validateFile(path);
        if (!isFileOk) {
            return Response.serverError().build();
        }

        // prepare path to the file under context path
        String contextPath = Constants.CONTEXT_DIR + "/" + path;

        // open the file
        URL fileUrl = StatResourceService.class.getClassLoader().getResource(contextPath);
        if (fileUrl == null) {
            logger.info("Send server error due to not find the file under path '" + contextPath + "'.");
            return Response.serverError().build();
        }

        try {
            String fileContent = IOUtils.toString(fileUrl);

            logger.info("<------------- Response with file content.");
            return Response.ok(fileContent).build();
        }
        catch (IOException e) {
            if (logger.isWarnEnabled()) {
                logger.warn("Problem with reading file '" + path + "' due to: " + e.getMessage() + ".");
            }
            throw new StatResourceUnexpectedException("Problem with reading file.", e);
        }
    }


    private boolean validateFile(final String filePath)
    {
        // the path argument can not be empty
        if (StringUtils.isEmpty(filePath)) {
            logger.info("Incorrect value of received path paramater.");
            return false;
        }

        if (Constants.FILES_WITH_NO_CLIENT_ACCESS.contains(filePath)) {
            logger.info("No access to file path.");
            return false;
        }

        return true;
    }
}
