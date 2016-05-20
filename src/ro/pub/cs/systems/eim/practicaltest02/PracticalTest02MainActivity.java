package ro.pub.cs.systems.eim.practicaltest02;

import android.app.Activity;
import android.os.Bundle;
import android.provider.SyncStateContract.Constants;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class PracticalTest02MainActivity extends Activity {

	final public static String TAG = "[PracticalTest02]";
	
	private EditText serverPortEditText = null;
    private Button connectButton = null;
    private EditText clientAddressEditText = null;
    private EditText clientPortEditText = null;
    private Button getTimeButton = null;
    private TextView response = null;
    private ServerThread serverThread = null;
    private ClientThread clientThread = null;
    
    
	private ConnectButtonClickListener connectButtonClickListener = new ConnectButtonClickListener();
    private class ConnectButtonClickListener implements Button.OnClickListener {

        @Override
        public void onClick(View view) {
            String serverPort = serverPortEditText.getText().toString();
            if (serverPort == null || serverPort.isEmpty()) {
                Toast.makeText(
                        getApplicationContext(),
                        "Server port should be filled!",
                        Toast.LENGTH_SHORT
                ).show();
                return;
            }

            serverThread = new ServerThread(Integer.parseInt(serverPort));
            if (serverThread.getServerSocket() != null) {
                serverThread.start();
            } else {
                Log.e(TAG, "[MAIN ACTIVITY] Could not creat server thread!");
            }
        }
    }

    private TimeClickListener getTimeClickListener = new TimeClickListener();
    private class TimeClickListener implements Button.OnClickListener {

        @Override
        public void onClick(View view) {
        	
        	 String clientAddress = clientAddressEditText.getText().toString();
             String clientPort    = clientPortEditText.getText().toString();
        	
        	clientThread = new ClientThread(
                    clientAddress,
                    Integer.parseInt(clientPort),
                    response);
            clientThread.start();
        }
    }
    
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test02_main);
        
        serverPortEditText = (EditText)findViewById(R.id.server_port_edit_text);
        clientAddressEditText = (EditText)findViewById(R.id.client_address_edit_text);
        clientPortEditText = (EditText)findViewById(R.id.client_port_edit_text);
        
        response = (TextView)findViewById(R.id.server_response);
        
        connectButton = (Button)findViewById(R.id.connect_button);
        connectButton.setOnClickListener(connectButtonClickListener);

        getTimeButton = (Button)findViewById(R.id.get_time_button);
        getTimeButton.setOnClickListener(getTimeClickListener);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.practical_test02_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    @Override
    protected void onDestroy() {
        if (serverThread != null) {
            serverThread.stopThread();
        }
        super.onDestroy();
    }
    
}
