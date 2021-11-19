package com.stimulsoft.samples;

import java.awt.Dimension;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.stimulsoft.base.drawing.StiColorEnum;
import com.stimulsoft.base.drawing.StiSolidBrush;
import com.stimulsoft.base.drawing.enums.StiBorderSides;
import com.stimulsoft.base.drawing.enums.StiTextHorAlignment;
import com.stimulsoft.base.exception.StiException;
import com.stimulsoft.base.system.geometry.StiRectangle;
import com.stimulsoft.report.StiReport;
import com.stimulsoft.report.components.StiPage;
import com.stimulsoft.report.components.bands.StiDataBand;
import com.stimulsoft.report.components.simplecomponents.StiText;
import com.stimulsoft.report.dictionary.StiDataColumn;
import com.stimulsoft.report.dictionary.StiDataColumnsCollection;
import com.stimulsoft.report.dictionary.StiDictionary;
import com.stimulsoft.report.dictionary.adapters.StiMySqlAdapter;
import com.stimulsoft.report.dictionary.dataSources.StiMySqlSource;
import com.stimulsoft.report.dictionary.databases.StiMySqlDatabase;
import com.stimulsoft.report.saveLoad.StiDocument;
import com.stimulsoft.report.utils.data.StiDataColumnsUtil;
import com.stimulsoft.report.utils.data.StiSqlField;
import com.stimulsoft.report.utils.data.StiTableFieldsRequest;
import com.stimulsoft.viewer.StiViewerFx;
import com.stimulsoft.viewer.events.StiViewCommonEvent;

/**
 * Copyright Stimulsoft
 */
public class CreatingReportwithMySQLDatabaseAtRuntime {

    public static StiReport createReport() throws ClassNotFoundException, SQLException, StiException, FileNotFoundException {
        StiReport report = StiReport.newInstance();
        StiPage page = report.getPages().get(0);
        report.setDictionary(new StiDictionary(report));
        StiMySqlDatabase db = new StiMySqlDatabase("test", "test", "url=jdbc:mysql://localhost:3306/sakila;user=root;password=terra2;database=sakila");
        report.getDictionary().getDatabases().add(db);
        StiMySqlSource source = new StiMySqlSource("test.actors", "actors", "actors", "select * from actor");
        source.setDictionary(report.getDictionary());
        report.getDictionary().getDataSources().add(source);
        source.setColumns(new StiDataColumnsCollection());
        StiMySqlAdapter adapter = new StiMySqlAdapter(db.getConnectionString());
        Class.forName(adapter.getDriverName());
        Connection con = com.stimulsoft.webdesigner.helper.StiDictionaryHelper.getConnection(adapter.getJdbcParameters());
        StiTableFieldsRequest request = StiDataColumnsUtil.getFields(con, source.getQuery(), source);
        for (StiSqlField field : request.getColumns()) {
            source.getColumns().add(new StiDataColumn(field.getName(), field.getName(), field.getSystemType()));
        }

        StiDataBand dataBand = new StiDataBand();
        dataBand.setDataSourceName("actors");
        dataBand.setHeight(0.5);
        dataBand.setName("DataBand");
        page.getComponents().add(dataBand);

        double pos = 0;
        double columnWidth = page.getWidth() / source.getColumns().size();
        Integer nameIndex = 1;
        for (StiDataColumn dataColumn : source.getColumns()) {
            // Create text on header
            StiText hText = new StiText(new StiRectangle(pos, 0, columnWidth, 0.5));

            hText.setTextInternal(dataColumn.getName());
            hText.setHorAlignment(StiTextHorAlignment.Center);
            hText.setName("HeaderText" + nameIndex.toString());
            hText.setBrush(new StiSolidBrush(StiColorEnum.Orange.color()));
            hText.getBorder().setSide(StiBorderSides.All);

            StiText dataText = new StiText(new StiRectangle(pos, 0, columnWidth, 0.5));
            dataText.setText("{actors." + dataColumn.getName() + "}");
            dataText.setName("DataText" + nameIndex.toString());
            dataText.getBorder().setSide(StiBorderSides.All);
            dataBand.getComponents().add(dataText);
            pos = pos + columnWidth;
            nameIndex++;
        }
        report.Render();
        return report;

    }

    public static void showReport(StiReport report) {
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(1000, 1000));
        StiViewerFx viewerPanel = new StiViewerFx(frame);
        panel.add(viewerPanel);
        frame.add(panel);
        frame.setSize(new Dimension(1000, 1000));
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        viewerPanel.getStiViewModel().getEventDispatcher()
                   .dispatchStiEvent(new StiViewCommonEvent(StiViewCommonEvent.DOCUMENT_FILE_LOADED, new StiDocument(report), null));
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException, StiException, FileNotFoundException {
        StiReport report = createReport();
        showReport(report);
    }

}
