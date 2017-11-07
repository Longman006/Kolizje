package trash;

/**
 * Created by longman on 07.05.17.
 */
public class IDGenerator {
    private static long currentID=0;
    public static long getID(){
        currentID++;
        return currentID;
    }
}
