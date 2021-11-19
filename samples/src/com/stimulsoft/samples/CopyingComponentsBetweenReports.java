package com.stimulsoft.samples;

import java.io.IOException;

import org.xml.sax.SAXException;

import com.stimulsoft.base.serializing.StiDeserializationException;
import com.stimulsoft.base.serializing.StiDeserializerControler;
import com.stimulsoft.base.serializing.StiSerializerControler;
import com.stimulsoft.base.utils.StiResourceUtil;
import com.stimulsoft.report.StiNameCreation;
import com.stimulsoft.report.StiReport;
import com.stimulsoft.report.StiSerializeManager;
import com.stimulsoft.report.components.StiComponent;
import com.stimulsoft.report.components.bands.StiHeaderBand;

/**
 * Copyright Stimulsoft
 */
public class CopyingComponentsBetweenReports {

    /**
     * @param args
     * @throws StiDeserializationException
     * @throws SAXException
     * @throws IOException
     */
    public static void main(String[] args) throws IOException, SAXException, StiDeserializationException {
        StiReport originalReport = StiSerializeManager.deserializeReport(StiResourceUtil.getStream("/com/stimulsoft/samples/OriginalReport.mrt"));
        StiReport customerReport = StiSerializeManager.deserializeReport(StiResourceUtil.getStream("/com/stimulsoft/samples/CustomerReport.mrt"));

        StiHeaderBand originalHeader = (StiHeaderBand) originalReport.getComponents().get("HeaderBand1");
        int originalIndex = originalHeader.getPage().getComponents().indexOf(originalHeader);
        originalHeader.getPage().getComponents().remove(originalIndex);

        StiHeaderBand customerHeader = (StiHeaderBand) customerReport.getComponents().get("HeaderBand1");
        String originalHeaderStr = StiSerializerControler.serializedObjectAsString(customerHeader);
        StiHeaderBand newCustomerHeader = new StiHeaderBand();
        StiDeserializerControler.deserializeFromString(originalHeaderStr, newCustomerHeader);
        newCustomerHeader.setPage(originalReport.getPages().get(0));
        originalReport.getPages().get(0).getComponents().add(originalIndex, newCustomerHeader);
        newCustomerHeader.setName(StiNameCreation.createName(originalReport, "HeaderBand"));
        for (StiComponent component : newCustomerHeader.getComponents()) {
            component.setPage(originalReport.getPages().get(0));
            component.setParent(newCustomerHeader);
            component.setName(StiNameCreation.createName(originalReport, component.getName().replaceAll("\\d*", "")));
        }
        // StiSerializeManager.serializeReport(originalReport, new
        // FileOutputStream("new_report.mrt"));

    }

}
