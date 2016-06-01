package com.mygdx.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


/**
 * Created by Vlad on 29.10.2015.
 */
public class GamePauseScreen implements Screen {

    final Drop game;

    OrthographicCamera camera;

    public Stage gameStage;
    public Stage stage;

    public GamePauseScreen(final Drop gam, Stage gameSta){
        this.game = gam;
        this.gameStage = gameSta;

        stage = new Stage(new ScreenViewport(), game.batch);



    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

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

    }
}
