package ooga.backend.layout;

import java.util.ArrayList;
import java.util.List;

public class Layout {

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

  public LayoutBlock getBlock(int row, int col){
    return layoutConfig.get(row).get(col);
  }

}
