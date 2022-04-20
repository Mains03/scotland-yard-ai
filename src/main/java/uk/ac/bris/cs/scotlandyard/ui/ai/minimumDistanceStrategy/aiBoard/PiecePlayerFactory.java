package uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.aiBoard;

import uk.ac.bris.cs.scotlandyard.model.Piece;
import uk.ac.bris.cs.scotlandyard.model.Player;
import uk.ac.bris.cs.scotlandyard.ui.ai.playerFactory.PlayerFactoryV2;

import java.util.Optional;

/**
 * @deprecated use {@link PlayerFactoryV2}.
 */
@Deprecated
public interface PiecePlayerFactory {
    Optional<Player> createPlayer(Piece piece);
}
