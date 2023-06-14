package com.devsuperior.dslist.projections;

import java.util.UUID;

public interface GameMinProjection {

	UUID getId();
	String getTitle();
	Integer getGameYear();
	String getImgUrl();
	String getShortDescription();
	Integer getPosition();
}
