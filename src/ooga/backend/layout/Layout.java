package ooga.backend.layout;

public class Layout {

  int width;
  int height;

  public Layout(String[][] layoutConfig){
    this.width = layoutConfig[0].length;
    this.height = layoutConfig.length;
  }


}
