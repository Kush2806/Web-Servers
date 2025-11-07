# Web-Servers

A comparative implementation of single-threaded and multithreaded TCP servers in Java, demonstrating fundamental networking concepts and concurrency patterns.

## Overview

This project provides two implementations of a basic HTTP-like server that handle client connections differently:

- **SingleThreaded**: Processes client requests sequentially, blocking on each connection until completion
- **Multithreaded**: Handles multiple clients concurrently by spawning a new thread per connection

Both servers listen on port `8010` and respond with a simple greeting message. The implementations serve as educational examples of Java socket programming and thread management.

## Architecture

### Single-Threaded Server

The single-threaded implementation (`SingleThreaded/src/Server.java`) follows a blocking I/O model:

```java
while(true) {
    Socket acceptConnection = socket.accept();  // Blocks until connection
    // Process request synchronously
    // Close connection
}
```

**Characteristics:**
- Sequential request processing
- One client at a time
- Simple, predictable execution flow
- Suitable for low-traffic scenarios or when request processing is extremely fast

**Limitations:**
- Cannot handle concurrent clients
- Blocking I/O prevents server from accepting new connections while processing existing ones
- Poor resource utilization under load

### Multithreaded Server

The multithreaded implementation (`Multithreaded/src/Server.java`) uses a thread-per-connection model:

```java
while(true) {
    Socket acceptedSocket = serverSocket.accept();
    Thread thread = new Thread(() -> server.getConsumer().accept(acceptedSocket));
    thread.start();  // Non-blocking, continues to accept new connections
}
```

**Characteristics:**
- Concurrent request handling
- Each client connection is processed in its own thread
- Server remains responsive to new connections while processing existing ones
- Better throughput under concurrent load

**Design Notes:**
- Uses `Consumer<Socket>` functional interface for request handling logic
- Thread creation overhead may become a bottleneck under extreme load
- No explicit thread pool management (creates unbounded threads)

## Project Structure

```
Web-Servers/
├── SingleThreaded/
│   ├── src/
│   │   ├── Server.java    # Single-threaded server implementation
│   │   └── Client.java       # Test client
│   └── SingleThreaded.iml
├── Multithreaded/
│   ├── src/
│   │   ├── Server.java    # Multithreaded server implementation
│   │   └── Client.java    # Concurrent test client (10 threads)
│   └── Multithreaded.iml
└── README.md
```

## Building and Running

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- Java compiler (`javac`) and runtime (`java`) in PATH

### Compilation

**Single-Threaded:**
```bash
cd SingleThreaded/src
javac Server.java Client.java
```

**Multithreaded:**
```bash
cd Multithreaded/src
javac Server.java Client.java
```

### Execution

**Start the Server:**

Single-threaded:
```bash
cd SingleThreaded/src
java Server
```

Multithreaded:
```bash
cd Multithreaded/src
java Server
```

**Run the Client:**

Single-threaded (single connection):
```bash
cd SingleThreaded/src
java Client
```

Multithreaded (10 concurrent connections):
```bash
cd Multithreaded/src
java Client
```

## Technical Details

### Port Configuration

Both servers listen on port `8010`. To change the port, modify the `port` variable in the respective `Server.java` files.

### Protocol

The servers implement a simple request-response protocol:
- Client sends: `"Helloo from Client"`
- Server responds: `"Hello from the server"`
- Connection is closed after response

### Resource Management

**Current Implementation:**
- Streams and sockets are closed after each request
- No connection pooling or keep-alive support
- Thread lifecycle in multithreaded version relies on JVM cleanup

**Considerations for Production:**
- Implement proper exception handling and resource cleanup with try-with-resources
- Use `ExecutorService` with thread pools instead of unbounded thread creation
- Add connection timeouts and request size limits
- Implement graceful shutdown mechanisms
- Consider using NIO (`java.nio`) for better scalability

## Performance Characteristics

| Aspect | Single-Threaded | Multithreaded |
|--------|----------------|---------------|
| **Concurrency** | Sequential (1 client) | Concurrent (N clients) |
| **Memory Overhead** | Low | Higher (per-thread stack) |
| **Context Switching** | None | Thread context switches |
| **Scalability** | Limited by blocking I/O | Limited by thread creation overhead |
| **Use Case** | Low traffic, simple requests | Moderate concurrent load |

## Potential Enhancements

1. **Thread Pool Management**: Replace unbounded thread creation with `ExecutorService` and configurable thread pools
2. **NIO Implementation**: Use `java.nio.channels` for non-blocking I/O and better scalability
3. **HTTP Protocol Support**: Implement proper HTTP request parsing and response formatting
4. **Connection Pooling**: Add connection reuse and keep-alive support
5. **Error Handling**: Comprehensive exception handling and logging
6. **Configuration**: Externalize port numbers, thread pool sizes, and other parameters
7. **Metrics**: Add request counting, latency measurement, and throughput monitoring

## License

This project is provided for educational purposes.
