package me.deecaad.weaponmechanics.mechanics;

import me.deecaad.core.file.SerializeData;
import me.deecaad.core.file.SerializerException;
import me.deecaad.core.mechanics.CastData;
import me.deecaad.core.mechanics.conditions.Condition;
import me.deecaad.weaponmechanics.WeaponMechanicsAPI;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ReloadingCondition extends Condition {

    /**
     * Default constructor for serializer.
     */
    public ReloadingCondition() {
    }

    @Override
    protected boolean isAllowed0(CastData cast) {
        return cast.getTarget() != null && WeaponMechanicsAPI.isReloading(cast.getTarget());
    }

    @Override
    public String getKeyword() {
        return "Reloading";
    }

    @Override
    public @Nullable String getWikiLink() {
        return "https://github.com/WeaponMechanics/MechanicsMain/wiki/SimpleConditions#Reloading";
    }

    @NotNull
    @Override
    public Condition serialize(@NotNull SerializeData data) throws SerializerException {
        return applyParentArgs(data, new ReloadingCondition());
    }
}
