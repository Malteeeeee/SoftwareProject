package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.world.GameMap;

public class Player extends Entity {
	
	private static final int SPEED = 80;
	private static final int JUMP_VELOCITY = 6;
	private boolean UP = false;
	
	Texture image;
	
	public Player(float x, float y, GameMap map) {
		super(x, y, EntityType.PLAYER, map);
		image = new Texture("player.png");
	}
	
	/*@Override
	public void create (EntitySnapshot snapshot, EntityType type, GameMap map) {
		super.create(snapshot, type, map);
		image = new Texture("player.png");
		//otherData = snapshot.getFloat("otherData", defaultValue) 
	}*/
	
	@Override
	public void update(float deltaTime, float gravity) {
		if (Gdx.input.isKeyPressed(Keys.SPACE) && grounded)
			this.velocityY += JUMP_VELOCITY * getWeight();
		else if (Gdx.input.isKeyPressed(Keys.SPACE) && !grounded &&this.velocityY > 0)
			this.velocityY += JUMP_VELOCITY * getWeight() * deltaTime;
		
		super.update(deltaTime, gravity);
		
		if (Gdx.input.isKeyPressed(Keys.A)) {
			moveCamX(-SPEED * deltaTime);
			moveX(-SPEED * deltaTime);
		}
			
		
		if (Gdx.input.isKeyPressed(Keys.D)) {
			moveCamX(SPEED * deltaTime);
			moveX(SPEED * deltaTime);
		}
		moveCamY(pos.y);
			
	}	
	
	public void moveCamX(float amount) {
		if (Gdx.input.isKeyPressed(Keys.D)||Gdx.input.isKeyPressed(Keys.A)) {
		Vector2 translation = new Vector2(getDeltaX(amount), 0f);
		MyGdxGame.cam.translate(translation);
		MyGdxGame.cam.update();	
		System.out.println(amount);
		}
	}

	public float getDeltaX(float amount) {
		if (Gdx.input.isKeyPressed(Keys.D)||Gdx.input.isKeyPressed(Keys.A)) {
		float newX = pos.x + amount;
		if (!map.doesRectCollideWithMap(newX, pos.y, getWidth(), getHeight()))
			return amount;
		}
		return 0f;
	}
	
	public void moveCamY(float y) {
		if(pos.y>MyGdxGame.gameMap.getPixelHeight()/2&&UP==false) {
		Vector2 translation = new Vector2(0f, MyGdxGame.gameMap.getPixelHeight()-Gdx.graphics.getHeight());
		UP=true;
		MyGdxGame.cam.translate(translation);
		}
		else if(pos.y<=MyGdxGame.gameMap.getPixelHeight()/2&&UP==true) {
		Vector2 translation = new Vector2(0f, -(MyGdxGame.gameMap.getPixelHeight()-Gdx.graphics.getHeight()));
		MyGdxGame.cam.translate(translation);
		UP=false;
		}
		MyGdxGame.cam.update();	
		System.out.println(y);
	}
	
	public float getY(float y) {
		if (y>MyGdxGame.gameMap.getPixelHeight()/2)
			return MyGdxGame.gameMap.getPixelHeight()/2;
			return -MyGdxGame.gameMap.getPixelHeight()/2;
	}
		
	@Override
	public void render(SpriteBatch batch) {
		batch.draw(image, pos.x, pos.y, getWidth(), getHeight());

	}
	

	
	/*public EntitySnapshot getSaveSnapshot() {
		EntitySnapshot snapshot = super.getSaveSnapshot();
		//snapshot.putFloat(otherData, defaultValue);
		return snapshot; 
	} */

}
