package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.structures;

import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.model.Piece;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.StandardAiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.visitor.GameTreeVisitor;
import uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.detectiveMoveGeneration.DetectiveMoveGeneration;
import uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.detectiveMoveGeneration.SimpleDetectiveMoveGen;

import java.util.*;

/**
 * Node with children.
 * Assumed to be after a detective moves
 * Go to {@link InnerNodeWithMrXMove} for after a mrX move
 */
public class InnerNode implements GameTreeNode {
    private final Set<GameTreeNode> children;

    public InnerNode(StandardAiBoard board, int depth) {
        this.children = createChildren(board, depth);
    }

    private Set<GameTreeNode> createChildren(StandardAiBoard board, int depth) {
        if (depth < 1)
            throw new IllegalArgumentException();
        Set<GameTreeNode> children = new HashSet<>();
        if (!isGameOver(board)) {
            children = new HashSet<>();
            Set<StandardAiBoard> newBoards = generateNewBoards(board);
            for (StandardAiBoard newBoard : newBoards) {
                GameTreeNode child = createChild(newBoard, depth);
                children.add(child);
            }
        }
        return children;
    }

    private boolean isGameOver(StandardAiBoard board) {
        return board.getWinner().size() != 0;
    }

    // possible boards reached by either moving MrX or all the detectives
    private Set<StandardAiBoard> generateNewBoards(StandardAiBoard board) {
        Set<StandardAiBoard> newBoards;
        if (isMrXMove(board))
            newBoards = moveMrX(board);
        else
            newBoards = moveDetectives(board);
        return newBoards;
    }

    // Check it's MrX's turn to move
    private boolean isMrXMove(StandardAiBoard board) {
        Move move = getAnyMove(board);
        Piece piece = move.commencedBy();
        return piece.isMrX();
    }

    private Move getAnyMove(StandardAiBoard board) {
        Optional<Move> move = board.getAvailableMoves().stream()
                .findAny();
        if (move.isEmpty())
            throw new NoSuchElementException("No moves");
        return move.get();
    }

    // possible boards by moving MrX
    private Set<StandardAiBoard> moveMrX(StandardAiBoard board) {
        Set<StandardAiBoard> boards = new HashSet<>();
        for (Move move : board.getAvailableMoves()) {
            StandardAiBoard newBoard = (StandardAiBoard) board.advance(move);
            boards.add(newBoard);
        }
        return boards;
    }

    // possible boards by moving detectives
    private Set<StandardAiBoard> moveDetectives(StandardAiBoard board) {
        DetectiveMoveGeneration moveGeneration = getDetectiveMoveGeneration();
        return moveGeneration.moveDetectives(board);
    }

    // Returns a singleton instance of a move generation strategy
    private DetectiveMoveGeneration getDetectiveMoveGeneration() {
        return SimpleDetectiveMoveGen.getInstance();
        //return new CombinationDetectiveMoveGen.getInstance();
    }

    private GameTreeNode createChild(StandardAiBoard board, int depth) {
        GameTreeNode child;
        if (depth == 1)
            child = new LeafNode(board);
        else
            child = new InnerNode(board, depth-1);
        return child;
    }

    @Override
    public int accept(GameTreeVisitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public Optional<Move> getMrXMove() {
        // MrX didn't make a move to get here
        return Optional.empty();
    }

    public Set<GameTreeNode> getChildren() {
        return Set.copyOf(children);
    }
}
