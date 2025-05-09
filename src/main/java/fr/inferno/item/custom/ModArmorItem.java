package fr.inferno.item.custom;

import com.google.common.collect.ImmutableMap;
import fr.inferno.item.ModArmorMaterials;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Map;

public class ModArmorItem extends ArmorItem {

    private static final Map<ArmorMaterial, MobEffectInstance> MATERIAL_TO_EFFECT_MAP =
            (new ImmutableMap.Builder<ArmorMaterial, MobEffectInstance>())
                    .put(ModArmorMaterials.HELLFORGED, new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 200, 25,
                            false, false, true)).build();


    public ModArmorItem(ArmorMaterial pMaterial, Type pType, Properties pProperties) {
        super(pMaterial, pType, pProperties);
    }


    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {
        if(!level.isClientSide) {
            if(hasFullSuitOfArmorOn(player)) {
                evaluateArmorEffect(player);
            }
        }
    }

    private boolean hasFullSuitOfArmorOn(Player player) {
        ItemStack boots = player.getInventory().getArmor(0);
        ItemStack leggings = player.getInventory().getArmor(1);
        ItemStack chestplate = player.getInventory().getArmor(2);
        ItemStack helmet = player.getInventory().getArmor(3);

        return !helmet.isEmpty() && !chestplate.isEmpty() && !leggings.isEmpty() && !boots.isEmpty();
    }

    private void evaluateArmorEffect(Player player) {
        for(Map.Entry<ArmorMaterial, MobEffectInstance> entry : MATERIAL_TO_EFFECT_MAP.entrySet()) {
          ArmorMaterial material = entry.getKey();
          MobEffectInstance effect = entry.getValue();

          if(hasCorrectArmorOn(material, player)) {
              addStatusEffectForMaterial(player, material, effect);
          }
        }
    }

    private void addStatusEffectForMaterial(Player player, ArmorMaterial material, MobEffectInstance effect) {
        boolean hasEffect = player.hasEffect(effect.getEffect());

        if(hasCorrectArmorOn(material, player) && !hasEffect) {
            player.addEffect(new MobEffectInstance(effect));
        }
    }


    private boolean hasCorrectArmorOn(ArmorMaterial material, Player player) {
        for(ItemStack armorPiece : player.getInventory().armor) {
            if(!(armorPiece.getItem() instanceof ArmorItem)) {
                return false;
            }
        }


        ArmorItem helmet = (ArmorItem) player.getInventory().getArmor(3).getItem();
        ArmorItem chestplate = (ArmorItem) player.getInventory().getArmor(2).getItem();
        ArmorItem leggings = (ArmorItem) player.getInventory().getArmor(1).getItem();
        ArmorItem boots = (ArmorItem) player.getInventory().getArmor(0).getItem();


        return helmet.getMaterial() == material &&
                chestplate.getMaterial() == material &&
                leggings.getMaterial() == material &&
                boots.getMaterial() == material;
    }
}
