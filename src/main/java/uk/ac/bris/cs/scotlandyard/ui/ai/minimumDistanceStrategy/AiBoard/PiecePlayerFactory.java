package uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.AiBoard;

import uk.ac.bris.cs.scotlandyard.model.Piece;
import uk.ac.bris.cs.scotlandyard.model.Player;

import java.util.Optional;

public interface PiecePlayerFactory {
    Optional<Player> createPlayer(Piece piece);
}
