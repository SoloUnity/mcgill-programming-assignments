public class TankyBee extends HoneyBee{

    private int attackDmg;
    private int armor;

    public TankyBee(Tile tile, int attackDmg, int armor) {
        super(tile, 30, 3);
        this.attackDmg = attackDmg;
        this.armor = armor;
    }

    public boolean takeAction() {

        Tile thisTile = this.tile;

        if ((thisTile != null) && ((thisTile.isOnThePath()) || (thisTile.isHive()))) {

            Hornet hornet = thisTile.getHornet();

            if (!(hornet == null)) {

                hornet.takeDamage(this.attackDmg);
                return true;

            }

        }

        return false;

    }

    public void takeDamage(int dmg) {
        int totalDmg = (dmg * 100/(100 + this.armor));

        super.takeDamage(totalDmg);
    }

    public boolean equals(Object obj) {
        return super.equals(obj);
    }

}
