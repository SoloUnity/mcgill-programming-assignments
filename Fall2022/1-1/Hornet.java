public class Hornet extends Insect {

    private int dmg;

    public Hornet(Tile tile, int hp, int dmg) {
        super(tile, hp);
        this.dmg = dmg;
    }

    public boolean takeAction() {

        System.out.println("HornetTakeAction");

        if (this.tile != null) {
            HoneyBee bee = this.tile.getBee();

            if (bee != null) {
                System.out.println("not null");
                bee.takeDamage(dmg);
                return true;
            }
            else if (bee == null) {

                System.out.println("null");

                if (!this.tile.isHive()) {

                    Tile nextTile = this.tile.towardTheHive();

                    while ((this.tile != null) && (this.tile.getNumOfHornets() != 0)) {

                        System.out.println("Moving");

                        Hornet hornet = this.tile.getHornet();
                        nextTile.addInsect(hornet);
                        this.tile.removeInsect(hornet);

                    }
                    

                    setPosition(nextTile);
                    return true;

                }

            }
        }


        return false;
    }

    public boolean equals(Object  obj) {
        return super.equals(obj);
    }

}