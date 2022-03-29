package uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy;

import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.model.Player;

public class AiPlayerAdapter implements AiPlayer {
    private Player player;

    public AiPlayerAdapter(Player player) {
        this.player = player;
    }

    @Override
    public int getLocation() {
        return player.location();
    }

    @Override
    public AiPlayer applyMove(Move move) {
        return new AiPlayerAdapter(player.use(move.tickets()).at(moveDestination(move)));
    }

    private int moveDestination(Move move) {
        return move.accept(new Move.Visitor<>() {
            @Override
            public Integer visit(Move.SingleMove move) {
                return move.destination;
            }

            @Override
            public Integer visit(Move.DoubleMove move) {
                return move.destination2;
            }
        });
    }
}
