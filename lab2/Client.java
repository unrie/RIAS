package lab2;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.stream.Stream;

public class Client implements AutoCloseable {
    private final Socket client;
    private final DataOutputStream os;
    private final BufferedReader is;

    public Client(String host, int port) throws IOException {
        client = new Socket(host, port);
        os = new DataOutputStream(client.getOutputStream());
        is = new BufferedReader(new InputStreamReader(client.getInputStream()));
    }

    public void sendReceive(String message) {
        try {
            System.out.println("Отправка сообщения на сервер: " + message);
            os.writeBytes(message + "\n");
            os.flush();
            String responseLine = is.readLine();
            if (responseLine != null) {
                System.out.println("Сервер ответил: " + responseLine);
            } else {
                System.out.println("Нет ответа от сервера");
            }
        } catch (UnknownHostException e) {
            System.err.println("Хост не известен");
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void close() throws IOException {
        sendReceive("Выход");
        is.close();
        os.close();
    }

    public static void main(String[] args) {
        int port = 8080;
        String host = "localhost";
        int totalClients = 4;
        Stream.iterate(1, x -> x + 1).limit(totalClients).forEach(id -> {
            new Thread(() -> {
                try (Client client = new Client(host, port)) {
                    client.sendReceive("Привет, пользователь " + id);
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        });
    }
}