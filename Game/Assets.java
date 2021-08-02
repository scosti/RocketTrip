package My.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 *  <p>
 *  In this class we create a new asset manager, then we assign to N strings the N names of file we need to load for the
 *  game (e.g. images,skins,sounds).
 *  </p>
 *  @author Benedetti Costi
 */

public class Assets {
    public static final AssetManager manager = new AssetManager();

    public static String buttons = "skin/buttons.pack";
    public static String  skins = "skin/skins.json";
    public static String  pack_1 = "pack1/pack_1.pack";
    public static String  skins_pack1 = "pack1/skins.json";
    public static String  nuvola1 = "img/sfondo/nuvola3.png";
    public static String  nuvola3 = "img/sfondo/nuvola4.png";
    public static String  LibyanSx = "img/LibyanSx.png";
    public static String  LibyanDx = "img/LibyanDx.png";
    public static String  aereoDx = "img/aereo1.png";
    public static String  aereoSX = "img/aereo2.png";
    public static String  ground2 = "img/ground2.png";
    public static String  rocket = "img/rocket.png";
    public static String  rocket_pack = "rocket/Rocket.pack";
    public static String  turbo = "turbo/Turbo.pack";
    public static String turbo_sound ="sound/rocket_turbo.mp3";
    public static String explosion_sound="sound/explosion.mp3";
    public static String alien_pack = "alien/alien.pack";
    public static String asteroid = "img/asteroid.png";
    public static String uiskin = "uiskin/uiskin.json";
    public static String uiskinatlas = "uiskin/uiskin.atlas";
    public static String touch = "img/Hand-Touch-2-icon.png";

    public static Texture  background;
    public static Music backgroundmusic;

    /**
     *  <p>
     *  This method loads the resources using {@link AssetManager#load(String, Class)} and {@link AssetManager#load(String, Class, AssetLoaderParameters)}.
     *  </p>
     */

    public static void load(){
        manager.load(buttons,TextureAtlas.class);
        manager.load(skins, Skin.class, new SkinLoader.SkinParameter(buttons));
        manager.load(pack_1,TextureAtlas.class);
        manager.load(skins_pack1,Skin.class, new SkinLoader.SkinParameter(pack_1));
        manager.load(nuvola1,Texture.class);
        manager.load(nuvola3,Texture.class);
        manager.load(ground2,Texture.class);
        manager.load(LibyanSx,Texture.class);
        manager.load(LibyanDx,Texture.class);
        manager.load(aereoDx,Texture.class);
        manager.load(aereoSX,Texture.class);
        manager.load(rocket,Texture.class);
        manager.load(asteroid,Texture.class);
        manager.load(rocket_pack,TextureAtlas.class);
        manager.load(turbo,TextureAtlas.class);
        manager.load(turbo_sound,Sound.class);
        manager.load(explosion_sound,Sound.class);
        manager.load(alien_pack,TextureAtlas.class);
        manager.load(uiskin,Skin.class,new SkinLoader.SkinParameter(uiskinatlas));
        manager.load(touch,Texture.class);

        backgroundmusic = Gdx.audio.newMusic(Gdx.files.internal("sound/Music_loop.wav"));
        background = new Texture(Gdx.files.internal("img/background.png"));

    }

    public static Music getBackgroundmusic(){
        return backgroundmusic;
    }


    public static void dispose(){
        manager.dispose();
        backgroundmusic.dispose();
    }
}
