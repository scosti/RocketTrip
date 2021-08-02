package My.Screens;

import My.Database.Score;
import My.Game.RocketTrip;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class ScrollPanel {
    private RocketTrip game;
    private Stage stage;
    private Skin skin;
    private ScoreDialog scoreDialog;
    private ScrollPane scroller;
    private Table scrollTable;

    public ScrollPanel(RocketTrip game,Stage stage, Skin skin) {
        this.stage = stage;
        this.skin = skin;
        this.game = game;
        scoreDialog = null;
        scrollTable = new Table();

        if (game.db != null) { //if is on android
            int rancking = 0;
            ArrayList<Score> scoreList = (ArrayList<Score>) game.db.getAllScore();
            Collections.sort(scoreList, new Comparator<Score>() {
                @Override
                public int compare(Score o1, Score o2) {
                    return o1.compareTo(o2);
                }
            });
            for (Score score : scoreList){
                rancking++;
                final Label text = new Label(rancking + score.toString(), skin);
                text.setAlignment(Align.left);
                text.setWrap(true);
                scrollTable.add(text);
                scrollTable.row();
            }

            if(!game.getGsm().isSaved()){
                int meters = game.getGsm().getMeters();
                scoreDialog = new ScoreDialog(this,game.db,meters);
                game.getGsm().setSaved(true);
            }
        }
        scrollTable.padLeft(-RocketTrip.WIDTH/2);
        scroller = new ScrollPane(scrollTable);

        final Table table = new Table();
        table.setFillParent(true);
        table.add(scroller).fill().expand();
        table.center();
        table.padTop(stage.getHeight()/7);
        table.padBottom(stage.getHeight()/6);
        this.stage.addActor(table);
    }

    public void update(){
        scrollTable.clear();
        int rancking = 0;
        ArrayList<Score> scoreList = (ArrayList<Score>) game.db.getAllScore();
        Collections.sort(scoreList, new Comparator<Score>() {
            @Override
            public int compare(Score o1, Score o2) {
                return o1.compareTo(o2);
            }
        });
        for (Score score : scoreList){
            rancking++;
            final Label text = new Label(rancking + score.toString(), skin);
            text.setAlignment(Align.left);
            text.setWrap(true);
            scrollTable.add(text);
            scrollTable.row();
        }
        scrollTable.padLeft(-RocketTrip.WIDTH/2);
        scroller.updateVisualScroll();
    }
}
