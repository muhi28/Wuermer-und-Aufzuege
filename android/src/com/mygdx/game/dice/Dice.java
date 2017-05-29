package com.mygdx.game.dice;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FileTextureData;

import java.util.Random;

/**
 * Created by Patrick on 05.04.2017.
 * <p>
 * Class Dice has two variables rang and dice_p
 * Until now only one relevant method is implemented (rollTheDice).
 */
public class Dice {

    private int range;
    private Texture diceTexture;
    private final boolean loadPictures;
    private final Random random;


    private int result;


    /**
     * Instantiates a new Dice.
     *
     * @param range        the range
     * @param loadPictures the load pictures
     */

    public Dice(int range, boolean loadPictures) {
        this(range, loadPictures, new Random().nextLong());
    }

    /*
          Constructor initializes range with delivered value and dice_p with dice_idle.png
         */
    public Dice(int range, boolean loadPictures, long randomSeed) {
        this.range = range;
        if (loadPictures) {
            this.diceTexture = new Texture(Gdx.files.internal("dice_idle.png"));
        }
        this.loadPictures = loadPictures;
        this.random = new Random(randomSeed);
    }

    /**
     * Instantiates a new Dice.
     *
     * @param range the range
     */
    public Dice(int range) {
        this(range, true);
    }

    /**
     * Gets range.
     *
     * @return the range
     */
    public int getRange() {
        return range;
    }

    /**
     * Gets result.
     *
     * @return the result
     */
    public int getResult() {
        return result;
    }

    /**
     * Gets dice texture.
     *
     * @return the dice texture
     */
    public Texture getDiceTexture() {
        return diceTexture;
    }

    /**
     * The method rollTheDice generates a random number and sets the texture of the dice.
     *
     * @return : the random value
     */
    public int rollTheDice() {

        result = random.nextInt(this.range - 1 + 1) + 1;         //(max - min + 1) + min
//        result = (int) (Math.random() * this.range + 1);
        /*
        sets diceTexture on the rolled texture
         */
        if (loadPictures) {

            this.diceTexture = new Texture(Gdx.files.internal("dice_" + result + ".png"));

        }

        return result;
    }

    /**
     * The method cheatDice sets the diceTexture on the texture the player clicked.
     *
     * @param dice_p the dice p
     * @return : the int value of the dice number
     */
    public int cheatDice(Texture dice_p) {
        int result = 0;

        /*
            The paths from the dice texture are saved, in the String Array pathOfDices..
         */


        //Here I get the path from the given texture
        String pathOfGivenTexture = ((FileTextureData) dice_p.getTextureData()).getFileHandle().path();


        for (int i = 1; i <= this.getRange(); i++) {
            String vgl = ((FileTextureData) (new Texture(Gdx.files.internal("dice_" + i + ".png"))).getTextureData()).getFileHandle().path();

            if (vgl.equals(pathOfGivenTexture)) {
                result = i;
            }
        }

        /*
        sets diceTexture depending on the given texture
         */
        /*
        switch (result) {
            case 1:
                this.diceTexture = (new Texture(Gdx.files.internal("dice_one.png")));
                break;
            case 2:
                this.diceTexture = (new Texture(Gdx.files.internal("dice_two.png")));
                break;
            case 3:
                this.diceTexture = (new Texture(Gdx.files.internal("dice_three.png")));
                break;
            case 4:
                this.diceTexture = (new Texture(Gdx.files.internal("dice_four.png")));
                break;
            case 5:
                this.diceTexture = (new Texture(Gdx.files.internal("dice_five.png")));
                break;
            case 6:
                this.diceTexture = (new Texture(Gdx.files.internal("dice_six.png")));
                break;
            default:
                System.out.println("Falscher Value uebergeben!");

        }*/
        return result;

    }

    /**
     * Create animation animation.
     *
     * @return the animation
     */
    public Animation createAnimation() {
        TextureRegion[] text = new TextureRegion[this.getRange()];

        for (int i = 0; i <= this.getRange() - 1; i++) {
            text[i] = new TextureRegion(new Texture(Gdx.files.internal("dice_" + (i + 1) + ".png")));
        }

        //Cast to Object because of warning.
        Animation diceAnimation = new Animation(0.15f, (Object[]) text);
        return diceAnimation;
    }
}