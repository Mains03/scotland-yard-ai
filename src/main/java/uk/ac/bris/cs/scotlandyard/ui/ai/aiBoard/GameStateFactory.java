package uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard;

import com.google.common.collect.ImmutableList;
import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.model.GameSetup;
import uk.ac.bris.cs.scotlandyard.model.MyGameStateFactory;
import uk.ac.bris.cs.scotlandyard.model.Player;

public class GameStateFactory {
    private static GameStateFactory instance;

    public static GameStateFactory getInstance() {
        if (instance == null)
            instance = new GameStateFactory();
        return instance;
    }

    private GameStateFactory() {}

    public Board.GameState build(Board board) {
        MyGameStateFactory factory = new MyGameStateFactory();
        GameSetup gameSetup = createGameSetup(board);
        Player mrX = createMrX(board);
        ImmutableList<Player> detectives = createDetectives(board);
        return factory.build(
                gameSetup,
                mrX,
                detectives
        );
    }

    private GameSetup createGameSetup(Board board) {
        return board.getSetup();
    }

    private Player createMrX(Board board) {
        PlayerFactory factory = PlayerFactory.getInstance();
        return factory.createMrX(board);
    }

    private ImmutableList<Player> createDetectives(Board board) {
        PlayerFactory factory = PlayerFactory.getInstance();
        return factory.createDetectives(board);
    }
}
