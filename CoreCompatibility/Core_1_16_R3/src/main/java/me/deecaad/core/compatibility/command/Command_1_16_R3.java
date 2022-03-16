package me.deecaad.core.compatibility.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import me.deecaad.core.commands.wrappers.Column;
import me.deecaad.core.commands.wrappers.Location2d;
import me.deecaad.core.commands.wrappers.Rotation;
import me.deecaad.core.utils.ReflectionUtil;
import net.minecraft.server.v1_16_R3.*;
import org.apache.commons.lang.math.DoubleRange;
import org.apache.commons.lang.math.IntRange;
import org.bukkit.Axis;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.advancement.Advancement;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.command.CommandSender;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.craftbukkit.v1_16_R3.CraftLootTable;
import org.bukkit.craftbukkit.v1_16_R3.CraftParticle;
import org.bukkit.craftbukkit.v1_16_R3.CraftServer;
import org.bukkit.craftbukkit.v1_16_R3.CraftSound;
import org.bukkit.craftbukkit.v1_16_R3.block.data.CraftBlockData;
import org.bukkit.craftbukkit.v1_16_R3.enchantments.CraftEnchantment;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_16_R3.potion.CraftPotionEffectType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.loot.LootTable;
import org.bukkit.potion.PotionEffectType;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@SuppressWarnings({"rawtypes", "unchecked"})
public class Command_1_16_R3 implements CommandCompatibility {

    public static final MinecraftServer SERVER = ((CraftServer) Bukkit.getServer()).getServer();

    @Override
    public SimpleCommandMap getCommandMap() {
        return SERVER.server.getCommandMap();
    }

    @Override
    public void resendCommandRegistry(Player player) {

    }

    @Override
    public CommandSender getCommandSender(CommandContext<Object> context) {
        CommandListenerWrapper source = (CommandListenerWrapper) context.getSource();
        return source.getBukkitSender();
    }

    @Override
    public CommandSender getCommandSenderRaw(Object nms) {
        return ((CommandListenerWrapper) nms).getBukkitSender();
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public CommandDispatcher<Object> getCommandDispatcher() {
        return (CommandDispatcher) SERVER.vanillaCommandDispatcher.a();
    }

    @Override
    public ArgumentType<?> angle() {
        return ArgumentAngle.a();
    }

    @Override
    public ArgumentType<?> axis() {
        return ArgumentRotationAxis.a();
    }

    @Override
    public ArgumentType<?> blockPredicate() {
        return ArgumentBlockPredicate.a();
    }

    @Override
    public ArgumentType<?> blockState() {
        return ArgumentTile.a();
    }

    @Override
    public ArgumentType<?> chat() {
        return ArgumentChat.a();
    }

    @Override
    public ArgumentType<?> chatComponent() {
        return ArgumentChatComponent.a();
    }

    @Override
    public ArgumentType<?> chatFormat() {
        return ArgumentChatFormat.a();
    }

    @Override
    public ArgumentType<?> dimension() {
        return ArgumentDimension.a();
    }

    @Override
    public ArgumentType<?> enchantment() {
        return ArgumentEnchantment.a();
    }

    @Override
    public ArgumentType<?> entity() {
        return ArgumentEntity.a();
    }

    @Override
    public ArgumentType<?> entities() {
        return ArgumentEntity.multipleEntities();
    }

    @Override
    public ArgumentType<?> player() {
        return ArgumentEntity.c();
    }

    @Override
    public ArgumentType<?> players() {
        return ArgumentEntity.d();
    }

    @Override
    public ArgumentType<?> entitySummon() {
        return ArgumentEntitySummon.a();
    }

    @Override
    public ArgumentType<?> itemPredicate() {
        return ArgumentItemPredicate.a();
    }

    @Override
    public ArgumentType<?> itemStack() {
        return ArgumentItemStack.a();
    }

    @Override
    public ArgumentType<?> mathOperation() {
        return ArgumentMathOperation.a();
    }

    @Override
    public ArgumentType<?> mobEffect() {
        return ArgumentMobEffect.a();
    }

    @Override
    public ArgumentType<?> nbtCompound() {
        return ArgumentNBTTag.a();
    }

    @Override
    public ArgumentType<?> particle() {
        return ArgumentParticle.a();
    }

    @Override
    public ArgumentType<?> profile() {
        return ArgumentProfile.a();
    }

    @Override
    public ArgumentType<?> rotation() {
        return ArgumentRotation.a();
    }

    @Override
    public ArgumentType<?> scoreboardCriteria() {
        return ArgumentScoreboardCriteria.a();
    }

    @Override
    public ArgumentType<?> scoreboardObjective() {
        return ArgumentScoreboardObjective.a();
    }

    @Override
    public ArgumentType<?> scoreboardSlot() {
        return ArgumentScoreboardSlot.a();
    }

    @Override
    public ArgumentType<?> scoreboardTeam() {
        return ArgumentScoreboardTeam.a();
    }

    @Override
    public ArgumentType<?> scoreholder(boolean single) {
        return single ? ArgumentScoreholder.a() : ArgumentScoreholder.b();
    }

    @Override
    public ArgumentType<?> tag() {
        return ArgumentTag.a();
    }

    @Override
    public ArgumentType<?> time() {
        return ArgumentTime.a();
    }

    @Override
    public ArgumentType<?> uuid() {
        return ArgumentUUID.a();
    }

    @Override
    public ArgumentType<?> location() {
        return ArgumentVec3.a();
    }

    @Override
    public ArgumentType<?> location2() {
        return ArgumentVec2.a();
    }

    @Override
    public ArgumentType<?> block() {
        return ArgumentPosition.a();
    }

    @Override
    public ArgumentType<?> block2() {
        return ArgumentVec2I.a();
    }

    @Override
    public ArgumentType<?> biome() {
        return key(); // this is actually only changed 1.18R2 and up
    }

    @Override
    public ArgumentType<?> key() {
        return ArgumentMinecraftKeyRegistered.a();
    }

    @Override
    public SuggestionProvider<Object> biomeKey() {
        return (SuggestionProvider) CompletionProviders.d;
    }

    @Override
    public SuggestionProvider<Object> recipeKey() {
        return (SuggestionProvider) CompletionProviders.b;
    }

    @Override
    public SuggestionProvider<Object> soundKey() {
        return (SuggestionProvider) CompletionProviders.c;
    }

    @Override
    public SuggestionProvider<Object> entityKey() {
        return (SuggestionProvider) CompletionProviders.e;
    }

    @Override
    public SuggestionProvider<Object> advancementKey() {
        return (context, builder) -> ICompletionProvider.a(SERVER.getAdvancementData().getAdvancements().stream().map(net.minecraft.server.v1_16_R3.Advancement::getName), builder);
    }

    @Override
    public SuggestionProvider<Object> lootKey() {
        return (context, builder) -> ICompletionProvider.a(SERVER.getLootTableRegistry().a(), builder);
    }

    private static NamespacedKey fromResourceLocation(MinecraftKey key) {
        return NamespacedKey.fromString(key.getNamespace() + ":" + key.getKey());
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private CommandContext<CommandListenerWrapper> cast(CommandContext<Object> context) {
        return (CommandContext) context;
    }

    @Override
    public Advancement getAdvancement(CommandContext<Object> context, String key) throws CommandSyntaxException {
        return ArgumentMinecraftKeyRegistered.a(cast(context), key).bukkit;
    }

    @Override
    public float getAngle(CommandContext<Object> context, String key) {
        return ArgumentAngle.a(cast(context), key);
    }

    @Override
    public EnumSet<Axis> getAxis(CommandContext<Object> context, String key) {
        EnumSet<Axis> bukkitAxis = EnumSet.noneOf(Axis.class);
        EnumSet<EnumDirection.EnumAxis> nmsAxis = ArgumentRotationAxis.a(cast(context), key);

        for (EnumDirection.EnumAxis axis : nmsAxis) {
            switch (axis) {
                case X:
                    bukkitAxis.add(Axis.X);
                    break;
                case Y:
                    bukkitAxis.add(Axis.Y);
                    break;
                case Z:
                    bukkitAxis.add(Axis.Z);
                    break;
            }
        }

        return bukkitAxis;
    }

    @Override
    public Biome getBiome(CommandContext<Object> context, String key) {
        return Biome.valueOf(context.getArgument(key, MinecraftKey.class).getKey().toUpperCase(Locale.ROOT));
    }

    @Override
    public Predicate<Block> getBlockPredicate(CommandContext<Object> context, String key) throws CommandSyntaxException {
        Predicate<ShapeDetectorBlock> predicate = ArgumentBlockPredicate.a(cast(context), key);
        return (Block block) -> {
            return predicate.test(new ShapeDetectorBlock(cast(context).getSource().getWorld(),
                    new BlockPosition(block.getX(), block.getY(), block.getZ()), true));
        };
    }

    @Override
    public BlockData getBlockState(CommandContext<Object> context, String key) {
        return CraftBlockData.fromData(ArgumentTile.a(cast(context), key).a());
    }

    @Override
    public World.Environment getDimension(CommandContext<Object> context, String key) throws CommandSyntaxException {
        return ArgumentDimension.a(cast(context), key).getWorld().getEnvironment();
    }

    @Override
    public Enchantment getEnchantment(CommandContext<Object> context, String key) {
        return new CraftEnchantment(ArgumentEnchantment.a(cast(context), key));
    }

    @Override
    public org.bukkit.entity.Entity getEntitySelector(CommandContext<Object> context, String key) throws CommandSyntaxException {
        EntitySelector selector = cast(context).getArgument(key, EntitySelector.class);

        // Setting this field allows non-op users to use entity selectors.
        // We let command permissions handle the permission system. We may have
        // to check if a vanished player can be seen in this list. TODO.
        ReflectionUtil.setField(ReflectionUtil.getField(EntitySelector.class, boolean.class, 3), selector, false);

        CommandListenerWrapper source = (CommandListenerWrapper) context.getSource();
        return selector.a(source).getBukkitEntity();
    }

    @Override
    public List<org.bukkit.entity.Entity> getEntitiesSelector(CommandContext<Object> context, String key) throws CommandSyntaxException {
        EntitySelector selector = cast(context).getArgument(key, EntitySelector.class);

        // Setting this field allows non-op users to use entity selectors.
        // We let command permissions handle the permission system. We may have
        // to check if a vanished player can be seen in this list. TODO.
        ReflectionUtil.setField(ReflectionUtil.getField(EntitySelector.class, boolean.class, 3), selector, false);

        CommandListenerWrapper source = (CommandListenerWrapper) context.getSource();
        return selector.getEntities(source).stream()
                .map(Entity::getBukkitEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Player getPlayerSelector(CommandContext<Object> context, String key) throws CommandSyntaxException {
        EntitySelector selector = cast(context).getArgument(key, EntitySelector.class);

        // Setting this field allows non-op users to use entity selectors.
        // We let command permissions handle the permission system. We may have
        // to check if a vanished player can be seen in this list. TODO.
        ReflectionUtil.setField(ReflectionUtil.getField(EntitySelector.class, boolean.class, 3), selector, false);

        CommandListenerWrapper source = (CommandListenerWrapper) context.getSource();
        return selector.c(source).getBukkitEntity();
    }

    @Override
    public List<Player> getPlayersSelector(CommandContext<Object> context, String key) throws CommandSyntaxException {
        EntitySelector selector = cast(context).getArgument(key, EntitySelector.class);

        // Setting this field allows non-op users to use entity selectors.
        // We let command permissions handle the permission system. We may have
        // to check if a vanished player can be seen in this list. TODO.
        ReflectionUtil.setField(ReflectionUtil.getField(EntitySelector.class, boolean.class, 3), selector, false);

        CommandListenerWrapper source = (CommandListenerWrapper) context.getSource();
        return selector.d(source).stream()
                .map(EntityPlayer::getBukkitEntity)
                .collect(Collectors.toList());
    }

    @Override
    public EntityType getEntityType(CommandContext<Object> context, String key) throws CommandSyntaxException {
        return EntityType.fromName(EntityTypes.getName(IRegistry.ENTITY_TYPE.get(ArgumentEntitySummon.a(cast(context), key))).getKey());
    }

    @Override
    public DoubleRange getDoubleRange(CommandContext<Object> context, String key) {
        CriterionConditionValue.FloatRange range = cast(context).getArgument(key, CriterionConditionValue.FloatRange.class);
        double min = range.a() == null ? Double.MIN_VALUE : range.a();
        double max = range.b() == null ? Double.MAX_VALUE : range.b();
        return new DoubleRange(min, max);
    }

    @Override
    public IntRange getIntRange(CommandContext<Object> context, String key) {
        CriterionConditionValue.IntegerRange range = cast(context).getArgument(key, CriterionConditionValue.IntegerRange.class);
        int min = range.a() == null ? Integer.MIN_VALUE : range.a();
        int max = range.b() == null ? Integer.MAX_VALUE : range.b();
        return new IntRange(min, max);
    }

    @Override
    public ItemStack getItemStack(CommandContext<Object> context, String key) throws CommandSyntaxException {
        return CraftItemStack.asBukkitCopy(ArgumentItemStack.a(cast(context), key).a(1, false));
    }

    @Override
    public Predicate<ItemStack> getItemStackPredicate(CommandContext<Object> context, String key) throws CommandSyntaxException {
        Predicate<net.minecraft.server.v1_16_R3.ItemStack> predicate = ArgumentItemPredicate.a(cast(context), key);
        return (item) -> predicate.test(CraftItemStack.asNMSCopy(item));
    }

    @Override
    public String getKeyedAsString(CommandContext<Object> context, String key) throws CommandSyntaxException {
        return ArgumentMinecraftKeyRegistered.a(cast(context), key).toString();
    }

    @Override
    public Column getLocation2DBlock(CommandContext<Object> context, String key) throws CommandSyntaxException {
        BlockPosition2D column = ArgumentVec2I.a(cast(context), key);
        World world = cast(context).getSource().getWorld().getWorld();
        return new Column(world, column.a, column.b);
    }

    @Override
    public Location2d getLocation2DPrecise(CommandContext<Object> context, String key) throws CommandSyntaxException {
        Vec2F vector = ArgumentVec2.a(cast(context), key);
        World world = cast(context).getSource().getWorld().getWorld();
        return new Location2d(world, vector.i, vector.j);
    }

    @Override
    public Block getLocationBlock(CommandContext<Object> context, String key) throws CommandSyntaxException {
        BlockPosition block = ArgumentPosition.a(cast(context), key);
        World world = cast(context).getSource().getWorld().getWorld();
        return world.getBlockAt(block.getX(), block.getY(), block.getZ());
    }

    @Override
    public Location getLocationPrecise(CommandContext<Object> context, String key) throws CommandSyntaxException {
        Vec3D vector = ArgumentVec3.a(cast(context), key);
        World world = cast(context).getSource().getWorld().getWorld();
        return new Location(world, vector.x, vector.y, vector.z);
    }

    @Override
    public LootTable getLootTable(CommandContext<Object> context, String key) {
        MinecraftKey minecraft = ArgumentMinecraftKeyRegistered.e(cast(context), key);
        return new CraftLootTable(fromResourceLocation(minecraft), SERVER.getLootTableRegistry().getLootTable(minecraft));
    }

    @Override
    public String getObjective(CommandContext<Object> context, String key) throws IllegalArgumentException, CommandSyntaxException {
        return ArgumentScoreboardObjective.a(cast(context), key).getName();
    }

    @Override
    public String getObjectiveCriteria(CommandContext<Object> context, String key) {
        return ArgumentScoreboardCriteria.a(cast(context), key).getName();
    }

    @Override
    public Particle getParticle(CommandContext<Object> context, String key) {
        return CraftParticle.toBukkit(ArgumentParticle.a(cast(context), key));
    }

    @Override
    public Player getPlayer(CommandContext<Object> context, String key) throws CommandSyntaxException {
        Player target = Bukkit.getPlayer(ArgumentProfile.a(cast(context), key).iterator().next().getId());
        if (target == null)
            throw ArgumentProfile.a.create();

        return target;
    }

    @Override
    public OfflinePlayer getOfflinePlayer(CommandContext<Object> context, String key) throws CommandSyntaxException {
        return Bukkit.getOfflinePlayer(ArgumentProfile.a(cast(context), key).iterator().next().getId());
    }

    @Override
    public PotionEffectType getPotionEffect(CommandContext<Object> context, String key) throws CommandSyntaxException {
        return new CraftPotionEffectType(ArgumentMobEffect.a(cast(context), key));
    }

    @Override
    public Recipe getRecipe(CommandContext<Object> context, String key) throws CommandSyntaxException {
        IRecipe<?> recipe = ArgumentMinecraftKeyRegistered.b(cast(context), key);
        return recipe.toBukkitRecipe();
    }

    @Override
    public Rotation getRotation(CommandContext<Object> context, String key) {
        Vec2F rotation = ArgumentRotation.a(cast(context), key).b(cast(context).getSource());
        return new Rotation(rotation.i, rotation.j);
    }

    @Override
    public Sound getSound(CommandContext<Object> context, String key) {
        return CraftSound.getBukkit(IRegistry.SOUND_EVENT.get(ArgumentMinecraftKeyRegistered.e(cast(context), key)));
    }

    @Override
    public String getTeam(CommandContext<Object> context, String key) throws CommandSyntaxException {
        return ArgumentScoreboardTeam.a(cast(context), key).getName();
    }

    @Override
    public int getTime(CommandContext<Object> context, String key) {
        return cast(context).getArgument(key, Integer.class);
    }

    @Override
    public UUID getUUID(CommandContext<Object> context, String key) {
        return ArgumentUUID.a(cast(context), key);
    }

    @Override
    public Map<String, Object> getCompound(CommandContext<Object> context, String key) {
        NBTTagCompound nbt = ArgumentNBTTag.a(context, key);
        return convertMap(nbt);
    }

    @SuppressWarnings("unchecked")
    private Object convert(NBTBase tag) {
        switch (tag.getTypeId()) {
            case 1: case 2: case 3: case 4:
                return ((NBTNumber) tag).asInt();
            case 5: case 6:
                return ((NBTNumber) tag).asDouble();
            case 8:
                return ((NBTTagString) tag).toString();
            case 7: case 11: case 12: case 9:
                return convertList((NBTList<NBTBase>) tag);
            case 10:
                return convertMap((NBTTagCompound) tag);
            default:
                throw new IllegalStateException("Unexpected value: " + tag);
        }
    }

    private Map<String, Object> convertMap(NBTTagCompound nbt) {
        Map<String, Object> temp = new HashMap<>();

        for (String key : nbt.getKeys()) {
            Object value = convert(Objects.requireNonNull(nbt.get(key)));
            temp.put(key, value);
        }

        return temp;
    }

    private List<Object> convertList(NBTList<NBTBase> values) {
        return values.stream().map(this::convert).collect(Collectors.toList());
    }
}