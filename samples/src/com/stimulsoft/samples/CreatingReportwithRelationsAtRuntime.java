package com.stimulsoft.samples;

import java.awt.Dimension;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.stimulsoft.base.drawing.enums.StiBorderSides;
import com.stimulsoft.base.drawing.enums.StiTextHorAlignment;
import com.stimulsoft.base.exception.StiExceptionProvider;
import com.stimulsoft.base.system.StiFont;
import com.stimulsoft.base.system.StiFontStyle;
import com.stimulsoft.base.system.geometry.StiRectangle;
import com.stimulsoft.base.system.type.StiSystemType;
import com.stimulsoft.report.StiNameCreation;
import com.stimulsoft.report.StiReport;
import com.stimulsoft.report.components.StiPage;
import com.stimulsoft.report.components.bands.StiDataBand;
import com.stimulsoft.report.components.bands.StiHeaderBand;
import com.stimulsoft.report.components.simplecomponents.StiText;
import com.stimulsoft.report.dictionary.StiDataColumn;
import com.stimulsoft.report.dictionary.StiDataColumnsCollection;
import com.stimulsoft.report.dictionary.StiDataRelation;
import com.stimulsoft.report.dictionary.StiDictionary;
import com.stimulsoft.report.dictionary.dataSources.StiDataTableSource;
import com.stimulsoft.report.saveLoad.StiDocument;
import com.stimulsoft.viewer.StiViewerFx;
import com.stimulsoft.viewer.events.StiViewCommonEvent;

/**
 * Sample demonstrate how to create report with relation Copyright Stimulsoft
 */
public class CreatingReportwithRelationsAtRuntime extends JPanel {

    private static final long serialVersionUID = 330448692680237867L;
    private static final Dimension FRAME_SIZE = new Dimension(800, 800);

    public CreatingReportwithRelationsAtRuntime(final JFrame parentFrame) throws IOException {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(FRAME_SIZE);
        StiViewerFx viewerPanel = new StiViewerFx(parentFrame);
        add(viewerPanel);

        StiReport report = new StiReport();

        StiPage page = new StiPage(report);
        report.getPages().add(page);
        page.setName(StiNameCreation.createName(report, StiNameCreation.generateName(page)));
        report.setDictionary(new StiDictionary(report));
        report.getDictionary().getDatabases().add(new ParentDatabase());
        report.getDictionary().getDatabases().add(new ChildDatabase());

        List<StiDataTableSource> tableSources = new ArrayList<StiDataTableSource>();
        // parent
        StiDataTableSource tSource = new StiDataTableSource("Demo.Parent", "Parent", "Parent");
        tSource.setColumns(new StiDataColumnsCollection());
        tSource.getColumns().add(new StiDataColumn("cId", "cId", StiSystemType.getSystemType("System.Object")));
        tSource.getColumns().add(new StiDataColumn("cName", "cName", StiSystemType.getSystemType("System.String")));
        tSource.setDictionary(report.getDictionary());
        report.getDictionary().getDataSources().add(tSource);
        tableSources.add(tSource);
        // child
        tSource = new StiDataTableSource("Demo.Child", "Child", "Child");
        tSource.setColumns(new StiDataColumnsCollection());
        tSource.getColumns().add(new StiDataColumn("cId", "cId", StiSystemType.getSystemType("System.Object")));
        for (int i = 0; i < 4; i++) {
            tSource.getColumns().add(new StiDataColumn("cData" + i, "cData" + i, StiSystemType.getSystemType("System.String")));
        }
        tSource.setDictionary(report.getDictionary());
        report.getDictionary().getDataSources().add(tSource);
        tableSources.add(tSource);

        // Create TitleBand
        StiHeaderBand titleBand = new StiHeaderBand();
        titleBand.setHeight(0.85);
        titleBand.setName("TitleBand");
        page.getComponents().add(titleBand);

        // Create Title text on header
        StiText headerText = new StiText(new StiRectangle(0, 0, page.getWidth(), 0.85));
        headerText.setTextInternal("Tacticdescription");
        headerText.setHorAlignment(StiTextHorAlignment.Left);
        headerText.setName("TitleHeader");
        headerText.setFont(new StiFont("Arial", 12F, StiFontStyle.Bold));
        titleBand.getComponents().add(headerText);
        Integer nameIndex = 1;
        List<StiDataBand> dataBands = new ArrayList<StiDataBand>();
        for (StiDataTableSource tableSource : tableSources) {

            // Create Databand
            StiDataBand dataBand = new StiDataBand();
            dataBand.setDataSourceName(tableSource.getName());
            dataBand.setHeight(0.5);
            dataBand.setName("DataBand" + nameIndex);
            nameIndex++;
            page.getComponents().add(dataBand);

            double pos = 0;
            double columnWidth = page.getWidth() / tableSource.getColumns().size();

            for (StiDataColumn dataColumn : tableSource.getColumns()) {

                StiText dataText = new StiText(new StiRectangle(pos, 0, columnWidth, 0.5));
                dataText.setText("{" + tableSource.getName() + "." + dataColumn.getName() + "}");
                dataText.setName("DataText" + nameIndex.toString());
                dataText.getBorder().setSide(StiBorderSides.All);
                dataBand.getComponents().add(dataText);

                pos = pos + columnWidth;
                nameIndex++;
            }
            dataBands.add(dataBand);
        }

        dataBands.get(1).setDataRelationName("Relation");
        dataBands.get(1).setMasterComponent(dataBands.get(0));

        ArrayList<String> cols = new ArrayList<String>();
        cols.add("cId");

        StiDataRelation rel = new StiDataRelation("Relation", tableSources.get(0), tableSources.get(1), cols, cols);

        report.getDictionary().getRelations().add(rel);

        report.Render();

        viewerPanel.getStiViewModel().getEventDispatcher()
                   .dispatchStiEvent(new StiViewCommonEvent(StiViewCommonEvent.DOCUMENT_FILE_LOADED, new StiDocument(report), null));
    }

    public static void main(final String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    JFrame frame = new JFrame();
                    frame.add(new CreateRelationsReport(frame));
                    frame.setSize(FRAME_SIZE);
                    frame.setLocationRelativeTo(null);
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setVisible(true);
                } catch (Throwable e) {
                    StiExceptionProvider.show(e, null);
                }
            }
        });
    }
}
