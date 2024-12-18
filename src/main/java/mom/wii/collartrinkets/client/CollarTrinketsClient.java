package mom.wii.collartrinkets.client;

import io.wispforest.accessories.api.client.AccessoriesRendererRegistry;
import mom.wii.collartrinkets.CollarTrinkets;
import mom.wii.collartrinkets.CollarTrinketsItems;
import mom.wii.collartrinkets.item.CollarItem;
import net.fabricmc.api.ClientModInitializer;

public class CollarTrinketsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        CollarTrinkets.LOGGER.info("collar trinkets client");

        AccessoriesRendererRegistry.registerRenderer(CollarTrinketsItems.COLLAR, CollarItem.Renderer::new);
    }
}
