package My.Game;


import My.GameObjects.Rocket;
import My.Screens.PlayScreen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Disposable;

import static My.Screens.PlayScreen.GameState.*;

/**
 *  <p>
 *  This class is used to manage the sound in the game.
 *  The sounds are first loaded, then with the method update to which we pass the gameState and the stateRocket the
 *  sounds are properly managed at any frame (e.g. if the game is stopped the music stops, etc.).
 *  </p>
 *  @author Benedetti Costi
 */

public class MusicManager implements Disposable{
    private Music music;
    private Sound turbo_sound;
    private Sound explosion_sound;
    private boolean isCrashing;
    private boolean isTurbing;

    public MusicManager() {
        music = Assets.getBackgroundmusic();
        turbo_sound=Assets.manager.get(Assets.turbo_sound,Sound.class);
        explosion_sound=Assets.manager.get(Assets.explosion_sound,Sound.class);
        isCrashing = false;
        isTurbing = false;
    }

    public void update(PlayScreen.GameState gameState, Rocket.State stateRocket){
        //Background music
        if(gameState==PLAY  && !music.isPlaying()){
            music.play();
        }
        if(gameState == PAUSE){
            music.pause();
        }
        if (gameState == CRASH) {
            music.stop();
        }

        //Turbo sound
        if(stateRocket== Rocket.State.TURBO && !isTurbing){
            turbo_sound.play();
            isTurbing = true;
        }
        if(stateRocket != Rocket.State.TURBO){
            turbo_sound.stop();
            isTurbing = false;
        }

        //explosion sound
        if(gameState == CRASH && !isCrashing) {
            explosion_sound.play();
            isCrashing = true;
        }
        if(gameState != CRASH){
            explosion_sound.stop();
            isCrashing = false;
        }

    }

    @Override
    public void dispose() {
        music.dispose();
        explosion_sound.dispose();
        turbo_sound.dispose();
    }
}
