package shared.definitions;

import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import shared.models.game.ClientModel;
import shared.models.game.Player;
import shared.models.game.TurnTracker;

import java.util.List;


public enum TurnStatus {

    @SerializedName("Rolling")
    ROLLING {
        @Override
        @Nullable
        public TurnResult finishRolling(ClientModel model, boolean moveRobber) {
            if (moveRobber) {
                if (model.getPlayers().stream().anyMatch(Player::hasExcess)) {
                    return new TurnResult(DISCARDING, null);
                } else {
                    return new TurnResult(ROBBING, null);
                }
            }
            return new TurnResult(PLAYING, null);
        }

        @Override
        public boolean canEndGame(List<Player> players) {
            return players.stream().anyMatch(Player::haveWon);
        }
    },

    @SerializedName("Robbing")
    ROBBING {
        @Override
        @Nullable
        public TurnResult finishRobbing() {
            return new TurnResult(PLAYING, null);
        }
    },

    @SerializedName("Playing")
    PLAYING {
        @Override
        @Nullable
        public TurnResult startRobbing() {
            return new TurnResult(ROBBING, null);
        }

        @Override
        @Nullable
        public TurnResult advanceTurn(@NotNull Player p) {
            if (p.haveWon()) {
                return new TurnResult(GAME_OVER, null);
            }
            for (DevCardType d : DevCardType.values()) {
                if (p.getNewDevCards().getOfType(d) > 0) {
                    p.getOldDevCards().setOfType(d, p.getOldDevCards().getOfType(d) + p.getNewDevCards().getOfType(d));
                    p.getNewDevCards().setOfType(d, 0);
                }
            }
            PlayerIndex nextPlayer = p.getPlayerIndex().nextPlayer();
            if (nextPlayer == null) {
                nextPlayer = PlayerIndex.FIRST;
            }
            return new TurnResult(ROLLING, nextPlayer);
        }

        @Override
        public boolean canEndTurn(@NotNull TurnTracker tt, @NotNull Player p) {
            return tt.getCurrentTurn() == p.getPlayerIndex();
        }

        @Override
        public boolean canEndGame(List<Player> players) {
            return players.stream().anyMatch(Player::haveWon);
        }
    },

    @SerializedName("Discarding")
    DISCARDING {
        @Override
        @Nullable
        public TurnResult finishDiscarding(ClientModel cm) {
            for (Player p : cm.getPlayers()) {
                if (!p.hasDiscarded() && p.hasExcess()) {
                    return new TurnResult(this, null);
                }
            }
            for (Player p : cm.getPlayers()) {
                p.setDiscarded(false);
            }
            return new TurnResult(ROBBING, null);
        }
    },

    @SerializedName("FirstRound")
    FIRST_ROUND {
        @Override
        @Nullable
        public TurnResult advanceTurn(@NotNull Player p) {
            PlayerIndex nextPlayer = p.getPlayerIndex().nextPlayer();
            if (nextPlayer == null) {
                return new TurnResult(SECOND_ROUND, PlayerIndex.LAST);
            }
            return new TurnResult(this, nextPlayer);
        }

        @Override
        public boolean canEndTurn(@NotNull TurnTracker tt, @NotNull Player p) {
            return (tt.getCurrentTurn() == p.getPlayerIndex() &&
                    p.getRoads() == Constants.START_ROADS - 1 &&
                    p.getSettlements() == Constants.START_SETTLEMENTS - 1);
        }

        @Override
        public boolean isSetup() {
            return true;
        }

        @Nullable
        @Override
        public TurnResult advanceSetup(ClientModel model) {
            if (currentPlayerHasConstructions(model, 1)) {
                return advanceTurn(model.getPlayer(model.getTurnTracker().getCurrentTurn()));
            }
            return null;
        }
    },

    @SerializedName("SecondRound")
    SECOND_ROUND {
        @Override
        @Nullable
        public TurnResult advanceTurn(@NotNull Player p) {
            // During the second setup round, turns go in reverse
            PlayerIndex nextPlayer = p.getPlayerIndex().previousPlayer();
            if (nextPlayer == null) {
                return new TurnResult(ROLLING, PlayerIndex.FIRST);
            }
            return new TurnResult(this, nextPlayer);
        }

        @Override
        public boolean canEndTurn(@NotNull TurnTracker tt, @NotNull Player p) {
            return (tt.getCurrentTurn() == p.getPlayerIndex() &&
                    p.getRoads() == Constants.START_ROADS - 2 &&
                    p.getSettlements() == Constants.START_SETTLEMENTS - 2);
        }

        @Override
        public boolean isSetup() {
            return true;
        }

        @Nullable
        @Override
        public TurnResult advanceSetup(ClientModel model) {
            if (currentPlayerHasConstructions(model, 2)) {
                return advanceTurn(model.getPlayer(model.getTurnTracker().getCurrentTurn()));
            }
            return null;
        }
    },

    @SerializedName("GameOver")
    GAME_OVER {
        public boolean isEndGame() {
            return true;
        }
    };

    public static boolean currentPlayerHasConstructions(ClientModel model, int number) {
        PlayerIndex current = model.getTurnTracker().getCurrentTurn();
        return (model.getPlayer(current).getSettlements() == number &&
                model.getPlayer(current).getRoads() == number);
    }

    @Nullable
    public TurnResult advanceTurn(@NotNull Player p) {
        return null;
    }

    @Nullable
    public TurnResult endTurn(@NotNull TurnTracker tt, @NotNull Player p) {
        if (!canEndTurn(tt, p)) {
            return null;
        }
        return advanceTurn(p);
    }

    public boolean canEndTurn(@NotNull TurnTracker tt, @NotNull Player p) {
        return false;
    }

    @Nullable
    public TurnResult finishDiscarding(ClientModel cm) {
        throw new IllegalStateException("Cannot finish discarding in the '" +
                this.name() + "' state");
    }

    @Nullable
    public TurnResult finishRobbing() {
        throw new IllegalStateException("Cannot finish robbing in the '" +
                this.name() + "' state");
    }

    @Nullable
    public TurnResult finishRolling(ClientModel model, boolean moveRobber) {
        throw new IllegalStateException("Cannot finish rolling in the '" +
                this.name() + "' state");
    }

    public boolean isEndGame() {
        return false;
    }

    @Nullable
    public TurnResult advanceSetup(ClientModel model) {
        throw new IllegalStateException("Cannot advance setup in the '" +
                this.name() + "' state");
    }

    public boolean isSetup() {
        return false;
    }

    public boolean canEndGame(List<Player> players) {
        return players.stream().anyMatch(Player::haveWon);
    }

    @Nullable
    public TurnResult endGame(List<Player> players) {
        if (canEndGame(players)) {
            return new TurnResult(GAME_OVER, null);
        }
        return null;
    }

    @Nullable
    public TurnResult startRobbing() {
        throw new IllegalStateException("Cannot start robbing in the '" +
                this.name() + "' state");
    }

    public class TurnResult {
        private TurnStatus phase;
        private PlayerIndex turn;

        TurnResult(TurnStatus phase, PlayerIndex player) {
            this.phase = phase;
            this.turn = player;
        }

        @Nullable
        public PlayerIndex getTurn() {
            return turn;
        }

        @Nullable
        public TurnStatus getPhase() {
            return phase;
        }
    }

}
