package uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.aiBoard;

import uk.ac.bris.cs.scotlandyard.model.Piece;
import uk.ac.bris.cs.scotlandyard.model.Player;
import uk.ac.bris.cs.scotlandyard.ui.ai.playerFactory.PlayerFactory;

import java.util.Optional;

public interface PiecePlayerFactory {
    Optional<Player> createPlayer(Piece piece);
}
