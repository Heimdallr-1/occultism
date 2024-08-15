package com.klikli_dev.occultism.datagen.book.familiarrituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookCraftingRecipePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookEntityPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookRitualRecipePageModel;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookSpiritFireRecipePageModel;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.item.crafting.Ingredient;

public class ResurrectionEntry extends EntryProvider {

    public static final String ENTRY_ID = "resurrection";


    public ResurrectionEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {


        this.page("soul_shard", () -> BookSpotlightPageModel.builder()
                .withItem(Ingredient.of(OccultismItems.SOUL_SHARD_ITEM.get()))
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build());
        this.pageTitle("Soul Shards");
        this.pageText("""
                If a familiar dies it does not merely return to the Otherworld. Due to the close connection to the summoner a splinter of the familiar's soul remains in the mortal realm.
                \\
                \\
                This splinter - shard - can be used to re-summon the familiar more easily.
                """);

        this.page("description", () -> BookTextPageModel.builder()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .build());
        this.pageTitle("Resurrection");
        this.pageText("""
                The resurrection is a relatively simple process. The soul shard is strengthened with {0} until it is strong enough to allow the familiar to return to the mortal realm and create a new body for itself.
                \\
                \\
                The essence is obtained by growing (lots of) Demon's Dream plants.
                """,
                this.itemLink(OccultismItems.OTHERWORLD_ESSENCE.get())
                );

        this.page("recipe_essence", () ->BookCraftingRecipePageModel.builder()
                .withRecipeId1(this.modLoc("crafting/demons_dream_essence_from_fruit"))
                .withRecipeId2(this.modLoc("crafting/demons_dream_essence_from_seeds"))
                .build());
        //no text

        this.page("recipe_essence_pure", () -> BookSpiritFireRecipePageModel.builder()
                .withRecipeId1(this.modLoc("spirit_fire/otherworld_essence"))
                .build());
        //no text


        this.page("ritual", () -> BookRitualRecipePageModel.builder()
                .withRecipeId1(this.modLoc("ritual/resurrect_familiar"))
                .build()
        );
        //no text
    }

    @Override
    protected String entryName() {
        return "Resurrecting Familiars";
    }

    @Override
    protected String entryDescription() {
        return "How to bring back a familiar from the dead.";
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(OccultismItems.SOUL_SHARD_ITEM.get());
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
