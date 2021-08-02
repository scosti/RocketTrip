package My.Screens;

import My.Game.RocketTrip;

import java.util.HashMap;

public class GameScreenManager {

    private final RocketTrip game;
    private HashMap <STATE, AbstractScreen> gameScreens;

    private int meters;
    private boolean saved;

    public enum STATE {
        MENU,
        PLAY,
        HIGHSCORE
    }

    public GameScreenManager(final RocketTrip game){
        this.game = game;
        saved = true;
        initGameScreens();
        setScreen(STATE.MENU);
    }

    private void initGameScreens(){
        gameScreens = new HashMap<STATE, AbstractScreen>();
        gameScreens.put(STATE.MENU, new MenuScreen(game));
        gameScreens.put(STATE.PLAY, new PlayScreen(game));
        gameScreens.put(STATE.HIGHSCORE, new HighScoreScreen(game));
    }

    public void setScreen(STATE nextScreens){
        game.setScreen(gameScreens.get(nextScreens));
    }

    public void save(int meters){
        this.meters = meters;
        saved = false;
    }

    public void dispose(){
        for (AbstractScreen screen : gameScreens.values()){
            if(screen != null){
                screen.dispose();
            }
        }
    }

    public int getMeters() {
        return meters;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }

    public boolean isSaved() {
        return saved;
    }
}
