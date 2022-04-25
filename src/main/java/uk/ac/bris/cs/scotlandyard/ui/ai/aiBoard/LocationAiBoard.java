package uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard;

import uk.ac.bris.cs.scotlandyard.model.*;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.stream.Collectors;

/**
 * {@link Board} adapter only storing the locations of the players.
 */
public class LocationAiBoard extends DefaultAiBoard implements AiBoard {
    private final GameState board;

    public final int mrXLocation;

    public final List<Integer> detectiveLocations;

    public LocationAiBoard(Board board) {
        super(board);
        this.board = GameStateFactory.getInstance().build(board);
        mrXLocation = PlayerFactory.getInstance().createMrX(board).location();
        detectiveLocations = createDetectiveLocations(board);
    }

    private LocationAiBoard(GameState board, int mrXLocation) {
        super(board);
        this.board = board;
        this.mrXLocation = mrXLocation;
        detectiveLocations = createDetectiveLocations(board);
    }

    private List<Integer> createDetectiveLocations(Board board) {
        return PlayerFactory.getInstance().createDetectives(board).stream()
                .map(Player::location)
                .collect(Collectors.toList());
    }

    @Nonnull
    @Override
    public GameState advance(Move move) {
        GameState board = this.board.advance(move);
        int mrXLocation = updateMrXLocation(move);
        return new LocationAiBoard(board, mrXLocation);
    }

    private int updateMrXLocation(Move move) {
        int mrXLocation;
        if (move.commencedBy().isMrX())
            mrXLocation = PlayerMoveAdvance.getInstance().moveDestination(move);
        else
            mrXLocation = this.mrXLocation;
        return mrXLocation;
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }
}
