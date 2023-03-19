package My.Database;

import java.util.List;


/**
     CRUD(Create, Read, Update, Delete)
 */

public interface Database {
    void addScore(Score score);
    Score getScore(int id);
    List<Score> getAllScore();
    int updateScore(Score score);
    void deleteScore(Score score);
    int getScoreCount();
}
