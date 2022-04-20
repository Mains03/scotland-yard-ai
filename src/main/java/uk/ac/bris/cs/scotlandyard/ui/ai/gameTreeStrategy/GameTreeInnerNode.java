package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy;

import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.model.Piece;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.aiBoard.AiBoard;

import java.util.*;

/**
 * An inner node has children.
 */
public class GameTreeInnerNode extends GameTreeNode {
    private final Set<GameTreeNode> children;

    public GameTreeInnerNode(AiBoard board, int depth) {
        this.children = createChildren(board, depth);
    }

    private Set<GameTreeNode> createChildren(AiBoard board, int depth) {
        if (depth < 0)
            throw new IllegalArgumentException();
        Set<GameTreeNode> children = new HashSet<>();
        List<AiBoard> newBoards = generateNewBoards(board);
        for (AiBoard newBoard : newBoards) {
            GameTreeNode child = createChild(newBoard, depth);
            children.add(child);
        }
        return children;
    }

    // possible boards reached by either moving MrX or all the detectives
    private List<AiBoard> generateNewBoards(AiBoard board) {
        List<AiBoard> newBoards;
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
    private List<AiBoard> moveMrX(AiBoard board) {
        List<AiBoard> boards = new ArrayList<>();
        for (Move move : board.getAvailableMoves()) {
            AiBoard newBoard = board.applyMove(move);
            boards.add(newBoard);
        }
        return boards;
    }

    // possible boards by moving detectives
    private List<AiBoard> moveDetectives(AiBoard board) {
        // TODO: implement me
        return null;
    }

    private GameTreeNode createChild(AiBoard board, int depth) {
        GameTreeNode child;
        if (depth == 0)
            child = new GameTreeLeafNode(board);
        else
            child = new GameTreeInnerNode(board, depth-1);
        return child;
    }

    @Override
    public int accept(GameTreeVisitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public Optional<Move> mrXMoveMade() {
        // MrX didn't make a move to get here
        return Optional.empty();
    }

    public Set<GameTreeNode> getChildren() {
        return Set.copyOf(children);
    }
}
