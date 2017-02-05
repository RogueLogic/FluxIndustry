package noxzet.fluxindustry.core.jei;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

import mezz.jei.api.BlankModPlugin;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import net.minecraft.item.ItemStack;
import noxzet.fluxindustry.core.block.FluxBlocks;
import noxzet.fluxindustry.core.container.ElectricCrusherContainerGui;
import noxzet.fluxindustry.core.container.ElectricFurnaceContainerGui;
import noxzet.fluxindustry.core.crafting.FluxCrusherRecipes;
import noxzet.fluxindustry.core.jei.crusher.CrusherRecipe;
import noxzet.fluxindustry.core.jei.crusher.CrusherRecipeCategory;
import noxzet.fluxindustry.core.jei.crusher.CrusherRecipeHandler;

@JEIPlugin
public class FluxJeiPlugin extends BlankModPlugin {

	public void register (@Nonnull IModRegistry registry)
	{
		IJeiHelpers jeiHelpers = registry.getJeiHelpers();
		IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
		registerCrusherRecipes(registry);
		registry.addRecipeCategories(new CrusherRecipeCategory(guiHelper));
		registry.addRecipeHandlers(new CrusherRecipeHandler(jeiHelpers));
		registry.addRecipeClickArea(ElectricFurnaceContainerGui.class, 78, 35, 26, 15, VanillaRecipeCategoryUid.SMELTING);
		registry.addRecipeClickArea(ElectricCrusherContainerGui.class, 71, 35, 38, 15, FluxCategoryUids.CRUSHER);
		registry.addRecipeCategoryCraftingItem(new ItemStack(FluxBlocks.electricCrusher), FluxCategoryUids.CRUSHER);
	}
	
	public void registerCrusherRecipes(@Nonnull IModRegistry registry)
	{
		Map<String, ItemStack> recipeMap = FluxCrusherRecipes.getOreDictCrushingRecipes();
		Map<ItemStack, ItemStack> nonDictRecipeMap = FluxCrusherRecipes.getNonDictCrushingRecipes();
		List<CrusherRecipe> recipeCollection = new ArrayList<CrusherRecipe>();
		for (Map.Entry<String, ItemStack> entry : recipeMap.entrySet())
		{
			recipeCollection.add(new CrusherRecipe(entry.getKey(), entry.getValue()));
		}
		for (Map.Entry<ItemStack, ItemStack> entry : nonDictRecipeMap.entrySet())
		{
			recipeCollection.add(new CrusherRecipe(entry.getKey(), entry.getValue()));
		}
		registry.addRecipes(recipeCollection);
	}

}