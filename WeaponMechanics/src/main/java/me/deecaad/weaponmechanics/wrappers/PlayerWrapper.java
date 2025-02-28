package me.deecaad.weaponmechanics.wrappers;

import me.deecaad.core.compatibility.CompatibilityAPI;
import me.deecaad.core.file.Configuration;
import me.deecaad.core.utils.NumberUtil;
import me.deecaad.weaponmechanics.WeaponMechanics;
import org.bukkit.entity.Player;

import org.jetbrains.annotations.Nullable;

/**
 * Wraps a {@link Player} object to simplify per-player data/methods that
 * are used by WeaponMechanics. Also contains useful API functionality for
 * plugins who want to check if an entity is scoped, reloading, etc.
 */
public class PlayerWrapper extends EntityWrapper {

    private final Player player;
    private long lastRightClick;
    private long lastStartSneak;
    private long lastWeaponDrop;
    private long lastInventoryDrop;
    private MessageHelper messageHelper;
    private long lastAmmoConvert;
    private StatsData statsData;

    public PlayerWrapper(Player player) {
        super(player);
        this.player = player;
        Configuration config = WeaponMechanics.getBasicConfigurations();
        if (config.getBool("Database.Enable", true)) {
            statsData = new StatsData(player.getUniqueId());
        }
    }

    public Player getPlayer() {
        return this.player;
    }

    public void rightClicked() {
        lastRightClick = System.currentTimeMillis();
    }

    public boolean didDoubleSneak() {
        if (lastStartSneak == 0) {
            lastStartSneak = System.currentTimeMillis();

            // There hasn't yet been last sneak -> false
            return false;
        }

        if (!NumberUtil.hasMillisPassed(lastStartSneak, 500)) {
            // Double sneaked
            lastStartSneak = 0; // Reset the timer
            return true;
        }

        lastStartSneak = System.currentTimeMillis();
        return false;
    }

    public void droppedWeapon() {
        lastWeaponDrop = System.currentTimeMillis();
    }

    public long getLastDropWeaponTime() {
        return lastWeaponDrop;
    }

    public void inventoryDrop() {
        lastInventoryDrop = System.currentTimeMillis();
    }

    public long getLastInventoryDropTime() {
        return lastInventoryDrop;
    }

    public MessageHelper getMessageHelper() {
        return messageHelper == null ? messageHelper = new MessageHelper() : messageHelper;
    }

    public void convertedAmmo() {
        lastAmmoConvert = System.currentTimeMillis();
    }

    public long getLastAmmoConvert() {
        return lastAmmoConvert;
    }

    @Override
    public boolean isRightClicking() {

        // When a player is blocking with a shield or a sword, then they are
        // definitely right-clicking.
        if (player.isBlocking())
            return true;

        int ping = CompatibilityAPI.getCompatibility().getPing(player);
        if (ping > 215) {
            // Ping was more than 215 so lets take player's ping in account
            // when checking if it is still right clicking
            return !NumberUtil.hasMillisPassed(lastRightClick, ping + 15);
        }
        return !NumberUtil.hasMillisPassed(lastRightClick, 215);
    }

    @Override
    public boolean isSneaking() {
        return player.isSneaking();
    }

    @Override
    public boolean isSprinting() {
        return player.isSprinting();
    }

    @Override
    public boolean isPlayer() {
        return true;
    }

    /**
     * @return the stats data or null if disabled or not yet synced
     */
    @Nullable
    public StatsData getStatsData() {
        return (statsData == null || !statsData.isSync()) ? null : statsData;
    }

    /**
     * This method might not be safe to use nor should be used. Use {@link #getStatsData()} instead.
     * By unsafe I mean that this object might not have been synced yet.
     *
     * @return the unsafe stats data object
     */
    public StatsData getStatsDataUnsafe() {
        return statsData;
    }
}
