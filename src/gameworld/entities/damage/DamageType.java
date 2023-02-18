package gameworld.entities.damage;

public enum DamageType {

    FireDMG(0), PoisonDMG(1), DarkDMG(2), ArcaneDMG(3), TrueDMG(4), PhysicalDMG(5);

    private final int value;

    DamageType(int val) {
        value = val;
    }
}


