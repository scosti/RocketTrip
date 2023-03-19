package My.GameObjects;

/**
 *  <p>
 *  This class allow us to implement the factory design pattern.
 *  When we have to generate an object (e.g. {@link My.Generators.EnemyGenerator} ) we use the method createObject(String,float)
 *  that returns a GameObject of the type specified in the passed string.
 *  </p>
 *  @author Benedetti Costi
 */

public class ObjectFactory {

    public GameObject createObject(String item,float y){
        if (item.equals("Alien")){
            return new Alien(y);
        }
        if (item.equals("Asteroid")){
            return new Asteroid(y);
        }
        if (item.equals("Plane")){
            return new Plane(y);
        }
        if (item.equals("Tank")){
            return new Tank(y);
        }
        if (item.equals("Turbo")){
            return new Turbo(y);
        }
        return null;
    }
}
