package com.mygdx.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.Drop;

import java.util.Iterator;

/**
 * Created by Vlad on 28.10.2015.
 */
public class DropsActor extends Actor {

    final Drop game;

    Texture dropImage;
    Sound dropSound;

    Array<Rectangle> raindrops;
    Rectangle bucket;
    int rotation;
    int countDrop;

    long lastDropTime;
    long lastUpSpeed ;

    public DropsActor(final Drop gam, Rectangle buck ) {

        this.game = gam;
        this.bucket = buck;

        dropImage = new Texture("Kapl9.png");
        dropSound = Gdx.audio.newSound(Gdx.files.internal("waterdrop.wav"));


        lastDropTime = TimeUtils.nanoTime();

        rotation = 0;

        raindrops = new Array<Rectangle>();
        spawnRaindrop();

    }

    @Override
    public void draw(Batch batch, float alpha) {
        for (Rectangle raindrop: raindrops){
            game.batch.draw(dropImage, raindrop.x, raindrop.y, dropImage.getWidth()/2 , dropImage.getHeight()/2, (float) ((int) (dropImage.getWidth())*0.6), (float) ((int) (dropImage.getHeight())*0.6),
                    1, 1, rotation, 0,0, dropImage.getWidth(),dropImage.getHeight(), false, false);
        }

        dropMove(alpha);
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

    public void dropMove(float delta){

        accelerationDrop();

        if (TimeUtils.nanoTime() - lastDropTime > game.timeCreateNewDrop) spawnRaindrop();

        Iterator<Rectangle> iter = raindrops.iterator();
        while (iter.hasNext()){
            Rectangle raindrop = iter.next();
            raindrop.y -=  game.speed * Gdx.graphics.getDeltaTime();
            if (raindrop.y  < 20){
                iter.remove();
                game.dropsLosed++;
                countDrop--;
            }
            if (raindrop.overlaps(bucket) && raindrop.y > 40){
                game.dropsGatchered++;
                dropSound.play();
                countDrop--;
                iter.remove();
            }
        }

    }

    public void accelerationDrop(){

        if((TimeUtils.nanoTime() - lastUpSpeed)/4 > 1000000000 && game.persent < 130){
            game.timeCreateNewDrop-= 500000000 / 100 *6;
            game.speed+= 8;
            lastUpSpeed = TimeUtils.nanoTime();
        }
    }

    public Array<Rectangle> getRaindrops(){
        return raindrops;
    }
}
