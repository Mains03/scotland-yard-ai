package uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.evaluation.minimumDistance;

import uk.ac.bris.cs.scotlandyard.model.Piece;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.*;

/**
 * Breadth-first search algorithm to find the minimum distance between
 * MrX and a detective.
 */
public class BFS implements MinimumDistanceAlgorithm {
    private static BFS instance;

    public static BFS getInstance() {
        if (instance == null)
            instance = new BFS();
        return instance;
    }

    protected BFS() {}

    protected static final int INITIAL_DISTANCE_VAL = -1;
    protected static final int POSITIVE_INFINITY = 1000000;

    @Override
    public int minimumDistance(AiBoard board, Piece piece) {
        if (piece.isMrX())
            throw new IllegalArgumentException("Expected detective");
        return board.accept(new AiBoard.Visitor<>() {
            @Override
            public Integer visit(StandardAiBoard board) {
                return StandardAiBoardBFS.getInstance().minimumDistance(board, piece);
            }

            @Override
            public Integer visit(LocationAiBoard board) {
                return LocationAiBoardBFS.getInstance().minimumDistance(board, piece);
            }
        });
    }


}
