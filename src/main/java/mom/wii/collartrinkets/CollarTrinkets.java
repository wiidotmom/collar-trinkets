package mom.wii.collartrinkets;

import eu.pb4.placeholders.api.PlaceholderResult;
import eu.pb4.placeholders.api.Placeholders;
import io.wispforest.accessories.api.slot.SlotEntryReference;
import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Collectors;

public class CollarTrinkets implements ModInitializer {
    public static final String MOD_ID = "collartrinkets";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final TagKey<Item> COLLARS = TagKey.of(RegistryKeys.ITEM, id("collars"));

    public boolean isCollar(ItemStack stack) {
        return stack.streamTags().collect(Collectors.toUnmodifiableSet()).contains(COLLARS);
    }

    @Override
    public void onInitialize() {
        LOGGER.info("collar trinkets common");

        CollarTrinketsItems.initialize();
        CollarTrinketsSounds.initialize();

        Placeholders.register(id("name"), (ctx, arg) -> {
            if (!ctx.hasEntity() || !(ctx.entity() instanceof LivingEntity)) {
                return PlaceholderResult.invalid("Not a living entity!");
            }
            LivingEntity entity = (LivingEntity) ctx.entity();
            if (entity.accessoriesCapability() == null) {
                return PlaceholderResult.invalid("No AccessoriesCapability!");
            }
            SlotEntryReference collar = entity.accessoriesCapability().getFirstEquipped(this::isCollar);
            if (collar.stack().hasCustomName()) {
                return PlaceholderResult.value(collar.stack().getName());
            } else {
                return PlaceholderResult.value(entity.getName());
            }
        });
    }

    public static Identifier id(String id) {
        return new Identifier(MOD_ID, id);
    }
}
