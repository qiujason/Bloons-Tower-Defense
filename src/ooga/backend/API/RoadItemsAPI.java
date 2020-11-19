package ooga.backend.API;

import ooga.backend.roaditems.RoadItemType;

public interface RoadItemsAPI {

  boolean shouldRemove();

  RoadItemType getType();
}
