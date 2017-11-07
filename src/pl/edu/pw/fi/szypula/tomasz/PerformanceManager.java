package pl.edu.pw.fi.szypula.tomasz;

/**
 * Created by longman on 07.05.17.
 */
public class PerformanceManager {
    private static long start=0;
    private static long end=0;
    
    public static void startMeasurement(){
        start = System.nanoTime();
    }
    public static double endMeasurement(){
        return (double)(System.nanoTime()-start)/1000000;
    }
}
