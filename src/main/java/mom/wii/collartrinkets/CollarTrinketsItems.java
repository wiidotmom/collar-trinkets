package mom.wii.collartrinkets;

import mom.wii.collartrinkets.item.CollarItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.List;

public class CollarTrinketsItems {
    public static void initialize() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS)
                .register((itemGroup) -> itemGroup.addAfter(Items.LEAD.getDefaultStack(), List.of(
                        COLLAR.getDefaultStack(),
                        COLLAR_WITH_BELL.getDefaultStack()
                )));
    }

    public static final CollarItem COLLAR = (CollarItem) register(
            new CollarItem(new FabricItemSettings().maxCount(1), false),
            "collar"
    );
    public static final CollarItem COLLAR_WITH_BELL = (CollarItem) register(
            new CollarItem(new FabricItemSettings().maxCount(1), true),
            "bell_collar"
    );

    public static Item register(Item item, String id) {
        Identifier itemID = CollarTrinkets.id(id);

        Item registeredItem = Registry.register(Registries.ITEM, itemID, item);

        return registeredItem;
    }
}
