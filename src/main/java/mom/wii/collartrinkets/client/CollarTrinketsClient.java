package mom.wii.collartrinkets.client;

import io.wispforest.accessories.api.client.AccessoriesRendererRegistry;
import mom.wii.collartrinkets.CollarTrinkets;
import mom.wii.collartrinkets.CollarTrinketsItems;
import mom.wii.collartrinkets.item.CollarItem;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;

public class CollarTrinketsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        CollarTrinkets.LOGGER.info("collar trinkets client");

        AccessoriesRendererRegistry.registerRenderer(CollarTrinketsItems.COLLAR, CollarItem.Renderer::new);

        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> CollarTrinketsItems.COLLAR.getColor(stack), CollarTrinketsItems.COLLAR);
    }
}
