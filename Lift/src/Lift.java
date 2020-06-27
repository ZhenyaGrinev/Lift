import java.util.ArrayList;
import java.util.Random;

public class Lift {
    private static final int maxPassengersOnFloors = 10;//максимально допустимое кол-во пассажиров на этаже при начальной генерации
    private static final int maxFloorInBuilding = 20;//максимально допустимое кол-во этажей  при начальной генерации
    private static final int minFloorInBuilding = 5;//минимально допустимое кол-во этажей  при начальной генерации
    private static final int maxSizeLift = 5;//максимально допустимое кол-во пассажиров  в лифте
    private static ArrayList<Floor> floors;//список этажей
    private static ArrayList<Integer> passangers;//список пассажиров в лифте
    private static int countFloor;//кол-во этажей в здании
    private static int currentFloor = 0;//текущий этаж где находится лифт
    private static boolean towardToTop = true;//определяем куда двигается лифт towardToTop = true означает вверх

    public static void main(String[] args) {
        initialization();
        printInit();
        while (true) {
            loop();
            try {
                Thread.sleep(1000);//итерация в 1 секунду
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    private static void loop() {
        ArrayList<Integer> newPass = goOutOfTheLift();//Пассажиры которые выходят(если пассажир вышел,ему назначают новый этаж но зайти когда только вышел он не может)
        if (passangers.size() != maxSizeLift) {
            if (passangers.size() != 0)
                enterTheLift();//запускаем пассажиров
            else {//если лифт пуст а на этаже есть желающие пассажиры
                if (!floors.get(currentFloor).getPassanger().isEmpty()) {
                    int choose = 0;
                    for (int i = 0; i < floors.get(currentFloor).getPassanger().size(); i++) {
                        int pass = floors.get(currentFloor).getPassanger().get(i);
                        choose = (pass > currentFloor) ? choose + 1 : choose - 1;//определяем в какую сторону ехать
                    }
                    towardToTop = choose > 0;
                    enterTheLift();//запускаем пассажиров
                }
            }
        }
        floors.get(currentFloor).getPassanger().addAll(newPass);
        int maxFloorForPassanger;
        if (!passangers.isEmpty()) {//пересчет этажа
            maxFloorForPassanger = getMaxFloorForPassanger();

        } else {///Если лифт пустой и на этаже никого нет
            print();
            if (towardToTop)
                currentFloor = (currentFloor + 1 != countFloor) ? currentFloor + 1 : currentFloor - 1;

            else
                currentFloor = (currentFloor != 0) ? currentFloor - 1 : currentFloor + 1;

            return;
        }
        print();
        if (towardToTop && (currentFloor < maxFloorForPassanger))
            currentFloor++;

        else if (currentFloor != 0)
            currentFloor--;
    }

    static int getMaxFloorForPassanger() {////////Пересчет макс этажа
        int maxFloorForPassanger;
        maxFloorForPassanger = passangers.get(0);
        for (int i = 1; i < passangers.size(); i++)
            if (maxFloorForPassanger < passangers.get(i)) maxFloorForPassanger = passangers.get(i);
        return maxFloorForPassanger;
    }

    static ArrayList<Integer> goOutOfTheLift() {//выход с лифта
        ArrayList<Integer> newPass = new ArrayList<>();//пассажиры которые вышли.Они добавляются в список этажей сразу чтобы не участвовать в процессе входа в лифт
        for (int i = 0; i < passangers.size(); i++) {
            if (passangers.get(i) == currentFloor) {
                int wantFloor = new Random().nextInt(countFloor - 1);
                if (currentFloor == wantFloor) {
                    i--;
                    continue;
                }
                newPass.add(wantFloor);
                passangers.remove(i);
                i--;
            }
        }
        return newPass;
    }

    private static void enterTheLift() {//вход в лифт
        for (int i = 0; i < floors.get(currentFloor).getPassanger().size(); i++) {
            int pass = floors.get(currentFloor).getPassanger().get(i);
            if ((pass > currentFloor && towardToTop) || (pass < currentFloor && !towardToTop)) {
                passangers.add(pass);
                floors.get(currentFloor).getPassanger().remove(i);
                i--;
            }
            if (passangers.size() == maxSizeLift) break;
        }
    }

    static void initialization() {
        passangers = new ArrayList<>(maxSizeLift);
        //count floor
        countFloor = new Random().nextInt(maxFloorInBuilding);
        if (countFloor < minFloorInBuilding) countFloor = minFloorInBuilding;
        //create floors
        floors = createFloors();
    }

    private static void printInit() {//печать начального вида размещения пассажиров
        for (Floor floor : floors) {
            StringBuilder pass = new StringBuilder();
            pass.append("Floor:").append(floor.getNumberFloor()).append("\n");
            for (int j = 0; j < floor.getPassanger().size(); j++) {
                pass.append(floor.getPassanger().get(j));
                if(j!=floor.getPassanger().size()-1) pass.append(",");
            }
            System.out.println(pass);
        }
    }

    private static ArrayList<Floor> createFloors() {//создание этажей и пассажиров
        ArrayList<Floor> floors2 = new ArrayList<>();
        for (int i = 0; i < countFloor; i++) {
            int countPassengersInFloor = new Random().nextInt(maxPassengersOnFloors);
            ArrayList<Integer> passangers2 = new ArrayList<>();
            for (int j = 0; j < countPassengersInFloor; j++) {
                //create passangers2
                int wantFloor = new Random().nextInt(countFloor - 1);
                if (i == wantFloor) {
                    j--;
                    continue;
                }
                passangers2.add(wantFloor);
            }
            floors2.add(new Floor(i, passangers2));
        }
        return floors2;
    }

    private static void print() {//печать изменений
        StringBuilder pass = new StringBuilder();
        pass.append("***Floor ").append(currentFloor).append("***").append("\n").append(passangers.size()).append("/").append(maxSizeLift).append("| ");
        for (int i = 0; i < maxSizeLift; i++) {
            if (passangers.size() > i) {
                pass.append(passangers.get(i));
                if (passangers.get(i) < 10)
                    pass.append(" ");
            } else
                pass.append("_ ");

            if (i != maxSizeLift - 1) pass.append(",");
        }
        pass.append(towardToTop ? " ^" : " v").append("| ");
        for (int i = 0; i < floors.get(currentFloor).getPassanger().size(); i++) {
            pass.append(floors.get(currentFloor).getPassanger().get(i));
            if (i != floors.get(currentFloor).getPassanger().size() - 1) pass.append(",");
        }
        System.out.println(pass);
    }

    static ArrayList<Floor> getFloors() {
        return floors;
    }

    static void setPassangers(ArrayList<Integer> passangers2) {
        passangers = passangers2;
    }

    static int getCountFloor() {
        return countFloor;
    }
}
