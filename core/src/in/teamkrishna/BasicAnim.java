package in.teamkrishna;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class BasicAnim extends ApplicationAdapter {
	SpriteBatch batch;
	private TextureAtlas textureAtlas;
	private Animation animation, animation1, animation_return;
	private Texture texture;
	private Sprite sprite,sprite_return;
	private Stage stage;

	private Array<TextureRegion> frames;
	int frameCount;
	TextureRegion textureRegion,trTmp;
	Sound bear_growl;

	float elapsedTime=0;


	@Override
	public void create () {

		frameCount=2;
		texture = new Texture("bear_shots.jpg");
		texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		textureRegion = new TextureRegion(texture);

		frames = new Array<TextureRegion>();
		int frameWidth = texture.getWidth() / frameCount;
		for(int i = 0; i < frameCount; i++) {
			trTmp = new TextureRegion(textureRegion, i * frameWidth, 0, frameWidth, textureRegion.getRegionHeight());
			frames.add(trTmp);
		}
		sprite = new Sprite(frames.get(0));
		animation = new Animation(1.0f, frames);

		frames.clear();
		for(int i = 0; i < frameCount; i++) {
			trTmp = new TextureRegion(textureRegion, i * frameWidth, 0, frameWidth, textureRegion.getRegionHeight());
			trTmp.flip(true,false);
			frames.add(trTmp);
		}
		sprite_return = new Sprite(frames.get(0));
		animation_return = new Animation(1.0f,frames);

		Pixmap pixmap = new Pixmap((int)sprite.getWidth(),(int)sprite.getHeight(), Pixmap.Format.RGBA8888);
		pixmap.setColor(Color.BLACK);
		pixmap.fill();

		Array<TextureRegion> blink = new Array<TextureRegion>();
		blink.add(new TextureRegion((new Texture(pixmap))));

		pixmap.setColor(Color.BLUE);
		pixmap.fill();
		blink.add(new TextureRegion((new Texture(pixmap))));



		batch = new SpriteBatch();
		//textureAtlas = new TextureAtlas(Gdx.files.internal("spritesheet.atlas"));
		//animation = new Animation(1/25f, textureAtlas.getRegions());

		animation1 = new Animation(0.5f, blink);



		bear_growl = Gdx.audio.newSound(Gdx.files.internal("bear_growl.mp3"));

		ScreenViewport viewport = new ScreenViewport();
		//initialising the stage while passing the viewport as parameter hence it will know it's area or range
		stage = new Stage(viewport);
		Gdx.input.setInputProcessor(stage);


		AnimActor actor = new AnimActor(sprite,sprite_return,animation,animation_return,Gdx.graphics.getWidth()-sprite.getWidth(),0,sprite.getWidth(),sprite.getHeight(),bear_growl);

		sprite = new Sprite(new Texture(pixmap));
		AnimActor actor1 = new AnimActor(sprite,sprite,animation1,animation1,300,300,sprite.getWidth(),sprite.getHeight(),null);

		//setting the actor class to the stage
		stage.addActor(actor);
		stage.setKeyboardFocus(actor);

		stage.addActor(actor1);
		stage.setKeyboardFocus(actor1);

		//Gdx.graphics.setContinuousRendering(false);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();

//		batch.begin();
//
//		batch.enableBlending();
//
//		elapsedTime += Gdx.graphics.getDeltaTime();
//		batch.draw(animation.getKeyFrame(elapsedTime, true), 0, 0);
//		batch.end();
	}

	@Override
	public void dispose() {
		batch.dispose();
		textureAtlas.dispose();
		texture.dispose();
	}
}
