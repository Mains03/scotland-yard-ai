package uk.ac.bris.cs.scotlandyard.ui.ai.playerFactory;

import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.model.Piece;
import uk.ac.bris.cs.scotlandyard.model.Player;

/**
 * Replacement for {@link PlayerFactory}.
 */
public interface PlayerFactoryV2 {
    Player createPlayer(Board board, Piece piece);
}
