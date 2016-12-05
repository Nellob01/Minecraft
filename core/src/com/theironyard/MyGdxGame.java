package com.theironyard;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	TextureRegion down, up, right, left, zDown, zUp, zRight, zLeft;
	boolean faceRight = true, zFaceRight = true;
	double d, f;
	int zDirection;

	Animation walk, walkUp, walkDown, zWalk, zWalkUp, zWalkDown ;
	float time;

	float x, y, xv, yv, xZ, yZ, xZv, yZv;
	static final float MAX_VELOCITY = 100, MAX_Z_VELOCITY = 100;
	static final float MAX_EXTRA_VELOCITY = 500;



	static final int WIDTH = 16, Z_WIDTH = 16;
	static final int HEIGHT = 16, Z_HEIGHT = 16;

	static final int DRAW_WIDTH = WIDTH * 2, DRAW_Z_WIDTH = Z_WIDTH * 2;
	static final int DRAW_HEIGHT = HEIGHT * 2, DRAW_Z_HEIGHT = Z_HEIGHT * 2;



	@Override
	public void create () {
		batch = new SpriteBatch();
		Texture tiles = new Texture("tiles.png");
		TextureRegion[][] grid = TextureRegion.split(tiles, WIDTH, HEIGHT);
		down = grid[6][0];
		up = grid[6][1];
		right = grid[6][2];
		left = new TextureRegion(right);
		left.flip(true, false);
		zDown = grid[6][4];
		zUp = grid[6][5];
		zRight = grid[6][6];
		zLeft = new TextureRegion(zRight);
		zLeft.flip(true, false);

		walk = new Animation (0.2f, grid[6][2], grid[6][3]);
		walkUp = new Animation (0.2f, grid[6][1], grid[7][1]);
		walkDown = new Animation (0.2f, grid[6][0], grid[7][0]);
		zWalk = new Animation (0.2f, grid[6][6], grid[6][7]);
		zWalkUp = new Animation(0.2f, grid[6][5]);
		zWalkDown = new Animation(0.2f, grid[6][4]);


		d = Math.random();
		d *= Gdx.graphics.getWidth();
		f = Math.random();
		f *= Gdx.graphics.getHeight();
		zDirection = (int)(Math.random() * 4);
		xZ = (float) d;
		yZ = (float) f;
	}
	public float decelerate(float velocity) {
		float deceleration = 0.95f;
		velocity *= deceleration;
		if(Math.abs(velocity) < 1) {
			velocity = 0;
		}
		return velocity;
	}
	public void move() {



		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			yv = MAX_VELOCITY * -1;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			xv = MAX_VELOCITY;
			faceRight = true;

		}
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			xv = MAX_VELOCITY * -1;
			faceRight = false;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
			yv = MAX_VELOCITY;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.UP) && (Gdx.input.isKeyPressed(Input.Keys.SPACE))) {
			yv = MAX_EXTRA_VELOCITY;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN) && (Gdx.input.isKeyPressed(Input.Keys.SPACE))) {
			yv = MAX_EXTRA_VELOCITY * -1;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && (Gdx.input.isKeyPressed(Input.Keys.SPACE))) {
			xv = MAX_EXTRA_VELOCITY;
			faceRight = true;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && (Gdx.input.isKeyPressed(Input.Keys.SPACE))) {
			xv = MAX_EXTRA_VELOCITY * -1;
			faceRight = false;
		}
		if (zDirection == 1) {

		yZv = MAX_Z_VELOCITY;
		}
		if (zDirection == 2) {
			xZv = MAX_Z_VELOCITY;
		}
		if ( zDirection == 3) {
			yZv = MAX_Z_VELOCITY * -1;
		}
		if (zDirection == 4) {
			xZv = MAX_Z_VELOCITY * -1;
		}

		y = y + (yv * Gdx.graphics.getDeltaTime());
		x = x + (xv * Gdx.graphics.getDeltaTime());
		yZ = yZ + (yZv * Gdx.graphics.getDeltaTime());
		xZ = xZ + (xZv * Gdx.graphics.getDeltaTime());

		if (x < 0) {
			x = Gdx.graphics.getWidth();
		}
		if (y < 0) {
			y = Gdx.graphics.getHeight();
		}
		if (y >Gdx.graphics.getHeight()) {
			y = 0;
		}
		if (x > Gdx.graphics.getWidth()) {
			x = 0;
		}
		if (xZ < 0) {
			xZ = Gdx.graphics.getWidth();
		}
		if (yZ < 0) {
			yZ = Gdx.graphics.getHeight();
		}
		if (xZ > Gdx.graphics.getWidth()) {
			xZ = 0;
		}
		if (yZ > Gdx.graphics.getHeight()) {
			yZ = 0;
		}




		yv = decelerate(yv);
		xv = decelerate(xv);


	}

	@Override
	public void render () {
		zDirection = (int)(Math.random() * 4);
		time += Gdx.graphics.getDeltaTime();
		move();

		TextureRegion img, zImg;
		img = right;
		zImg = zRight;


		if (xv != 0) {
			img = walk.getKeyFrame(time, true);
		}
		else if (xv < 0) {
			img = down;
		}
		else if (yv > 0) {
			img =walkUp.getKeyFrame(time, true);
		}
		else if (yv < 0) {
			img = walkDown.getKeyFrame(time, true);
		}

		if (zDirection == 1 ) {
			zImg = zWalkUp.getKeyFrame(time, true);
		}
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();

		if (faceRight) {
			batch.draw(img, x, y, DRAW_WIDTH, DRAW_HEIGHT);
		}
		else {
			batch.draw(img, x, y, DRAW_WIDTH * -1, DRAW_HEIGHT);
		}
		if(zFaceRight) {
			batch.draw(zImg, xZ, yZ, DRAW_Z_WIDTH, DRAW_Z_HEIGHT );
		}
		else {
			batch.draw(zImg, xZ, yZ, DRAW_Z_WIDTH * -1, DRAW_Z_HEIGHT );

		}




		batch.end();


	}


	@Override
	public void dispose () {
		batch.dispose();
	}
}
