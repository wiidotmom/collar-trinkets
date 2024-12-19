package mom.wii.collartrinkets.mixin;

import mom.wii.collartrinkets.CollarTrinketsItems;
import mom.wii.collartrinkets.CollarTrinketsSounds;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(method = "jump", at = @At("HEAD"))
    private void collartrinkets$jump(CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (!entity.getWorld().isClient()) {
            if (!entity.isSprinting() && entity.accessoriesCapability() != null && entity.accessoriesCapability().isEquipped(CollarTrinketsItems.COLLAR_WITH_BELL)) {
                entity.getWorld().playSound(null, entity.getX(), entity.getY(), entity.getZ(), CollarTrinketsSounds.COLLAR_BELL, entity.getSoundCategory(), 1f, 1f);
            }
        }
    }
}
