package server;

import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;

public class SimpleAuthService implements AuthService {

    private static Connection connection;
    private static Statement statement;
    private static PreparedStatement prStGetNick;
    private static PreparedStatement prStFindUser;
    private static PreparedStatement prStRegUser;


//    private class UserData{
//        String login;
//        String password;
//        String nickname;
//
//        public UserData(String login, String password, String nickname) {
//            this.login = login;
//            this.password = password;
//            this.nickname = nickname;
//        }
//    }

//    private List<UserData> users;

//    public SimpleAuthService() {
//        this.users = new ArrayList<>();
//
//        for (int i = 1; i <= 10 ; i++) {
//            users.add(new UserData("login"+i, "pass"+i, "nick"+i));
//        }
//
//        for (int i = 1; i <= 3 ; i++) {
//            users.add(new UserData(""+i, ""+i, "simple_nick"+i));
//        }
//    }

//    @Override
//    public String getNicknameByLoginAndPassword(String login, String password) {
//        for (UserData o:users ) {
//            if(o.login.equals(login) && o.password.equals(password)){
//                return o.nickname;
//            }
//        }
//
//        return null;
//    }
//
//    @Override
//    public boolean registration(String login, String password, String nickname) {
//        for (UserData o:users ) {
//            if(o.login.equals(login)) {
//                return false;
//            }
//        }
//        users.add(new UserData(login, password, nickname));
//        return true;
//    }


    public static void connect() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:chat.db");
        statement = connection.createStatement();
    }

    public static void disconnect() {
        try {
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String getNicknameByLoginAndPassword(String login, String password) {
        try {
            connect();
            prStGetNick = connection.prepareStatement("SELECT * FROM users WHERE login = ? AND password = ?");
            prStGetNick.setString(1, login);
            prStGetNick.setString(2, password);
            ResultSet rs = prStGetNick.executeQuery();
            return rs.getString("nick");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
        return null;
    }

    @Override
    public boolean registration(String login, String password, String nickname) {
        try {
            connect();
            prStFindUser = connection.prepareStatement("SELECT * FROM users WHERE login = ?");
            prStFindUser.setString(1, login);
            ResultSet rs = prStFindUser.executeQuery();
            if (rs.getString("login") != null) {
                return false;
            } else {
                prStRegUser = connection.prepareStatement("INSERT INTO users (login, password, nick) VALUES (?,?,?)");
                prStRegUser.setString(1, login);
                prStRegUser.setString(2, password);
                prStRegUser.setString(3, nickname);
                prStRegUser.executeUpdate();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
        return true;
    }

}
