package ro.pub.cs.systems.eim.practicaltest02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

public class CommunicationThread extends Thread{

	private ServerThread serverThread;
    private Socket socket;

    final public static String WEB_SERVICE_ADDRESS = "http://www.timeapi.org/utc/now";
    

	final public static String TAG = "[PracticalTest02]";
    
    public CommunicationThread(ServerThread serverThread, Socket socket) {
        this.serverThread = serverThread;
        this.socket = socket;
    }
    
    
    @Override
    public void run() {
        if (socket != null) {
            try {
                BufferedReader bufferedReader = Utilities.getReader(socket);
                PrintWriter printWriter = Utilities.getWriter(socket);
                if (bufferedReader != null && printWriter != null) {
                    String receivedString = bufferedReader.readLine();
                    HashMap<String, TimeInformation> data = serverThread.getData();
                    TimeInformation timeInformation = null;
                    String requestResult = null;
                    String ip_address = socket.getInetAddress().toString();
                    String socketAddress = socket.getLocalAddress().toString();
                    
                    if(data.containsKey(ip_address)) {
                    	timeInformation = data.get(ip_address);
                    } else {
                    	HttpClient httpClient = new DefaultHttpClient();
                        HttpGet httpGet = new HttpGet(WEB_SERVICE_ADDRESS);
                        
                        ResponseHandler<String> responseHandler = new BasicResponseHandler();
                        String pageSourceCode = httpClient.execute(httpGet, responseHandler);
                        
                        serverThread.setData(socketAddress, new TimeInformation(pageSourceCode));
                        requestResult = pageSourceCode;
                   
                    }
                    
                    if(timeInformation != null) {
                    	String result = timeInformation.getData();
                    	printWriter.print(result);
                    	printWriter.flush();
                    }
                    
                } else {
                    Log.e(TAG, "[COMMUNICATION THREAD] BufferedReader / PrintWriter are null!");
                }
                socket.close();
            } catch (IOException ioException) {
                Log.e(TAG, "[COMMUNICATION THREAD] An exception has occurred: " + ioException.getMessage());
                if (true) {
                    ioException.printStackTrace();
                }
            } 
        } else {
            Log.e(TAG, "[COMMUNICATION THREAD] Socket is null!");
        }
    }
    
}
