package theking530.staticpower.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelSignalMultiplier extends ModelBase {
	
    ModelRenderer Base;
    ModelRenderer BorderRight;
    ModelRenderer BorderLeft;
    ModelRenderer BorderTop;
    ModelRenderer BorderBottom;
  
  public ModelSignalMultiplier() {
    textureWidth = 64;
    textureHeight = 32;
    
      Base = new ModelRenderer(this, 0, 0);
      Base.addBox(0F, -1F, 0F, 16, 2, 16);
      Base.setRotationPoint(-8F, 23F, -8F);
      Base.setTextureSize(64, 32);
      Base.mirror = true;
      setRotation(Base, 0F, 0F, 0F);
      BorderRight = new ModelRenderer(this, 0, 19);
      BorderRight.addBox(0F, 0F, 0F, 1, 1, 8);
      BorderRight.setRotationPoint(-4F, 21.5F, -4F);
      BorderRight.setTextureSize(64, 32);
      BorderRight.mirror = true;
      setRotation(BorderRight, 0F, 0F, 0F);
      BorderLeft = new ModelRenderer(this, 0, 19);
      BorderLeft.addBox(0F, 0F, 0F, 1, 1, 8);
      BorderLeft.setRotationPoint(3F, 21.5F, -4F);
      BorderLeft.setTextureSize(64, 32);
      BorderLeft.mirror = true;
      setRotation(BorderLeft, 0F, 0F, 0F);
      BorderTop = new ModelRenderer(this, 0, 26);
      BorderTop.addBox(0F, 0F, 0F, 6, 1, 1);
      BorderTop.setRotationPoint(-3F, 21.5F, 3F);
      BorderTop.setTextureSize(64, 32);
      BorderTop.mirror = true;
      setRotation(BorderTop, 0F, 0F, 0F);
      BorderBottom = new ModelRenderer(this, 0, 26);
      BorderBottom.addBox(0F, 0F, 0F, 6, 1, 1);
      BorderBottom.setRotationPoint(-3F, 21.5F, -4F);
      BorderBottom.setTextureSize(64, 32);
      BorderBottom.mirror = true;

      setRotation(BorderBottom, 0F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    Base.render(f5);
    BorderRight.render(f5);
    BorderLeft.render(f5);
    BorderTop.render(f5);
    BorderBottom.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z){
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
  }
}
