package net.improved.improvedscoreboard.v1_20_1;

import io.papermc.paper.adventure.PaperAdventure;
import net.improved.improvedscoreboard.common.Handler;
import net.improved.improvedscoreboard.common.ReflectionHandler;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundSetDisplayObjectivePacket;
import net.minecraft.network.protocol.game.ClientboundSetObjectivePacket;
import net.minecraft.network.protocol.game.ClientboundSetPlayerTeamPacket;
import net.minecraft.network.protocol.game.ClientboundSetScorePacket;
import net.minecraft.server.ServerScoreboard;
import net.minecraft.world.scores.Team;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.Optional;

public class Handler1_20_1 implements Handler {
    private ClientboundSetObjectivePacket initSetObjective(String id, Component name, int method) {
        ClientboundSetObjectivePacket packet = ReflectionHandler.createInstance(ClientboundSetObjectivePacket.class);
        ReflectionHandler.setField(packet, Constants.SET_OBJECTIVE_OBJECTIVE_NAME, id);
        ReflectionHandler.setField(packet, Constants.SET_OBJECTIVE_DISPLAY_NAME, name);
        ReflectionHandler.setField(packet, Constants.SET_OBJECTIVE_RENDER_TYPE, ObjectiveCriteria.RenderType.INTEGER);
        ReflectionHandler.setField(packet, Constants.SET_OBJECTIVE_METHOD, method);
        return packet;
    }

    public void createObjective(Player player, String id, net.kyori.adventure.text.Component title) {
        sendPacket(player, initSetObjective(id, PaperAdventure.asVanilla(title), 0));
    }

    public void removeObjective(Player player, String id) {
        sendPacket(player, initSetObjective(id, Component.empty(), 1));
    }

    public void setTitle(Player player, String id, net.kyori.adventure.text.Component title) {
        sendPacket(player, initSetObjective(id, PaperAdventure.asVanilla(title), 2));
    }

    public void displayScoreboard(Player player, String id) {
        ClientboundSetDisplayObjectivePacket packet = ReflectionHandler.createInstance(ClientboundSetDisplayObjectivePacket.class);
        ReflectionHandler.setField(packet, Constants.SET_DISPLAY_SLOT, 1);
        ReflectionHandler.setField(packet, Constants.SET_DISPLAY_OBJECTIVE_NAME, id);

        sendPacket(player, packet);
    }

    public void setScore(Player player, String id, byte score) {
        sendPacket(player, new ClientboundSetScorePacket(ServerScoreboard.Method.CHANGE, id, Constants.COLORS[score], score));
    }

    public void removeScore(Player player, String id, byte score) {
        sendPacket(player, new ClientboundSetScorePacket(ServerScoreboard.Method.REMOVE, id, Constants.COLORS[score], 0));
    }

    private ClientboundSetPlayerTeamPacket initSetTeam(Component prefix, int method, String id) {
        ClientboundSetPlayerTeamPacket.Parameters parameters = ReflectionHandler.createInstance(ClientboundSetPlayerTeamPacket.Parameters.class);
        ReflectionHandler.setField(parameters, Constants.SET_TEAM_PARAMETERS_DISPLAY_NAME, Component.empty());
        ReflectionHandler.setField(parameters, Constants.SET_TEAM_PARAMETERS_PLAYER_PREFIX, prefix);
        ReflectionHandler.setField(parameters, Constants.SET_TEAM_PARAMETERS_PLAYER_SUFFIX, Component.empty());
        ReflectionHandler.setField(parameters, Constants.SET_TEAM_PARAMETERS_NAMETAG_VISIBILITY, Team.Visibility.ALWAYS.name);
        ReflectionHandler.setField(parameters, Constants.SET_TEAM_PARAMETERS_COLLISION_RULE, Team.CollisionRule.ALWAYS.name);
        ReflectionHandler.setField(parameters, Constants.SET_TEAM_PARAMETERS_COLOR, ChatFormatting.RESET);
        ReflectionHandler.setField(parameters, Constants.SET_TEAM_PARAMETERS_OPTIONS, 0);

        ClientboundSetPlayerTeamPacket packet = ReflectionHandler.createInstance(ClientboundSetPlayerTeamPacket.class);
        ReflectionHandler.setField(packet, Constants.SET_TEAM_METHOD, method);
        ReflectionHandler.setField(packet, Constants.SET_TEAM_NAME, id);
        ReflectionHandler.setField(packet, Constants.SET_TEAM_PARAMETERS, Optional.of(parameters));

        return packet;
    }

    public void createTeam(Player player, String id, net.kyori.adventure.text.Component text, byte score) {
        ClientboundSetPlayerTeamPacket packet = initSetTeam(PaperAdventure.asVanilla(text), 0, id);
        ReflectionHandler.setField(packet, Constants.SET_TEAM_PLAYERS, Collections.singleton(Constants.COLORS[score]));

        sendPacket(player, packet);
    }

    public void updateTeam(Player player, String id, net.kyori.adventure.text.Component text) {
        sendPacket(player, initSetTeam(PaperAdventure.asVanilla(text), 2, id));
    }

    public void removeTeam(Player player, String id) {
        ClientboundSetPlayerTeamPacket packet = ReflectionHandler.createInstance(ClientboundSetPlayerTeamPacket.class);
        ReflectionHandler.setField(packet, Constants.SET_TEAM_METHOD, 1);
        ReflectionHandler.setField(packet, Constants.SET_TEAM_NAME, id);

        sendPacket(player, packet);
    }

    private void sendPacket(Player player, Packet<?> packet) {
        ((CraftPlayer) player).getHandle().connection.send(packet);
    }
}
