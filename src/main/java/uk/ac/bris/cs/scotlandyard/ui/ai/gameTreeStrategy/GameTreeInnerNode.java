package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy;
;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.aiBoard.AiBoard;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class GameTreeInnerNode extends GameTree {
    private final Set<GameTree> children;

    public GameTreeInnerNode(AiBoard board, int depth) {
        if (depth < 1)
            throw new IllegalArgumentException();
        Set<GameTree> children = new HashSet<>();
        for (Move move : board.getAvailableMovesNormal()) {
            AiBoard newBoard = board.applyMove(move);
            GameTree child;
            if (depth == 0)
                child = new GameTreeLeafNode(newBoard, Optional.of(move));
            else
                child = new GameTreeInnerNode(newBoard, depth-1);
            children.add(child);
        }
        this.children = children;
    }

    @Override
    public Optional<Move> accept(GameTreeVisitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public Optional<Move> mrXMoveMade() {
        return Optional.empty();
    }

    public Set<GameTree> getChildren() {
        return Set.copyOf(children);
    }
}
