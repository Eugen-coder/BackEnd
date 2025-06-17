package ait.chat.server.task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class ChatServerReceiver implements Runnable {
    private final Socket socket;
    private final BlockingQueue<String> messageBox;

    public ChatServerReceiver(Socket socket, BlockingQueue<String> messageBox) {
        this.socket = socket;
        this.messageBox = messageBox;
    }

    @Override
    public void run() {

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            String message;
            while ((message = reader.readLine()) != null) {
                messageBox.put(message);
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Ошибка при получении сообщения: " + e.getMessage());
            Thread.currentThread().interrupt();
        }

    }
}
