package server;

import java.util.ArrayList;
import java.util.List;

public class ListAuthService implements AuthService {

    private static class UserData{
        String login;
        String password;
        String nickname;

        public UserData(String login, String password, String nickname) {
            this.login = login;
            this.password = password;
            this.nickname = nickname;
        }
    }

    private final List<UserData> users;

    public ListAuthService() {
        this.users = new ArrayList<>();

        for (int i = 1; i <= 10 ; i++) {
            users.add(new UserData("log" + i, "pas" + i, "nick_" + i));
        }
    }

    @Override
    public String getNicknameByLoginAndPassword(String login, String password) {
        for (UserData o:users ) {
            if(o.login.equals(login) && o.password.equals(password)){
                return o.nickname;
            }
        }
        return null;
    }

    @Override
    public boolean registration(String login, String password, String nickname) {
        for (UserData o:users ) {
            if(o.login.equals(login)) {
                return false;
            }
        }
        users.add(new UserData(login, password, nickname));
        return true;
    }

    @Override
    public boolean setNick(String newNick, String oldNick) {
        return false;
    }
}
