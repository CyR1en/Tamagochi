package com.cyr1en.tamagochi.handler;

import com.cyr1en.cgdl.Handlers.GameStateManager;
import com.cyr1en.tamagochi.states.IntroState;
import com.cyr1en.tamagochi.states.MenuState;


/**
 * This handles all the switching of the game state
 */
public class StateManager extends GameStateManager {

    //constants that we can just call through out the code
    private static final int INTRO_STATE = -1;
    public static final int MENU_STATE = 0;

    //constructor for the state manager class
    public StateManager() {
        currentState = INTRO_STATE;
        loadState(currentState);
    }

    //loads specific states, stated in the parameter
    public void loadState(int state) {
        if(state == INTRO_STATE)
            gameState = new IntroState(this);
        if(state == MENU_STATE)
            gameState = new MenuState(this);
    }
}
