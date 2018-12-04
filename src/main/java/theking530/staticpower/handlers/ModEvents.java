package theking530.staticpower.handlers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.CombatRules;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ServerTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import theking530.staticpower.assists.Reference;
import theking530.staticpower.assists.customboundingbox.CustomBoundingBox;
import theking530.staticpower.assists.customboundingbox.ICustomBoundingBox;
import theking530.staticpower.assists.customboundingbox.RenderCustomBoundingBox;
import theking530.staticpower.items.armor.BaseArmor;
import theking530.staticpower.items.armor.ModArmor;
import theking530.staticpower.items.armor.SkeletonArmor;
import theking530.staticpower.items.armor.UndeadArmor;
import theking530.staticpower.potioneffects.BasePotion;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class ModEvents {
	
	public final Random RANDOM;
	public static ArrayList<PotionEffect> POTION_EFFECTS;
	
	public ModEvents() {
		RANDOM = new Random();
		POTION_EFFECTS = new ArrayList<PotionEffect>();
	}	
	public static void init() {
		MinecraftForge.EVENT_BUS.register(new ModEvents());
	}
	
	@SubscribeEvent
	public void Tick(ServerTickEvent e){

	}
    @SubscribeEvent(priority=EventPriority.HIGH, receiveCanceled=true)
	public void attackEvent(LivingAttackEvent e) {
    	handleArmorDamage(e);
	}
    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
    public void dropEvent(LivingDropsEvent event) {
    	handleArmorSetDrops(event);
    } 
    @SubscribeEvent(priority=EventPriority.HIGHEST, receiveCanceled=true)
    public void onEntityUpdate(LivingUpdateEvent event) {
    	Collection<PotionEffect> tempEffects = event.getEntityLiving().getActivePotionEffects();
    	if(tempEffects == null) {
    		return;
    	}
    	Object[] tempEffectsArray = tempEffects.toArray();
		for(int i=0; i<tempEffectsArray.length; i++) {
			PotionEffect tempEffect = (PotionEffect) tempEffectsArray[i];
			if(tempEffect.getPotion() instanceof BasePotion) {
				BasePotion tempPotion = (BasePotion) tempEffect.getPotion();
				if(!POTION_EFFECTS.contains(tempEffect)) {
					tempPotion.potionEffectApplied(event.getEntityLiving());
					POTION_EFFECTS.add(tempEffect);
				}else if(tempEffect.getDuration() == 1) {
					tempPotion.potionEffectRemoved(event.getEntityLiving());
					POTION_EFFECTS.remove(tempEffect);
				}else{
					tempPotion.potionEffectTick(event.getEntityLiving(), tempEffect.getDuration());
				}
			}
		}
    }
    @SideOnly(Side.CLIENT)
	@SubscribeEvent (priority = EventPriority.HIGHEST)
	public void onBlockHighlight(DrawBlockHighlightEvent event) {
		RayTraceResult target = event.getTarget();
		EntityPlayer player = event.getPlayer();
		float partialTicks = event.getPartialTicks();

		if (renderConduitHitbox(target, player, partialTicks)) {
			event.setCanceled(true);
		}
	}
	private boolean renderConduitHitbox(RayTraceResult target, EntityPlayer player, float partialTicks) {

		if (target.typeOfHit != Type.BLOCK) {
			return false;
		}

		retraceBlock(player.world, player, target.getBlockPos());
		TileEntity tile = player.world.getTileEntity(target.getBlockPos());
	
		if (tile instanceof ICustomBoundingBox) {
			ICustomBoundingBox hitbox = (ICustomBoundingBox) tile;

			if (hitbox.shouldRenderCustomHitBox(target.subHit, player)) {
				CustomBoundingBox box = hitbox.getCustomBoundingBox(target.subHit, player);
				if(box != null) {
					RenderCustomBoundingBox.drawSelectionBox(player, target, partialTicks, hitbox.getCustomBoundingBox(target.subHit, player));
					return true;
				}
			}
		}
		return false;
	}
	public static RayTraceResult retraceBlock(World world, EntityPlayer player, BlockPos pos) {

		Vec3d startVec = getStartVec(player);
		Vec3d endVec = getEndVec(player);  
		return world.getBlockState(pos).collisionRayTrace(world, pos, startVec, endVec);
	}
	public static Vec3d getEndVec(EntityPlayer player) {

		Vec3d headVec = getCorrectedHeadVec(player);
		Vec3d lookVec = player.getLook(1.0F);
		double reach = getBlockReachDistance(player);
		return headVec.addVector(lookVec.x * reach, lookVec.y * reach, lookVec.z * reach);
	}
	public static double getBlockReachDistance(EntityPlayer player) {

		return player.world.isRemote ? getBlockReachDistanceClient() : player instanceof EntityPlayerMP ? getBlockReachDistanceServer((EntityPlayerMP) player) : 5D;
	}
	@SuppressWarnings("deprecation")
	private static double getBlockReachDistanceServer(EntityPlayerMP player) {

		return player.interactionManager.getBlockReachDistance();
	}

	@SideOnly (Side.CLIENT)
	private static double getBlockReachDistanceClient() {

		return Minecraft.getMinecraft().playerController.getBlockReachDistance();
	}
	public static Vec3d getStartVec(EntityPlayer player) {

		return getCorrectedHeadVec(player);
	}
	public static Vec3d getCorrectedHeadVec(EntityPlayer player) {

		return new Vec3d(player.posX, player.posY + player.getEyeHeight(), player.posZ);
	}
    public void handleArmorDamage(LivingAttackEvent e) {
		if (!(e.getEntityLiving() instanceof EntityPlayer)) {
			return;
		}
		EntityPlayer player = (EntityPlayer) e.getEntityLiving();
		
		float damageAmount = e.getAmount();
        damageAmount = CombatRules.getDamageAfterAbsorb(damageAmount, (float)player.getTotalArmorValue(), (float)player.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());

        if (!e.getSource().isDamageAbsolute()){
            if (player.isPotionActive(MobEffects.RESISTANCE) && e.getSource() != DamageSource.OUT_OF_WORLD) {
                int i = (player.getActivePotionEffect(MobEffects.RESISTANCE).getAmplifier() + 1) * 5;
                int j = 25 - i;
                float f = damageAmount * (float)j;
                damageAmount = f / 25.0F;
            }

            if (damageAmount <= 0.0F){

            } else {
                int k = EnchantmentHelper.getEnchantmentModifierDamage(player.getArmorInventoryList(), e.getSource());
                if (k > 0) {
                	damageAmount = CombatRules.getDamageAfterMagicAbsorb(damageAmount, (float)k);
                }
            }
        }
        if(player != null && player.inventory != null) {
        	for(int i=0; i<4; i++) {
    			if(player.inventory.armorInventory.get(i) != null && player.inventory.armorInventory.get(i).getItem() instanceof BaseArmor) {
    				BaseArmor tempArmor = (BaseArmor) player.inventory.armorInventory.get(i).getItem();
    				EntityEquipmentSlot slot;
    				if(i == 0) {
    					slot = EntityEquipmentSlot.FEET;
    				}else if(i == 1) {
    					slot = EntityEquipmentSlot.LEGS;
    				}else if(i == 2) {
    					slot = EntityEquipmentSlot.CHEST;
    				}else{
    					slot = EntityEquipmentSlot.HEAD;
    				}
    				tempArmor.onWearerDamaged(e, damageAmount, player, slot, player.inventory.armorInventory.get(i));
    			}	
        	}	
        }
    }

    public void handleArmorSetDrops(LivingDropsEvent event) {
		Entity entity = event.getEntity();
		World world = event.getEntity().getEntityWorld();
		BlockPos pos = event.getEntity().getPosition();
		
		if(entity instanceof EntityZombie && !(entity instanceof EntityPigZombie)){	
			UndeadArmor tempArmor = (UndeadArmor) ModArmor.UndeadHelmet;
			ItemStack tempStack = tempArmor.getRandomDrop(0.06);
			if(tempStack != null) {
				EntityItem itemDropX = new EntityItem(world, pos.getX()+.5, pos.getY()+.5, pos.getZ()+.5, tempStack);
				if(itemDropX != null) {
					event.getDrops().add(itemDropX);			
				}
			}
		}else if(entity instanceof EntityWitherSkeleton){	
			EntityWitherSkeleton tempSkele = (EntityWitherSkeleton) entity;
			if(tempSkele != null) {
				SkeletonArmor tempArmor = (SkeletonArmor) ModArmor.SkeletonHelmet;
				ItemStack tempStack = tempArmor.getRandomDrop(0.06);
				if(tempStack != null) {
					EntityItem itemDropX = new EntityItem(world, pos.getX()+.5, pos.getY()+.5, pos.getZ()+.5, tempStack);
					event.getDrops().add(itemDropX);	
				}
			}
		}	
    }
    
    
	public boolean diceRoll(float percentage) {
		if(percentage >= 1) {
			return true;
		}
		float randFloat = RANDOM.nextFloat();		
		return percentage > randFloat ? true : false;
	}
}
