package com.benbenlaw.strainers.screen;

import com.benbenlaw.strainers.Strainers;
import com.benbenlaw.strainers.screen.custom.StrainerDropConfigMenu;
import com.benbenlaw.strainers.screen.custom.StrainerMenu;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class StrainersMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =  DeferredRegister.create(BuiltInRegistries.MENU, Strainers.MOD_ID);

    public static final DeferredHolder<MenuType<?>, MenuType<StrainerMenu>> WOODEN_STRAINER_MENU =
            MENUS.register("wooden_strainer_menu", () -> IMenuTypeExtension.create(StrainerMenu::new));

    public static final DeferredHolder<MenuType<?>, MenuType<StrainerDropConfigMenu>> STRAINER_DROP_CONFIG_MENU =
            MENUS.register("strainer_drop_config_menu", () -> IMenuTypeExtension.create(StrainerDropConfigMenu::new));

}
