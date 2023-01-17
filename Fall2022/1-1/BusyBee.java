public class BusyBee extends HoneyBee {

    public BusyBee(Tile tile) {
        super(tile, 5, 2);
        super.takeDamage();
    }

    public boolean takeAction() {
        if (this.tile != null) {
            this.tile.storeFood(2);
            return true;
        }
        return false;
    }
    
}