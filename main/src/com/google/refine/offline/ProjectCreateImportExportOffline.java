package com.google.refine.offline;

import com.google.refine.ProjectManager;
import com.google.refine.browsing.Engine;
import com.google.refine.commands.history.ApplyOperationsCommand;
import com.google.refine.exporters.CsvExporter;
import com.google.refine.importing.DefaultImportingController;
import com.google.refine.importing.ImportingJob;
import com.google.refine.importing.ImportingManager;
import com.google.refine.importing.ImportingUtilities;
import com.google.refine.io.FileProjectManager;
import com.google.refine.model.Project;
import com.google.refine.operations.OperationRegistry;
import com.google.refine.operations.cell.*;
import com.google.refine.operations.column.*;
import com.google.refine.operations.recon.*;
import com.google.refine.operations.row.RowFlagOperation;
import com.google.refine.operations.row.RowRemovalOperation;
import com.google.refine.operations.row.RowReorderOperation;
import com.google.refine.operations.row.RowStarOperation;
import edu.mit.simile.butterfly.ButterflyModule;
import org.json.JSONException;

import javax.servlet.ServletException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * User: rmcooksey
 * Date: 05/03/13
 * Time: 12:24
 */
public class ProjectCreateImportExportOffline {
    public void applyRefinementsToFile(File workspaceRefineProjectFolder, File inputFile, File outputFile, String grelOperationsToApply) throws IOException, ServletException, JSONException {
        ProjectManager projectManager = new FileProjectManager(workspaceRefineProjectFolder);
        ProjectManager.singleton = projectManager;

        ImportingJob importingJob = ImportingManager.createOfflineJob();
        importingJob.getOrCreateDefaultConfig();
        long id = importingJob.id;

        ImportingUtilities.loadDataAndPrepareJobOffline(importingJob, importingJob.config, inputFile);

        DefaultImportingController defaultImportingController = new DefaultImportingController();
        long projectId = 0;
        try {
            projectId = defaultImportingController.doCreateProjectOffline(id);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Project myProject = projectManager.getProject(projectId);
        Engine engine = new Engine(myProject);

        registerRefineOperations(new DummyButterflyModule());

        new ApplyOperationsCommand(myProject, grelOperationsToApply);

//        projectManager.ensureProjectSaved(projectId);

        CsvExporter csvExporter = new CsvExporter();
        csvExporter.export(myProject, null, engine, new FileWriter(outputFile));

        System.out.println("Access in Refine here: http://127.0.0.1:3333/project?project=" + projectId);
    }

    private void registerRefineOperations(ButterflyModule dummyButterfly) {
        OperationRegistry.registerOperation(dummyButterfly, "text-transform", TextTransformOperation.class);
        OperationRegistry.registerOperation(dummyButterfly, "mass-edit", MassEditOperation.class);

        OperationRegistry.registerOperation(dummyButterfly, "multivalued-cell-join", MultiValuedCellJoinOperation.class);
        OperationRegistry.registerOperation(dummyButterfly, "multivalued-cell-split", MultiValuedCellSplitOperation.class);
        OperationRegistry.registerOperation(dummyButterfly, "fill-down", FillDownOperation.class);
        OperationRegistry.registerOperation(dummyButterfly, "blank-down", BlankDownOperation.class);
        OperationRegistry.registerOperation(dummyButterfly, "transpose-columns-into-rows", TransposeColumnsIntoRowsOperation.class);
        OperationRegistry.registerOperation(dummyButterfly, "transpose-rows-into-columns", TransposeRowsIntoColumnsOperation.class);
        OperationRegistry.registerOperation(dummyButterfly, "key-value-columnize", KeyValueColumnizeOperation.class);

        OperationRegistry.registerOperation(dummyButterfly, "column-addition", ColumnAdditionOperation.class);
        OperationRegistry.registerOperation(dummyButterfly, "column-removal", ColumnRemovalOperation.class);
        OperationRegistry.registerOperation(dummyButterfly, "column-rename", ColumnRenameOperation.class);
        OperationRegistry.registerOperation(dummyButterfly, "column-move", ColumnMoveOperation.class);
        OperationRegistry.registerOperation(dummyButterfly, "column-split", ColumnSplitOperation.class);
        OperationRegistry.registerOperation(dummyButterfly, "column-addition-by-fetching-urls", ColumnAdditionByFetchingURLsOperation.class);
        OperationRegistry.registerOperation(dummyButterfly, "column-reorder", ColumnReorderOperation.class);

        OperationRegistry.registerOperation(dummyButterfly, "row-removal", RowRemovalOperation.class);
        OperationRegistry.registerOperation(dummyButterfly, "row-star", RowStarOperation.class);
        OperationRegistry.registerOperation(dummyButterfly, "row-flag", RowFlagOperation.class);
        OperationRegistry.registerOperation(dummyButterfly, "row-reorder", RowReorderOperation.class);

        OperationRegistry.registerOperation(dummyButterfly, "recon", ReconOperation.class);
        OperationRegistry.registerOperation(dummyButterfly, "recon-mark-new-topics", ReconMarkNewTopicsOperation.class);
        OperationRegistry.registerOperation(dummyButterfly, "recon-match-best-candidates", ReconMatchBestCandidatesOperation.class);
        OperationRegistry.registerOperation(dummyButterfly, "recon-discard-judgments", ReconDiscardJudgmentsOperation.class);
        OperationRegistry.registerOperation(dummyButterfly, "recon-match-specific-topic-to-cells", ReconMatchSpecificTopicOperation.class);
        OperationRegistry.registerOperation(dummyButterfly, "recon-judge-similar-cells", ReconJudgeSimilarCellsOperation.class);
        OperationRegistry.registerOperation(dummyButterfly, "recon-clear-similar-cells", ReconClearSimilarCellsOperation.class);
        OperationRegistry.registerOperation(dummyButterfly, "recon-copy-across-columns", ReconCopyAcrossColumnsOperation.class);
    }

}
