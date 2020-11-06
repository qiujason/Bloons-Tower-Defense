package ooga.backend.layout;

import java.util.ArrayList;
import java.util.List;
import ooga.backend.API.LayoutAPI;

public class Layout implements LayoutAPI {

  private int width;
  private int height;
  private List<List<LayoutBlock>> layoutConfig;

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

  public int[] getStartBlockCoordinates(){
    for (int i = 0; i < height; i++){
      for (int j = 0; j < width; j++){
        if (layoutConfig.get(i).get(j).getBlockType().equals("*")){
          return new int[]{i,j};
        }
      }
    }
    return new int[2];
  }

}
