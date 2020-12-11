package editor;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextEditor extends JFrame {

    JTextArea jTextArea;
    JTextField searchField;
    JFileChooser jfc;
    JCheckBox useRegex;
    ArrayList<Integer> searchResult = new ArrayList<>();
    ArrayList<Integer> resultLength = new ArrayList<>();
    int currentPosition = 0;


    public TextEditor() {

        jTextArea = new JTextArea();
        jTextArea.setName("TextArea");

        JScrollPane jScrollPane = new JScrollPane(jTextArea);
        jScrollPane.setName("ScrollPane");
        add(jScrollPane, BorderLayout.CENTER);

        JPanel saveLoadPanel = new JPanel();

        JButton openButton = new JButton();
        openButton.setSize(20, 20);
        setIcon("C:\\Users\\lt\\IdeaProjects\\Text Editor\\open.png", openButton);
        openButton.addActionListener(actionEvent -> loadFile());
        openButton.setName("OpenButton");
        saveLoadPanel.add(openButton);

        JButton saveButton = new JButton();
        saveButton.setSize(20, 20);
        setIcon("C:\\Users\\lt\\IdeaProjects\\Text Editor\\save.png", saveButton);
        saveButton.addActionListener(actionEvent -> saveFile());
        saveButton.setName("SaveButton");
        saveLoadPanel.add(saveButton);

        searchField = new JTextField(15);
        searchField.setName("SearchField");
        saveLoadPanel.add(searchField);

        JButton startSearchButton = new JButton();
        startSearchButton.setSize(20, 20);
        setIcon("C:\\Users\\lt\\IdeaProjects\\Text Editor\\search.png", startSearchButton);
        startSearchButton.addActionListener(actionEvent -> startSearching());
        startSearchButton.setName("StartSearchButton");
        saveLoadPanel.add(startSearchButton);

        JButton previousButton = new JButton();
        previousButton.setSize(20, 20);
        setIcon("C:\\Users\\lt\\IdeaProjects\\Text Editor\\previous.png", previousButton);
        previousButton.addActionListener(actionEvent -> previous());
        previousButton.setName("PreviousMatchButton");
        saveLoadPanel.add(previousButton);

        JButton nextButton = new JButton();
        nextButton.setSize(20, 20);
        setIcon("C:\\Users\\lt\\IdeaProjects\\Text Editor\\next.png", nextButton);
        nextButton.addActionListener(actionEvent -> next());
        nextButton.setName("NextMatchButton");
        saveLoadPanel.add(nextButton);

        useRegex = new JCheckBox("Use regex");
        useRegex.setName("UseRegExCheckbox");
        saveLoadPanel.add(useRegex);

        jfc = new JFileChooser();
        jfc.setName("FileChooser");
        jfc.setVisible(false);
        saveLoadPanel.add(jfc);

        add(saveLoadPanel, BorderLayout.NORTH);

        addMenu();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setTitle("Text Editor");
        setVisible(true);
        setLayout(null);
    }

    private void setIcon(String file,JButton button){
        ImageIcon ico = new ImageIcon(file);
        ico.getImage();
        Image temp = ico.getImage().getScaledInstance(button.getWidth(),
                button.getHeight(), Image.SCALE_DEFAULT);
        ico = new ImageIcon(temp);
        button.setIcon(ico);
    }

    private void addMenu() {

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        fileMenu.setName("MenuFile");

        JMenuItem openItem = new JMenuItem("Open");
        openItem.setName("MenuOpen");
        openItem.addActionListener(actionEvent -> loadFile());

        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.setName("MenuSave");
        saveItem.addActionListener(actionEvent -> saveFile());

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.setName("MenuExit");
        exitItem.addActionListener(actionEvent -> dispose());

        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        menuBar.add(fileMenu);

        JMenu searchMenu = new JMenu("Search");
        searchMenu.setName("MenuSearch");

        JMenuItem startItem = new JMenuItem("Start search");
        startItem.setName("MenuStartSearch");
        startItem.addActionListener(actionEvent -> startSearching());

        JMenuItem previousItem = new JMenuItem("Previous search");
        previousItem.setName("MenuPreviousMatch");
        previousItem.addActionListener(actionEvent -> previous());

        JMenuItem nextItem = new JMenuItem("Next match");
        nextItem.setName("MenuNextMatch");
        nextItem.addActionListener(actionEvent -> next());

        JMenuItem regexItem = new JMenuItem("Use regular expressions");
        regexItem.setName("MenuUseRegExp");
        regexItem.addActionListener(actionEvent -> useRegex.setSelected(!useRegex.isSelected()));

        searchMenu.add(startItem);
        searchMenu.add(previousItem);
        searchMenu.add(nextItem);
        searchMenu.add(regexItem);

        menuBar.add(searchMenu);

        setJMenuBar(menuBar);

    }

    private void loadFile() {

        jfc = new JFileChooser("C:\\Users\\lt\\IdeaProjects\\Text Editor",
                FileSystemView.getFileSystemView());
        jfc.setName("FileChooser");
        jfc.setDialogTitle("Open file");

        String path = searchField.getText().trim();
        if (!"".equals(path)) {
            jfc.setSelectedFile(new File(path));
        }

        int returnValue = jfc.showOpenDialog(null);
        jfc.setVisible(true);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            if (selectedFile.exists()) {
                long fileLength = selectedFile.length();
                byte[] fileContent = new byte[(int) fileLength];
                try (FileInputStream fis = new FileInputStream(selectedFile)) {
                    fis.read(fileContent);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                StringBuilder sb = new StringBuilder();
                for (byte b : fileContent) {
                    sb.append((char) b);
                }
                jTextArea.setText(sb.toString());
            } else {
                jTextArea.setText("");
            }
        }

    }

    private void saveFile() {

        jfc = new JFileChooser("C:\\Users\\lt\\IdeaProjects\\Text Editor",
                FileSystemView.getFileSystemView());
        jfc.setName("FileChooser");
        jfc.setDialogTitle("Save file");

        String path = searchField.getText().trim();
        if (!"".equals(path)) {
            jfc.setSelectedFile(new File(path));
        }

        int returnValue = jfc.showSaveDialog(null);
        jfc.setVisible(true);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();

            try (PrintWriter printWriter = new PrintWriter(selectedFile)) {
                printWriter.print(jTextArea.getText());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    private void startSearching() {
        searchResult.clear();
        resultLength.clear();

        SearchTask searchTask = new SearchTask();
        searchTask.run();

        currentPosition = 0;
    }

    private void next() {
        if (searchResult.isEmpty()) {
            return;
        }

        currentPosition++;
        if (currentPosition >= searchResult.size()) {
            currentPosition = 0;
        }

        jTextArea.setCaretPosition(searchResult.get(currentPosition) + resultLength.get(currentPosition));
        jTextArea.select(searchResult.get(currentPosition),
                searchResult.get(currentPosition) + resultLength.get(currentPosition));
        jTextArea.grabFocus();
    }

    private void previous() {
        if (searchResult.isEmpty()) {
            return;
        }

        currentPosition--;
        if (currentPosition < 0) {
            currentPosition = searchResult.size() - 1;
        }

        jTextArea.setCaretPosition(searchResult.get(currentPosition) + resultLength.get(currentPosition));
        jTextArea.select(searchResult.get(currentPosition),
                searchResult.get(currentPosition) + resultLength.get(currentPosition));
        jTextArea.grabFocus();
    }

    class SearchTask extends SwingWorker<String, String> {

        @Override
        protected String doInBackground() {
            if (searchField.getText().trim().equals("") || jTextArea.getText().trim().equals("")) {
                return null;
            }

            if (useRegex.isSelected()) {
                Pattern pattern = Pattern.compile(searchField.getText());
                Matcher matcher = pattern.matcher(jTextArea.getText());
                while (matcher.find()) {
                    searchResult.add(matcher.start());
                    resultLength.add(matcher.group().length());
                }
            } else {
                String toFind = searchField.getText();
                String text = jTextArea.getText();
                int position = 0;
                int start = text.indexOf(toFind, position);
                while (start != -1) {
                    searchResult.add(start);
                    resultLength.add(toFind.length());
                    position = start + toFind.length();
                    start = text.indexOf(toFind, position);
                }
            }
            return null;
        }

        @Override
        protected void done() {
            if (!searchResult.isEmpty()) {
                jTextArea.setCaretPosition(searchResult.get(0) + resultLength.get(0));
                jTextArea.select(searchResult.get(0), searchResult.get(0) + resultLength.get(0));
                jTextArea.grabFocus();
            }
        }
    }

}
