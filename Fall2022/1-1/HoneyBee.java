public abstract class HoneyBee extends Insect {

    private  int cost;

    public HoneyBee(Tile tile, int hp, int cost) {
        super(tile, hp);
        this.cost = cost;
    }

    public int getCost() {
        return this.cost;
    }

    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}