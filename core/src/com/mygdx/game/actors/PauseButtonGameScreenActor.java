package com.mygdx.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.CameScreen;
import com.mygdx.game.Drop;

/**
 * Created by Vlad on 29.10.2015.
 */
public class PauseButtonGameScreenActor extends Actor {

    final Drop game;
    CameScreen gameScreen;

    Texture pauseIcon;
    private Sprite pauseSpritbach;

    public PauseButtonGameScreenActor(final Drop gam, CameScreen cam)  {
        this.game = gam;
        this.gameScreen = cam;

        pauseIcon = new Texture("PauseDisabled.png");
        pauseSpritbach = new Sprite(pauseIcon);
        pauseSpritbach.setSize(30, 30);
        pauseSpritbach.setPosition(375, 450);

        addListener(new ClickListener() {
            @Override
            public boolean  touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            };
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            };
        });

    }

    @Override
    public void draw(Batch batch, float alpha) {
        pauseSpritbach.draw(batch);
    }
}
