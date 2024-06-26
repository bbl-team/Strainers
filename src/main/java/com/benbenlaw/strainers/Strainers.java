package com.benbenlaw.strainers;

import com.benbenlaw.strainers.block.ModBlocks;
import com.benbenlaw.strainers.block.entity.ModBlockEntities;
import com.benbenlaw.strainers.config.StrainersConfigFile;
import com.benbenlaw.strainers.fluid.ModFluids;
import com.benbenlaw.strainers.item.ModCreativeTab;
import com.benbenlaw.strainers.item.ModItems;
import com.benbenlaw.strainers.recipe.ModRecipes;
import com.benbenlaw.strainers.screen.ModMenuTypes;
import com.benbenlaw.strainers.screen.custom.WoodenStrainerScreen;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(Strainers.MOD_ID)
public class Strainers {

    public static final String MOD_ID = "strainers";
    private static final Logger LOGGER = LogManager.getLogger();


    public Strainers(IEventBus modEventBus) {
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModCreativeTab.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);
        ModRecipes.register(modEventBus);

        modEventBus.addListener(this::registerCapabilities);


        ModFluids.register(modEventBus);

        modEventBus.addListener(this::commonSetup);

        ModLoadingContext.get().getActiveContainer().registerConfig(ModConfig.Type.COMMON, StrainersConfigFile.SPEC, "strainers.toml");

    }

    public void registerCapabilities(RegisterCapabilitiesEvent event) {
        ModBlockEntities.registerCapabilities(event);
    }

    public void commonSetup(RegisterPayloadHandlersEvent event) {
    //    ModMessages.registerNetworking(event);
    }


    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void registerScreens(RegisterMenuScreensEvent event) {

            event.register(ModMenuTypes.WOODEN_STRAINER_MENU.get(), WoodenStrainerScreen::new);


        }

        /*
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {

            event.enqueueWork(() -> {

                MenuScreens.register(ModMenuTypes.WOODEN_STRAINER_MENU.get(), WoodenStrainerScreen::new);

                ItemBlockRenderTypes.setRenderLayer(ModFluids.SOURCE_ERODING_WATER.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.FLOWING_ERODING_WATER.get(), RenderType.translucent());

                ItemBlockRenderTypes.setRenderLayer(ModFluids.SOURCE_PURIFIED_WATER.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.FLOWING_PURIFIED_WATER.get(), RenderType.translucent());

            });
        }

         */
    }
}

