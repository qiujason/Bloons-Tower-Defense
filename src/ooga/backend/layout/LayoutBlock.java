package ooga.backend.layout;

import java.util.HashMap;
import java.util.Map;

public class LayoutBlock {

  private String blockType;
  private double dx;
  private double dy;
  private Map<String, double[]> velocityMap = new HashMap<>(){{put(">", new double[]{1,0});
                                                                put("<", new double[]{-1,0});
                                                                put("v", new double[]{0,1});
                                                                put("^", new double[]{0,-1});}};


  public LayoutBlock(String blockType){
    this.blockType = blockType;
    setDxDy(blockType);
  }

  private void setDxDy(String blockType) {
    if (!blockType.equals("0")){
      this.dx = velocityMap.get(blockType)[0];
      this.dy = velocityMap.get(blockType)[1];
    }
  }

  public String toString(){
    return "dx: " + dx + " dy: " + dy;
  }


  public double getDx() {
    return dx;
  }

  public double getDy() {
    return dy;
  }
}
