package com.mygdx.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Drop;

/**
 * Created by Vlad on 28.10.2015.
 */
public class TextGameScreenActor extends Actor {

    final Drop game;


    public TextGameScreenActor(final Drop gam) {
        this.game = gam;
    }

    @Override
    public void draw(Batch batch, float alpha) {
        game.font.draw(game.batch, " Collected: " + game.dropsGatchered, 0, 480);
        game.font.draw(game.batch, "Lost: " + game.dropsLosed, 650, 480);
    }

}
