package ro.pub.cs.systems.eim.practicaltest02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import android.util.Log;
import android.widget.TextView;

public class ClientThread extends Thread{

	final public static String TAG = "[PracticalTest02]";
	private String address;
    private int port;
    private TextView getTimeTextView;

    private Socket socket;

    
    
    public ClientThread(
            String address,
            int port,
            TextView getTimeTextView) {
        this.address = address;
        this.port = port;
        this.getTimeTextView = getTimeTextView;
    }

    @Override
    public void run() {
        try {
            socket = new Socket(address, port);
            if (socket == null) {
                Log.e(TAG, "[CLIENT THREAD] Could not create socket!");
            }
            
            BufferedReader bufferedReader = Utilities.getReader(socket);
            PrintWriter printWriter = Utilities.getWriter(socket);
            if (bufferedReader != null && printWriter != null) {
                printWriter.println("giveMeTime");
                printWriter.flush();
                String timeInformation;
                while ((timeInformation = bufferedReader.readLine()) != null) {
                    final String finalizedTimeInformation = timeInformation;
                    getTimeTextView.post(new Runnable() {
                        @Override
                        public void run() {
                        	getTimeTextView.append(finalizedTimeInformation + "\n");
                        }
                    });
                }
            }
            else {
                Log.e(TAG, "[CLIENT THREAD] BufferedReader / PrintWriter are null!");
            }
            socket.close();
        } catch (IOException ioException) {
            Log.e(TAG, "[CLIENT THREAD] An exception has occurred: " + ioException.getMessage());
            if (true) {
                ioException.printStackTrace();
            }
        }
    }
}
