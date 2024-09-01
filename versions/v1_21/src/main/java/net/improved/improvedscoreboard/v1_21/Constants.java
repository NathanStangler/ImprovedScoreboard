package net.improved.improvedscoreboard.v1_21;

import net.improved.improvedscoreboard.common.ReflectionHandler;
import net.minecraft.network.chat.numbers.NumberFormat;
import net.minecraft.network.chat.numbers.StyledFormat;

import java.util.Arrays;
import java.util.Optional;

public interface Constants {
    Enum<?>[] CHAT_FORMATTING = ReflectionHandler.invoke(ReflectionHandler.getClazz("net.minecraft.EnumChatFormat"), "values");
    String[] COLORS = Arrays.stream(CHAT_FORMATTING).map(Enum::toString).toArray(String[]::new);
    Enum<?> RESET = CHAT_FORMATTING[21];
    Optional<NumberFormat> NUMBER_FORMAT = Optional.of(StyledFormat.SIDEBAR_DEFAULT);

    String SET_OBJECTIVE_OBJECTIVE_NAME = "objectiveName";
    String SET_OBJECTIVE_DISPLAY_NAME = "displayName";
    String SET_OBJECTIVE_RENDER_TYPE = "renderType";
    String SET_OBJECTIVE_NUMBER_FORMAT = "numberFormat";
    String SET_OBJECTIVE_METHOD = "method";

    String SET_DISPLAY_SLOT = "slot";
    String SET_DISPLAY_OBJECTIVE_NAME = "objectiveName";

    String SET_TEAM_METHOD = "method";
    String SET_TEAM_NAME = "name";
    String SET_TEAM_PLAYERS = "players";
    String SET_TEAM_PARAMETERS = "parameters";

    String SET_TEAM_PARAMETERS_DISPLAY_NAME = "displayName";
    String SET_TEAM_PARAMETERS_PLAYER_PREFIX = "playerPrefix";
    String SET_TEAM_PARAMETERS_PLAYER_SUFFIX = "playerSuffix";
    String SET_TEAM_PARAMETERS_NAMETAG_VISIBILITY = "nametagVisibility";
    String SET_TEAM_PARAMETERS_COLLISION_RULE = "collisionRule";
    String SET_TEAM_PARAMETERS_COLOR = "color";
    String SET_TEAM_PARAMETERS_OPTIONS = "options";
}
