package com.benbenlaw.strainers.data;

import com.benbenlaw.strainers.Strainers;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;
import org.jetbrains.annotations.NotNull;

public class StrainersLangProvider extends LanguageProvider {

    public StrainersLangProvider(PackOutput output) {
        super(output, Strainers.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add("itemGroup.strainers", "Strainers");

        //Blocks


        //JEI
        add("jei.strainers.strainer", "Strainer");
        add("jei.strainers.chance", "Chance: %s%%");
        add("jei.strainers.additional", "Additional %s%% per level above minimum mesh tier");

    }

    @Override
    public @NotNull String getName() {
        return Strainers.MOD_ID + " Language Provider";
    }
}
