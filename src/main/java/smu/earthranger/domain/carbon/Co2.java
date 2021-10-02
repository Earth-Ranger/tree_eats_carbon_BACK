package smu.earthranger.domain.carbon;

public enum Co2 {

    carCo2(145),subwayCo2(60),busCo2(58),treeNum(7.16),treeCo2(2.43);

    private final double emission;

    Co2(double emission) {
        this.emission = emission;
    }

    public double getEmission(){
        return emission;
    }
}
