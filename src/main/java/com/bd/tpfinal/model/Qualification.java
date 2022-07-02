package com.bd.tpfinal.model;

import org.springframework.data.mongodb.core.mapping.Field;

public class Qualification {
	
	@Field
	private Integer score;
	@Field
	private String commentary;
        
    public Qualification() { /* empty for framework */ }    

    public Qualification( Integer score, String commentary) {
    	this.score = score;
		this.commentary = commentary;
	}

	public int getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getCommentary() {
        return commentary;
    }

    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }

}
