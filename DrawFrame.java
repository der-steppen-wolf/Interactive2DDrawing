import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GradientPaint;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DrawFrame extends JFrame
{
	private final JPanel northPanel;
	private final JButton undoButton;
	private final JButton clearButton;
	private final DrawPanel drawPanel;
	private final JLabel label;
	private final JComboBox<String> shapesJComboBox;
	private final String[] shapeNames = {"Line", "Oval", "Rectangle"};
	private final JCheckBox filledJCheckBox;
	private final JCheckBox gradientJCheckBox;
	private final JButton firstColorButton;
	private final JButton secondColorButton;
	private final JTextField lineTextField;
	private final JTextField dashTextField;
	private final JCheckBox dashedJCheckBox;
	private final JButton saveButton;
	private final JButton loadButton;
	private Path filePath;
	private Color color1, color2;
	private static int width = 2;
	private static float []dashes = {1f};
	
	public DrawFrame()
	{
		super("Java 2D Drawings");
		northPanel = new JPanel();
		northPanel.setLayout(new BorderLayout());
		
		JPanel topNorthPanel = new JPanel();
		topNorthPanel.setLayout(new FlowLayout());
		
		undoButton = new JButton("Undo");
		undoButton.setToolTipText("Clear last shape");
		clearButton = new JButton("Clear");
		clearButton.setToolTipText("Clear design");
		topNorthPanel.add(undoButton);
		topNorthPanel.add(clearButton);
		
		JLabel shapeLabel = new JLabel("Shape:");
		topNorthPanel.add(shapeLabel);
		
		shapesJComboBox = new JComboBox<>(shapeNames);
		shapesJComboBox.setMaximumRowCount(3);
		topNorthPanel.add(shapesJComboBox);
		
		filledJCheckBox = new JCheckBox("Filled");
		topNorthPanel.add(filledJCheckBox);
		
		northPanel.add(topNorthPanel, BorderLayout.NORTH);
		
		label = new JLabel("(x,y)");
		add(label, BorderLayout.SOUTH);
		
		color1 = Color.BLACK;
		color2 = Color.BLACK;
		drawPanel = new DrawPanel(label);
		add(drawPanel);
		
		JPanel centerNorthPanel = new JPanel();
		centerNorthPanel.setLayout(new FlowLayout());
		
		gradientJCheckBox = new JCheckBox("Use Gradient");
		centerNorthPanel.add(gradientJCheckBox);
		firstColorButton = new JButton("1st Color");
		centerNorthPanel.add(firstColorButton);
		secondColorButton = new JButton("2nd Color");
		centerNorthPanel.add(secondColorButton);
		JLabel lineLabel = new JLabel("Line Width:");
		centerNorthPanel.add(lineLabel);
		lineTextField = new JTextField("def", 2);
		centerNorthPanel.add(lineTextField);
		JLabel dashLabel = new JLabel("Dash Length:");
		centerNorthPanel.add(dashLabel);
		dashTextField = new JTextField("def", 2);
		centerNorthPanel.add(dashTextField);
		dashedJCheckBox = new JCheckBox("Dashed");
		centerNorthPanel.add(dashedJCheckBox);
		
		northPanel.add(centerNorthPanel);
		
		JPanel bottomNorthPanel = new JPanel();
		bottomNorthPanel.setLayout(new FlowLayout());
		
		saveButton = new JButton("Save");
		bottomNorthPanel.add(saveButton);
		loadButton = new JButton("Load");
		bottomNorthPanel.add(loadButton);
		
		northPanel.add(bottomNorthPanel, BorderLayout.SOUTH);
		add(northPanel, BorderLayout.NORTH);
		
		ButtonHandler buttonHandler = new ButtonHandler();
		undoButton.addActionListener(buttonHandler);
		clearButton.addActionListener(buttonHandler);
		firstColorButton.addActionListener(buttonHandler);
		secondColorButton.addActionListener(buttonHandler);
		saveButton.addActionListener(buttonHandler);
		loadButton.addActionListener(buttonHandler);
		
		shapesJComboBox.addItemListener(
				new ItemListener()
				{
					@Override
					public void itemStateChanged(ItemEvent event)
					{
						if (event.getStateChange() == ItemEvent.SELECTED)
							drawPanel.setShapeType(shapesJComboBox.getSelectedIndex());
					}
				}
			);
		
		checkBoxHandler checkBoxHandler = new checkBoxHandler();
		filledJCheckBox.addItemListener(checkBoxHandler);
		gradientJCheckBox.addItemListener(checkBoxHandler);
		dashedJCheckBox.addItemListener(checkBoxHandler);
		
		TextFieldHandler textFieldHandler = new TextFieldHandler();
		lineTextField.addActionListener(textFieldHandler);
		dashTextField.addActionListener(textFieldHandler);
		
		filePath = null;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1000, 650);
		setVisible(true);
	}
	
	private void validateLineWidth()
	{
		if (lineTextField.getText().matches("\\d+"))
			width = Integer.parseInt(lineTextField.getText());
		else
			width = 2;
	}
	
	private void validateDashLength()
	{
		if(dashTextField.getText().matches("([1-9]\\d*.\\d+)|[1-9]\\d*"))
			dashes[0] = Float.parseFloat(dashTextField.getText());
		else
			dashes[0] = 1f;
	}
	
	 private Path getFilePath(String text)
	 		throws NullPointerException
	 {
		 JFileChooser fileChooser = new JFileChooser();
		 fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		 fileChooser.setDialogTitle(text + " File");
		 fileChooser.setApproveButtonText(text);
		fileChooser.showOpenDialog(northPanel);
		 
		 return  fileChooser.getSelectedFile().toPath() ;
	 }
	
	private class ButtonHandler implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent event)
		{
			if (event.getSource()  == undoButton)
			{
				drawPanel.clearLastShape();
			}
			else if (event.getSource() == clearButton)
			{
				drawPanel.clearDesign();
			}
			else if (event.getSource() == firstColorButton)
			{
				color1 = JColorChooser.showDialog(drawPanel, "Choose 1st Color", color1);
				if (gradientJCheckBox.isSelected())
					drawPanel.setCurrentPaint(new GradientPaintSer(new Point(0, 0), color1, new Point(50, 50), color2, true));
				else
					drawPanel.setCurrentPaint(color1);
			}
			else if (event.getSource() == secondColorButton)
			{
				color2 = JColorChooser.showDialog(drawPanel, "Choose 2nd Color", color2);
				if (gradientJCheckBox.isSelected())
					drawPanel.setCurrentPaint(new GradientPaintSer(new Point(0, 0), color1, new Point(50, 50), color2, true));
			}
			else if (event.getSource() == loadButton)
			{
				try { filePath = getFilePath("Load"); }
				catch (NullPointerException e) { }

				if (filePath != null && Files.exists(filePath))
				{
					try (ObjectInputStream input = new ObjectInputStream(Files.newInputStream(filePath)))
					{
						drawPanel.setShapes((MyShape[]) input.readObject());
						drawPanel.setShapeCount((int) input.readInt());
						drawPanel.repaint();
					}
					catch (IOException e) {
						e.printStackTrace();
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
				else
				{
					if (filePath != null)
						JOptionPane.showMessageDialog(drawPanel, filePath.getFileName() +
							" does not exist in path: " + filePath.getParent(), "ERROR", JOptionPane.ERROR_MESSAGE);
				}
				
				filePath = null;
			}
			else if (event.getSource() == saveButton)
			{	
				try { filePath = getFilePath("Save"); }
				catch (NullPointerException e) { }
				
				if (filePath != null)
				{
					try (ObjectOutputStream output = new ObjectOutputStream(Files.newOutputStream(filePath))){
						output.writeObject(drawPanel.getShapes());
						output.writeInt(drawPanel.getShapeCount());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				filePath = null;
			}
		}
	}
	
	private class checkBoxHandler implements ItemListener
	{	
		@Override
		public void itemStateChanged(ItemEvent event)
		{
			if (filledJCheckBox.isSelected())
				drawPanel.setFilledShape(true);
			else
				drawPanel.setFilledShape(false);
			
			if (gradientJCheckBox.isSelected())
				drawPanel.setCurrentPaint(new GradientPaintSer(new Point(0, 0), color1, new Point(50, 50), color2, true));
			else
				drawPanel.setCurrentPaint(color1);
			
			if (dashedJCheckBox.isSelected())
			{
				validateDashLength();
				drawPanel.setCurrentStroke(new BasicStrokeSer(width, BasicStroke.CAP_ROUND,
						BasicStroke.JOIN_ROUND, 10, dashes, 0));
			}
			else
				drawPanel.setCurrentStroke(new BasicStrokeSer(width, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		}
	}
	
	private class TextFieldHandler implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent event)
		{
			validateLineWidth();
			validateDashLength();
				
			if (dashedJCheckBox.isSelected())
			{
				drawPanel.setCurrentStroke(new BasicStrokeSer(width, BasicStroke.CAP_ROUND,
							BasicStroke.JOIN_ROUND, 10, dashes, 0));
			}
			else
				drawPanel.setCurrentStroke(new BasicStrokeSer(width, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		}
	}
}
