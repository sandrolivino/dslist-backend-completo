package com.devsuperior.dslist.dto;

import com.devsuperior.dslist.entities.Game;
import com.devsuperior.dslist.projections.GameMinProjection;

import java.util.UUID;

public class GameMinDTO {

	private UUID id;
	private String title;
	private Integer year;
	private String imgUrl;
	private Double score;
	private String shortDescription;
	private Integer price;
	private Integer position;

	public GameMinDTO(Game entity) {
		this.id = entity.getId();
		this.title = entity.getTitle();
		this.year = entity.getYear();
		this.imgUrl = entity.getImgUrl();
		this.score = entity.getScore();
		this.shortDescription = entity.getShortDescription();
		this.price = entity.getPrice();
	}

	public GameMinDTO(GameMinProjection projection) {
		this.id = projection.getId();
		this.title = projection.getTitle();
		this.imgUrl = projection.getImgUrl();
		this.shortDescription = projection.getShortDescription();
		this.position = projection.getPosition();
		this.year = projection.getGameYear();
	}

	public UUID getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public Integer getYear() {
		return year;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public Double getScore() {
        return score;
    }
	public String getShortDescription() {
		return shortDescription;
	}
}
