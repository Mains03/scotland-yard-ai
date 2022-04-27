package uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree;

import com.google.common.collect.ImmutableList;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.AiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.LocationAiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.StandardAiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.evaluation.EvaluationStrategy;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.evaluation.MinimumDistanceEvaluation;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link AiBoard.Visitor<List<Node>>} used to create a game tree.
 */
public class GameTree implements AiBoard.Visitor<List<Node>> {
    private EvaluationStrategy strategy = MinimumDistanceEvaluation.getInstance();

    // each Node corresponds to a MrX move
    public final ImmutableList<Node> children;

    public GameTree(AiBoard board) {
        children = ImmutableList.copyOf(
                board.accept(this)
        );
    }

    @Override
    public List<Node> visit(StandardAiBoard board) {
        return visit((AiBoard) board);
    }

    @Override
    public List<Node> visit(LocationAiBoard board) {
        return visit((AiBoard) board);
    }

    private List<Node> visit(AiBoard board) {
        List<Node> gameTreeNodes = new ArrayList<>();
        for (Move move : board.getAvailableMoves()) {
            AiBoard newBoard = (AiBoard) board.advance(move);
            Node node = new LeafNodeWithMove(newBoard, move);
            gameTreeNodes.add(node);
        }
        return gameTreeNodes;
    }
}
