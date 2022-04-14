package uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.aiMove;

import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.model.ScotlandYard;

import java.util.Objects;

/**
 * @deprecated Deprecated since {@link AiMove} is deprecated
 */
@Deprecated
public class AiMoveAdapter implements AiMove {
    private final Move move;

    public AiMoveAdapter(Move move) {
        Objects.requireNonNull(move);
        this.move = move;
    }

    @Override
    public int getDestination() {
        return move.accept(new Move.Visitor<Integer>() {
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

    @Override
    public Iterable<ScotlandYard.Ticket> tickets() {
        return move.tickets();
    }

    @Override
    public Move asMove() {
        return move;
    }
}
