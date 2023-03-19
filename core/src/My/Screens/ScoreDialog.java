package My.Screens;

import My.Database.Score;
import My.Database.Database;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;


public class ScoreDialog {

    private String name;

    public ScoreDialog(final ScrollPanel scrollPanel, final Database db, final int meters) {

        Gdx.input.getTextInput(new Input.TextInputListener() {
            @Override
            public void input(String text) {
                if(text.length()>10){
                    text = text.substring(1,10);
                }
                else{
                    String blank =" ";
                    while(text.length() < 10){
                        text = text.concat(blank);
                    }
                }
                name = text;

                db.addScore(new Score(name,meters));

                scrollPanel.update();
            }

            @Override
            public void canceled() {
                db.addScore(new Score(" player",meters));
                if(db.getScoreCount() > 25){
                    db.deleteScore(db.getScore(25));
                }
                scrollPanel.update();
            }}, "insert name", " ", "name..-"
        );
    }

}
