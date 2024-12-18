package mom.wii.collartrinkets;

import mom.wii.collartrinkets.item.CollarItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class CollarTrinketsItems {
    public static void initialize() {}

    public static final CollarItem COLLAR = (CollarItem) register(
            new CollarItem(new FabricItemSettings().maxCount(1)),
            "collar"
    );

    public static Item register(Item item, String id) {
        Identifier itemID = CollarTrinkets.id(id);

        Item registeredItem = Registry.register(Registries.ITEM, itemID, item);

        return registeredItem;
    }
}
