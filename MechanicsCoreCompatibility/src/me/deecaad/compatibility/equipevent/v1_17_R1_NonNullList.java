package me.deecaad.compatibility.equipevent;

import net.minecraft.world.item.ItemStack;
import net.minecraft.core.NonNullList;
import org.bukkit.craftbukkit.v1_17_R1.inventory.CraftItemStack;

import java.util.Arrays;
import java.util.List;

public class v1_17_R1_NonNullList extends NonNullList<ItemStack> {

    private final TriIntConsumer<org.bukkit.inventory.ItemStack, org.bukkit.inventory.ItemStack> consumer;

    public v1_17_R1_NonNullList(int size, TriIntConsumer<org.bukkit.inventory.ItemStack, org.bukkit.inventory.ItemStack> consumer) {
        super(generate(size), ItemStack.b);

        this.consumer = consumer;
    }

    @Override
    public ItemStack set(int index, ItemStack newItem) {
        ItemStack oldItem = get(index);

        // Extra check
        if (!ItemStack.matches(oldItem, newItem)) {
            consumer.accept(CraftItemStack.asBukkitCopy(oldItem), CraftItemStack.asBukkitCopy(newItem), index);
        }

        return super.set(index, newItem);
    }

    private static List<ItemStack> generate(int size) {
        ItemStack[] items = new ItemStack[size];
        Arrays.fill(items, ItemStack.b);
        return Arrays.asList(items);
    }
}
