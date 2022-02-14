package client;

import helpers.GameLevels;
import helpers.Result;

public class User {

    private String username;
    private  GameLevels level;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setLevel(GameLevels level) {
        this.level = level;
    }

    public void setScore(Result score) {
        this.score = score;
    }

    private  Result score;

    public User(String username, GameLevels level, Result score) {
        this.username = username;
        this.level = level;
        this.score = score;
    }
    public String getUsername() {
        return this.username;
    }


    public GameLevels getLevel() {
        return level;
    }



    public Result getScore() {
        return score;
    }


    @Override
    public String toString() {
        return "username=\n'" + username + '\'' +
                ", level=\n" + level +
                ", score=\n" + score;
    }
}
