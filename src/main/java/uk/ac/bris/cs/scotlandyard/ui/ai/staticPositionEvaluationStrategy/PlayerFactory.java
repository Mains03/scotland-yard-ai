package uk.ac.bris.cs.scotlandyard.ui.ai.staticPositionEvaluationStrategy;

import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.model.Piece;
import uk.ac.bris.cs.scotlandyard.model.Player;

import java.util.List;

public interface PlayerFactory {
    /**
     * @deprecated Use {@link #createMrX(Board)}
     */
    @Deprecated
    Player createMrX();

    default Player createMrX(Board board) {
        return new PlayerFactoryAdapterV2().createMrX(board);
    }

    /**
     * @deprecated Use {@link #createDetectives(Board)}
     */
    @Deprecated
    List<Player> createDetectives();

    default List<Player> createDetectives(Board board) {
        return new PlayerFactoryAdapterV2().createDetectives(board);
    }

    /**
     * @deprecated Use {@link #createFromPiece(Board, Piece)} 
     */
    @Deprecated
    Player createFromPiece(Piece piece);

    default Player createFromPiece(Board board, Piece piece) {
        return new PlayerFactoryAdapterV2().createFromPiece(board, piece);
    }
}
