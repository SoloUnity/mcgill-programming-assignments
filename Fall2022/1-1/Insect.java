public abstract class Insect {

    Tile tile;
    int hp;

    public Insect(Tile tile, int hp) {

        boolean addToTile = tile.addInsect(this);

        if (addToTile) {
            this.tile = tile;
            this.hp = hp;
        }
        else {
            throw new IllegalArgumentException("Already an insect here");
        }

    }

    public final Tile getPosition() {
        return this.tile;
    }

    public final int getHealth() {
        return this.hp;
    }

    public void setPosition(Tile tile) {
        this.tile = tile;
    }

    public void takeDamage(int dmg) {

        if (this.tile != null) {

            if ((this.tile.isHive()) && (this instanceof HoneyBee)) {

                this.hp -= ((dmg * 90) / 100);

            }
            else {
                this.hp -= dmg;
            }

            if (this.hp < 0) {

                this.tile.removeInsect(this);
                setPosition(null);

            }

        }



    }

    public abstract boolean takeAction();

    public boolean equals(Object obj) {

        if (obj instanceof Insect) {

            Insect objInsect = (Insect) obj;
            if ((objInsect.tile != null) && (objInsect.tile.equals(this.tile)) && (objInsect.hp == this.hp)) {
                return true;
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }



}
