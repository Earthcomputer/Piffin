package net.earthcomputer.piffin;

import blue.endless.jankson.JsonGrammar;
import blue.endless.jankson.JsonObject;
import net.earthcomputer.piffin.dumper.TopLevelDumper;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Piffin {

    private static final Logger LOGGER = LogManager.getLogger("Piffin");
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");

    public static void onGameLoaded() {
        try {
            FabricLoader loader = FabricLoader.getInstance();
            Path dumpDir = loader.getConfigDirectory().toPath()
                    .resolve("piffin")
                    .resolve("dumps")
                    .resolve("dump_" + DATE_FORMAT.format(new Date()));
            Files.createDirectories(dumpDir);

            @SuppressWarnings("unchecked")
            List<TopLevelDumper<?>> dumpers = (List<TopLevelDumper<?>>) (List<?>) loader.getEntrypoints("piffin:dumper", TopLevelDumper.class);
            LOGGER.info("Dumping {} Piffin dumpers", dumpers.size());
            long startTime = System.nanoTime();
            for (TopLevelDumper<?> dumper : dumpers) {
                topLevelDump(dumper, dumpDir);
            }
            long elapsedTime = System.nanoTime() - startTime;
            LOGGER.info("Dumped Piffin dumpers in {}ms", elapsedTime / 1000000);
        } catch (IOException e) {
            throw new RuntimeException("An I/O error occurred when dumping data", e);
        }
    }

    private static <T> void topLevelDump(TopLevelDumper<T> dumper, Path dumpDir) throws IOException {
        T thing = dumper.getTopLevelThing();
        JsonObject output = new JsonObject();
        dumper.dump(thing, output);
        Path outputFile = dumpDir.resolve(dumper.getName() + ".json");
        try (BufferedWriter writer = Files.newBufferedWriter(outputFile)) {
            writer.write(output.toJson(JsonGrammar.STRICT));
            writer.flush();
        }
    }

}
