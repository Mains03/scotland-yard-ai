package uk.ac.bris.cs.scotlandyard.ui.ai.gameTree;

import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.model.Player;

import java.util.List;

class PlayerFactory {
    private static PlayerFactory playerFactory;

    static PlayerFactory getInstance() {
        if (playerFactory == null)
            playerFactory = new PlayerFactory();
        return playerFactory;
    }

    private PlayerFactory() {}

    Player createMrX(final Board board, final Move move) {
        return null;
    }

    List<Player> createDetectives(final Board board) {
        return null;
    }
}
