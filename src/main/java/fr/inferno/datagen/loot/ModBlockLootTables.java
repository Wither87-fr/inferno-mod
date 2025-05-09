package fr.inferno.datagen.loot;

import fr.inferno.block.ModBlocks;
import fr.inferno.item.ModItems;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.BinomialDistributionGenerator;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {


    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        dropSelf(ModBlocks.HELLFORGED_IRON_BLOCK.get());
        dropSelf(ModBlocks.RAW_HELLFORGED_IRON_BLOCK.get());
        dropSelf(ModBlocks.HELLFIRE_SMITHING_TABLE.get());
        dropSelf(ModBlocks.HELL_GATE.get());


        this.add(ModBlocks.HELLFORGED_IRON_ORE.get(),
                block -> createWeightedOreDrops(ModBlocks.HELLFORGED_IRON_ORE.get(), ModItems.RAW_HELLFORGED_IRON.get(), 1, 0.15f));
    }



    protected LootTable.Builder createWeightedOreDrops(Block block, Item item, int expected, float chance) {
       return createSilkTouchDispatchTable(block,
               this.applyExplosionDecay(block,
                       LootItem.lootTableItem(item)
                               .apply(SetItemCountFunction.setCount(BinomialDistributionGenerator.binomial(expected, chance)))
                               .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
    }

    protected LootTable.Builder createSimpleOreDrops(Block block, Item item) {
        return createWeightedOreDrops(block, item, 1, 1.0f);
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
