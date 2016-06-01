package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.actors.BackgroundActor;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

import java.util.HashMap;

/**
 * Created by Vlad on 24.10.2015.
 */
public class Drop extends Game {

    public SpriteBatch batch;
    public BackgroundActor background;
    public int dropsGatchered;
    public 	int dropsLosed;
    public long timeCreateNewDrop;
    public int speed;
    public int persent;

    public BitmapFont font;
    private static final String FONT_CHARACTERS = "абвгдеёжзийклмнопрстуфхцчшщъыьэюяАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯabcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!:$%#@|\\/?-+=()*&.;,{}\"´`'<>";



    @Override
    public void create() {
        batch = new SpriteBatch();
        background = new BackgroundActor();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/russoone.ttf"));
        FreeTypeFontParameter param = new FreeTypeFontParameter();
        param.size = Gdx.graphics.getHeight() / 18;
        param.characters = FONT_CHARACTERS;
        font = generator.generateFont(param);
        font.setColor(Color.BLACK );
        generator.dispose();

        timeCreateNewDrop =  1000000000;
        speed = 180;

        this.setScreen(new MainMenuScreen(this));
    }

    @Override
    public void render() {
        super.render();

    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        font.dispose();
    }
}
