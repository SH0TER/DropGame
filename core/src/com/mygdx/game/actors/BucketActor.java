package com.mygdx.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Drop;

/**
 * Created by Vlad on 28.10.2015.
 */
public class BucketActor extends Actor {

    final Drop game;
    Texture bucketImage;

    public OrthographicCamera camera;

    Rectangle bucket;
    Vector3 touchPos;

    public BucketActor(final Drop gam, OrthographicCamera cam, Vector3 vector3) {
        this.game = gam;
        this.camera = cam;
        this.touchPos = vector3;

        bucketImage = new Texture("bucket1.png");



        bucket = new Rectangle();
        bucket.x = 800 / 2 - 64 / 2;
        bucket.y = 20;
        bucket.width = 72;
        bucket.height = 75;
    }

    @Override
    public void draw(Batch batch, float alpha) {
        game.batch.draw(bucketImage, bucket.x, bucket.y, 70, 70);
        bucketMove();
    }


    public void bucketMove(){

        if(Gdx.input.isTouched()){
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            bucket.x = (int) (touchPos.x -64 / 2);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) bucket.x -= 200 * Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) bucket.x += 200 * Gdx.graphics.getDeltaTime();

        if (bucket.x < 0) bucket.x = 0;
        if (bucket.x > 800 - 64) bucket.x = 800 - 64;
    }

    public Rectangle getBucket(){
        return bucket;
    }
}
