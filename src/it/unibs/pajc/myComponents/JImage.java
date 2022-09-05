package it.unibs.pajc.myComponents;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.GrayFilter;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class JImage extends JLabel {
  private static final long serialVersionUID = 1L;

  private Image image;
  private Image disabledImage;
  private Image hoveredImage;
  private Image clickedImage;
  private final Map<Image, Image> scaledInstances = new HashMap<Image, Image>();

  private boolean enabled = true;
  private boolean hovered;
  private boolean clicked;

  private final List<ActionListener> actionListeners = new ArrayList<ActionListener>();

  private boolean isCursorSet = false;
  private boolean mouseListenersInitialized = false;

  public JImage(Icon icon) {
    this(toImage(icon));
  }

  public JImage(Image image) {
    this.image = image;
    addFocusListener(new FocusListener() {

      @Override
      public void focusLost(FocusEvent e) {
        repaint();
      }

      @Override
      public void focusGained(FocusEvent e) {
        repaint();
      }
    });
    addKeyListener(new KeyAdapter() {

      @Override
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
          if (!actionListeners.isEmpty()) {
            doClick();
          }
        }
      }
    });
  }

  private void initMouseListeners() {
    addMouseListener(new MouseAdapter() {

      @Override
      public void mouseReleased(MouseEvent e) {
        clicked = false;
        repaint();
      }

      @Override
      public void mousePressed(MouseEvent e) {
        clicked = true;
        repaint();
      }

      @Override
      public void mouseExited(MouseEvent e) {
        hovered = false;
        repaint();
      }

      @Override
      public void mouseEntered(MouseEvent e) {
        hovered = true;
        repaint();
      }

      @Override
      public void mouseClicked(MouseEvent e) {
        doClick(e.getButton());
      }
    });

    mouseListenersInitialized = true;
  }

  public void doClick() {
    doClick(MouseEvent.BUTTON1);
  }

  public void doClick(int mouseButton) {
    if (isEnabled() && mouseButton == MouseEvent.BUTTON1) {
      for (ActionListener actionListener : getActionListeners())
        actionListener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "mouseClicked"));
    }
  }

  @Override
  public Dimension getPreferredSize() {
    if (super.isPreferredSizeSet()) {
      return super.getPreferredSize();
    } else if (getImage() != null) {
      int height = getFontMetrics(getFont()).getHeight();
      double imageRatio = (double) getImage().getWidth(null) / getImage().getHeight(null);
      int width = (int) (height * imageRatio);
      return new Dimension(width, height);
    } else
      return new Dimension(0, 0);
  }

  @Override
  public Dimension getMinimumSize() {
    return getPreferredSize();
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.setCursor(getCursor());
    super.paintComponent(g);

    Image image;
    if (!isEnabled() && getDisabledImage() != null)
      image = getDisabledImage();
    else if (clicked && getClickedImage() != null)
      image = getClickedImage();
    else if ((hovered || isFocusOwner()) && getHoveredImage() != null)
      image = getHoveredImage();
    else
      image = this.getImage();

    if (image != null) {
      if (image.getWidth(null) == 0 || image.getHeight(null) == 0)
        System.out.println("not yet loaded");
      Insets insets;
      if (getBorder() == null) {
        insets = new Insets(0, 0, 0, 0);
      } else {
        insets = getBorder().getBorderInsets(this);
      }
      int width = getWidth() - insets.right - insets.left;
      int height = getHeight() - insets.bottom - insets.top;
      double imageRatio = (double) image.getWidth(null) / image.getHeight(null);
      double containerRatio = (double) width / height;
      if (imageRatio > containerRatio) {
        height = (int) (width / imageRatio);
      } else {
        width = (int) (height * imageRatio);
      }
      int x = (getWidth() - width) / 2;
      int y = (getHeight() - height) / 2;

      Image scaled = scaledInstances.get(image);
      if (scaled == null || scaled.getWidth(null) != width || scaled.getHeight(null) != height) {
        scaled = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        scaledInstances.put(image, scaled);
      }

      g.drawImage(scaled, x, y, null);
    }
  }

  public void scalePreferredSize(float scale) {
    Dimension prefSize = getPreferredSize();
    prefSize.width *= scale;
    prefSize.height *= scale;
    setPreferredSize(prefSize);
  }

  /**
   * Convenience method to use JImages as Buttons
   * <br/>Adds a click listener.
   * <br/>Also changes the cursor to a hand cursor when on top of this component.
   * <br/>To revert this, call {@link #setCursor(java.awt.Cursor)}
   * @param actionListener the listener to be called on click
   */
  public void addActionListener(final ActionListener actionListener) {
    actionListeners.add(actionListener);
    if (!mouseListenersInitialized)
      initMouseListeners();
    setFocusable(true);
  }

  protected List<ActionListener> getActionListeners() {
    return actionListeners;
  }

  public void generateStateImages() {
    generateStateImages(true, true, true);
  }

  public void generateStateImages(boolean disabled, boolean hovered, boolean clicked) {
    if (image == null)
      throw new IllegalStateException("Cannot generate state images while default image is null.");
    if (disabled) {
      this.disabledImage = grayScale(image);
    }
    if (hovered) {
      this.hoveredImage = changeBrightness(image, 1.2f);
    }
    if (clicked) {
      this.clickedImage = changeBrightness(image, 0.8f);
    }
    if (!mouseListenersInitialized)
      initMouseListeners();
  }

  public Image getImage() {
    return image;
  }

  public void setImage(Image image) {
    this.image = image;
    repaint();
  }

  public Image getDisabledImage() {
    return disabledImage;
  }

  public void setDisabledImage(Image disabledImage) {
    this.disabledImage = disabledImage;
    if (!mouseListenersInitialized && disabledImage != null)
      initMouseListeners();
    repaint();
  }

  public Image getHoveredImage() {
    return hoveredImage;
  }

  public void setHoveredImage(Image hoveredImage) {
    this.hoveredImage = hoveredImage;
    if (!mouseListenersInitialized && hoveredImage != null)
      initMouseListeners();
    repaint();
  }

  public Image getClickedImage() {
    return clickedImage;
  }

  public void setClickedImage(Image clickedImage) {
    this.clickedImage = clickedImage;
    if (!mouseListenersInitialized && clickedImage != null)
      initMouseListeners();
    repaint();
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
    repaint();
  }

  public boolean isHovered() {
    return hovered;
  }

  public boolean isClicked() {
    return clicked;
  }

  @Override
  public Cursor getCursor() {
    if (!isCursorSet && !actionListeners.isEmpty() && isEnabled())
      return Cursor.getPredefinedCursor(isEnabled() ? Cursor.HAND_CURSOR : Cursor.DEFAULT_CURSOR);
    else
      return super.getCursor();
  }

  @Override
  public void setCursor(Cursor cursor) {
    super.setCursor(cursor);
    isCursorSet = true;
  }

  /**
   * @return a copy that will update its image when the original its updated.
   */
  public JImage createDelegate() {
    final JImage thisImage = this;
    JImage delegate = new JImage(getImage()) {

      public boolean isEnabled() {
        return thisImage.isEnabled();
      }

      public List<ActionListener> getActionListeners() {
        List<ActionListener> actionListeners = new ArrayList<ActionListener>(thisImage.getActionListeners());
        actionListeners.addAll(super.getActionListeners());
        return actionListeners;
      }

      public Image getImage() {
        return thisImage.getImage();
      }

      public Image getDisabledImage() {
        return thisImage.getDisabledImage();
      }

      public Image getHoveredImage() {
        return thisImage.getHoveredImage();
      }

      public Image getClickedImage() {
        return thisImage.getClickedImage();
      }
    };

    return delegate;
  }

  // Extracted from my image utilities
  private static Image grayScale(Image image) {
    ImageFilter filter = new GrayFilter(true, 50);
    ImageProducer producer = new FilteredImageSource(image.getSource(), filter);
    return Toolkit.getDefaultToolkit().createImage(producer);
  }

  // Extracted from my image utilities
  private static Image changeBrightness(Image image, float mult) {
    BufferedImage newImage = new BufferedImage(image.getWidth(null), image.getHeight(null),
        BufferedImage.TYPE_INT_ARGB);
    newImage.getGraphics().drawImage(image, 0, 0, null);

    WritableRaster wr = newImage.getRaster();
    int[] pixel = new int[4];
    int[] overflows = new int[3];

    for (int i = 0; i < wr.getWidth(); i++) {
      for (int j = 0; j < wr.getHeight(); j++) {
        wr.getPixel(i, j, pixel);
        pixel[0] = (int) (pixel[0] * mult);
        pixel[1] = (int) (pixel[1] * mult);
        pixel[2] = (int) (pixel[2] * mult);

        // if a value exceeds 255, the other color values will instead be raised to bleach the image
        overflows[0] = 0;
        overflows[1] = 0;
        overflows[2] = 0;
        if (pixel[0] > 255) {
          overflows[1] += pixel[0] - 255;
          overflows[2] += pixel[0] - 255;
          pixel[0] = 255;
        }
        if (pixel[1] > 255) {
          pixel[0] += pixel[1] - 255;
          pixel[2] += pixel[1] - 255;
          pixel[1] = 255;
        }
        if (pixel[2] > 255) {
          pixel[0] += pixel[2] - 255;
          pixel[1] += pixel[2] - 255;
          pixel[2] = 255;
        }
        pixel[0] = Math.min(255, pixel[0] + overflows[0]);
        pixel[1] = Math.min(255, pixel[1] + overflows[1]);
        pixel[2] = Math.min(255, pixel[2] + overflows[2]);

        wr.setPixel(i, j, pixel);
      }
    }

    return newImage;
  }

  // Extracted from my image utilities
  private static Image toImage(Icon icon) {
    if (icon instanceof ImageIcon) {
      return ((ImageIcon) icon).getImage();
    } else {
      int w = icon.getIconWidth();
      int h = icon.getIconHeight();
      BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
      Graphics g = image.getGraphics();
      icon.paintIcon(null, g, 0, 0);
      g.dispose();
      return image;
    }
  }

}