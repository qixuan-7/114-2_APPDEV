class MarineLife {
    protected String name;
    protected String habitat;

    public MarineLife(String name, String habitat) {
        this.name = name;
        this.habitat = habitat;
    }

    public String info() {
        return name + "，棲息地：" + habitat;
    }

    public String move() {
        return name + " 在水中移動";
    }
}

class Fish extends MarineLife {
    private String scaleColor;

    public Fish(String name, String habitat, String scaleColor) {
        super(name, habitat);
        this.scaleColor = scaleColor;
    }

    @Override
    public String move() {
        return name + " 擺動魚鰭游泳";
    }

    public String fishInfo() {
        return info() + " 鱗片：" + scaleColor;
    }
}

class Whale extends MarineLife {
    private double length;

    public Whale(String name, String habitat, double length) {
        super(name, habitat);
        this.length = length;
    }

    @Override
    public String move() {
        return name + " 擺動尾鰭前進";
    }

    public String whaleInfo() {
        return info() + " 體長：" + length + " 公尺";
    }
}

public class MarineLifeTest {
    public static void main(String[] args) {
        Fish fish = new Fish("小丑魚", "珊瑚礁", "橘白相間");
        Whale whale = new Whale("藍鯨", "太平洋", 30.0);

        System.out.println(fish.fishInfo());
        System.out.println(fish.move());

        System.out.println(whale.whaleInfo());
        System.out.println(whale.move());
    }
}
