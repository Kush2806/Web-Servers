import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    public void run() throws IOException {
        int port = 8010;
        InetAddress address = InetAddress.getByName("localhost");
        Socket socket = new Socket(address,port);
        PrintWriter toserver = new PrintWriter(socket.getOutputStream());
        BufferedReader fromserver = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        toserver.println("Helloo from Client");
        String line = fromserver.readLine();
        System.out.println("Response from the server is :" + line);
    }

    public static void main(String[] args) {
        try {
            Client client = new Client();
            client.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
