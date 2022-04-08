import org.williams.st.FEPoint;
import org.williams.st.FlatEarth;
import org.williams.st.Utils;

public class AvForTest {

	public static void main(String[] args) {
		FEPoint originFE = new FEPoint(Utils.toRad(51.38287), Utils.toRad(1.33574));
		
		FlatEarth fe = new FlatEarth(originFE);
		
		FEPoint testFE = new FEPoint(Utils.toRad(51.753), Utils.toRad(1.746));
		
        double distFE = fe.distance(testFE);
        double brngFE = Utils.toDeg(fe.bearing(testFE));
        
        System.out.println("distFE (m) = " + distFE);
        System.out.println("brngFE (deg) = " + brngFE);
	}

}
