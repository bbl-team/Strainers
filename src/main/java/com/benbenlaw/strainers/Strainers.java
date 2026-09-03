package com.benbenlaw.strainers;

import com.benbenlaw.strainers.block.StrainersBlocks;
import com.benbenlaw.strainers.block.StrainersBlockEntities;
import com.benbenlaw.strainers.block.StrainersCapabilities;
import com.benbenlaw.strainers.block.entity.renderer.StrainerBlockEntityRenderer;
import com.benbenlaw.strainers.config.StrainersConfig;
import com.benbenlaw.strainers.fluid.StrainersFluids;
import com.benbenlaw.strainers.item.StrainersCreativeTab;
import com.benbenlaw.strainers.item.StrainersDataComponents;
import com.benbenlaw.strainers.item.StrainersItems;
import com.benbenlaw.strainers.loot.StrainersLootConditions;
import com.benbenlaw.strainers.loot.StrainersLootModifiers;
import com.benbenlaw.strainers.network.StrainersMessages;
import com.benbenlaw.strainers.recipe.StrainersRecipes;
import com.benbenlaw.strainers.screen.StrainersMenuTypes;
import com.benbenlaw.strainers.screen.custom.StrainerDropConfigScreen;
import com.benbenlaw.strainers.screen.custom.StrainerScreen;
import net.minecraft.resources.Identifier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(Strainers.MOD_ID)
public class Strainers {

    public static final String MOD_ID = "strainers";
    private static final Logger LOGGER = LogManager.getLogger();


    public Strainers(IEventBus modEventBus, final ModContainer modContainer) {

        StrainersBlocks.BLOCKS.register(modEventBus);
        StrainersItems.ITEMS.register(modEventBus);
        StrainersBlockEntities.BLOCK_ENTITIES.register(modEventBus);
        StrainersFluids.FLUIDS.register(modEventBus);
        StrainersDataComponents.COMPONENTS.register(modEventBus);
        StrainersCreativeTab.CREATIVE_MODE_TABS.register(modEventBus);
        StrainersMenuTypes.MENUS.register(modEventBus);
        StrainersRecipes.SERIALIZER.register(modEventBus);
        StrainersRecipes.TYPES.register(modEventBus);
        StrainersLootConditions.LOOT_CONDITION_SERIALIZERS.register(modEventBus);
        StrainersLootModifiers.LOOT_MODIFIER_SERIALIZERS.register(modEventBus);

        modContainer.registerConfig(ModConfig.Type.COMMON, StrainersConfig.SPEC, "bbl/strainers/common.toml");

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::registerCapabilities);
        modEventBus.addListener(this::networkingSetup);

    }

    public void registerCapabilities(RegisterCapabilitiesEvent event) {
        StrainersCapabilities.registerCapabilities(event);
    }

    public void commonSetup(RegisterPayloadHandlersEvent event) {

    }

    @EventBusSubscriber(modid = MOD_ID)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(StrainersBlockEntities.STRAINER_BLOCK_ENTITY.get(), StrainerBlockEntityRenderer::new);
        }

        @SubscribeEvent
        public static void registerScreens(RegisterMenuScreensEvent event) {
            event.register(StrainersMenuTypes.WOODEN_STRAINER_MENU.get(), StrainerScreen::new);
            event.register(StrainersMenuTypes.STRAINER_DROP_CONFIG_MENU.get(), StrainerDropConfigScreen::new);
        }
    }

    public void networkingSetup(RegisterPayloadHandlersEvent event) {
        StrainersMessages.registerNetworking(event);
    }

    public static Identifier identifier(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }

}

