import java.util.ArrayList;

class Floor {
    private ArrayList<Integer> passanger;
    private int numberFloor;

    Floor(int numberFloor, ArrayList passenger) {
        this.numberFloor = numberFloor;
        this.passanger = passenger;
    }

    ArrayList<Integer> getPassanger() {
        return passanger;
    }

    int getNumberFloor() {
        return numberFloor;
    }
}
