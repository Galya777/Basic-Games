package Server.addIns;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserInformation implements Serializable {
    private String username;
    private Map<Level, Integer> difficultyLevelsPlayed;
    private long timePlayed;
    private Map<Result, Integer> results;

    public UserInformation(String username, Map<Level, Integer> difficultyLevelsPlayed, long timePlayed, Map<Result, Integer> results) {
        setUsername(username);
        setDifficultyLevelsPlayed(difficultyLevelsPlayed);
        setTimePlayed(timePlayed);
        setResults(results);
    }

    public UserInformation() {
        this("",null,0,null);
    }
    public UserInformation(String username) {
        this(username,null,0,null);
    }
    public UserInformation(UserInformation another) {
        this(another.getUsername(),another.getDifficultyLevelsPlayed(), another.getTimePlayed(), another.getResults());
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if(username!=null)
        this.username = username;
        else this.username="";
    }

    public Map<Level, Integer> getDifficultyLevelsPlayed() {
        return difficultyLevelsPlayed;
    }

    public void setDifficultyLevelsPlayed(Map<Level, Integer> difficultyLevelsPlayed) {
        this.difficultyLevelsPlayed = Objects.requireNonNullElseGet(difficultyLevelsPlayed, HashMap::new);
    }

    public long getTimePlayed() {
        return timePlayed;
    }

    public void setTimePlayed(long timePlayed) {
        if (timePlayed > 0) this.timePlayed = timePlayed;
        else this.timePlayed = 0;
    }

    public Map<Result, Integer> getResults() {
        return results;
    }

    public void setResults(Map<Result, Integer> results) {
        this.results = Objects.requireNonNullElseGet(results, HashMap::new);
    }

    @Override
    public String toString() {
        return String.format("%-10s%n%s%n%s%n%s%n",
                username, this.printLevels(), this.printResults(), this.printTotalTime());
    }

    private Object printTotalTime() {
        long totalSeconds = timePlayed / 1000;
        long hours = (totalSeconds / 3600);
        long minutes = (totalSeconds / 60) % 60;
        long seconds = totalSeconds % 60;

        return String.format("Total time played: %02d:%02d:%02d", hours, minutes, seconds);
    }

    private Object printResults() {
        String print = "Results\n";
        return print.concat(difficultyLevelsPlayed.entrySet().stream()
                .map(e -> " " + e.getKey() + ": " + e.getValue() + " game(s)")
                .collect(Collectors.joining("\n")));
    }

    private Object printLevels() {
        String print = "Levels\n";
        return print.concat(difficultyLevelsPlayed.entrySet().stream()
                .map(e -> " " + e.getKey() + ": " + e.getValue() + " game(s)")
                .collect(Collectors.joining("\n")));
    }

    public void addTime(long time){
        this.timePlayed+=time;
    }

    public void addResult(Result result){
        if(results.containsKey(result))
            results.put(result, results.get(result) + 1);
        else
            results.put(result, 1);
    }

    public void addLevel(Level level){
        if(difficultyLevelsPlayed.containsKey(level))
            difficultyLevelsPlayed.put(level, difficultyLevelsPlayed.get(level) + 1);
        else
            difficultyLevelsPlayed.put(level, 1);
    }
}
