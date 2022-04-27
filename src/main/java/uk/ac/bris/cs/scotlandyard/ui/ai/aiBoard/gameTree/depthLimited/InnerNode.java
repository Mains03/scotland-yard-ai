package uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.depthLimited;

import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.model.Piece;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.AiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.DetectiveMoveBoardFactory;

import java.util.*;

/**
 * Node with children.
 */
public class InnerNode implements Node {
    private final Set<Node> children;

    public InnerNode(AiBoard board, int depth) {
        this.children = createChildren(board, depth);
    }

    private Set<Node> createChildren(AiBoard board, int depth) {
        if (depth < 1)
            throw new IllegalArgumentException();
        Set<Node> children = new HashSet<>();
        if (board.getWinner().size() == 0) {
            Set<AiBoard> newBoards = generateNewBoards(board);
            for (AiBoard newBoard : newBoards) {
                Node child = createChild(newBoard, depth);
                children.add(child);
            }
        }
        return children;
    }

    // possible boards reached by either moving MrX or all the detectives
    private Set<AiBoard> generateNewBoards(AiBoard board) {
        Set<AiBoard> newBoards;
        if (isMrXMove(board))
            newBoards = moveMrX(board);
        else
            newBoards = moveDetectives(board);
        return newBoards;
    }

    private boolean isMrXMove(AiBoard board) {
        Move move = getAnyMove(board);
        Piece piece = move.commencedBy();
        return piece.isMrX();
    }

    private Move getAnyMove(AiBoard board) {
        Optional<Move> move = board.getAvailableMoves().stream()
                .findAny();
        if (move.isEmpty())
            throw new NoSuchElementException("No moves");
        return move.get();
    }

    // possible boards by moving MrX
    private Set<AiBoard> moveMrX(AiBoard board) {
        Set<AiBoard> boards = new HashSet<>();
        for (Move move : board.getAvailableMoves()) {
            AiBoard newBoard = (AiBoard) board.advance(move);
            boards.add(newBoard);
        }
        return boards;
    }

    // possible boards by moving detectives
    private Set<AiBoard> moveDetectives(AiBoard board) {
        DetectiveMoveBoardFactory factory = DetectiveMoveBoardFactory.getInstance();
        return board.accept(factory);
    }

    private Node createChild(AiBoard board, int depth) {
        Node child;
        if (depth == 1)
            child = new LeafNode(board);
        else
            child = new InnerNode(board, depth-1);
        return child;
    }

    @Override
    public <T> T accept(Node.Visitor<T> visitor) {
        return visitor.visit(this);
    }

    public Set<Node> getChildren() {
        return Set.copyOf(children);
    }
}
