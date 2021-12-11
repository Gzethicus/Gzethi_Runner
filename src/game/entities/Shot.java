package game.entities;

import game.entities.projectiles.Projectile;

public interface Shot {
    void onShot(Projectile projectile);
}
