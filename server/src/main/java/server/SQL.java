package server;

import java.sql.*;

public class SQL {
    private static Connection connection;
    private static PreparedStatement psGetNick;
    private static PreparedStatement psNewReg;
    private static PreparedStatement psSetNick;
    private static PreparedStatement psSetHistory;
    private static PreparedStatement psGetHistory;

    public static boolean connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:chat.db");
            prepareAllStatements();
            System.out.println("Подключились к БД");
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void disconnect() {
        try {
            psGetNick.close();
            psNewReg.close();
            psSetNick.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void prepareAllStatements() {
        try {
            psGetNick = connection.prepareStatement("SELECT nick FROM users WHERE login = ? AND password = ?;");
            psNewReg = connection.prepareStatement("INSERT INTO users (login, password, nick) VALUES (?,?,?);");
            psSetNick = connection.prepareStatement("UPDATE users SET nick = ? WHERE nick = ?;");
            psSetHistory = connection.prepareStatement("INSERT INTO history (sender,receiver,message) " +
                    "VALUES ((SELECT id FROM users WHERE nick = ?), " +
                    "(SELECT id FROM users WHERE nick = ?), " +
                    "?)");
            psGetHistory = connection.prepareStatement("SELECT (SELECT nick FROM users WHERE id = sender), (SELECT nick FROM users WHERE id = receiver), message, date FROM history WHERE sender = (SELECT id FROM users WHERE nick = ?) OR receiver = (SELECT id FROM users WHERE nick = ?)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String getNick(String login, String password) {
        try {
            psGetNick.setString(1, login);
            psGetNick.setString(2, password);
            ResultSet rs = psGetNick.executeQuery();
            return rs.getString("nick");
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static boolean newReg(String login, String password, String nick) {
        try {
            psNewReg.setString(1, login);
            psNewReg.setString(2, password);
            psNewReg.setString(3, nick);
            psNewReg.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean setNick(String newNick, String oldNick) {
        try {
            psSetNick.setString(1, newNick);
            psSetNick.setString(2, oldNick);
            psSetNick.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void addToHistory(String sender, String receiver, String message) {
        try {
            psSetHistory.setString(1, sender);
            psSetHistory.setString(2, receiver);
            psSetHistory.setString(3, message);
            psSetHistory.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String getHistory(String nick) {
        StringBuilder sb = new StringBuilder();
        try {
            psGetHistory.setString(1, nick);
            psGetHistory.setString(2, nick);
            ResultSet rs = psGetHistory.executeQuery();
            while (rs.next()) {
                String sender = rs.getString(1);
                String receiver = rs.getString(2);
                String message = rs.getString(3);
                String date = rs.getString(4);
                sb.append(String.format("%s -> %s [%s]: %s \n", sender, receiver, date, message));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
