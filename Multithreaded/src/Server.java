import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public class Server {
    public Consumer<Socket> getConsumer(){
        return (clientSocket) -> {
            try {
                PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream());
                toClient.println("Hello from the server");
                toClient.close();
                clientSocket.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }
    public static void main(String[] args) {
        Server server = new Server();
        int port = 8010;
        try {
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                while (true){
                    Socket acceptedSocket = serverSocket.accept();
                    Thread thread = new Thread(() -> server.getConsumer().accept(acceptedSocket));
                    thread.start();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}