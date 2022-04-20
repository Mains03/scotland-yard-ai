package uk.ac.bris.cs.scotlandyard.ui.ai.staticPositionEvaluationStrategy.strategies;

import com.google.common.collect.ImmutableMap;
import uk.ac.bris.cs.scotlandyard.model.Player;
import uk.ac.bris.cs.scotlandyard.model.ScotlandYard;
import uk.ac.bris.cs.scotlandyard.ui.ai.adapters.aiBoard.AiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.adapters.aiPlayer.AiPlayer;
import uk.ac.bris.cs.scotlandyard.ui.ai.staticPositionEvaluationStrategy.StaticPosEvalStrategy;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class TicketStaticPositionEvaluation implements StaticPosEvalStrategy {
    private final Function<ScotlandYard.Ticket, Integer> ticketEvaluation;

    public TicketStaticPositionEvaluation(
            Function<ScotlandYard.Ticket, Integer> ticketEvaluation
    ) {
        Objects.requireNonNull(ticketEvaluation);
        this.ticketEvaluation = ticketEvaluation;
    }

    @Override
    public int evaluate(AiBoard board) {
        int mrXEval = mrXTicketEvaluation(board.getMrX().asPlayer());
        int detectivesEval = detectivesTicketEvaluation(board.getAiDetectives()
                .stream().map(AiPlayer::asPlayer).toList());
        return mrXEval - detectivesEval;
    }

    private int mrXTicketEvaluation(Player mrX) {
        int evaluation = 0;
        ImmutableMap<ScotlandYard.Ticket, Integer> mrXTickets = mrX.tickets();
        for (ScotlandYard.Ticket ticket : mrXTickets.keySet())
            evaluation += ticketEvaluation.apply(ticket);
        return evaluation;
    }

    private int detectivesTicketEvaluation(List<Player> detectives) {
        int evaluation = 0;
        for (Player detective : detectives) {
            ImmutableMap<ScotlandYard.Ticket, Integer> tickets = detective.tickets();
            for (ScotlandYard.Ticket ticket : tickets.keySet())
                evaluation += ticketEvaluation.apply(ticket);
        }
        return evaluation;
    }
}
