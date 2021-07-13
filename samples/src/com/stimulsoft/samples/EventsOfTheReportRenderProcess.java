package com.stimulsoft.samples;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.xml.sax.SAXException;

import com.stimulsoft.base.exception.StiExceptionProvider;
import com.stimulsoft.base.serializing.StiDeserializationException;
import com.stimulsoft.base.system.StiEventHandlerListener;
import com.stimulsoft.base.system.StiEventObject;
import com.stimulsoft.base.worker.StiSimpleWorker;
import com.stimulsoft.report.StiReport;
import com.stimulsoft.report.StiSerializeManager;
import com.stimulsoft.report.dictionary.databases.StiXmlDatabase;

/**
 * Copyright Stimulsoft
 */
public class EventsOfTheReportRenderProcess extends JPanel {

    private static final long serialVersionUID = 873209843262329949L;

    private static final Dimension FRAME_SIZE = new Dimension(500, 400);

    private JTextField beginRender;
    private JTextField subProcessField1;
    private JTextField subProcessField2;
    private JTextField subProcessField3;
    private JTextField subProcessField4;
    private JTextField finishField;
    private JTextArea textArea;

    public EventsOfTheReportRenderProcess(final JFrame parentFrame) throws IOException, SAXException, StiDeserializationException {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setPreferredSize(FRAME_SIZE);

        JPanel leftPanel = new JPanel(new GridLayout(7, 1, 10, 10));
        leftPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Event handler"));
        leftPanel.setPreferredSize(new Dimension(250, 250));
        leftPanel.setMaximumSize(new Dimension(250, 250));

        add(leftPanel);

        leftPanel.add(beginRender = new JTextField("handlerBeginRender"));
        leftPanel.add(subProcessField1 = new JTextField("handlerRendering 1"));
        leftPanel.add(subProcessField2 = new JTextField("handlerRendering 2"));
        leftPanel.add(subProcessField3 = new JTextField("page handlerBeginRender"));
        leftPanel.add(subProcessField4 = new JTextField("page handlerEndRender"));
        leftPanel.add(finishField = new JTextField("handlerEndRender"));

        add(textArea = new JTextArea(""));
        textArea.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Console process"));
        textArea.setEditable(false);

        final StiReport report = StiSerializeManager.deserializeReport(new File("Reports", "SimpleList.mrt"));
        StiXmlDatabase xmlDatabase = new StiXmlDatabase("Demo", "Data/" + "Demo.xsd", "Data/" + "Demo.xml");
        report.getDictionary().getDatabases().add(xmlDatabase);
        report.handlerBeginRender.add(new StiEventHandlerListener() {
            public void invoke(StiEventObject myEvent) {
                appendText(beginRender);
            }

        });

        report.handlerRendering.add(new StiEventHandlerListener() {
            public void invoke(StiEventObject myEvent) {
                appendText(subProcessField1);
            }
        });
        report.handlerRendering.add(new StiEventHandlerListener() {
            public void invoke(StiEventObject myEvent) {
                appendText(subProcessField2);
            }
        });

        report.getPages().get(0).handlerBeginRender.add(new StiEventHandlerListener() {
            public void invoke(StiEventObject myEvent) {
                appendText(subProcessField3);
            }
        });

        report.getPages().get(0).handlerEndRender.add(new StiEventHandlerListener() {
            public void invoke(StiEventObject myEvent) {
                appendText(subProcessField4);
            }
        });

        report.handlerEndRender.add(new StiEventHandlerListener() {
            public void invoke(StiEventObject myEvent) {
                appendText(finishField);
            }
        });
        final JButton button = new JButton("Render");
        leftPanel.add(button);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textArea.setText("");
                StiSimpleWorker renderWorker = new StiSimpleWorker() {
                    @Override
                    protected void doInBackground() throws Throwable {
                        report.render();
                    }
                };
                renderWorker.execute();
            }
        });
    }

    private void appendText(JTextField field) {
        final String text = field.getText() + "\n";
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                textArea.append(text);
            }
        });
    }

    public static void main(final String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    JFrame frame = new JFrame();
                    frame.add(new EventsOfTheReportRenderProcess(frame));
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
