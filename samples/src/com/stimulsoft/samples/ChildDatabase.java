package com.stimulsoft.samples;

import com.stimulsoft.base.exception.StiException;
import com.stimulsoft.report.StiReport;
import com.stimulsoft.report.dictionary.data.DataRow;
import com.stimulsoft.report.dictionary.data.DataTable;
import com.stimulsoft.report.dictionary.dataSources.StiDataStoreSource;
import com.stimulsoft.report.dictionary.databases.StiDatabase;

/**
 * Copyright Stimulsoft
 */
public class ChildDatabase extends StiDatabase {

    public ChildDatabase() {
        super("Demo.Child");// Database name
    }

    public void connect(StiDataStoreSource stiDataStoreSource, Boolean fillTable) throws StiException {
        DataTable dataTable = stiDataStoreSource.createNewTable();
        for (int i = 0; i < 5; i++) {
            for (int k = 0; k < 5; k++) {
                DataRow dataRow = dataTable.createNewRow();
                dataRow.addCell("cId", new ObjectCell("Object" + i));
                for (int j = 1; j < dataRow.getColumns().size(); j++) {// fill row wiht my data
                    dataRow.addCell(dataRow.getColumns().get(j).getColumnName(), "Child cId: " + i + " value: " + j);
                }
            }
        }
        stiDataStoreSource.setDataTable(dataTable);
    }

    public void disconnect() {
    }

    public void connect(StiDataStoreSource stiDataStoreSource) throws StiException {
        connect(stiDataStoreSource, true);
    }

    @Override
    public void connect(StiDataStoreSource stiDataStoreSource, StiReport report) throws StiException {
        connect(stiDataStoreSource);
    }

    @Override
    public void connect(StiDataStoreSource source, Boolean fillTable, StiReport report) throws StiException {
        connect(source);
    }

}
