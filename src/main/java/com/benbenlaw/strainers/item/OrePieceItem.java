package com.benbenlaw.strainers.item;

import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;

public class OrePieceItem extends Item {
    public OrePieceItem(Properties properties) {
        super(properties);
    }

    @Override
    public Component getName(ItemStack itemStack) {
        String oreName = itemStack.get(StrainersDataComponents.ORE_TYPE.get());

        if (oreName == null) return Component.translatable("THIS IS BROKEN REPORT TO DEV");

        String[] parts = oreName.split("_");
        StringBuilder capitalized = new StringBuilder();

        for (int i = 0; i < parts.length; i++) {
            String part = parts[i];

            if (part.isEmpty()) continue;

            capitalized.append(part.substring(0, 1).toUpperCase());
            capitalized.append(part.substring(1));

            if (i < parts.length - 1) {
                capitalized.append(" ");
            }
        }

        return Component.translatable("item.strainers.ore_piece", capitalized.toString());
    }

    public static ItemStackTemplate createOrePiece(String oreName) {
        DataComponentPatch patch = DataComponentPatch.builder()
                .set(StrainersDataComponents.ORE_TYPE.get(), oreName)
                .build();

        return new ItemStackTemplate(StrainersItems.ORE_PIECE.get(), patch);
    }
}
