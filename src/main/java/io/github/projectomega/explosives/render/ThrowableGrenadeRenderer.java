package io.github.projectomega.explosives.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import io.github.projectomega.explosives.entity.ThrowableGrenadeEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;

/*
 * Author: Forked from MrCrayfishGunMod, continued by ProjectOmega developers
 */
public class ThrowableGrenadeRenderer extends EntityRenderer<ThrowableGrenadeEntity> {

  public ThrowableGrenadeRenderer(EntityRendererProvider.Context context) {
    super(context);
  }

  @Override
  public ResourceLocation getTextureLocation(ThrowableGrenadeEntity entity) {
    return null;
  }

  @Override
  public void render(ThrowableGrenadeEntity entity, float entityYaw, float partialTicks,
      PoseStack poseStack, MultiBufferSource renderTypeBuffer, int light) {

    poseStack.pushPose();

    /* Makes the grenade face in the direction of travel */
    poseStack.mulPose(Axis.YP.rotationDegrees(180F));
    poseStack.mulPose(Axis.YP.rotationDegrees(entityYaw));

    /* Offsets to the center of the grenade before applying rotation */
    float rotation =
        entity.previousRotation + (entity.rotation - entity.previousRotation) * partialTicks;
    poseStack.translate(0, 0.15, -0.6);
    poseStack.mulPose(Axis.XP.rotationDegrees(-rotation));
    poseStack.translate(0, -0.15, 0);

    poseStack.translate(0.0, 0.45, 0.0);

    Minecraft.getInstance().getItemRenderer().renderStatic(entity.getItem(),
        ItemDisplayContext.NONE, light, OverlayTexture.NO_OVERLAY, poseStack, renderTypeBuffer,
        entity.level(), 0);

    poseStack.popPose();
  }
}
