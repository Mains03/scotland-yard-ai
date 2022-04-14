package uk.ac.bris.cs.scotlandyard.ui.ai.deprecated.minimumDistanceStrategy.aiBoard;

import uk.ac.bris.cs.scotlandyard.model.Piece;
import uk.ac.bris.cs.scotlandyard.model.Player;
import uk.ac.bris.cs.scotlandyard.ui.ai.staticPositionEvaluationStrategy.PlayerFactory;

import java.util.Optional;

/**
 * @deprecated Use {@link PlayerFactory}
 */
@Deprecated
public interface PiecePlayerFactory {
    Optional<Player> createPlayer(Piece piece);
}
