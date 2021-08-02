package My.Database;

/**
 * Created by Stefano on 12/11/2016.
 *
 *
 *
 */

public class Score {
    //private variables
    private int _id;
    private String _name;
    private int _meters;

    // Empty constructor
    public Score(){}

    // constructor
    public Score(int id, String name, int _meters){
        this._id = id;
        this._name = name;
        this._meters = _meters;
    }

    // constructor
    public Score(String name, int _meters){
        this._name = name;
        this._meters = _meters;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public int get_meters() {
        return _meters;
    }

    public void set_meters(int _meters) {
        this._meters = _meters;
    }


    public int compareTo(Score compscore) {
        int comparemeters=((Score)compscore).get_meters();
        /* For Descending order*/
        return comparemeters-this._meters;
    }

    @Override
    public String toString() {
        return  "" + _name + "  " +_meters;
    }
}