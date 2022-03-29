package ru.tpu.javaEELabs.lab2;

import java.io.*;

import java.net.*;

public class Server {
private BufferedReader in = null;
private String str = null;
private byte[] buffer;
private DatagramPacket packet;
private InetAddress address;
private DatagramSocket socket;

public Server() throws IOException {
System.out.println("Sending messages");

socket = new DatagramSocket();

transmit();
}
public void transmit() {
try {

in = new BufferedReader(new InputStreamReader(System.in));
while (true) {
System.out.println(
"Введите строку для передачи клиентам: ");
str = in.readLine();
buffer = str.getBytes();
address = InetAddress.getByName("233.0.0.1");

packet = new DatagramPacket(
buffer,
buffer.length,
address,
1502);

socket.send(packet);
}
} catch (Exception e) {
e.printStackTrace();
} finally {

try {

in.close();
socket.close();
} catch (Exception e) {
e.printStackTrace();
}
}
}
public static void main(String arg[]) throws Exception {

new Server();
}}
