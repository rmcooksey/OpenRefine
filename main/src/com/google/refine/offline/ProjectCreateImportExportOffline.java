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

    long projectId = 0;
    Project myProject;

    public ProjectCreateImportExportOffline (File workspaceRefineProjectFolder, File inputFile) {
        ProjectManager projectManager = new FileProjectManager(workspaceRefineProjectFolder);
        ProjectManager.singleton = projectManager;

        ImportingJob importingJob = ImportingManager.createOfflineJob();
        importingJob.getOrCreateDefaultConfig();
        long id = importingJob.id;

        ImportingUtilities.loadDataAndPrepareJobOffline(importingJob, importingJob.config, inputFile);

        DefaultImportingController defaultImportingController = new DefaultImportingController();
        try {
            projectId = defaultImportingController.doCreateProjectOffline(id);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        myProject = projectManager.getProject(projectId);

        ProjectManager.singleton.ensureProjectSaved(projectId,myProject);
        System.out.println("Access in Refine here: http://127.0.0.1:3333/project?project=" + projectId);
    }

    public boolean applyRefinements(String grelOperationsToApply) {
        registerRefineOperations();

        try {
            new ApplyOperationsCommand(myProject, grelOperationsToApply);
        } catch (JSONException e) {
            return false;
        }

        ProjectManager.singleton.ensureProjectSaved(projectId,myProject);
        return true;
    }

    public void exportCSV(File outputFile) throws IOException {
        Engine engine = new Engine(myProject);

        CsvExporter csvExporter = new CsvExporter();
        csvExporter.export(myProject, null, engine, new FileWriter(outputFile));
    }

    private void registerRefineOperations() {
        String butterflyModuleName = "core";

        OperationRegistry.registerOperation(butterflyModuleName, "text-transform", TextTransformOperation.class);
        OperationRegistry.registerOperation(butterflyModuleName, "mass-edit", MassEditOperation.class);

        OperationRegistry.registerOperation(butterflyModuleName, "multivalued-cell-join", MultiValuedCellJoinOperation.class);
        OperationRegistry.registerOperation(butterflyModuleName, "multivalued-cell-split", MultiValuedCellSplitOperation.class);
        OperationRegistry.registerOperation(butterflyModuleName, "fill-down", FillDownOperation.class);
        OperationRegistry.registerOperation(butterflyModuleName, "blank-down", BlankDownOperation.class);
        OperationRegistry.registerOperation(butterflyModuleName, "transpose-columns-into-rows", TransposeColumnsIntoRowsOperation.class);
        OperationRegistry.registerOperation(butterflyModuleName, "transpose-rows-into-columns", TransposeRowsIntoColumnsOperation.class);
        OperationRegistry.registerOperation(butterflyModuleName, "key-value-columnize", KeyValueColumnizeOperation.class);

        OperationRegistry.registerOperation(butterflyModuleName, "column-addition", ColumnAdditionOperation.class);
        OperationRegistry.registerOperation(butterflyModuleName, "column-removal", ColumnRemovalOperation.class);
        OperationRegistry.registerOperation(butterflyModuleName, "column-rename", ColumnRenameOperation.class);
        OperationRegistry.registerOperation(butterflyModuleName, "column-move", ColumnMoveOperation.class);
        OperationRegistry.registerOperation(butterflyModuleName, "column-split", ColumnSplitOperation.class);
        OperationRegistry.registerOperation(butterflyModuleName, "column-addition-by-fetching-urls", ColumnAdditionByFetchingURLsOperation.class);
        OperationRegistry.registerOperation(butterflyModuleName, "column-reorder", ColumnReorderOperation.class);

        OperationRegistry.registerOperation(butterflyModuleName, "row-removal", RowRemovalOperation.class);
        OperationRegistry.registerOperation(butterflyModuleName, "row-star", RowStarOperation.class);
        OperationRegistry.registerOperation(butterflyModuleName, "row-flag", RowFlagOperation.class);
        OperationRegistry.registerOperation(butterflyModuleName, "row-reorder", RowReorderOperation.class);

        OperationRegistry.registerOperation(butterflyModuleName, "recon", ReconOperation.class);
        OperationRegistry.registerOperation(butterflyModuleName, "recon-mark-new-topics", ReconMarkNewTopicsOperation.class);
        OperationRegistry.registerOperation(butterflyModuleName, "recon-match-best-candidates", ReconMatchBestCandidatesOperation.class);
        OperationRegistry.registerOperation(butterflyModuleName, "recon-discard-judgments", ReconDiscardJudgmentsOperation.class);
        OperationRegistry.registerOperation(butterflyModuleName, "recon-match-specific-topic-to-cells", ReconMatchSpecificTopicOperation.class);
        OperationRegistry.registerOperation(butterflyModuleName, "recon-judge-similar-cells", ReconJudgeSimilarCellsOperation.class);
        OperationRegistry.registerOperation(butterflyModuleName, "recon-clear-similar-cells", ReconClearSimilarCellsOperation.class);
        OperationRegistry.registerOperation(butterflyModuleName, "recon-copy-across-columns", ReconCopyAcrossColumnsOperation.class);
    }

    public long getProjectId() {
        return projectId;
    }
}
