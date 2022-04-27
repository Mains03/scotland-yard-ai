package uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard;

import uk.ac.bris.cs.scotlandyard.model.*;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.stream.Collectors;

/**
 * {@link Board} adapter only storing the locations of the players.
 */
public class LocationAiBoard extends DefaultGameState implements AiBoard {
    public final int mrXLocation;

    public final List<Integer> detectiveLocations;

    public LocationAiBoard(Board board) {
        super(GameStateFactory.getInstance().build(board));
        mrXLocation = PlayerFactory.getInstance().createMrX(board).location();
        detectiveLocations = createDetectiveLocations(board);
    }

    private LocationAiBoard(GameState gameState, int mrXLocation) {
        super(gameState);
        this.mrXLocation = mrXLocation;
        detectiveLocations = createDetectiveLocations(gameState);
    }

    private List<Integer> createDetectiveLocations(Board board) {
        return PlayerFactory.getInstance().createDetectives(board).stream()
                .map(Player::location)
                .collect(Collectors.toList());
    }

    @Nonnull
    @Override
    public GameState advance(Move move) {
        int mrXLocation;
        if (move.commencedBy().isMrX()) {
            mrXLocation = move.accept(new Move.Visitor<>() {
                @Override
                public Integer visit(Move.SingleMove move) {
                    return move.destination;
                }

                @Override
                public Integer visit(Move.DoubleMove move) {
                    return move.destination2;
                }
            });
        } else
            mrXLocation = this.mrXLocation;
        return new LocationAiBoard(super.advance(move), mrXLocation);
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }
}
