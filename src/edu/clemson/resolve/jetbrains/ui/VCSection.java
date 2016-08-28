package edu.clemson.resolve.jetbrains.ui;

import com.intellij.util.ui.AnimatedIcon;
import edu.clemson.resolve.jetbrains.RESOLVEIcons;
import edu.clemson.resolve.jetbrains.verifier.VerificationEditorPreview;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Adapted from code provided by Oliver Watkins:
 * See <a href="https://github.com/oliverwatkins/swing_library">Oliver's swing component library</a>.
 */
public class VCSection extends JPanel {

	private static final int FRAMES_COUNT = 8;
	public static final Icon[] FRAMES = new Icon[FRAMES_COUNT];

	static {
		FRAMES[0] = RESOLVEIcons.PROCESSING1;
		FRAMES[1] = RESOLVEIcons.PROCESSING2;
		FRAMES[2] = RESOLVEIcons.PROCESSING3;
		FRAMES[3] = RESOLVEIcons.PROCESSING4;
		FRAMES[4] = RESOLVEIcons.PROCESSING5;
		FRAMES[5] = RESOLVEIcons.PROCESSING6;
		FRAMES[6] = RESOLVEIcons.PROCESSING7;
		FRAMES[7] = RESOLVEIcons.PROCESSING8;
	}

    public static enum State {
		PROVED {
			@Override
			public Icon getIcon() {
				return RESOLVEIcons.PROVED;
			}
		},
		NOT_PROVED {
			@Override
			public Icon getIcon() {
				return RESOLVEIcons.NOT_PROVED;
			}
		},
		TIMED_OUT {
			@Override
			public Icon getIcon() {
				return RESOLVEIcons.TIMED_OUT;
			}
		},
        PROCESSING {
            @Override
            //Shouldn't get called. TODO: Would be nice if I could figure out how to get this returning an intellij AnimatedIcon
            public Icon getIcon() {
                return RESOLVEIcons.TIMED_OUT;
            }
        };

		public abstract Icon getIcon();
    }

	private static final long serialVersionUID = 1L;
	
	public int minComponentHeight = 40;
	public int minComponentWidth = 350;
	
	public JComponent titlePanel;
	private SideBar sideBarOwner;
	public JComponent previewEditor; //the information about the vc
	
	private ArrowPanel arrowPanel;

	private int calculatedHeight;
    public JLabel vcNameLabel;
    public String name;
    public State state;
	public AnimatedIcon processingSpinner;

	int numOfLines = 7;

    public VCSection(SideBar owner, String name, JComponent preview) {
        this(owner, name, preview, State.PROCESSING);
    }

    //Starting constructor.
    public VCSection(SideBar owner, String name, JComponent preview, State activeState) {
        this.sideBarOwner = owner;
        this.name = name;
		this.processingSpinner = new AnimatedIcon("processing", FRAMES, FRAMES[0], 800);
        this.previewEditor = preview;
		if (owner.thisMode == SideBar.SideBarMode.INNER_LEVEL) {
			minComponentHeight = 30;
		}
		else {
			minComponentHeight = 40;
		}
		titlePanel = new JPanel();
		titlePanel.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {

				if (VCSection.this != sideBarOwner.getCurrentSection()) {
					if (sideBarOwner.getCurrentSection() != null) {
						sideBarOwner.getCurrentSection().collapse(true);
					}
					expand(); //expand this!
				}
				else {
					collapse(true);
				}
			}
		});
		setLayout(new BorderLayout());
		add(titlePanel, BorderLayout.NORTH);

		titlePanel.setLayout(new BorderLayout());
		titlePanel.setPreferredSize(new Dimension(this.getPreferredSize().width, minComponentHeight));
		titlePanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));

        this.vcNameLabel = new JLabel("VC " + name);
        vcNameLabel.setBorder(new CompoundBorder(BorderFactory.createEmptyBorder(2, 8, 2, 2), vcNameLabel.getBorder()));
        titlePanel.add(vcNameLabel);

        if (activeState == State.PROCESSING) {
            processingSpinner.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 10));
            titlePanel.add(processingSpinner, BorderLayout.WEST);
        }
        else {
            vcNameLabel.setIcon(activeState.getIcon());
        }
        arrowPanel = new ArrowPanel(BasicArrowButton.EAST);
        arrowPanel.setPreferredSize(new Dimension(40, 40));

        if (sideBarOwner.showArrow) {
            //add into tab panel the arrow and labels.
            titlePanel.add(arrowPanel, BorderLayout.EAST);
        }
		add(preview, BorderLayout.CENTER);
	}

	public void changeToFinalState(State e) {
	    this.state = e;
        titlePanel.remove(processingSpinner);
        vcNameLabel.setIcon(state.getIcon());
    }

    public JComponent getTitlePanel() {
        return titlePanel;
    }

	public void expand() {
		sideBarOwner.setCurrentSection(this);
		
		arrowPanel.changeDirection(BasicArrowButton.SOUTH);
		arrowPanel.updateUI();
		
		calculatedHeight = -1;
		calculatedHeight = previewEditor.getHeight();
		
		System.out.println("sidebarSection.previewEditor.getHeight() " + previewEditor.getHeight());
		
		/*if (this.sideBarOwner.animate) {
			SidebarAnimation anim = new SidebarAnimation(this, 200); // ANIMATION BIT
			
			anim.setStartValue(minComponentHeight);
			anim.setEndValue(calculatedHeight);
			anim.start();
		} else {*/
			if (sideBarOwner.thisMode == SideBar.SideBarMode.INNER_LEVEL) {
				calculatedHeight = 1000;
				Dimension d = new Dimension(Integer.MAX_VALUE, calculatedHeight);
				setMaximumSize(d);
				sideBarOwner.setPreferredSize(d);
				previewEditor.setVisible(true);
				revalidate();
			} else {
				setMaximumSize(new Dimension(Integer.MAX_VALUE, calculatedHeight));
				previewEditor.setVisible(true);
				revalidate();
			}

//			printDimensions();
		//}
	}
	
	
	
	public void collapse(boolean animate) {
		// remove reference
		if (sideBarOwner.getCurrentSection() == VCSection.this)
			sideBarOwner.setCurrentSection(null);
		
		arrowPanel.changeDirection(BasicArrowButton.EAST);
		arrowPanel.updateUI();
		
		/*if (animate && this.sideBarOwner.animate) {
			SidebarAnimation anim = new SidebarAnimation(this, 200); // ANIMATION BIT
			anim.setStartValue(calculatedHeight);
			anim.setEndValue(minComponentHeight);
			anim.start();
		} else {*/
			setMaximumSize(new Dimension(Integer.MAX_VALUE, titlePanel.getPreferredSize().height));
			previewEditor.setVisible(false);
			revalidate();
//				printDimensions();
		//}
	}
	
	@Override
	public Dimension getMinimumSize(){
		return new Dimension(minComponentWidth,minComponentHeight);
	}
	
	@Override
	public Dimension getPreferredSize(){
		return new Dimension(minComponentWidth,minComponentHeight);
	}
	
	public void printDimensions() {
		System.out.println("-- DIMENSIONS -- ");
		
		System.out.println("sideBar height                     " + this.sideBarOwner.getSize().height);

		System.out.println("sideBarSection height              " + getSize().height);
		System.out.println("sideBarSection titlePanel height   " + titlePanel.getSize().height);
		System.out.println("sideBarSection.previewEditor height  " + previewEditor.getSize().height);
	}
	
}