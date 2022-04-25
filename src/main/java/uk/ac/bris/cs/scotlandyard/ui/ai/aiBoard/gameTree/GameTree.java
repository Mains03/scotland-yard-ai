package uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree;

import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.AiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.bestMove.BestMoveStrategy;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.bestMove.GameTreeBestMoveStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GameTree implements Node, BestMoveStrategy {
    private final GameTreeBestMoveStrategy strategy;

    // each Node corresponds to a MrX move
    private List<Node> children;

    public GameTree(GameTreeBestMoveStrategy strategy) {
        this.strategy = Objects.requireNonNull(strategy);
    }

    @Override
    public Move bestMove(AiBoard board) {
        children = createGameTreeNodes(board);
        return accept(strategy);
    }

    private List<Node> createGameTreeNodes(AiBoard board) {
        List<Node> gameTreeNodes = new ArrayList<>();
        for (Move move : board.getAvailableMoves()) {
            Node gameTreeNode = createGameTreeNode(board, move);
            gameTreeNodes.add(gameTreeNode);
        }
        return gameTreeNodes;
    }

    private Node createGameTreeNode(AiBoard board, Move move) {
        AiBoard newBoard = (AiBoard) board.advance(move);
        return new InnerNodeWithMove(newBoard, 1, move);
    }

    public List<Node> getChildren() {
        return List.copyOf(children);
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }
}
