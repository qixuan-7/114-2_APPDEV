public class Main {

    public static void main(String[] args) {

        final int OCEAN_DEPTH = 11034;

        System.out.println("海洋最深處：" + OCEAN_DEPTH + " 公尺");

        Creature[] ecosystem = {
                new Shark("鯊魚", "海洋"),
                new Dolphin("海豚", "海洋"),
                new Octopus("章魚", "海底"),
                new Crab("螃蟹", "海岸")
        };

        for (Creature c : ecosystem) {
            System.out.println(c.describe());
            System.out.println("分類：" + c.kingdom());
            System.out.println("移動：" + c.move());
            System.out.println("覓食：" + c.eat());
            System.out.println("餵食：" + CreatureUtil.feed(c));
            System.out.println("餵食：" + CreatureUtil.feed(c, "小魚"));
            System.out.println("餵食：" + CreatureUtil.feed(c, "小魚", 3));
            System.out.println();
        }
    }
}

class Creature {

    protected String name;
    protected String habitat;

    public Creature(String name, String habitat) {
        this.name = name;
        this.habitat = habitat;
    }

    public String move() {
        return "一般移動";
    }

    public String eat() {
        return "一般覓食";
    }

    public String describe() {
        return name + " 棲息於 " + habitat;
    }

    public final String kingdom() {
        return "動物界";
    }
}

class Shark extends Creature {

    public Shark(String name, String habitat) {
        super(name, habitat);
    }

    @Override
    public String move() {
        return "高速衝刺";
    }

    @Override
    public String eat() {
        return "撕咬獵物";
    }
}

class Dolphin extends Creature {

    public Dolphin(String name, String habitat) {
        super(name, habitat);
    }

    @Override
    public String move() {
        return "躍出水面";
    }

    @Override
    public String eat() {
        return "合作追捕魚群";
    }
}

class Octopus extends Creature {

    public Octopus(String name, String habitat) {
        super(name, habitat);
    }

    @Override
    public String move() {
        return "噴射水流推進";
    }

    @Override
    public String eat() {
        return "利用觸手捕捉獵物";
    }
}

class Crab extends Creature {

    public Crab(String name, String habitat) {
        super(name, habitat);
    }

    @Override
    public String move() {
        return "橫向移動";
    }

    @Override
    public String eat() {
        return "用蟹夾取食物";
    }
}

class CreatureUtil {

    public static String feed(Creature c) {
        return c.name + " 正在覓食";
    }

    public static String feed(Creature c, String food) {
        return c.name + " 正在吃 " + food;
    }

    public static String feed(Creature c, String food, int amount) {
        return c.name + " 吃了 " + amount + " 份 " + food;
    }
}
