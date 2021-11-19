package com.stimulsoft.samples;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.AttributeSet;
import javax.print.attribute.HashAttributeSet;
import javax.print.attribute.standard.PrinterName;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.xml.sax.SAXException;

import com.stimulsoft.base.exception.StiExceptionProvider;
import com.stimulsoft.base.serializing.StiDeserializationException;
import com.stimulsoft.report.StiReport;
import com.stimulsoft.report.StiSerializeManager;
import com.stimulsoft.report.dictionary.databases.StiXmlDatabase;
import com.stimulsoft.report.enums.StiCalculationMode;
import com.stimulsoft.report.print.StiPrintHelper;

/**
 * Copyright Stimulsoft
 */
public class PrintingTheReportTemplate extends JPanel {

    private static final long serialVersionUID = -1979299780453288035L;

    private static final Dimension FRAME_SIZE = new Dimension(500, 400);

    private StiReport report;

    public PrintingTheReportTemplate(final JFrame parentFrame) throws IOException, SAXException, StiDeserializationException {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setPreferredSize(FRAME_SIZE);

        JPanel leftPanel = new JPanel(new GridLayout(7, 1, 10, 10));
        leftPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Print with print dialog"));
        leftPanel.setPreferredSize(new Dimension(250, 250));
        leftPanel.setMaximumSize(new Dimension(250, 250));
        add(leftPanel);
        JButton button = new JButton("Print");
        leftPanel.add(button);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StiReport report = getReport();
                PrinterJob printerJob = StiPrintHelper.preparePrinterJob(report.getRenderedPages());
                try {
                    StiPrintHelper.printJob(printerJob, report, true);
                } catch (PrinterException pe) {
                    StiExceptionProvider.show(pe, null);
                }
            }
        });

        JPanel rightPanel = new JPanel(new GridLayout(7, 1, 10, 10));
        rightPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Print without print dialog"));
        rightPanel.setPreferredSize(new Dimension(250, 250));
        rightPanel.setMaximumSize(new Dimension(250, 250));
        add(rightPanel);
        rightPanel.add(new JLabel("Select printer:"));
        final JComboBox printerList = new JComboBox(PrintServiceLookup.lookupPrintServices(null, null));
        rightPanel.add(printerList);
        printerList.setSelectedIndex(0);
        button = new JButton("Print");
        rightPanel.add(button);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StiReport report = getReport();
                PrinterJob printerJob = StiPrintHelper.preparePrinterJob(report.getRenderedPages());
                try {
                    AttributeSet attr_set = new HashAttributeSet();
                    PrintService printService = (PrintService) printerList.getSelectedItem();
                    attr_set.add(new PrinterName(printService.getName(), null));
                    PrintService[] services = PrintServiceLookup.lookupPrintServices(null, attr_set);
                    printerJob.setPrintService(services[0]);
                    StiPrintHelper.printJob(printerJob, report);
                } catch (Exception e1) {
                    StiExceptionProvider.show(e1, parentFrame);
                }
            }
        });

    }

    private StiReport getReport() {
        if (report == null) {
            try {
                String demoDir = "Data/";
                StiXmlDatabase xmlDatabase = new StiXmlDatabase("Demo", demoDir + "Demo.xsd", demoDir + "Demo.xml");
                StiReport renderReport = StiSerializeManager.deserializeReport(new File("Reports/SimpleList.mrt"));
                renderReport.getDictionary().getDatabases().add(xmlDatabase);
                renderReport.setCalculationMode(StiCalculationMode.Interpretation);
                renderReport.Render(false);
                report = renderReport;
            } catch (Exception e) {
                StiExceptionProvider.show(e, null);
            }
        }
        return report;
    }

    public static void main(final String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    JFrame frame = new JFrame();
                    frame.add(new PrintingTheReportTemplate(frame));
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
