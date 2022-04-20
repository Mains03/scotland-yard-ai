package uk.ac.bris.cs.scotlandyard.ui.ai.playerFactory;

import uk.ac.bris.cs.scotlandyard.model.Piece;
import uk.ac.bris.cs.scotlandyard.model.Player;

import java.util.List;

/**
 * Separates the process of creating players since this is useful in various places.
 *
 * @deprecated since more information is required to create players, use {@link PlayerFactoryV2}.
 */
@Deprecated
public interface PlayerFactory {
    Player createMrX();

    List<Player> createDetectives();

    Player createFromPiece(Piece piece);
}
