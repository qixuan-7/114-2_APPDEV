/**
 * W05 課後作業：海洋生態系統模擬器
 * 整合：多載 (Overloading)、覆寫 (Overriding)、多型 (Polymorphism) 與 final 使用
 */

// 1. 父類別 Creature
class Creature {
    String name;
    String habitat;

    // 建構子
    public Creature(String name, String habitat) {
        this.name = name;
        this.habitat = habitat;
    }

    // 一般方法：移動
    public String move() {
        return name + " 正在水中移動";
    }

    // 一般方法：覓食
    public String eat() {
        return name + " 正在尋找食物";
    }

    // 方法：描述生物資訊
    public String describe() {
        return name + "（" + habitat + "）";
    }

    // final 方法：子類別不能覆寫
    public final String kingdom() {
        return "動物界";
    }

    // 3. 方法多載 feed() - 版本 1：無參數
    public String feed() {
        return name + " 正在覓食";
    }

    // 方法多載 feed() - 版本 2：指定食物
    public String feed(String food) {
        return name + " 正在吃 " + food;
    }

    // 方法多載 feed() - 版本 3：指定食物和數量
    public String feed(String food, int amount) {
        return name + " 吃了 " + amount + " 份 " + food;
    }
}

// 2. 子類別實作 (至少 4 個)

class Shark extends Creature {
    public Shark(String name, String habitat) {
        super(name, habitat);
    }

    @Override
    public String move() {
        return name + " 高速衝刺獵食";
    }

    @Override
    public String eat() {
        return name + " 兇猛地撕咬獵物";
    }
}

class Turtle extends Creature {
    public Turtle(String name, String habitat) {
        super(name, habitat);
    }

    @Override
    public String move() {
        return name + " 緩慢地划動四肢游動";
    }

    @Override
    public String eat() {
        return name + " 悠閒地啃食海草";
    }
}

class Dolphin extends Creature {
    public Dolphin(String name, String habitat) {
        super(name, habitat);
    }

    @Override
    public String move() {
        return name + " 輕盈地躍出水面";
    }

    @Override
    public String eat() {
        return name + " 正在合作圍捕魚群";
    }
}

class Octopus extends Creature {
    public Octopus(String name, String habitat) {
        super(name, habitat);
    }

    @Override
    public String move() {
        return name + " 利用噴射水流迅速前進";
    }

    @Override
    public String eat() {
        return name + " 用靈活的觸手捕捉獵物";
    }
}

// 5. 主程式類別
public class MarineEcosystem {
    public static void main(String[] args) {
        // 4. final 變數
        final int OCEAN_DEPTH = 11034;
        System.out.println("海洋最深處：" + OCEAN_DEPTH + " 公尺\n");

        // 5. 展示多型：使用 Creature 陣列放入不同子類別物件
        Creature[] ecosystem = {
            new Shark("大白鯊", "深海"),
            new Turtle("綠蠵龜", "珊瑚礁"),
            new Dolphin("瓶鼻海豚", "近海"),
            new Octopus("藍環章魚", "潮間帶")
        };

        // 使用 for-each 迴圈展示
        for (Creature c : ecosystem) {
            System.out.println(c.describe());
            System.out.println("  分類：" + c.kingdom()); // 呼叫 final 方法
            System.out.println("  移動：" + c.move());    // 呼叫覆寫的方法
            System.out.println("  覓食：" + c.eat());     // 呼叫覆寫的方法
            
            // 展示方法多載 (Overloading)
            System.out.println("  餵食測試 (無參數)：" + c.feed());
            System.out.println("  餵食測試 (加食物)：" + c.feed("小魚"));
            System.out.println("  餵食測試 (食物+數量)：" + c.feed("磷蝦", 5));
            System.out.println();
        }
    }
}
