import com.cyr1en.cgdl.util.SplineInterpolator;

public class Test {
    public static void main(String[] args) {
        SplineInterpolator si = new SplineInterpolator(2, 0, 0, 2);
        for (double t = 0; t <= 1; t += 0.1) {
            System.out.println(si.interpolate(t));

        }
    }
}
