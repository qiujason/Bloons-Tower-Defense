package ooga.backend.layout;

import java.util.List;
import ooga.backend.LayoutReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class LayoutTest {

  private Layout layout;

  @BeforeEach
  void setUpLayout(){
    LayoutReader reader = new LayoutReader();
    List<List<String>> layoutConfig = reader.getLayoutFromFile("test_layouts/updownleftright.csv");
    layout = new Layout(layoutConfig);
  }


  @Test
  void LeftBlockDxTest(){
    LayoutBlock leftBlock = layout.getBlock(0,0);
    assertEquals(leftBlock.getDx(), -1);
    assertEquals(leftBlock.getDy(), 0);
  }

  @Test
  void RightBlockDxTest(){
    LayoutBlock rightBlock = layout.getBlock(0,1);
    assertEquals(rightBlock.getDx(), 1);
    assertEquals(rightBlock.getDy(), 0);
  }

  @Test
  void UpBlockDxTest(){
    LayoutBlock upBlock = layout.getBlock(0,2);
    assertEquals(upBlock.getDx(), 0);
    assertEquals(upBlock.getDy(), -1);
  }

  @Test
  void DownBlockDxTest(){
    LayoutBlock downBlock = layout.getBlock(0,3);
    assertEquals(downBlock.getDx(), 0);
    assertEquals(downBlock.getDy(), 1);
  }
}
