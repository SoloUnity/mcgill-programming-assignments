public class AngryBee extends HoneyBee {

    private int attackDmg;

    public AngryBee(Tile tile, int attackDmg) {
        super(tile, 10, 1);
        this.attackDmg = attackDmg;
    }

    public boolean takeAction() {

        Tile thisTile = this.tile;

        if ((this.tile != null) && ((thisTile.isOnThePath()) || (thisTile.isHive()))) {

            Tile tile = thisTile;

            while (!tile.isNest()) {

                Hornet firstHornet = tile.getHornet();

                if (firstHornet != null) {
                    firstHornet.takeDamage(this.attackDmg);
                    return true;
                }

                tile = tile.towardTheNest();

            }

        }

        return false;

    }

    public boolean equals(Object obj) {
        return super.equals(obj);
    }

}