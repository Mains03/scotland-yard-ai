package uk.ac.bris.cs.scotlandyard.ui.ai.staticPositionEvaluationStrategy;

import uk.ac.bris.cs.scotlandyard.model.Piece;
import uk.ac.bris.cs.scotlandyard.model.Player;

import java.util.List;

public interface PlayerFactory {
    Player createMrX();

    List<Player> createDetectives();

    Player createFromPiece(Piece piece);
}
