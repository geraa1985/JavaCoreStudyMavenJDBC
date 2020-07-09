package server;

import com.sun.org.slf4j.internal.LoggerFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Vector;

public class Server {
    private final List<ClientHandler> clients;
    private final AuthService authService;
    private static final Logger LOG = LogManager.getLogger(Server.class);

    public static Logger getLOG() {
        return LOG;
    }

    public Server() {

        clients = new Vector<>();

        if (!SQL.connect()) {
            throw new RuntimeException("Ошибка подключения к БД!!!");
        }

        authService = new DBAuthService();

        final int PORT = 8120;

        ServerSocket server = null;
        Socket socket;

        try {
            server = new ServerSocket(PORT);
            System.out.println("Сервер запущен!");
            LOG.info("Сервер запущен!");
            while (true) {
                socket = server.accept();
                System.out.println("Клиент подключился ");
                LOG.info("Клиент подключился");
                new ClientHandler(this, socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
            LOG.error("Ошибка подключения");
        } finally {
            SQL.disconnect();
            try {
                assert server != null;
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public AuthService getAuthService() {
        return authService;
    }

    public boolean isLoginAuthorized(String login) {
        for (ClientHandler c : clients) {
            if (c.getLogin().equals(login)) {
                return true;
            }
        }
        return false;
    }

    public void subscribe(ClientHandler clientHandler) {
        clients.add(clientHandler);
        broadcastClientList();
    }

    public void broadcastClientList() {
        StringBuilder sb = new StringBuilder("/clientlist ");

        for (ClientHandler c : clients) {
            sb.append(c.getNick()).append(" ");
        }
        String msg = sb.toString();

        for (ClientHandler c : clients) {
            c.sendMsg(msg);
        }
    }

    public void broadcastMsg(String nick, String msg) {
        for (ClientHandler c : clients) {
            c.sendMsg(nick + ": " + msg);
        }
        SQL.addToHistory(nick, "to All", msg);
    }

    public void systemMsg(String msg) {
        for (ClientHandler c : clients) {
            c.sendMsg("System: " + msg);
        }
    }

    public void privateMsg(ClientHandler sender, String receiver, String msg) {
        String message = String.format("[ %s ] private [ %s ] : %s", sender.getNick(), receiver, msg);

        for (ClientHandler c : clients) {
            if (c.getNick().equals(receiver)) {
                c.sendMsg(message);
                SQL.addToHistory(sender.getNick(),receiver,msg);
                if (!sender.getNick().equals(receiver)) {
                    sender.sendMsg(message);
                }
                return;
            }
        }

        sender.sendMsg("not found user: " + receiver);
    }

    public void unsubscribe(ClientHandler clientHandler) {
        clients.remove(clientHandler);
        broadcastClientList();
    }

}
