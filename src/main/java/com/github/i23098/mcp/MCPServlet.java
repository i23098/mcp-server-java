package com.github.i23098.mcp;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * HttpServlet that forwards requests to MCP Server
 */
@WebServlet("/mcp")
public class MCPServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(MCPServlet.class);
    
    private HelloWorldMCPServer helloWorldMCPServer;
    
    @Override
    public void init() throws ServletException {
        LOG.debug("MCPServlet::init");
        
        if (helloWorldMCPServer != null) {
            throw new ServletException("Already initialized!");
        }
        
        helloWorldMCPServer = new HelloWorldMCPServer();
    }
    
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        helloWorldMCPServer.service(req, resp);
    }
    
    @Override
    public void destroy() {
        helloWorldMCPServer.destroy();
        
        helloWorldMCPServer = null;
    }
}
