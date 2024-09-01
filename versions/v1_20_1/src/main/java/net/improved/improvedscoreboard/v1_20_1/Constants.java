package net.improved.improvedscoreboard.v1_20_1;

import net.minecraft.ChatFormatting;

import java.util.Arrays;

public interface Constants {
    String[] COLORS = Arrays.stream(ChatFormatting.values()).map(ChatFormatting::toString).toArray(String[]::new);

    String SET_OBJECTIVE_OBJECTIVE_NAME = "d";
    String SET_OBJECTIVE_DISPLAY_NAME = "e";
    String SET_OBJECTIVE_RENDER_TYPE = "f";
    String SET_OBJECTIVE_METHOD = "g";

    String SET_DISPLAY_SLOT = "a";
    String SET_DISPLAY_OBJECTIVE_NAME = "b";

    String SET_TEAM_METHOD = "h";
    String SET_TEAM_NAME = "i";
    String SET_TEAM_PLAYERS = "j";
    String SET_TEAM_PARAMETERS = "k";

    String SET_TEAM_PARAMETERS_DISPLAY_NAME = "a";
    String SET_TEAM_PARAMETERS_PLAYER_PREFIX = "b";
    String SET_TEAM_PARAMETERS_PLAYER_SUFFIX = "c";
    String SET_TEAM_PARAMETERS_NAMETAG_VISIBILITY = "d";
    String SET_TEAM_PARAMETERS_COLLISION_RULE = "e";
    String SET_TEAM_PARAMETERS_COLOR = "f";
    String SET_TEAM_PARAMETERS_OPTIONS = "g";
}
