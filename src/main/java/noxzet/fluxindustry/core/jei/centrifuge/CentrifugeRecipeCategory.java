package noxzet.fluxindustry.core.jei.centrifuge;

import javax.annotation.Nonnull;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import noxzet.fluxindustry.core.container.CentrifugeContainerGui;
import noxzet.fluxindustry.core.jei.FluxCategoryUids;

public class CentrifugeRecipeCategory extends BlankRecipeCategory<CentrifugeRecipeWrapper> {

	private static final int[] INPUT_SLOTS = { 0 };
	private static final int[] OUTPUT_SLOTS = { 1, 2, 3 };
	
	private final IDrawable background;
	private final String title;
	
	public CentrifugeRecipeCategory(IGuiHelper guiHelper)
	{
		background = guiHelper.createDrawable(CentrifugeContainerGui.background, 46, 16, 84, 54);
		title = "tile.fluxindustry.centrifuge.name";
	}
	
	@Override
	public String getUid()
	{
		return FluxCategoryUids.CENTRIFUGE;
	}
	
	@Override
	public String getTitle()
	{
		return I18n.translateToLocal(title);
	}
	
	@Override
	public IDrawable getBackground()
	{
		return background;
	}
	
	@Override
	public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull CentrifugeRecipeWrapper recipeWrapper, @Nonnull IIngredients ingredients)
	{
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
		guiItemStacks.init(INPUT_SLOTS[0], true, 0, 0);
		guiItemStacks.init(OUTPUT_SLOTS[0], false, 66, 0);
		guiItemStacks.init(OUTPUT_SLOTS[1], false, 66, 18);
		guiItemStacks.init(OUTPUT_SLOTS[2], false, 66, 36);
		guiItemStacks.set(INPUT_SLOTS[0], ingredients.getInputs(ItemStack.class).get(0));
		guiItemStacks.set(OUTPUT_SLOTS[0], ingredients.getOutputs(ItemStack.class).get(0));
		guiItemStacks.set(OUTPUT_SLOTS[1], ingredients.getOutputs(ItemStack.class).get(1));
		guiItemStacks.set(OUTPUT_SLOTS[2], ingredients.getOutputs(ItemStack.class).get(2));
	}
	
}