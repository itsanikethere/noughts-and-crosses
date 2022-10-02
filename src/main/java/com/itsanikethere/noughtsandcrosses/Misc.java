package com.itsanikethere.noughtsandcrosses;

import javax.swing.*;
import java.util.Random;

/**
 * @author Aniket Panchal
 * @since 02-10-2022
 */
@SuppressWarnings("ALL")
final class Misc {

    ImageIcon getGameIcon(String name) {
        return new ImageIcon(getClass().getClassLoader().getResource(name));
    }

    boolean getGameTurn() {
        Random random = new Random();
        return random.nextBoolean();
    }

}