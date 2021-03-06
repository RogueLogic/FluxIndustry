package noxzet.fluxindustry.core.crafting;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import noxzet.fluxindustry.core.block.BlockMetalBasic;
import noxzet.fluxindustry.core.block.BlockOreBasic;
import noxzet.fluxindustry.core.block.FluxBlocks;
import noxzet.fluxindustry.core.item.FluxItems;
import noxzet.fluxindustry.core.item.ItemFluxCable;
import noxzet.fluxindustry.core.item.ItemFluxMulti;

public class FluxCrafting {
	
	public static void init()
	{
		initMetals();
		initMaterials();
		initCables();
		initCrafting();
	}
	
	public static void postInit()
	{
		initCrusher();
		initCentrifuge();
	}
	
	private static void initMetals()
	{
		int i, len;
		String name;
		// Ores, smelting recipes
		BlockOreBasic oreBasic = (BlockOreBasic)FluxBlocks.oreBasic;
		len = oreBasic.variant.length;
		for(i = 0; i < len; i++)
		{
			name = camelCase(oreBasic.variant[i]);
			OreDictionary.registerOre("ore"+name, new ItemStack(FluxBlocks.oreBasic, 1, i));
			if (oreBasic.smeltable[i])
				FurnaceRecipes.instance().addSmeltingRecipe(new ItemStack(FluxBlocks.oreBasic, 1, i), new ItemStack(FluxItems.ingotBasic, 1, i), oreBasic.experience[i]);
		}
		// Ingots, metal blocks, nuggets
		BlockMetalBasic metalBasic = (BlockMetalBasic)FluxBlocks.blockMetal;
		len = metalBasic.variant.length;
		for(i = 0; i < len; i++)
		{
			name = camelCase(metalBasic.variant[i]);
			OreDictionary.registerOre("ingot"+name, new ItemStack(FluxItems.ingotBasic, 1, i));
			OreDictionary.registerOre("nugget"+name, new ItemStack(FluxItems.nuggetBasic, 1, i));
			OreDictionary.registerOre("block"+name, new ItemStack(FluxBlocks.blockMetal, 1, i));
			GameRegistry.addRecipe(new ItemStack(FluxBlocks.blockMetal, 1, i), "###", "###", "###", '#', new ItemStack(FluxItems.ingotBasic, 1, i));
			GameRegistry.addRecipe(new ItemStack(FluxItems.ingotBasic, 1, i), "###", "###", "###", '#', new ItemStack(FluxItems.nuggetBasic, 1, i));
			GameRegistry.addShapelessRecipe(new ItemStack(FluxItems.ingotBasic, 9, i), new ItemStack(FluxBlocks.blockMetal, 1, i));
			GameRegistry.addShapelessRecipe(new ItemStack(FluxItems.nuggetBasic, 9, i), new ItemStack(FluxItems.ingotBasic, 1, i));
		}
		// Powders and smelting of FI dusts
		ItemFluxMulti powderBasic = ((ItemFluxMulti)FluxItems.powderBasic);
		len = powderBasic.NAME.length;
		for(i = 0; i < len; i++)
		{
			name = camelCase(powderBasic.NAME[i]);
			OreDictionary.registerOre("dust"+name, new ItemStack(FluxItems.powderBasic, 1, i));
			if (i < 7)
				FurnaceRecipes.instance().addSmeltingRecipe(new ItemStack(FluxItems.powderBasic, 1, i), new ItemStack(FluxItems.ingotBasic, 1, i), 0.0F);
		}
		// Iron and gold powder smelting
		FurnaceRecipes.instance().addSmeltingRecipe(new ItemStack(FluxItems.powderBasic, 1, 8), new ItemStack(Items.IRON_INGOT, 1), 0.0F);
		FurnaceRecipes.instance().addSmeltingRecipe(new ItemStack(FluxItems.powderBasic, 1, 9), new ItemStack(Items.GOLD_INGOT, 1), 0.0F);
		// Bronze and brass crafting
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(FluxItems.powderBasic, 4, 5), "dustCopper", "dustCopper", "dustCopper", "dustTin"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(FluxItems.powderBasic, 2, 6), "dustCopper", "dustZinc"));
	}
	
	private static void initMaterials()
	{
		// Rubber
		OreDictionary.registerOre("itemRubber", new ItemStack(FluxItems.materialBasic, 1, 1));
		FurnaceRecipes.instance().addSmeltingRecipe(new ItemStack(FluxItems.materialBasic, 1, 0), new ItemStack(FluxItems.materialBasic, 2, 1), 0.0F);
	}
	
	private static void initCables()
	{
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(FluxItems.cableUninsulated, 1, 0), "CCC",
				'C', "nuggetCopper"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(FluxItems.cableUninsulated, 1, 1), "TTT",
				'T', "nuggetTin"));
		// Insulated tin and copper wire with basic properties
		ItemStack resultStack;
		resultStack = new ItemStack(FluxItems.cableBasic, 1, 0);
		resultStack.setTagCompound(ItemFluxCable.itemTag[0]);
		GameRegistry.addRecipe(new ShapelessOreRecipe(resultStack, new ItemStack(FluxItems.cableUninsulated, 1, 0), "itemRubber"));
		resultStack = new ItemStack(FluxItems.cableBasic, 1, 1);
		resultStack.setTagCompound(ItemFluxCable.itemTag[1]);
		GameRegistry.addRecipe(new ShapelessOreRecipe(resultStack, new ItemStack(FluxItems.cableUninsulated, 1, 1), "itemRubber"));
	}
	
	private static void initCrafting()
	{
		// Batteries
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(FluxItems.batteryBasic, 1, 0), "C", "G", "Z",
				'C', "ingotCopper", 'G', Items.COAL, 'Z', "ingotZinc"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(FluxItems.batteryBasic, 1, 1), "CCC", "BBB", "RRR",
				'C', new ItemStack(FluxItems.cableUninsulated, 1, 0), 'R', "dustRedstone", 'B', new ItemStack(FluxItems.batteryBasic, 1, 0)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(FluxItems.batteryBasic, 1, 2), "CCC", "BBB", "LLL",
				'C', new ItemStack(FluxItems.cableUninsulated, 1, 3), 'L', "ingotLead", 'B', new ItemStack(FluxItems.batteryBasic, 1, 1)));
		// Misc
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(FluxItems.wrenchBrass, 1), " B ", " BB", "B  ",
				'B', "ingotBrass"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(FluxItems.treetap, 1), " I ", "W W", "SWS",
				'I', "ingotIron", 'W', "plankWood", 'S', "stickWood"));
		// Machines
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(FluxBlocks.blockMachine, 1, 0), "IBI", "ITI", "ITI",
				'I', "ingotIron", 'T', "ingotTin", 'B', new ItemStack(FluxItems.batteryBasic, 1, 0)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(FluxBlocks.blockMachine, 1, 1), "ALA", "A#A", "ALA",
				'A', "ingotAluminum", 'L', "ingotLead", '#', new ItemStack(FluxBlocks.blockMachine, 1, 0)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(FluxBlocks.generatorCoal, 1), "CCC", "R#R", " F ",
				'C', "ingotCopper", 'R', "dustRedstone", '#', new ItemStack(FluxBlocks.blockMachine, 1), 'F', new ItemStack(Blocks.FURNACE)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(FluxBlocks.electricFurnace, 1), " T ", "R#R", "RFR",
				'T', "ingotTin", 'R', "dustRedstone", '#', new ItemStack(FluxBlocks.blockMachine, 1), 'F', new ItemStack(Blocks.FURNACE)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(FluxBlocks.electricCrusher, 1), "FRF", "F#F", "FIF",
				'F', new ItemStack(Items.FLINT, 1), 'R', "dustRedstone", '#', new ItemStack(FluxBlocks.blockMachine, 1), 'I', "ingotIron"));
	}
	
	public static void initCrusher()
	{
		int i, len;
		// 7 basic ingot crushing
		// 5 basic ores crushing
		for(i = 0; i < 10; i++)
		{
			if (i < 5)
				FluxCrusherRecipes.instance().registerOreDictRecipe("ore"+camelCase(((ItemFluxMulti)FluxItems.powderBasic).NAME[i]),
						new ItemStack(FluxItems.powderBasic, 2, i));
			if (i != 7)
				FluxCrusherRecipes.instance().registerOreDictRecipe("ingot"+camelCase(((ItemFluxMulti)FluxItems.powderBasic).NAME[i]),
						new ItemStack(FluxItems.powderBasic, 1, i));
		}
		// Crushing remaining ones
		FluxCrusherRecipes.instance().registerOreDictRecipe("oreUranium", new ItemStack(FluxItems.powderBasic, 2, 7));
		FluxCrusherRecipes.instance().registerOreDictRecipe("oreIron", new ItemStack(FluxItems.powderBasic, 2, 8));
		FluxCrusherRecipes.instance().registerOreDictRecipe("oreGold", new ItemStack(FluxItems.powderBasic, 2, 9));
		FluxCrusherRecipes.instance().registerOreDictRecipe("stoneGranite", new ItemStack(FluxItems.powderBasic, 1, 10));
		FluxCrusherRecipes.instance().registerOreDictRecipe("stoneDiorite", new ItemStack(FluxItems.powderBasic, 1, 11));
		FluxCrusherRecipes.instance().registerOreDictRecipe("stoneAndesite", new ItemStack(FluxItems.powderBasic, 1, 12));
		FluxCrusherRecipes.instance().registerRecipe(new ItemStack(Blocks.STONE, 1, 0), new ItemStack(FluxItems.powderBasic, 1, 13), false);
	}
	
	public static void initCentrifuge()
	{
		FluxCentrifugeRecipes.instance().registerOreDictRecipe("dustGranite", new FluxCentrifugeRecipe(
				new ItemStack(Blocks.STONE, 1, 0), new ItemStack[]{new ItemStack(FluxItems.materialBasic, 1, 8),
						new ItemStack(FluxItems.materialBasic, 1, 2), new ItemStack(FluxItems.powderBasic, 1, 13)},
				new int[]{4, 2, 4}));
		FluxCentrifugeRecipes.instance().registerOreDictRecipe("dustAndesite", new FluxCentrifugeRecipe(
				new ItemStack(Blocks.STONE, 1, 0), new ItemStack[]{new ItemStack(FluxItems.materialBasic, 1, 3),
						new ItemStack(FluxItems.materialBasic, 1, 4), new ItemStack(FluxItems.powderBasic, 1, 13)},
				new int[]{3, 2, 5}));
	}
	
	public static String camelCase(String name)
	{
		return Character.toUpperCase(name.charAt(0))+name.substring(1);
	}
	
}
