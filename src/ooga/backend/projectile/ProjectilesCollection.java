package ooga.backend.projectile;

import java.util.ArrayList;
import java.util.List;
import ooga.backend.GamePiece;
import ooga.backend.collections.GamePieceCollection;
import ooga.backend.collections.Iterator;

public class ProjectilesCollection implements GamePieceCollection {

  List<Projectile> projectiles;

  public ProjectilesCollection(){
    projectiles = new ArrayList<>();
  }

  @Override
  public boolean add(GamePiece gamePiece) {
    if (gamePiece instanceof Projectile) {
      projectiles.add((Projectile) gamePiece);
      return true;
    }
    return false;
  }

  @Override
  public boolean remove(GamePiece gamePiece) {
    if (gamePiece instanceof Projectile) {
      return projectiles.remove(gamePiece);
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
  public Iterator createIterator() {
    return new ProjectilesIterator(projectiles);
  }
}
