package com.toolive.model;

public class Rewards 
{
	private int venue_id;
	private String image_path;
	private double points;
	private String name;
	
	public int getVenue_id() {
		return venue_id;
	}
	public void setVenue_id(int venue_id) {
		this.venue_id = venue_id;
	}
	public String getImage_path() {
		return image_path;
	}
	public void setImage_path(String image_path) {
		this.image_path = image_path;
	}
	public double getPoints() {
		return points;
	}
	public void setPoints(double points) {
		this.points = points;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public static Rewards parseJSON(String content)
	{
		Rewards model = new Rewards();
		
		return model;
	}
}
