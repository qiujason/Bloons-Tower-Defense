package ooga.backend.API;

import ooga.backend.layout.LayoutBlock;

public interface LayoutAPI {

  LayoutBlock getBlock(int row, int col);

  double getBlockDx(int row, int col);

  double getBlockDy(int row, int col);


}
