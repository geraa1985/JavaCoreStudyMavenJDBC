package server;


public class DBAuthService implements AuthService {


    @Override
    public String getNicknameByLoginAndPassword(String login, String password) {
        return SQL.getNick(login, password);
    }

    @Override
    public boolean registration(String login, String password, String nickname) {
        return SQL.newReg(login, password, nickname);
    }

    @Override
    public boolean setNick(String newNick, String oldNick) {
        return SQL.setNick(newNick, oldNick);
    }
}
