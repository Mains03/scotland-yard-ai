package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.aiPlayerData;

import com.google.common.collect.ImmutableSet;
import com.google.common.graph.ImmutableValueGraph;
import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.model.Piece;
import uk.ac.bris.cs.scotlandyard.model.Player;
import uk.ac.bris.cs.scotlandyard.model.ScotlandYard;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.AiPlayer.AiPlayerAdapter;

public class AiPlayerWithMoveFlagAdapter extends AiPlayerAdapter implements AiPlayerWithMoveFlag {
    private boolean moved;

    public AiPlayerWithMoveFlagAdapter(Board board, Player player) {
        super(board, player);
    }

    public AiPlayerWithMoveFlagAdapter(
            ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph,
            Player player
    ) {
        super(graph, player);
    }

    public AiPlayerWithMoveFlagAdapter(Board board, Piece piece) {
        super(board, piece);
    }

    @Override
    public void markMoved() {
        moved = true;
    }

    @Override
    public void unmarkMoved() {
        moved = false;
    }

    @Override
    public boolean hasMoved() {
        return moved;
    }
}
