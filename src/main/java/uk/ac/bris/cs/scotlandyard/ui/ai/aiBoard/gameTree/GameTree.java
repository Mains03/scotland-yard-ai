package uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree;

import com.google.common.collect.ImmutableList;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.AiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.LocationAiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.PotentialDetectiveLocationsAiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.StandardAiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.evaluation.EvaluationStrategy;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.evaluation.MinimumDistanceEvaluation;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link AiBoard.Visitor<List<Node>>} used to create a game tree.
 */
public class GameTree {
    private EvaluationStrategy strategy = MinimumDistanceEvaluation.getInstance();

    // each Node corresponds to a MrX move
    public final ImmutableList<Node> children;

    public GameTree(List<Node> children) {
        this.children = ImmutableList.copyOf(children);
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
