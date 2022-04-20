package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy;

import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.model.Piece;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.aiBoard.AiBoardV2;

import java.util.*;

/**
 * An inner node has children.
 */
public class GameTreeInnerNode extends GameTreeNode {
    private final Set<GameTreeNode> children;

    public GameTreeInnerNode(AiBoardV2 board, int depth) {
        this.children = createChildren(board, depth);
    }

    private Set<GameTreeNode> createChildren(AiBoardV2 board, int depth) {
        if (depth < 0)
            throw new IllegalArgumentException();
        Set<GameTreeNode> children = new HashSet<>();
        List<AiBoardV2> newBoards = generateNewBoards(board);
        for (AiBoardV2 newBoard : newBoards) {
            GameTreeNode child = createChild(newBoard, depth);
            children.add(child);
        }
        return children;
    }

    // possible boards reached by either moving MrX or all the detectives
    private List<AiBoardV2> generateNewBoards(AiBoardV2 board) {
        List<AiBoardV2> newBoards;
        if (isMrXMove(board))
            newBoards = moveMrX(board);
        else
            newBoards = moveDetectives(board);
        return newBoards;
    }

    private boolean isMrXMove(AiBoardV2 board) {
        Move move = getAnyMove(board);
        Piece piece = move.commencedBy();
        return piece.isMrX();
    }

    private Move getAnyMove(AiBoardV2 board) {
        Optional<Move> move = board.getAvailableMoves().stream()
                .findAny();
        if (move.isEmpty())
            throw new NoSuchElementException("No moves");
        return move.get();
    }

    // possible boards by moving MrX
    private List<AiBoardV2> moveMrX(AiBoardV2 board) {
        List<AiBoardV2> boards = new ArrayList<>();
        for (Move move : board.getAvailableMoves()) {
            AiBoardV2 newBoard = board.applyMove(move);
            boards.add(newBoard);
        }
        return boards;
    }

    // possible boards by moving detectives
    private List<AiBoardV2> moveDetectives(AiBoardV2 board) {
        // TODO: implement me
        return null;
    }

    private GameTreeNode createChild(AiBoardV2 board, int depth) {
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
