import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

class TestLift {
    @Test
    void testGetMaxFloorForPassanger() {
        ArrayList<Integer> pass = new ArrayList<Integer>();
        for (int i = 0; i < 10; i++)
            pass.add(i);
        Lift.setPassangers(pass);
        int result = Lift.getMaxFloorForPassanger();
        Assertions.assertEquals(9, result);
    }

    @Test
    void testOutComePass() {
        Lift.initialization();
        ArrayList<Integer> pass = new ArrayList<Integer>();
        for (int i = 0; i < 10; i++)
            pass.add(i);
        Lift.setPassangers(pass);
        ArrayList<Integer> passOut = Lift.goOutOfTheLift();

        Assertions.assertEquals(1, passOut.size());
    }

    @Test
    void testCreateFloors() {
        Lift.initialization();
        Assertions.assertEquals(Lift.getCountFloor(), Lift.getFloors().size());
    }
}
