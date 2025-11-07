import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    public Runnable getRunnable(){
        return new Runnable() {
            @Override
            public void run() {
                int port = 8010;
                try{
                    InetAddress address = InetAddress.getByName("localhost");
                    Socket socket = new Socket(address,port);
                    PrintWriter toserver = new PrintWriter(socket.getOutputStream());
                    BufferedReader fromserver = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    toserver.println("Helloo from Client");
                    String line = fromserver.readLine();
                    System.out.println("Response from the server is :" + line);
                } catch (Exception e) {
                    return;
                }
            }
        };
    }
    public static void main(String[] args) {
        Client client = new Client();
        for (int i=0;i<10;i++){
            try{
                Thread thread = new Thread(client.getRunnable());
                thread.start();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}