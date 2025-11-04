package com.github.i23098.mcp;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.modelcontextprotocol.json.McpJsonMapper;
import io.modelcontextprotocol.server.McpServer;
import io.modelcontextprotocol.server.McpStatelessServerFeatures.SyncToolSpecification;
import io.modelcontextprotocol.server.McpStatelessSyncServer;
import io.modelcontextprotocol.server.transport.HttpServletStatelessServerTransport;
import io.modelcontextprotocol.spec.McpSchema;
import io.modelcontextprotocol.spec.McpSchema.CallToolResult;
import io.modelcontextprotocol.spec.McpSchema.ServerCapabilities;
import io.modelcontextprotocol.spec.McpSchema.Tool;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

/**
 * Simple Hello World MCP Server with a single tool that always returns "Hello World!"
 */
public class HelloWorldMCPServer {
    private static final Logger LOG = LoggerFactory.getLogger(HelloWorldMCPServer.class);
    
    private HttpServletStatelessServerTransport transportProvider;
    private McpStatelessSyncServer mcpServer;

    /**
     * Instantiates a new HelloWorldMCPServer
     */
    public HelloWorldMCPServer() {
        LOG.info("Hello World MCP Server is starting...");
        
        McpJsonMapper jsonMapper = McpJsonMapper.getDefault();
        
        this.transportProvider = HttpServletStatelessServerTransport.builder()
                .jsonMapper(jsonMapper)
                .build();
        
        this.mcpServer = McpServer.sync(transportProvider)
                .serverInfo("Hello World MCP Server", "0.0.1")
                .capabilities(ServerCapabilities.builder().tools(false).build())
                .tools(getHelloWorldToolSpecification(jsonMapper))
                .build();
        
        LOG.info("Hello World MCP Server initialized!");
    }
    
    /**
     * Get HelloWorldTool specification
     * 
     * @return HelloWorldTool specification
     */
    private SyncToolSpecification getHelloWorldToolSpecification(McpJsonMapper jsonMapper) {
        Tool tool = Tool.builder()
                .name("hello_world")
                .title("Hello World")
                .description("Simple tool that the result is always a string Hello World!")
                .inputSchema(jsonMapper, "{\"type\": \"object\", \"properties\": {}}")
                .build();
        
        SyncToolSpecification toolSpec = SyncToolSpecification.builder()
                .tool(tool)
                .callHandler((context, request) -> CallToolResult.builder().addTextContent("Hello World!").build())
                .build();
        
        return toolSpec;
    }
    
    /**
     * Forwards servlet requests to the MCP Server
     * 
     * @param req
     * @param res
     * 
     * @throws ServletException
     * @throws IOException
     */
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        this.transportProvider.service(req, res);
    }
    
    /**
     * Cleans up resources
     */
    public void destroy() {
        this.mcpServer.close();
        
        this.transportProvider.close();
        this.transportProvider.destroy();
    }
}
