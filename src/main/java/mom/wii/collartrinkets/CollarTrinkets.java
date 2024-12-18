package mom.wii.collartrinkets;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CollarTrinkets implements ModInitializer {
    public static final String MOD_ID = "collartrinkets";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("collar trinkets common");
        CollarTrinketsItems.initialize();
    }

    public static Identifier id(String id) {
        return new Identifier(MOD_ID, id);
    }
}
