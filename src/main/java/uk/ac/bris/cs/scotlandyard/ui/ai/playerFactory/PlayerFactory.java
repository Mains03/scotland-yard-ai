package uk.ac.bris.cs.scotlandyard.ui.ai.playerFactory;

import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.model.Piece;
import uk.ac.bris.cs.scotlandyard.model.Player;

/**
 * Create a player from the board is used in multiple locations.
 */
public interface PlayerFactory {
    Player createPlayer(Board board, Piece piece);
}
