package hu.nye.progtech.rushhour.model;

import java.util.Objects;

public class Player {

    private final String name;
    private final PlayerType type;
    private final String targetVehicleId;

    public Player(
            String name,
            PlayerType type,
            String targetVehicleId
    ) {

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(
                    "Player name must not be blank."
            );
        }

        if (targetVehicleId == null || targetVehicleId.isBlank()) {
            throw new IllegalArgumentException(
                    "Target vehicle ID must not be blank."
            );
        }

        this.name = name;
        this.type = Objects.requireNonNull(type);
        this.targetVehicleId = targetVehicleId;
    }

    public String getName() {
        return name;
    }

    public PlayerType getType() {
        return type;
    }

    public String getTargetVehicleId() {
        return targetVehicleId;
    }

    @Override
    public boolean equals(Object object) {

        if (this == object) {
            return true;
        }

        if (!(object instanceof Player player)) {
            return false;
        }

        return name.equals(player.name)
                && type == player.type
                && targetVehicleId.equals(player.targetVehicleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, targetVehicleId);
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", targetVehicleId='" + targetVehicleId + '\'' +
                '}';
    }
}