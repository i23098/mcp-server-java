# MCP Server - Hello World

Simple Hello World MCP Server done in Java, using https://github.com/modelcontextprotocol/java-sdk

## Build

Execute:

```sh
./gradlew :build
```

This will generate `build/libs/hello-world-mcp.war`

## Execute

Can be executed in Tomcat 11. Just copy `build/libs/hello-world-mcp.war` to the `webapps` folder.

## MCP Client

To test the server, can use MCP inspector by executing:

```sh
./gradlew :runMCPInspector
```

It will open in the browser and should be configured using:

* Transport Type: Streamable HTTP
* URL: http://localhost:8080/hello-world-mcp/mcp
* Connection Type: Via Proxy

Click **Connect** button.

Click **List Tools** button. Click on the `hello_world` tool that is listed.

Click **Run Tool** button. Should have a success tool result with a `"Hello World!"` response.