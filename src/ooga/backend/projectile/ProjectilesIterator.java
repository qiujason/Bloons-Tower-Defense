package ooga.backend.projectile;

import java.util.ArrayList;
import java.util.List;
import ooga.backend.API.GamePiece;
import ooga.backend.collections.Iterator;

public class ProjectilesIterator implements Iterator {

  private int index;
  private final List<Projectile> projectiles;

  public ProjectilesIterator(List<Projectile> projectiles){
    index = 0;
    this.projectiles = projectiles;
  }

  @Override
  public GamePiece getNext() {
    if (hasMore()) {
      GamePiece gamePiece = projectiles.get(index);
      index++;
      return gamePiece;
    }
    return null;
  }

  @Override
  public boolean hasMore() {
    return index < projectiles.size();
  }

  @Override
  public void reset() {
    index = 0;
  }
}
