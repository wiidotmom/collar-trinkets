package mom.wii.collartrinkets.item;

import com.google.common.base.Suppliers;
import io.wispforest.accessories.api.AccessoryItem;
import io.wispforest.accessories.api.client.AccessoryRenderer;
import io.wispforest.accessories.api.slot.SlotReference;
import mom.wii.collartrinkets.CollarTrinkets;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.model.*;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public class CollarItem extends AccessoryItem {
    public CollarItem(Settings settings) {
        super(settings);
    }

    public static String getOwner(ItemStack stack) {
        return stack.getOrCreateNbt().getString("Owner");
    }

    private static void setOwner(ItemStack stack, String owner) {
        stack.getOrCreateNbt().putString("Owner", owner);
    }

    public static Boolean hasOwner(ItemStack stack) {
        return stack.getOrCreateNbt().contains("Owner");
    }

    @Override
    public void onCraft(ItemStack stack, World world, PlayerEntity player) {
        setOwner(stack, player.getEntityName());
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (hasOwner(stack)) {
            tooltip.add(Text.translatable("collartrinkets.tooltip.owned_by", getOwner(stack)).formatted(Formatting.GRAY));
        }
    }

    @Environment(EnvType.CLIENT)
    public static class Model extends BipedEntityModel<LivingEntity> {
        public Model(ModelPart root) {
            super(root);
            this.setVisible(false);
            this.body.visible = true;
        }

        public static TexturedModelData createTexturedModelData() {
            ModelData modelData = new ModelData();
            ModelPartData root = modelData.getRoot();
            ModelPartData body = root.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create(), ModelTransform.NONE);
            body.addChild("collar", ModelPartBuilder.create().uv(0, 0).cuboid(-3.0F, -24.0F, -2.0F, 6.0F, 3.0F, 4.0F, new Dilation(0.3F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

            root.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create(), ModelTransform.NONE);
            root.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create(), ModelTransform.NONE);
            root.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create(), ModelTransform.NONE);
            root.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create(), ModelTransform.NONE);
            root.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create(), ModelTransform.NONE);
            root.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create(), ModelTransform.NONE);

            return TexturedModelData.of(modelData, 64, 64);
        }
    }

    @Environment(EnvType.CLIENT)
    public static class Renderer implements AccessoryRenderer {
        private static final Identifier TEXTURE = CollarTrinkets.id("textures/entity/collar.png");
        private static final Supplier<BipedEntityModel<LivingEntity>> MODEL = Suppliers.memoize(() ->
                new Model(Model.createTexturedModelData().createModel()));

        @Override
        public <M extends LivingEntity> void render(ItemStack itemStack, SlotReference slotReference, MatrixStack matrixStack, EntityModel<M> entityModel, VertexConsumerProvider vertexConsumerProvider, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
            BipedEntityModel<LivingEntity> model = MODEL.get();
            model.setAngles(slotReference.entity(), limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            model.animateModel(slotReference.entity(), limbSwing, limbSwingAmount, ageInTicks);
            followBodyRotations(slotReference.entity(), model);
            VertexConsumer consumer = vertexConsumerProvider.getBuffer(model.getLayer(TEXTURE));
            model.render(matrixStack, consumer, light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
        }

        private static void followBodyRotations(LivingEntity entity, BipedEntityModel<LivingEntity> model) {
            EntityRenderer<? super LivingEntity> render = MinecraftClient.getInstance().getEntityRenderDispatcher().getRenderer(entity);

            if (render instanceof LivingEntityRenderer<?, ?> renderer && renderer.getModel() instanceof BipedEntityModel<?> entityModel) {
                ((BipedEntityModel<LivingEntity>) entityModel).copyBipedStateTo(model);
            }
        }
    }
}
