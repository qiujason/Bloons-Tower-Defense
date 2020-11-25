/**
 * ProjectilesCollection to represent a collection of projectiles
 * @author Annshine
 */
package ooga.backend.projectile;

import java.util.ArrayList;
import java.util.List;
import ooga.backend.collections.GamePieceCollection;
import ooga.backend.collections.GamePieceIterator;

public class ProjectilesCollection implements GamePieceCollection<Projectile> {

  private List<Projectile> projectiles;

  /**
   * Constructor for ProjectilesCollection
   */
  public ProjectilesCollection() {
    projectiles = new ArrayList<>();
  }

  @Override
  public boolean add(Projectile projectile) {
    if (projectile != null) {
      projectiles.add(projectile);
      return true;
    }
    return false;
  }

  @Override
  public boolean remove(Projectile projectile) {
    if (projectile != null) {
      projectiles.remove(projectile);
    }
    return false;
  }

  @Override
  public void updateAll() {
    for (Projectile projectile : projectiles) {
      projectile.update();
    }
  }

  @Override
  public void clear() {
    projectiles = new ArrayList<>();
  }

  @Override
  public GamePieceIterator<Projectile> createIterator() {
    return new GamePieceIterator<>(projectiles);
  }

  @Override
  public int size() {
    return projectiles.size();
  }

  @Override
  public boolean contains(Projectile projectile) {
    return projectiles.contains(projectile);
  }

  @Override
  public boolean isEmpty() {
    return projectiles.isEmpty();
  }
}
