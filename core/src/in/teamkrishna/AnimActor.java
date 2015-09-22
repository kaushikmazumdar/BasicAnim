package in.teamkrishna;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Created by Kaushik on 9/20/2015.
 */
public class AnimActor extends Actor {
    Sprite sprite,sprite_return;
    Animation animation, animation_return;
    boolean animationPlaying;
    private float elapsedTime;
    private float x,y,width,height;
    Sound sound;
    long soundID;
    float factor;
    boolean turn=false;

    AnimActor(Sprite sprite, Sprite sprite_return,Animation animation, Animation animation_return, float x, float y, float width, float height, final Sound sound)
    {
        this.sprite = sprite;
        this.sprite_return=sprite_return;
        this.animation = animation;
        this.animation_return=animation_return;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.sound = sound;

        animationPlaying = false;

        setBounds(x,y,width,height);
        setTouchable(Touchable.enabled);

        addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                if (animationPlaying == false) {
                    animationPlaying = true;
                    elapsedTime = 0;
                    if(sound!=null) {
                        sound.loop();
                    }
                } else {
                    animationPlaying = false;
                    if(sound!=null)
                        sound.stop();
                }

                super.clicked(event, x, y);
            }
        });
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(animationPlaying == false) {
           // sprite.setAlpha(0.0f);

            if(!turn)
                batch.draw(sprite, x, y, width, height);
            else
                batch.draw(sprite_return, x, y, width, height);
            //sprite.setAlpha(1.0f);
        }
        else
        {
            elapsedTime += Gdx.graphics.getDeltaTime();
            //batch.draw(animation.getKeyFrame(elapsedTime, true), x, y);

            if(!turn) {
                if(x>0f) {
                    batch.draw(animation.getKeyFrame(elapsedTime, true), x, y);
                    x -= 1;
                }
                else
                {
                    turn = true;
                }
            }
            if(turn == true)
            {
                if(x <= (Gdx.graphics.getWidth()-width)) {
                    batch.draw(animation_return.getKeyFrame(elapsedTime,true),x,y);
                    x += 1;
                }
                else {
                    turn = false;
                }
            }
            setBounds(x, y, width, height);

        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }
}
