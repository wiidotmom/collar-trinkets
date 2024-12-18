package mom.wii.collartrinkets.mixin;

import mom.wii.collartrinkets.CollarTrinketsItems;
import mom.wii.collartrinkets.CollarTrinketsSounds;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow public abstract World getWorld();

    @Inject(method = "playStepSound", at = @At("TAIL"))
    private void collartrinkets$playStepSound(BlockPos pos, BlockState state, CallbackInfo ci) {
        if ((Entity) (Object) this instanceof LivingEntity && !this.getWorld().isClient) {
            LivingEntity entity = (LivingEntity) (Object) this;
            if (entity.accessoriesCapability() != null && entity.accessoriesCapability().isEquipped(CollarTrinketsItems.COLLAR_WITH_BELL)) {
                entity.getWorld().playSound(null, entity.getX(), entity.getY(), entity.getZ(), CollarTrinketsSounds.COLLAR_BELL, entity.getSoundCategory(), 1f, 1f);
            }
        }
    }

    @Inject(method = "setSneaking", at = @At("HEAD"))
    private void collartrinkets$setSneaking(boolean sneaking, CallbackInfo ci) {
        if ((Entity) (Object) this instanceof LivingEntity && !this.getWorld().isClient) {
            LivingEntity entity = (LivingEntity) (Object) this;
            if (entity.accessoriesCapability() != null && entity.accessoriesCapability().isEquipped(CollarTrinketsItems.COLLAR_WITH_BELL)) {
                entity.getWorld().playSound(null, entity.getX(), entity.getY(), entity.getZ(), CollarTrinketsSounds.COLLAR_BELL, entity.getSoundCategory(), 1f, 1f);
            }
        }
    }
}
