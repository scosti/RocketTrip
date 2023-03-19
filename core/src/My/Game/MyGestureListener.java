package My.Game;

import My.GameObjects.Rocket;
import My.Screens.PlayScreen;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;

/**
 *  <p>
 *  Register an instance of this class with a {@link GestureDetector} to receive gestures such as taps, long presses,
 *  flings,panning or pinch zooming. Each method returns a boolean indicating if the event should be handed to the next
 *  listener (false to hand it to the next listener, true otherwise).
 *  Note: For this game we only use taps.
 *  </p>
 *  @author Benedetti Costi
 */

public class MyGestureListener implements GestureDetector.GestureListener{

    private PlayScreen playScreen;
    private Rocket rocket;

    public MyGestureListener(PlayScreen playScreen, Rocket rocket) {
        this.playScreen = playScreen;
        this.rocket = rocket;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        if (playScreen.getInternalState()== PlayScreen.GameState.PLAY && rocket.getInternalState() == Rocket.State.START ) {
            rocket.setInternalState(Rocket.State.FLY);
        }
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) { return false; }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) { return false; }

    @Override
    public void pinchStop() {

    }

}
