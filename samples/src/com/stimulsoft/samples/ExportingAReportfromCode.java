package com.stimulsoft.samples;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.stimulsoft.base.exception.StiException;
import com.stimulsoft.base.exception.StiExceptionProvider;
import com.stimulsoft.report.StiExportManager;
import com.stimulsoft.report.StiReport;
import com.stimulsoft.report.StiSerializeManager;
import com.stimulsoft.report.dictionary.databases.StiXmlDatabase;
import com.stimulsoft.report.enums.StiCalculationMode;
import com.stimulsoft.report.enums.StiExportFormat;
import com.stimulsoft.viewer.controls.dialogs.StiFileSaveDialog;

/**
 * Copyright Stimulsoft
 */
public class ExportingAReportfromCode extends JPanel {

    private static final long serialVersionUID = 9151954987423009222L;
    private static final Dimension FRAME_SIZE = new Dimension(1000, 500);
    private StiReport report;

    public ExportingAReportfromCode(JFrame parentFrame) {
        setLayout(new GridLayout(9, 2, 20, 20));
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Export"));
        setPreferredSize(FRAME_SIZE);

        JButton exportBtn = new JButton("Export to PDF");
        exportBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                export(StiExportFormat.Pdf);
            }
        });
        add(exportBtn);

        exportBtn = new JButton("Export to XPS");
        exportBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                export(StiExportFormat.Xps);
            }
        });
        add(exportBtn);

        exportBtn = new JButton("Export to HTML");
        exportBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                export(StiExportFormat.Html);
            }
        });
        add(exportBtn);

        exportBtn = new JButton("Export to Text");
        exportBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                export(StiExportFormat.Text);
            }
        });
        add(exportBtn);

        exportBtn = new JButton("Export to Rich Text");
        exportBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                export(StiExportFormat.Rtf);
            }
        });
        add(exportBtn);

        exportBtn = new JButton("Export to Microsoft Word 2007");
        exportBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                export(StiExportFormat.Word2007);
            }
        });
        add(exportBtn);

        exportBtn = new JButton("Export to Microsoft Excel");
        exportBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                export(StiExportFormat.Excel);
            }
        });
        add(exportBtn);

        exportBtn = new JButton("Export to Microsft Excel XML");
        exportBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                export(StiExportFormat.ExcelXml);
            }
        });
        add(exportBtn);

        exportBtn = new JButton("Export to Microsoft Excel 2007");
        exportBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                export(StiExportFormat.Excel2007);
            }
        });
        add(exportBtn);

        exportBtn = new JButton("Export to BMP");
        exportBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                export(StiExportFormat.ImageBmp);
            }
        });
        add(exportBtn);

        exportBtn = new JButton("Export to JPEG");
        exportBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                export(StiExportFormat.ImageJpeg);
            }
        });
        add(exportBtn);

        exportBtn = new JButton("Export to PCX");
        exportBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                export(StiExportFormat.ImagePcx);
            }
        });
        add(exportBtn);

        exportBtn = new JButton("Export to PNG");
        exportBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                export(StiExportFormat.ImagePng);
            }
        });
        add(exportBtn);

        exportBtn = new JButton("Export to SVG");
        exportBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                export(StiExportFormat.ImageSvg);
            }
        });
        add(exportBtn);

        exportBtn = new JButton("Export to SVGZ");
        exportBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                export(StiExportFormat.ImageSvgz);
            }
        });
        add(exportBtn);

        exportBtn = new JButton("Export to CSV");
        exportBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                export(StiExportFormat.Csv);
            }
        });
        add(exportBtn);

        exportBtn = new JButton("Export to XML");
        exportBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                export(StiExportFormat.Xml);
            }
        });
        add(exportBtn);

        exportBtn = new JButton("Export to SYLK");
        exportBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                export(StiExportFormat.Sylk);
            }
        });
        add(exportBtn);

    }

    private void export(StiExportFormat format) {
        final StiReport report = getReport();
        final StiFileSaveDialog stiFileChooser = new StiFileSaveDialog(format, report, report.getReportAlias());
        int chooserResult = stiFileChooser.showSaveDialog(this);
        if (chooserResult == JFileChooser.APPROVE_OPTION) {
            FileOutputStream outputStream = null;
            try {
                outputStream = new FileOutputStream(stiFileChooser.getFile());
                switch (format) {
                case Pdf:
                    StiExportManager.exportPdf(report, outputStream);
                    break;
                case Xps:
                    StiExportManager.exportXps(report, outputStream);
                    break;
                case Html:
                    StiExportManager.exportHtml(report, outputStream);
                    break;
                case Text:
                    StiExportManager.exportText(report, outputStream);
                    break;
                case Rtf:
                    StiExportManager.exportRtf(report, outputStream);
                    break;
                case Word2007:
                    StiExportManager.exportWord2007(report, outputStream);
                    break;
                case Excel:
                    StiExportManager.exportExcel(report, outputStream);
                    break;
                case ExcelXml:
                    StiExportManager.exportExcelXml(report, outputStream);
                    break;
                case Excel2007:
                    StiExportManager.exportExcel2007(report, outputStream);
                    break;
                case Csv:
                    StiExportManager.exportCsv(report, outputStream);
                    break;
                case Xml:
                    StiExportManager.exportXml(report, outputStream);
                    break;
                case Sylk:
                    StiExportManager.exportSylk(report, outputStream);
                    break;
                case ImageBmp:
                    StiExportManager.exportImageBmp(report, outputStream);
                    break;
                case ImageJpeg:
                    StiExportManager.exportImageJpeg(report, outputStream);
                    break;
                case ImagePcx:
                    StiExportManager.exportImagePcx(report, outputStream);
                    break;
                case ImagePng:
                    StiExportManager.exportImagePng(report, outputStream);
                    break;
                case ImageSvg:
                    StiExportManager.exportImageSvg(report, outputStream);
                    break;
                case ImageSvgz:
                    StiExportManager.exportImageSvgz(report, outputStream);
                    break;

                }
                JOptionPane.showMessageDialog(null, "Export finished");

            } catch (FileNotFoundException e) {
                StiExceptionProvider.show(e, null);
            } catch (StiException e) {
                StiExceptionProvider.show(e, null);
            } finally {
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        StiExceptionProvider.show(e, null);
                    }
                }
            }

        }

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
                    frame.add(new ExportingAReportfromCode(frame));
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
