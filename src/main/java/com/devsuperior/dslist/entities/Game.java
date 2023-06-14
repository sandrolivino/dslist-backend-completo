package com.devsuperior.dslist.entities;

import java.util.Objects;
import java.util.UUID;

import com.devsuperior.dslist.enums.Genre;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_game")
public class Game {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	private String title;
	
	@Column(name = "game_year")
	private Integer year;
	private Integer genre;
	private String platforms;
	private Double score;
	private String imgUrl;
	
	@Column(columnDefinition = "TEXT")
	private String shortDescription;

	@Column(columnDefinition = "TEXT")
	private String longDescription;
	@Column(name="inventory_status")
	private String inventoryStatus;
	private Integer price;
	
    public Game() {
    }
    
	public Game(UUID id, String title, Integer year, Genre genre, String platforms, Double score, String imgUrl,
			String shortDescription, String longDescription, Integer price, String inventoryStatus) {
		this.id = id;
		this.title = title;
		this.year = year;
		setGenre(genre);
		this.platforms = platforms;
		this.score = score;
		this.imgUrl = imgUrl;
		this.shortDescription = shortDescription;
		this.longDescription = longDescription;
		this.price = price;
		this.inventoryStatus = inventoryStatus;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Genre getGenre() {
		return Genre.valueOf(genre);
	}

	public void setGenre(Genre genre) {
		if (genre != null){
			this.genre = genre.getCode();
		}
	}

	public String getPlatforms() {
		return platforms;
	}

	public void setPlatforms(String platforms) {
		this.platforms = platforms;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getLongDescription() {
		return longDescription;
	}

	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getInventoryStatus() {
		return inventoryStatus;
	}

	public void setInventoryStatus(String inventoryStatus) {
		this.inventoryStatus = inventoryStatus;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Game other = (Game) obj;
		return Objects.equals(id, other.id);
	}
}
