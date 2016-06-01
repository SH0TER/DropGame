package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.actors.BucketActor;
import com.mygdx.game.actors.DropsActor;
import com.mygdx.game.actors.PauseButtonGameScreenActor;
import com.mygdx.game.actors.TextGameScreenActor;


public class CameScreen implements Screen {

	final Drop game;

	public DropsActor drops;
	public BucketActor bucket;
	public TextGameScreenActor textActor;
	public PauseButtonGameScreenActor pauseActor;

	public OrthographicCamera camera;
	public Vector3 touchPos;
	public ScreenViewport screenViewport;


	public  Stage stage;

	Music rainMusic;

	private TextButton exit;


	public CameScreen (final Drop gam) {
		this.game = gam;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		touchPos = new Vector3();
		screenViewport = new ScreenViewport();
		screenViewport.setScreenSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		screenViewport.setWorldSize(800, 480);

		stage = new Stage(screenViewport, game.batch);

		textActor = new TextGameScreenActor(game);

		bucket = new BucketActor(game, camera, touchPos );
		drops = new DropsActor(game, bucket.getBucket());
		pauseActor = new PauseButtonGameScreenActor(game, this);

		pauseButton();

		stage.addActor(game.background);
		stage.addActor(drops);
		stage.addActor(bucket);
		stage.addActor(textActor);
		stage.addActor(pauseActor);
		stage.addActor(exit);

		Gdx.input.setInputProcessor(stage);

		rainMusic = Gdx.audio.newMusic(Gdx.files.internal("undertreeinrain.mp3"));

		rainMusic.setLooping(true);
		rainMusic.play();

	}

	public void pauseButton(){

		TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
		textButtonStyle.font = game.font;

		exit = new TextButton("", textButtonStyle);
		exit.setSize(30,30);
		exit.setPosition(375, 450);
		exit.addListener(new ClickListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				pause();
				return true;
			};

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

			};
		});

	}


	@Override
	public void render (float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		//camera.unproject(touchPos);
		game.batch.setProjectionMatrix(camera.combined);

		if (game.dropsLosed > 5 ){
			game.setScreen(new MainMenuScreen(game));
			dispose();
		}
		if(game.dropsGatchered >= 2){
			game.setScreen(new FinishScreen(game, drops.getRaindrops(), TimeUtils.nanoTime()));
			dispose();
		}


		stage.draw();
		stage.act(delta);
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {
		//game.setScreen(new GamePauseScreen(game, stage));
		//dispose();
	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {
		stage.dispose();
		rainMusic.dispose();
	}

	@Override
	public void show() {
		rainMusic.play();
	}
}
