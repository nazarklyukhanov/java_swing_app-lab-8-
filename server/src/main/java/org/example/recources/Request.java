package org.example.recources;

import java.io.Serializable;

public class Request implements Serializable {
    private String message;
    private MusicBand musicBand;
    private String login;
    private String password;
    private static final long serialVersionUID = 5760575944040770153L;

    public Request(String message, MusicBand musicBand, String login, String password) {
        this.message = message;
        this.musicBand = musicBand;
        this.login = login;
        this.password = password;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MusicBand getMusicBand() {
        return musicBand;
    }

    public void setMusicBand(MusicBand musicBand) {
        this.musicBand = musicBand;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
