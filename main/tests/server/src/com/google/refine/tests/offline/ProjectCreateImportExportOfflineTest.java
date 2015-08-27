package server.src.com.google.refine.tests.offline;

import com.google.refine.offline.ProjectCreateImportExportOffline;
import org.json.JSONException;
import org.testng.annotations.Test;

import javax.servlet.ServletException;
import java.io.File;
import java.io.IOException;

/**
 * User: rmcooksey
 * Date: 06/03/13
 * Time: 12:19
 */
public class ProjectCreateImportExportOfflineTest {

    /**
     * This simple test reads in a CSV input, and then uses some GREL to construct a new Refine project, and from this
     * exports a CSV output with the GREL refinements applied.
     *
     * @throws IOException
     * @throws ServletException
     * @throws JSONException
     */
    @Test
    public void OfflineProjectCreateImportExportTest() throws IOException, ServletException, JSONException {
        File workspaceRefineProjectFolder = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getFile(),"workspace");

        File inputFile = new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().getFile(),"testInput.csv");
        File outputFile = new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().getFile(),"testOutput.csv");

        String grelOperationsToApply =
                "[\n" +
                        "  {\n" +
                        "    \"op\": \"core/column-addition\",\n" +
                        "    \"description\": \"Create column RichCol at index 3 based on column treatment using expression grel:\\\"Rich \\\" + value\",\n" +
                        "    \"engineConfig\": {\n" +
                        "      \"facets\": [],\n" +
                        "      \"mode\": \"row-based\"\n" +
                        "            },\n" +
                        "    \"newColumnName\": \"RichCol\",\n" +
                        "    \"columnInsertIndex\": 3,\n" +
                        "    \"baseColumnName\": \"Colour\",\n" +
                        "    \"expression\": \"grel:\\\"Rich \\\" + value\",\n" +
                        "    \"onError\": \"set-to-blank\"\n" +
                        "              }\n" +
                        "]";

        ProjectCreateImportExportOffline projectCreateImportExportOffline = new ProjectCreateImportExportOffline(workspaceRefineProjectFolder,inputFile);
//        projectCreateImportExportOffline.applyRefinements(grelOperationsToApply);
        projectCreateImportExportOffline.exportCSV(outputFile);
    }
}
