package mom.wii.collartrinkets.item;

import com.google.common.base.Suppliers;
import io.wispforest.accessories.api.AccessoryItem;
import io.wispforest.accessories.api.client.AccessoryRenderer;
import io.wispforest.accessories.api.slot.SlotReference;
import mom.wii.collartrinkets.CollarTrinkets;
import mom.wii.collartrinkets.CollarTrinketsItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.*;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import java.util.function.Supplier;

public class CollarItem extends AccessoryItem implements DyeableItem {
    public boolean hasBell;

    public CollarItem(Settings settings, boolean hasBell) {
        super(settings);
        this.hasBell = hasBell;
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

        public static TexturedModelData createBellModelData() {
            ModelData modelData = new ModelData();
            ModelPartData root = modelData.getRoot();
            ModelPartData body = root.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create(), ModelTransform.NONE);
            body.addChild("bell", ModelPartBuilder.create().uv(0, 14).cuboid(-1.0F, -23.0F, -2.75F, 2.0F, 2.0F, 1.0F, new Dilation(0.3F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

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
        private static final Supplier<BipedEntityModel<LivingEntity>> BELL_MODEL = Suppliers.memoize(() ->
                new Model(Model.createBellModelData().createModel()));
        private boolean hasBell;

        public Renderer(boolean hasBell) {
            this.hasBell = hasBell;
        }

        @Override
        public <M extends LivingEntity> void render(ItemStack itemStack, SlotReference slotReference, MatrixStack matrixStack, EntityModel<M> entityModel, VertexConsumerProvider vertexConsumerProvider, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
            BipedEntityModel<LivingEntity> model = MODEL.get();
            model.setAngles(slotReference.entity(), limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            model.animateModel(slotReference.entity(), limbSwing, limbSwingAmount, ageInTicks);
            followBodyRotations(slotReference.entity(), model);
            VertexConsumer consumer = vertexConsumerProvider.getBuffer(model.getLayer(TEXTURE));
            int i = CollarTrinketsItems.COLLAR.getColor(itemStack);
            float f = (float)(i >> 16 & 255) / 255.0F;
            float g = (float)(i >> 8 & 255) / 255.0F;
            float h = (float)(i & 255) / 255.0F;
            model.render(matrixStack, consumer, light, OverlayTexture.DEFAULT_UV, f, g, h, 1.0F);
            if (hasBell) {
                BipedEntityModel<LivingEntity> bellModel = BELL_MODEL.get();
                bellModel.setAngles(slotReference.entity(), limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
                bellModel.animateModel(slotReference.entity(), limbSwing, limbSwingAmount, ageInTicks);
                followBodyRotations(slotReference.entity(), bellModel);
                VertexConsumer bellConsumer = vertexConsumerProvider.getBuffer(bellModel.getLayer(TEXTURE));
                bellModel.render(matrixStack, bellConsumer, light, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0F);
            }
        }

        private static void followBodyRotations(LivingEntity entity, BipedEntityModel<LivingEntity> model) {
            EntityRenderer<? super LivingEntity> render = MinecraftClient.getInstance().getEntityRenderDispatcher().getRenderer(entity);

            if (render instanceof LivingEntityRenderer<?, ?> renderer && renderer.getModel() instanceof BipedEntityModel<?> entityModel) {
                ((BipedEntityModel<LivingEntity>) entityModel).copyBipedStateTo(model);
            }
        }
    }
}
