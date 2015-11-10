
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**

 */
public class ConnectionHandler implements Runnable{

    protected Socket clientSocket = null;
    protected String serverText   = null;
    private int count = 0;

    public ConnectionHandler(Socket clientSocket, String serverText, int count) {
        this.clientSocket = clientSocket;
        this.serverText   = serverText;
        this.count = count;
    }

    public void run() {
        try {
        	BufferedReader input= new BufferedReader( new InputStreamReader( clientSocket.getInputStream() ) );
            PrintWriter output= new PrintWriter( clientSocket.getOutputStream(), true );
            long time = System.currentTimeMillis();
            String temp = input.readLine();
            output.println("Thread" + this.count + " responded");
            output.close();
            input.close();
            System.out.println("Request processed: " + time);
        } catch (IOException e) {
            //report exception somewhere.
            e.printStackTrace();
        }
    }
}