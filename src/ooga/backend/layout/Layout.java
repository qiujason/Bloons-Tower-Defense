package ooga.backend.layout;

import java.util.ArrayList;
import java.util.List;
import ooga.backend.API.LayoutAPI;

public class Layout implements LayoutAPI {

  int width;
  int height;
  List<List<LayoutBlock>> layoutConfig;

  public Layout(List<List<String>> layoutConfig){
    this.width = layoutConfig.get(0).size();
    this.height = layoutConfig.size();
    this.layoutConfig = createLayoutConfig(layoutConfig);
  }

  private List<List<LayoutBlock>> createLayoutConfig(List<List<String>> layoutConfig) {
    List<List<LayoutBlock>> layoutBlocks = new ArrayList<>();
    for (List<String> row : layoutConfig) {
      List<LayoutBlock> blockRow = new ArrayList<>();
      for (String blockType : row) {
        blockRow.add(new LayoutBlock(blockType));
      }
      layoutBlocks.add(blockRow);
    }
    return layoutBlocks;
  }

  @Override
  public LayoutBlock getBlock(int row, int col){
    return layoutConfig.get(row).get(col);
  }

  @Override
  public double getBlockDx(int row, int col) {
    return getBlock(row, col).getDx();
  }

  @Override
  public double getBlockDy(int row, int col) {
    return getBlock(row, col).getDy();
  }

}
