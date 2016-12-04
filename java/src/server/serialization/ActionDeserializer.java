package server.serialization;

import com.google.gson.*;
import org.jetbrains.annotations.NotNull;
import server.models.AddAIAction;
import server.models.CreateGameAction;
import server.models.JoinGameAction;
import server.models.RegisterAction;
import shared.models.ICommandAction;
import shared.models.moves.*;
import shared.serialization.ModelSerializer;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Stream;

import static shared.utils.ClassUtils.getStackTrace;

public class ActionDeserializer {
    private static final Logger LOGGER = Logger.getLogger("ActionDeserializer");
    private static ActionDeserializer instance;
    public Map<String, Class<? extends ICommandAction>> types = new HashMap<>();

    private ActionDeserializer() {
        Stream.of(
                // Server Actions
                AddAIAction.class,
                CreateGameAction.class,
                JoinGameAction.class,
                RegisterAction.class,

                // Game Actions
                AcceptTradeAction.class,
                BuildCityAction.class,
                BuildRoadAction.class,
                BuildSettlementAction.class,
                BuyDevCardAction.class,
                DiscardCardsAction.class,
                FinishMoveAction.class,
                MaritimeTradeAction.class,
                MonopolyAction.class,
                MonumentAction.class,
                OfferTradeAction.class,
                RoadBuildingAction.class,
                RobPlayerAction.class,
                RollNumberAction.class,
                SendChatAction.class,
                SoldierAction.class,
                YearofPlentyAction.class
        ).forEach(commandType -> {
            try {
                // Could also just directly serialize instance and use that
                final Field typeField = commandType.getDeclaredField("TYPE");
                ICommandAction command = commandType.newInstance();
                typeField.setAccessible(true);
                Object typeVal = typeField.get(command);
                typeField.setAccessible(false);
                String typeStr;
                if (typeVal instanceof String) {
                    typeStr = (String) typeVal;
                } else {
                    JsonElement typeJson = ModelSerializer.getInstance().toJsonTree(
                            typeVal, typeField.getType());
                    if (typeJson.getAsString() == null) {
                        throw new NoSuchFieldException("TYPE field could not serialize to a string");
                    }
                    typeStr = typeJson.getAsString();
                }
                types.put(typeStr, commandType);
            } catch (NoSuchFieldException | IllegalAccessException | InstantiationException e) {
                LOGGER.warning(getStackTrace(e));
            }
        });
    }

    public static ActionDeserializer getInstance() {
        if (instance == null)
            instance = new ActionDeserializer();
        return instance;
    }

    public ICommandAction deserializeAction(@NotNull String s) {
        JsonElement e = new JsonParser().parse(s);
        JsonObject o = e.getAsJsonObject();
        if (o == null) {
            throw new JsonParseException("Action given is not an object!");
        }
        JsonPrimitive p = o.getAsJsonPrimitive("type");
        if (p == null || p.getAsString() == null) {
            throw new JsonParseException("Action given does not contain a string type field");
        }
        String typeStr = p.getAsString();
        Class<? extends ICommandAction> c = types.get(typeStr);
        if (c == null) {
            throw new JsonParseException("No action type with name '" + typeStr + "' is registered");
        }
        return ModelSerializer.getInstance().fromJson(e, c);
    }
}
