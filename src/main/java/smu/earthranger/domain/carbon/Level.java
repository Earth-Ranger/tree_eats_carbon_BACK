package smu.earthranger.domain.carbon;

public enum Level {

    Lv1(10000),Lv2(30000),Lv3(50000),Lv4(70000),Lv5(90000);

    private final double level;

    Level(double level) {
        this.level = level;
    }

    public double level(){
        return level;
    }
}
