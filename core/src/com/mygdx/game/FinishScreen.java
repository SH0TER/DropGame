package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

/**
 * Created by Vlad on 27.10.2015.
 */
public class FinishScreen implements Screen {

    final Drop game;
    OrthographicCamera camera;
    Texture dropImage;
    Texture bucketImage;
    Texture bacgroundImage;
    Sound dropSound;
    Music rainMusic;
    Rectangle bucket;
    Vector3 touchPos;
    Array<Rectangle> raindrops;
    long lastDropTime;
    int dropsLosed;
    long timeCreateNewDrop;
    long lastUpSpeed ;
    long timeLastMove;
    long startTime;
    int persent;
    int speed;
    int rotation;
    int countDrop;


    public FinishScreen(final Drop gam, Array<Rectangle> raindrops, long startTime){
        this.game = gam;
        this.raindrops = raindrops;
        this.startTime = startTime;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        touchPos = new Vector3();

        timeCreateNewDrop = 10000;
        lastDropTime = TimeUtils.nanoTime();
        speed = 180;
        rotation = 0;

        timeLastMove = TimeUtils.nanoTime();

        dropImage = new Texture("droplet.png");
        bucketImage = new Texture("bucket1.png");
        bacgroundImage = new Texture("rain-on-a-sunny-day.jpg");

        dropSound = Gdx.audio.newSound(Gdx.files.internal("waterdrop.wav"));
        rainMusic = Gdx.audio.newMusic(Gdx.files.internal("undertreeinrain.mp3"));

        rainMusic.setLooping(true);
        rainMusic.play();

        bucket = new Rectangle();
        bucket.x = 800 / 2 - 64 / 2;
        bucket.y = 20;

        bucket.width = 72;
        bucket.height = 75;

        spawnRaindrop();
    }

    private void spawnRaindrop(){
        Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random(0, 800 - 64);
        raindrop.y = 480;
        raindrop.width =70;
        raindrop.height = 10;
        raindrops.add(raindrop);
        lastDropTime = TimeUtils.nanoTime();
        countDrop++;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(bacgroundImage, 0, 0, 800, 480);
        game.font.draw(game.batch, " Collected: " + game.dropsGatchered, 0, 480);
        game.font.draw(game.batch, "Lost: " + dropsLosed, 650, 480);
        for (Rectangle raindrop: raindrops){
            game.batch.draw(dropImage, raindrop.x, raindrop.y, dropImage.getWidth()/2 , dropImage.getHeight()/2, dropImage.getWidth(), dropImage.getHeight(),
                    1, 1, rotation, 0,0, dropImage.getWidth(),dropImage.getHeight(), false, false);
        }
        game.batch.draw(bucketImage, bucket.x, bucket.y, 70, 70);
        game.batch.end();

        if(Gdx.input.isTouched()){
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            bucket.x = (int) (touchPos.x -64 / 2);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) bucket.x -= 200 * Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) bucket.x += 200 * Gdx.graphics.getDeltaTime();

        if (bucket.x < 0) bucket.x = 0;
        if (bucket.x > 800 - 64) bucket.x = 800 - 64;

        if(TimeUtils.nanoTime() - startTime < 1000000000*2) {
            dropMove(delta);
        }else{
            dropToUp(delta);
        }
    }

    public boolean dropMove(float delta){

        accelerationDrop();

        if (TimeUtils.nanoTime() - lastDropTime > timeCreateNewDrop) spawnRaindrop();

        Iterator<Rectangle> iter = raindrops.iterator();
        while (iter.hasNext()){
            Rectangle raindrop = iter.next();
            raindrop.y -= speed * Gdx.graphics.getDeltaTime();
            if (raindrop.overlaps(bucket) && raindrop.y > 40){
                dropSound.play();
                countDrop--;
                iter.remove();
            }
        }

        return true;
    }

    public void accelerationDrop(){

        if((TimeUtils.nanoTime() - lastUpSpeed)/4 > 1000000000 && persent < 130){
            timeCreateNewDrop-= 500000000 / 100 *7;
            speed+= 8;
            lastUpSpeed = TimeUtils.nanoTime();
        }
    }


    public void dropToUp(float delta){
        if(!returnDrop(delta)){
            upDrop(delta);
        }
        Iterator<Rectangle> iter = raindrops.iterator();
        if(iter == null){
            game.setScreen(new MainMenuScreen(game));
            dispose();
        }
        if(countDrop <= 0){
            game.setScreen(new MainMenuScreen(game));
            dispose();
        }

    }

    public boolean returnDrop(float delta){

        if((TimeUtils.nanoTime() - timeLastMove) > 10000000/3 && rotation <= 180){
            rotation+= 2;
            timeLastMove = TimeUtils.nanoTime();
            return true;
        }
        else return false;
    }

    public void upDrop(float delta){

        Iterator<Rectangle> iter = raindrops.iterator();
        while (iter.hasNext()){
            Rectangle raindrop = iter.next();
            raindrop.y += 200 * Gdx.graphics.getDeltaTime();
            if (raindrop.y  > 480){
                countDrop--;
                iter.remove();
            }
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        dropImage.dispose();
        bucketImage.dispose();
        dropSound.dispose();
        rainMusic.dispose();
    }
}
