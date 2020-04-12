package net.earthcomputer.piffin.dumper;

import blue.endless.jankson.JsonObject;
import net.earthcomputer.piffin.util.JsonArrayBuilder;
import net.earthcomputer.piffin.util.JsonObjectBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.metadata.ModDependency;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.fabricmc.loader.api.metadata.Person;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ModListDumper implements TopLevelDumper<List<ModContainer>> {
    @Override
    public List<ModContainer> getTopLevelThing() {
        List<ModContainer> list = new ArrayList<>(FabricLoader.getInstance().getAllMods());
        list.sort(Comparator.comparing(mod -> mod.getMetadata().getId()));
        return list;
    }

    @Override
    public void dump(List<ModContainer> thing, JsonObject output) {
        for (ModContainer mod : thing) {
            ModMetadata metadata = mod.getMetadata();
            JsonObjectBuilder modObj = JsonObjectBuilder.create()
                    .putString("name", metadata.getName())
                    .putString("version", metadata.getVersion().getFriendlyString())
                    .putString("description", metadata.getDescription())
                    .put("authors", JsonArrayBuilder.create()
                        .addStrings(metadata.getAuthors().stream().map(Person::getName))
                    )
                    .put("contributors", JsonArrayBuilder.create()
                        .addStrings(metadata.getContributors().stream().map(Person::getName))
                    )
                    .put("license", JsonArrayBuilder.create().addStrings(metadata.getLicense().stream()))
                    .put("depends", JsonArrayBuilder.create()
                            .addStrings(metadata.getDepends().stream().map(ModDependency::getModId))
                    )
                    .put("recommends", JsonArrayBuilder.create()
                            .addStrings(metadata.getRecommends().stream().map(ModDependency::getModId))
                    )
                    .put("suggests", JsonArrayBuilder.create()
                            .addStrings(metadata.getSuggests().stream().map(ModDependency::getModId))
                    )
                    .put("conflicts", JsonArrayBuilder.create()
                            .addStrings(metadata.getConflicts().stream().map(ModDependency::getModId))
                    )
                    .put("breaks", JsonArrayBuilder.create()
                            .addStrings(metadata.getBreaks().stream().map(ModDependency::getModId))
                    );
            metadata.getContact().get("homepage").ifPresent(homepage -> modObj.putString("homepage", homepage));
            metadata.getContact().get("issues").ifPresent(homepage -> modObj.putString("issues", homepage));
            metadata.getContact().get("sources").ifPresent(homepage -> modObj.putString("sources", homepage));
            output.put(metadata.getId(), modObj.build());
        }
    }

    @Override
    public String getName() {
        return "mods";
    }
}
