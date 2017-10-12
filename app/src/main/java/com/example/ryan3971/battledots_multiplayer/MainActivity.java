package com.example.ryan3971.battledots_multiplayer;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.UUID;

/**
 * Created by ryan3971 on 2/11/2016.
 *
 * Variables:
 *
 * mBluetoothAdapter - Extends BluetoothAdapter - Used ???
 * REQUEST_ENABLE_BT - Unchangeable int - The set chosen integer is used to ???
 * MESSAGE_READ - Unchangeable int - ???
 * NAME - Unchangeable String - ???
 * MY_UUID - Extends UUID - ???
 * TAG - String - Used for Debugging - Attaches a tag to any Log item I create to easily find it in the Android Monitor
 * mConnectedThread - Extends ConnectedThread - ???
 * mConnectThread - Extends ConnectThread - ???
 * acceptThread - Extends AcceptThread - ??
 * foundPairedDevice - boolean - ???
 * PLAYER_TAG - int -
 * CONSTANTS - Extends custom class, Constants - Used to access the variables in the class Constants
 * nextPairedDevice - boolean -
 * connectThreadList - ArrayList containing objects for the class ConnectThread -
 * handle - Extends dataHandler -
 * handler - Extends Handler - used for various handler cases
 * VF - Extends ViewFlipper -
 * countDownText - Extends TextView -
 * countDownNum - int -
 * game - Extends GameView -
 * endGame - Extends TextView -
 * START_GAME - boolean -
 * statues - Extends TextView -
 * animations - Extends Animation -
 *
 * Purpose:
 *
 *
 * Other Stuff:
 *
 */
public class MainActivity extends Activity{


    BluetoothAdapter mBluetoothAdapter;

    public final int REQUEST_ENABLE_BT = 1;
    public static final int MESSAGE_READ = 1;

    public final String NAME = "BattleDots";
    public UUID MY_UUID;

    public final String TAG = "RYYYYAN";

    static ConnectedThread mConnectedThread;
    ConnectThread mConnectThread;

    AcceptThread acceptThread;

    boolean foundPairedDevice = false;

    public static int PLAYER_TAG;

    static Constants CONSTANTS;

    boolean nextPairedDevice = false;

  //  OtherObstaclesAndPowerUps OtherOandP;

    ArrayList<ConnectThread> connectThreadList;

    dataHandler handle;
    Handler handler;

    static ViewFlipper VF;

    TextView countDownText;
    int countDownNum;

    GameView game;

    static TextView endGame;

    static boolean START_GAME = true;

   // StartGameThread startGameThread;

    TextView statues;

    Animations animations;

    /**
     * Basically used to initialise variables and set the layout
     *
     * @param savedInstanceState - ???
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.master_layout);

        VF = (ViewFlipper) findViewById(R.id.ViewFlipper01);
        game = (GameView) findViewById(R.id.game_view);

        MY_UUID = UUID.fromString("acb0f9ad-0ff0-4e52-b176-8dfdaa752137");

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth
        }

        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

        CONSTANTS = new Constants();

        connectThreadList = new ArrayList<>();

        handle = new dataHandler(this);
        handler = new Handler();

        countDownText = (TextView) findViewById(R.id.count_down);
        endGame = (TextView)findViewById(R.id.endGameText);

        statues = (TextView)findViewById(R.id.statues);

        animations = new Animations(getApplicationContext());

    }

    /**
     * Called when the button "Accept Thread" is clicked. Begins the process of connecting to another device by
     * making this device discoverable, and than enabling to accept a connection from another device
     *
     * Variables:
     *
     * getVisible - Extends Intent -
     *
     * @param onClick - Enables it to be called from the layout activity_main
     */

    public  void makeDiscoverableAndRunAcceptThread(View onClick){
        Intent getVisible = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        startActivityForResult(getVisible, 0);
        runAcceptThread();
    }

    /**
     * Called when the button "Connect Thread" is clicked. Causes the device to search for a device to connect to via
     * Bluetooth
     *
     * @param onClick - Enables it to be called from the layout activity_main
     */

    public void doDiscoveryAndRunConnectThread(View onClick) {

        statues.setText("Connecting...");


        BluetoothAdapter mBtAdapter = BluetoothAdapter.getDefaultAdapter();

        // Request discover from BluetoothAdapter
        mBtAdapter.startDiscovery();
        handler.postDelayed(scanningTimer, 0);
    }

    /**
     * Initiates the thread that allows the client to connect to the host
     */
    public Runnable scanningTimer = new Runnable() {
        @Override
        public void run() {
            connectToPairedDevices();
        }
    };


    public void runAcceptThread()   {

        statues.setText("Waiting...");
        acceptThread = new AcceptThread();
        acceptThread.start();

    }

    /**
     * Variables:
     *
     * pairedDevices - Set ??? - Contains a list of all devices that the host has paired with
     *
     * Starts a thread that attempts to connect with a previously paired device. The for loop runs through all the devices contained in the "pairedDevices" list,
     * attempting to connect with each one until it connects to the correct one (that being the host)
     *
     */

    public void connectToPairedDevices()    {

        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        // If there are paired devices
        if (pairedDevices.size() > 0) {
            // Loop through paired devices
            for (BluetoothDevice devicePaired : pairedDevices) {

                mConnectThread = new ConnectThread(devicePaired);
                connectThreadList.add(mConnectThread);
                mConnectThread.setDaemon(true);
                mConnectThread.start();
            }
        }
    }

    /**
     * Once a thread has successfully connected to the host, this function cancels all reamaing threads that are running. The parameter is included so as to not
     * cancel the thread that successfully connected.
     *
     * @param thread - Contains the thread object that was able to successfully connect to the host.
     */

    public void cancelConnectThreads(ConnectThread thread)  {

        for (ConnectThread connectThread : connectThreadList) {

            if (connectThread != thread)    //ensures the good connection isn't closed
                connectThread.cancel();
        }
    }


    /**
     * Variables:
     *
     * mmServerSocket - Extends BluetoothServerSocket - ???
     * tmp - Extends BluetoothServerSocket - ???
     * socket - Extends BluetoothSocket - ???
     *
     * This thread opens up a connection via Bluetooth, and will accept any incoming connections with a client that contains the same "NAME" and "MY_UUID"
     * (basically, it will accept a connection from another device running this game). The connection remains open, via the while loop, until a connection
     * is made, or fails to be made. If a connection is made, several self-explanatory events occur, and a method is called that will maintain the connection.
     */

    private class AcceptThread extends Thread {

        private final BluetoothServerSocket mmServerSocket;

        public AcceptThread() {
            // Use a temporary object that is later assigned to mmServerSocket,
            // because mmServerSocket is final
            BluetoothServerSocket tmp = null;
            try {
                // MY_UUID is the app's UUID string, also used by the client code
                tmp = mBluetoothAdapter.listenUsingRfcommWithServiceRecord(NAME, MY_UUID);
            } catch (IOException e) { }
            mmServerSocket = tmp;
        }

        public void run() {
            BluetoothSocket socket = null;
            // Keep listening until exception occurs or a socket is returned
            Log.w(TAG, "Accept Thread Running");
            while (true) {
                try {
                    socket = mmServerSocket.accept();
                } catch (IOException e) {
                    break;
                }
                // If a connection was accepted
                if (socket != null) {
                    // Do work to manage the connection (in a separate thread)
                    manageConnectedSocket(socket);
                    Log.w(TAG, "Accept - Success");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            statues.setText("Connected!");

                        }
                    });

                    GameConstants.PLAYER_TAG = CONSTANTS.PLAYER_TAG_HOST;
                    PLAYER_TAG = CONSTANTS.PLAYER_TAG_HOST;

                    try {
                        mmServerSocket.close();
                    }catch (IOException closeException)   {  }
                    break;
                }
            }
        }

        /**
         * Will cancel the listening socket, and cause the thread to finish
         */

        public void cancel() {
            try {
                mmServerSocket.close();
            } catch (IOException e) { }
        }
    }


    /**
     * Variables:
     *
     * mmSocket - Extends BluetoothSocket - ???
     *
     * This thread checks once, than closes. It checks to see if there are any open connections with the same "MY_UUID" that it can connect too. If
     * unsuccessful, it closes. If successful, several events occur, and a function is called to start a new thread that will maintain the connection. Upon
     * a successful connection, "cancelConnectThreads" is called to cancel any other threads attempting to make a connection.
     *
     */

    private class ConnectThread extends Thread {
        private final BluetoothSocket mmSocket;
    //    private final BluetoothDevice mmDevice;

        public ConnectThread(BluetoothDevice device) {
            // Use a temporary object that is later assigned to mmSocket,
            // because mmSocket is final
            BluetoothSocket tmp = null;
        //    mmDevice = device;

            // Get a BluetoothSocket to connect with the given BluetoothDevice
            try {
                // MY_UUID is the app's UUID string, also used by the server code
                tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
            } catch (IOException e) { }
            mmSocket = tmp;
        }

        public void run() {
            // Cancel discovery because it will slow down the connection
            // mBluetoothAdapter.cancelDiscovery();
            Log.w(TAG, "Connected Thread Running");
            try {
                // Connect the device through the socket. This will block
                // until it succeeds or throws an exception
                mmSocket.connect();
                Log.w(TAG, "Connected Thread - Success");

            } catch (IOException connectException) {
                // Unable to connect; close the socket and get out
                Log.w(TAG, "Connected Thread - Failed");
                nextPairedDevice = true;

                try {
                    mmSocket.close();
                } catch (IOException closeException) { }
                return;
            }

            // Do work to manage the connection (in a separate thread)
            manageConnectedSocket(mmSocket);
            foundPairedDevice = true;
            Log.w(TAG, "Connect - Success");

            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    statues.setText("Connected!");

                }
            });

            PLAYER_TAG = CONSTANTS.PLAYER_TAG_CLIENT;
            GameConstants.PLAYER_TAG = CONSTANTS.PLAYER_TAG_CLIENT;

            cancelConnectThreads(this); // closes all threads currently trying to connect

        }

        /** Will cancel an in-progress connection, and close the socket */
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) { }
        }
    }


    /**
     * ???
     *
     *
     *
     * @param socket - ???
     */

    public synchronized void manageConnectedSocket(BluetoothSocket socket) {

        // Cancel the thread that completed the connection
        if (mConnectThread != null) {
            mConnectThread.cancel();
            mConnectThread = null;
        }

        // Cancel any thread currently running a connection
        if (mConnectedThread != null) {
            mConnectedThread.cancel();
            mConnectedThread = null;
        }

        // Start the thread to manage the connection and perform transmissions

    //    for (int num = 0; num < 3; num++) {
            mConnectedThread = new ConnectedThread(socket);
            mConnectedThread.start();
   //     }
    }


    /**
     *
     * Variables:
     *
     * mmSocket - Extends BluetoothSocket -
     * mmInStream - Extends InputStream -
     * mmOutStream - Extends OutputStream -
     * tmpIn - Extends InputStream -
     * tmpOut - Extends OutputStream -
     *
     * buffer - byte[] -
     * bytes - int -
     *
     *
     * ???
     *
     *
     */

    private class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the input and output streams, using temp objects because
            // member streams are final
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) { }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            byte[] buffer = new byte[4096];  // buffer store for the stream
            int bytes; // bytes returned from read()

            // Keep listening to the InputStream until an exception occurs
            while (true) {
                try {
                    // Read from the InputStream
                    bytes = mmInStream.read(buffer);
                    // Send the obtained bytes to the UI activity.
                    handle.obtainMessage(MESSAGE_READ, bytes, -1, Arrays.copyOf(buffer, bytes))
                            .sendToTarget();
//                    recieveMessage(MESSAGE_READ, bytes, -1, buffer);

                    Log.w(TAG, "Message Recieved");

                } catch (IOException e) {
                    break;
                }
            }
        }

        /**
         *
         * ???
         *
         *
         * @param bytes - The data to be sent to the other device
         */

        /* Call this from the main activity to send data to the remote device */
        public void write(byte[] bytes) {

            while (true) {
                try {
                    mmOutStream.write(bytes);
                    mmOutStream.flush();
             //       Log.w("GAME", "SEEEENT");
                    break;

                } catch (IOException e) {
           //         Log.w("GAME", "SEEENT FAILED");
                }
            }
        }

        /*
        * Call this from the main activity to shutdown the connection
        */
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) { }
        }
    }


    /**
     *
     * Variables:
     *
     * mainActivityWeakReference - WeakReference ?Array? containing objects for the class MainActivity - all I recall is that it is
     *                              used to prevent a massive warning for this Handler
     *
     * This handler is called when data is received via bluetooth. The data received is broken up into the three components that make it up, and sent to
     * "checkRecievedData" using the variable "mainActivityWeakReference"
     *
     */

    static class dataHandler extends Handler{

        static WeakReference<MainActivity> mainActivityWeakReference;


        dataHandler(MainActivity m)  {

            mainActivityWeakReference = new WeakReference<MainActivity>(m);
        }

        /**
         * Variables:
         *
         *
         * readBuf = byte[] -
         * readMessage - String -
         * isDataList - int -
         * recievedIntList - Array of type Integers -
         * dataDoubleList - Array of type Double -
         * dataString - String -
         *
         * Explanation:
         * Info about how it works/what it does
         *
         *
         * @param msg contains info regarding where the data came from/what is to be done with it, as well as the data itself.
         */

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {

                case MESSAGE_READ:
                    byte[] readBuf = (byte[]) msg.obj;
                    Log.w("GAME", "RECIEVED");

                    // construct a string from the valid bytes in the buffer
                    String readMessage = new String(readBuf, 0, msg.arg1);

                    int isDataList = 0;
                    ArrayList<Integer> recievedIntList = new ArrayList<Integer>();
                    ArrayList<Double> dataDoubleList = new ArrayList<Double>();
                    String dataString = "";

                    for (int i = 0; i < readMessage.length(); i++) {

                        dataString += readMessage.charAt(i);

                        if (Character.isWhitespace(readMessage.charAt(i))) {

                            if (isDataList >= 2) {
                                try {
                                    double dataDouble = Double.parseDouble(dataString.trim());
                                    dataDoubleList.add(dataDouble);
                                }catch (NumberFormatException e)    {

                                }
                                dataString = "";

                            } else {
                                int dataInt = Integer.parseInt(dataString.trim());
                                recievedIntList.add(dataInt);
                                isDataList++;
                                dataString = "";
                            }
                        }
                    }

                    mainActivityWeakReference.get().checkRecievedData(recievedIntList.get(0), recievedIntList.get(1), dataDoubleList);

            }
        }

    }


    /**
     *
     * Variables:
     *
     * playerTag - int -
     * dataListString - String -
     * size - int -
     * dataNum - double -
     * sendString - String -
     * messageByte - byte[] -
     *
     * Explanation:
     *
     *
     *
     * @param referenceTag
     * @param data
     */

    public static void DataToByteAndSend(int referenceTag, ArrayList<Double> data) {

        int playerTag = PLAYER_TAG;
        String dataListString = "";
        int size = 0;

        try {
            size = data.size();
        }catch (NullPointerException e)   {}

        for (int i = 0; i < size; i++) {

            double dataNum = data.get(i);
            dataListString += dataNum + " ";
        }

        String sendString = playerTag + " " + referenceTag + " " + dataListString; //space not needed here since done above
        byte[] messageByte = sendString.getBytes();
        mConnectedThread.write(messageByte);
    }

/**

    public void sendMessage(View onClick)   {

        int a = 11;
        int b = 22;
        int c = 33;

        String data = a + " " + b + " " + c;
        //   String message = String.valueOf(num);
        byte[] messageByte = data.getBytes();
        mConnectedThread.write(messageByte);
    }
*/


    /**
     * Explanation:
     *
     *
     *
      * @param onClick
     */

    public void startGame(View onClick) {
        Log.w("TAGTAG", "UH-HUH");

     //   ArrayList<Double> dataIntList = new ArrayList<>();
      //  dataIntList.add(CONSTANTS.START_GAME_TRUE_CONSTANT);
      //  DataToByteAndSend(CONSTANTS.START_GAME_CONSTANT, dataIntList);
        GameConstants.PLAYER_TAG = 1;
        handler.postDelayed(startGameChecker, 0);

    }

    /**
     * Explanation:
     *
     *
     */

    public Runnable startGameChecker = new Runnable() {
        @Override
        public void run() {

            if (START_GAME == true)     {
                START_GAME = false;
                game.initilizer();
                VF.setDisplayedChild(1);
                game.setClickable(false);       //figure out latter
                countDownNum = 3;
                countDownText.setVisibility(View.VISIBLE);
                handler.postDelayed(countDown, 0);

            }else   {
                handler.postDelayed(startGameChecker, 1);

            }
        }
    };


    /**
     * Explanation: (of the purpose of this method - is very self explanatory)
     *
     *
     * @param playerTag
     * @param referenceTag
     * @param data
     */

    public static void checkRecievedData(int playerTag, int referenceTag, ArrayList<Double> data)     {

            if (playerTag != PLAYER_TAG) {

                if (referenceTag == CONSTANTS.START_GAME_CONSTANT) {
                    START_GAME = true;

                }

                if (referenceTag == CONSTANTS.OTHER_DOT_X_AND_Y_CONSTANT) {
                    try {

                        double x = data.get(0);
                        double y = data.get(1);

                        double x_converted = convertDpToPixel((float) x);
                        double y_converted = convertDpToPixel((float)y);

                        Constants.setOtherDotX(x_converted);
                        Constants.setOtherDotY(y_converted);

                    } catch (IndexOutOfBoundsException e) {

                    }
                }

                if (referenceTag == CONSTANTS.OBSTACLES_CONSTANT) {

                    ArrayList<Double> OTHER_OBSTACLE_LIST_X = new ArrayList<>();
                    ArrayList<Double> OTHER_OBSTACLE_LIST_Y = new ArrayList<>();

                    double size = data.size();

                    for (int i = 0; i < size; i++) {

                        double newData = data.get(i);
                        float dataConverted = convertDpToPixel((float) newData);

                        if (i % 2 == 0) {
                            OTHER_OBSTACLE_LIST_X.add((double) dataConverted);
                        } else {
                            OTHER_OBSTACLE_LIST_Y.add((double) dataConverted);
                        }
                    }

                    GameConstants.setObstacleListXandY(OTHER_OBSTACLE_LIST_X, OTHER_OBSTACLE_LIST_Y);
                }

                if (referenceTag == CONSTANTS.POWERUPS_CONSTANT) {

                    ArrayList<Double> OTHER_POWERUPS_LIST_X = new ArrayList<>();
                    ArrayList<Double> OTHER_POWERUPS_LIST_Y = new ArrayList<>();

                    double size = data.size();
                    for (int i = 0; i < size; i++) {

                        double newData = data.get(i);
                        float dataConverted = convertDpToPixel((float) newData);

                        if (i % 2 == 0) {
                            OTHER_POWERUPS_LIST_X.add((double) dataConverted);
                        } else {
                            OTHER_POWERUPS_LIST_Y.add((double) dataConverted);
                        }
                    }
                    GameConstants.setPowerUpListXandY(OTHER_POWERUPS_LIST_X, OTHER_POWERUPS_LIST_Y);
                }

                if (referenceTag == CONSTANTS.NEW_POWERUPS_CONSTANT) {
                    GameConstants.setNewPowerUp(true);
                }

                if (referenceTag == CONSTANTS.NEW_OBSTACLES_CONSTANT) {
                    GameConstants.setNewObstacles(true);
                }

                if (referenceTag == CONSTANTS.DOT_SIZE) {
                    GameConstants.setDot2Size(data.get(0));
                }

                if (referenceTag == CONSTANTS.DOT_INVISIBLE) {

                    if (data.get(0) == CONSTANTS.DOT_IS_INVISIBLE)
                        GameConstants.setDot2Invisible(true);
                    else if (data.get(0) == CONSTANTS.DOT_IS_VISIBLE)
                        GameConstants.setDot2Invisible(false);
                }
                if (referenceTag == CONSTANTS.DOT_SHIELD) {

                    if (data.get(0) == CONSTANTS.DOT_IS_SHIELD)
                        GameConstants.setDot2Shield(true);
                    else if (data.get(0) == CONSTANTS.DOT_IS_NOT_SHIELD)
                        GameConstants.setDot2Shield(false);
                }
            }

        if (referenceTag == CONSTANTS.LASER) {

            double x = data.get(0);
            double y = data.get(1);
            double angle = data.get(2);


            double laser_x = convertDpToPixel((float)x);
            double laser_y = convertDpToPixel((float)y);

            GameConstants.setNewLaser(true);
            GameConstants.setNewLaserX(laser_x);
            GameConstants.setNewLaserY(laser_y);
            GameConstants.setNewLaserAngle(angle);

        }

        if (referenceTag == CONSTANTS.BALL) {
            Log.w("BALL", "ECIEVED");

            double x = data.get(0);
            double y = data.get(1);
            double angle = data.get(2);


            double ball_x = convertDpToPixel((float)x);
            double ball_y = convertDpToPixel((float)y);

            GameConstants.setNewBall(true);
            GameConstants.setNewBallX(ball_x);
            GameConstants.setNewBallY(ball_y);
            GameConstants.setNewBallAngle(angle);

        }

        if (referenceTag == CONSTANTS.MULTI_LASER) {

            ArrayList<Double> LASER_LIST_X = new ArrayList<>();
            ArrayList<Double> LASER_LIST_Y = new ArrayList<>();
            ArrayList<Double> ANGLE_LIST = new ArrayList<>();

            double order = 0;

            double size = data.size();
            for (int i = 0; i < size; i++) {

                if (order == 0) {
                    double newData = data.get(i);
                    float dataConverted = convertDpToPixel((float) newData);
                    LASER_LIST_X.add((double) dataConverted);
                    order = 1;
                } else if (order == 1) {
                    double newData = data.get(i);
                    float dataConverted = convertDpToPixel((float) newData);
                    LASER_LIST_Y.add((double) dataConverted);
                    order = 2;
                }else if (order == 2){
                    double angle = data.get(i);
                    ANGLE_LIST.add(angle);
                    order = 0;
                }
            }

            GameConstants.setNewMultiLaser(true);
            GameConstants.setNewMultiLaserX(LASER_LIST_X);
            GameConstants.setNewMultiLaserY(LASER_LIST_Y);
            GameConstants.setNewMultiLaserAngle(ANGLE_LIST);

        }
        if (referenceTag == CONSTANTS.TARGET_BALL) {

            double x = data.get(0);
            double y = data.get(1);

            double ball_x = convertDpToPixel((float) x);
            double ball_y = convertDpToPixel((float) y);

            GameConstants.setNewTargetBall(true);
            GameConstants.setNewTargetBallX(ball_x);
            GameConstants.setNewTargetBallY(ball_y);
        }

        if (referenceTag == CONSTANTS.POWER_WAVE) {

            double x = data.get(0);
            double y = data.get(1);

            double wave_x = convertDpToPixel((float)x);
            double wave_y = convertDpToPixel((float) y);

            GameConstants.setNewPowerWave(true);
            GameConstants.setNewPowerWaveX(wave_x);
            GameConstants.setNewPowerWaveY(wave_y);
        }

        if (referenceTag == CONSTANTS.RAPID_FIRE) {

            double x = data.get(0);
            double y = data.get(1);
            double angle = data.get(2);


            double rapid_fire_x = convertDpToPixel((float)x);
            double rapid_fire_y = convertDpToPixel((float)y);

            GameConstants.setNewRapidFire(true);
            GameConstants.setNewRapidFireX(rapid_fire_x);
            GameConstants.setNewRapidFireY(rapid_fire_y);
            GameConstants.setNewRapidFireAngle(angle);

        }

        if (referenceTag == CONSTANTS.SENTRY_GUN) {

            double x = data.get(0);
            double y = data.get(1);


            double sentry_gun_fire_x = convertDpToPixel((float)x);
            double sentry_gun_fire_y = convertDpToPixel((float)y);

            GameConstants.setNewSentryGun(true);
            GameConstants.setNewSentryGunX(sentry_gun_fire_x);
            GameConstants.setNewSentryGunY(sentry_gun_fire_y);

        }

        if (referenceTag == CONSTANTS.SENTRY_GUN_LASER) {

            double x = data.get(0);
            double y = data.get(1);
            double angle = data.get(2);


            double laser_x = convertDpToPixel((float)x);
            double laser_y = convertDpToPixel((float)y);

            GameConstants.setNewSentryGunLaser(true);
            GameConstants.setNewSentryGunLaserX(laser_x);
            GameConstants.setNewSentryGunLaserY(laser_y);
            GameConstants.setNewSentryGunLaserAngle(angle);

        }

        if (referenceTag == CONSTANTS.END_GAME) {
            endGame(CONSTANTS.WINNER);
        }
    }
    public static float convertDpToPixel(float dp){
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }


    public static void endGame(int winOrLose) {

        VF.setDisplayedChild(2);
        if (winOrLose == 0) {
            endGame.setTextColor(Color.GREEN);
            endGame.setText("WINNER");
        }else if (winOrLose == 1) {
            endGame.setTextColor(Color.RED);
            endGame.setText("LOSER");
        }

    }

    public Runnable countDown = new Runnable() {
        @Override
        public void run() {
            Log.w("TAG", "YUP");
            if (countDownNum == 3) {
                countDownNum = 2;
                countDownText.setText("3");
                animations.animateStartTimer(countDownText);
                handler.postDelayed(countDown, 1000);

            }else if (countDownNum == 2) {
                countDownNum = 1;
                countDownText.setText("2");
                animations.animateStartTimer(countDownText);
                handler.postDelayed(countDown, 1000);

            }else if (countDownNum == 1) {
                countDownNum = 0;
                countDownText.setText("1");
                animations.animateStartTimer(countDownText);
                handler.postDelayed(countDown, 1000);

            }else {
                countDownText.setVisibility(View.INVISIBLE);
                game.setClickable(true);
            }
        }
    };


}

//not working from my phone cause of my running through all paired phones thing
//might change hoe constants object is passed on
// might not call GameConstants statically. May use instance instead, depending on performance
//too much data over bluetooth causes crash - google and research
// relook at transfering of obstaclles and powerups - find more efficient method
//sometimes, obstacles and powerps aren't removed. Theory is that the data isn't sent over bluetooth. happens approximetly every 1 in 10. Fix latter
//just change all dot_radius from float to double - stay consistent
// game may start with dots on an obstacle or powerup - has happened
//set invisible dot using alpha instead of white
//instead of two classes for all powerups, just send both manage_Objects and check which is null to determine what to do
//lasers may move at different speeds on both devices
//when other device hits a powerup that should have disappered, crashes game with indexoutofbounds error
//slight delay with movement of objects, depending on the devices used - big delay with moms, minimul delay with justyns
//sentry gun and rapid fire rely on processing speed for timing - might be issues for different phones with different procesing speeds
//reason lag after start game because other connect threads still running??? - i think so cause lag is always on one connecting -COMFIRMED
//convert spped from dp to pixals
// theory - crashes when both players get a powerup or obstacle simaltaneously. Solution - thought that sort of issue was fixed, try using a try...catch statment?
//find if there is a way to check if device recieved data from other device
//if issues with making new obstacles and powerups, have a set size that it should maintan - a set number of obstacles and powerups that there will always be
//PROBLEM - checkinh collision, dot_x and dot_y jut use the middle of the screen - INCORRECT - need actual coordinates
//not enough obstacles and powerups may be caused from the frequent demand for new ones, preventing current ones being made from being completed. may be good idea to include set number of obstacles and powerups, like what I've already done
//for connecting, have all connect threads put in a public array, and when connected, call a method that stops all threads in that array.
//rapid fire image not showing
//sentry gun has limited number of shots?
//may change obstacles and powerups creation to have it chose a completly random location and check if it is on top of boundaries or other objects
//create method in powerupvariables that checks dot size to determine shield size. use getter whenever refrencing shield from other classes


//mode showing three possible powerups you could get from one - shown through 3 images on one powerup
//timer grow and fade out effect
//way to arm yourself
//circular buffer - method so data isn't lost over bluetooth
//STRATEGY!!!!!!
//minor fixes - speed, size, etc.
//fix up spped boost
//many different options for setting up the game - timer, enable speedboost, etc.
//maintain bluetooth connection when phone turns off
//make Constants statically called or variable?
//issue with firing if not moving

/*
make discoverable
scan
connect



Questions to ask:

- open up multiple bluetooth connections with the same devices
- multithreading with android + with bluetooth
- smoothly open up new layouts
- touch views from thread that did not create view hierarchy
 */