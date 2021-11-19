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
import com.stimulsoft.base.system.StiFileExecuter;
import com.stimulsoft.report.StiExportManager;
import com.stimulsoft.report.StiReport;
import com.stimulsoft.report.StiSerializeManager;
import com.stimulsoft.report.dictionary.databases.StiXmlDatabase;
import com.stimulsoft.report.enums.StiCalculationMode;
import com.stimulsoft.report.enums.StiExportFormat;
import com.stimulsoft.report.export.settings.StiBmpExportSettings;
import com.stimulsoft.report.export.settings.StiCsvExportSettings;
import com.stimulsoft.report.export.settings.StiExcel2007ExportSettings;
import com.stimulsoft.report.export.settings.StiExcelExportSettings;
import com.stimulsoft.report.export.settings.StiExcelXmlExportSettings;
import com.stimulsoft.report.export.settings.StiExportSettings;
import com.stimulsoft.report.export.settings.StiHtmlExportSettings;
import com.stimulsoft.report.export.settings.StiJpegExportSettings;
import com.stimulsoft.report.export.settings.StiPcxExportSettings;
import com.stimulsoft.report.export.settings.StiPdfExportSettings;
import com.stimulsoft.report.export.settings.StiPngExportSettings;
import com.stimulsoft.report.export.settings.StiRtfExportSettings;
import com.stimulsoft.report.export.settings.StiSvgExportSettings;
import com.stimulsoft.report.export.settings.StiSvgzExportSettings;
import com.stimulsoft.report.export.settings.StiSylkExportSettings;
import com.stimulsoft.report.export.settings.StiTxtExportSettings;
import com.stimulsoft.report.export.settings.StiWord2007ExportSettings;
import com.stimulsoft.report.export.settings.StiXmlExportSettings;
import com.stimulsoft.report.export.settings.StiXpsExportSettings;
import com.stimulsoft.viewer.controls.dialogs.StiFileSaveDialog;
import com.stimulsoft.viewer.form.export.StiDataExportDialog;
import com.stimulsoft.viewer.form.export.StiExcelExportDialog;
import com.stimulsoft.viewer.form.export.StiHtmlExportDialog;
import com.stimulsoft.viewer.form.export.StiImageExportDialog;
import com.stimulsoft.viewer.form.export.StiPdfExportDialog;
import com.stimulsoft.viewer.form.export.StiRtfExportDialog;
import com.stimulsoft.viewer.form.export.StiTxtExportDialog;
import com.stimulsoft.viewer.form.export.StiWord2007ExportDialog;
import com.stimulsoft.viewer.form.export.StiXpsExportDialog;

/**
 * Copyright Stimulsoft
 */
public class ExportingAReportWithTheExportDialog extends JPanel {

    private static final long serialVersionUID = -5616850994684236452L;
    private static final Dimension FRAME_SIZE = new Dimension(1000, 500);
    private StiReport report;
    private JFrame parentFrame;

    public ExportingAReportWithTheExportDialog(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        setLayout(new GridLayout(9, 2, 20, 20));
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Export with settings"));
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

        StiExportSettings settings = null;
        switch (format) {
        case Html:
            settings = StiHtmlExportDialog.showDialog(parentFrame, false, 1);
            break;
        case ImageBmp:
        case ImageJpeg:
        case ImagePng:
        case ImageSvg:
        case ImageSvgz:
        case ImagePcx:
            settings = StiImageExportDialog.showDialog(parentFrame, false, 1);
            break;
        case Text:
            settings = StiTxtExportDialog.showDialog(parentFrame, false, 1);
            break;
        case Rtf:
            settings = StiRtfExportDialog.showDialog(parentFrame, false, 1);
            break;
        case Xps:
            settings = StiXpsExportDialog.showDialog(parentFrame, false, 1);
            break;
        case Csv:
            settings = StiDataExportDialog.showDialog(parentFrame, false, 1);
            break;
        case Word2007:
            settings = StiWord2007ExportDialog.showDialog(parentFrame, false, 1);
            break;
        case Pdf:
            settings = StiPdfExportDialog.showDialog(parentFrame, false, 1);
            break;
        case Excel:
            settings = StiExcelExportDialog.showDialog(parentFrame, false, 1);
            break;
        default:
            break;
        }
        if (settings != null) {
            if (settings instanceof StiExcel2007ExportSettings) {
                format = StiExportFormat.Excel2007;
            } else if (settings instanceof StiExcelExportSettings) {
                format = StiExportFormat.Excel;
            } else if (settings instanceof StiExcelXmlExportSettings) {
                format = StiExportFormat.ExcelXml;
            } else if (settings instanceof StiSylkExportSettings) {
                format = StiExportFormat.Sylk;
            } else if (settings instanceof StiXmlExportSettings) {
                format = StiExportFormat.Xml;
            }

            final StiFileSaveDialog stiFileChooser = new StiFileSaveDialog(format, report, report.getReportAlias());
            int chooserResult = stiFileChooser.showSaveDialog(this);
            if (chooserResult == JFileChooser.APPROVE_OPTION) {
                FileOutputStream outputStream = null;
                try {
                    outputStream = new FileOutputStream(stiFileChooser.getFile());
                    switch (format) {
                    case Pdf:
                        StiExportManager.exportPdf(report, (StiPdfExportSettings) settings, outputStream);
                        break;
                    case Xps:
                        StiExportManager.exportXps(report, (StiXpsExportSettings) settings, outputStream);
                        break;
                    case Html:
                        StiExportManager.exportHtml(report, (StiHtmlExportSettings) settings, outputStream);
                        break;
                    case Text:
                        StiExportManager.exportText(report, (StiTxtExportSettings) settings, outputStream);
                        break;
                    case Rtf:
                        StiExportManager.exportRtf(report, (StiRtfExportSettings) settings, outputStream);
                        break;
                    case Word2007:
                        StiExportManager.exportWord2007(report, (StiWord2007ExportSettings) settings, outputStream);
                        break;
                    case Excel2007:
                        StiExportManager.exportExcel2007(report, (StiExcel2007ExportSettings) settings, outputStream);
                        break;
                    case Excel:
                        StiExportManager.exportExcel(report, (StiExcelExportSettings) settings, outputStream);
                        break;
                    case ExcelXml:
                        StiExportManager.exportExcelXml(report, (StiExcelXmlExportSettings) settings, outputStream);
                        break;
                    case Csv:
                        StiExportManager.exportCsv(report, (StiCsvExportSettings) settings, outputStream);
                        break;
                    case Xml:
                        StiExportManager.exportXml(report, (StiXmlExportSettings) settings, outputStream);
                        break;
                    case Sylk:
                        StiExportManager.exportSylk(report, (StiSylkExportSettings) settings, outputStream);
                        break;
                    case ImageBmp:
                        StiExportManager.exportImageBmp(report, (StiBmpExportSettings) settings, outputStream);
                        break;
                    case ImageJpeg:
                        StiExportManager.exportImageJpeg(report, (StiJpegExportSettings) settings, outputStream);
                        break;
                    case ImagePcx:
                        StiExportManager.exportImagePcx(report, (StiPcxExportSettings) settings, outputStream);
                        break;
                    case ImagePng:
                        StiExportManager.exportImagePng(report, (StiPngExportSettings) settings, outputStream);
                        break;
                    case ImageSvg:
                        StiExportManager.exportImageSvg(report, (StiSvgExportSettings) settings, outputStream);
                        break;
                    case ImageSvgz:
                        StiExportManager.exportImageSvgz(report, (StiSvgzExportSettings) settings, outputStream);
                        break;
                    default:
                        break;

                    }
                    if (settings.isOpenAfterExport()) {
                        StiFileExecuter.openByExtension(stiFileChooser.getFile().getAbsolutePath());
                    } else {
                        JOptionPane.showMessageDialog(null, "Export finished");
                    }

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
                    frame.add(new ExportReportSettings(frame));
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
