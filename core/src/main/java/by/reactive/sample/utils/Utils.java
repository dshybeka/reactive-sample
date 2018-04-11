package by.reactive.sample.utils;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Utils {

    public static String getAddress() {

        String result = "unknown";
        try (final DatagramSocket socket = new DatagramSocket()) {

            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
            result = socket.getLocalAddress().getHostAddress();
        } catch (SocketException | UnknownHostException e) {

            e.printStackTrace();
        }

        return result;
    }
}
